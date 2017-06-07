
#include "cuda_runtime.h"
#include "device_launch_parameters.h"

#include <stdio.h>
#include "util.h"

using namespace std;

#define chunk 256


void clockStart();	
void clockStop(const char * str);


float sum_matrix(const float *M,  int width);
void print_matrix(const float *M,  int width);
void clean_matrix(float *M,  int width);
void init_matrix(float *M,  int width);

//Kernel

// Suma por columnas de una matriz con un solo bloque
__global__ void MatrixSumKernel_0(int M, float* A_dev, float* SumPar_dev){

	// Pvalue es usado para el valor intermedio
	double Pvalue = 0;
  
	int offset = threadIdx.y * M;
  
	for (int k = 0; k < M; k++) {
		Pvalue = Pvalue + A_dev[offset+k];
	}
	
	SumPar_dev[threadIdx.y] = Pvalue;

}

__global__ void MatrixSumKernel_1(int M, float* A_dev, float* SumPar_dev){
	// Pvalue es usado para el valor intermedio
	float Pvalue = 0;
  
	int columna = blockIdx.x;
	int fCol = columna * gridDim.x;
  
	for (int k = 0; k < M; k++) {
		Pvalue = Pvalue + A_dev[fCol+k];
	}
	
	SumPar_dev[blockIdx.x] = Pvalue;
}

__global__ void MatrixSumKernel_2(int M,float* A_dev, float* SumPar_dev){

	float parcial = 0;
	int nCol = blockIdx.y;
	int fCol = nCol * gridDim.y;
	
	int pasos = M/ blockDim.x;
	int step = fCol + threadIdx.x*pasos;

	if(threadIdx.x<M){
		for (int k = 0; k < pasos; ++k) {
			parcial = parcial + A_dev[step + k];
		}
		atomicAdd(&(SumPar_dev[nCol]), parcial);
	}
}

__global__ void MatrixSumKernel_3(int M,float* A_dev, float* SumPar_dev){

	int pasos;	
	if(blockDim.x>M){
		pasos = 1;
	}else{
		pasos = M / blockDim.x;
	}	
		
	float parcial = 0;
	int nCol = blockIdx.y;
	int fCol = nCol * gridDim.y;
	int step = fCol + threadIdx.x;
	
	if(threadIdx.x<M){
		for (int k = 0; k < pasos; ++k) {
			parcial = parcial + A_dev[step + k*blockDim.x];
		}
		atomicAdd(&(SumPar_dev[nCol]), parcial);
	}
}

__global__ void MatrixSumKernel_4(int M,float* A_dev, float* SumPar_dev){
	
	// Cant Filas por bloque
	int rowXblock = M / gridDim.y;
	
	// Cant Columnas por bloque
	int colXblock = M / gridDim.x;
	
	//Cant Columnas que le conrresponden a un thread
	int colXthread = colXblock / blockDim.x ;	
	
	
	float parcial;
	int nCol;
	int paso;
	
	for (int j = 1; j <= colXthread; ++j) {
		parcial = 0;
		nCol = blockIdx.x * colXblock + threadIdx.x * j;
		paso = nCol * M + rowXblock * blockIdx.y;
		
		for (int k = 0; k < rowXblock; ++k) {
			parcial = parcial + A_dev[paso + k];
		}
		atomicAdd(&(SumPar_dev[nCol]), parcial);
	}
}

__global__ void MatrixSumKernel_5(int M,float* A_dev, float* SumPar_dev){
	
	// Cant Filas por bloque
	int rowXblock = M / gridDim.y;
	
	// Cant Columnas por bloque
	int colXblock = M / gridDim.x;
	
	//Cant Filas que le conrresponden a un thread
	int rowXthread = rowXblock / blockDim.x ;	
	
	float parcial;
	int nCol;
	int paso;
	
	for (int j = 0; j < colXblock; ++j) {
		parcial = 0;
		nCol = blockIdx.x * colXblock + j;
		paso = nCol * M + rowXblock * blockIdx.y;
		
		//For que suma la columna
		for (int k = 0; k < rowXthread; ++k) {
			int step = paso + threadIdx.x + k * blockDim.x;
			parcial = parcial + A_dev[step];
		}
		atomicAdd(&(SumPar_dev[nCol]), parcial);
	}
}

__global__ void MatrixSumKernel_6(int M,float* A_dev, float* SumPar_dev){
	
	extern __shared__ float Nds[];
	
	int pasos;	
	if(blockDim.x>M){
		pasos = 1;
	}else{
		pasos = M / blockDim.x;
	}	
		
	float parcial = 0;
	int nCol = blockIdx.y;
	int fCol = nCol * gridDim.y;
	int step = fCol + threadIdx.x;
	
	if(threadIdx.x<M){
		for (int k = 0; k < pasos; ++k) {
			parcial = parcial + A_dev[step + k*blockDim.x];
		}
	}else{
		parcial = 0;
	}
	Nds[threadIdx.x] = parcial;
	
	__syncthreads(); 
	
	if (threadIdx.x == 0){
		for (int i = 1; i < blockDim.x; ++i) {
			Nds[0] = Nds[0]+Nds[i];
		}
	SumPar_dev[nCol] = Nds[0];
	}
}

//extern "C" 
float sumaColMatriz(int M, int N, float * A_hst, int algoritmo){

	size_t size = M * N * sizeof(float);
	size_t size2 = N*sizeof(float);

	float* A_dev, *SumPar_dev;

	float *SumPar_hst = (float *)malloc(N*sizeof(float));

	// Allocate en device 
	cudaMalloc(&A_dev, size);
	cudaMalloc(&SumPar_dev, size2);

	// Inicializo matrices en el device
	//clockStart();
	cudaMemcpy(A_dev, A_hst, size, cudaMemcpyHostToDevice);
	cudaMemset(SumPar_dev,0, size2);
	//clockStop("transf CPU -> GPU");

	clockStart();

	switch(algoritmo){
		case 0:{
			//Configurar la grilla
			dim3 tamGrid (1, 1); //Grid dimensión
			dim3 tamBlock(1, N); //Block dimensión

			MatrixSumKernel_0<<<tamGrid, tamBlock>>>(M, A_dev, SumPar_dev);

			cudaDeviceSynchronize();
			clockStop("kernel 0");
			
			break;
		}case 1:{
			//Configurar la grilla
			dim3 tamGrid (N, 1); //Grid dimensión
			dim3 tamBlock(1, 1); //Block dimensión

			MatrixSumKernel_1<<<tamGrid, tamBlock>>>(M, A_dev, SumPar_dev);
			cudaDeviceSynchronize();
			clockStop("kernel 1");
			
			break;
		}case 2:{
			// configuración de la ejecución
			int chunk2 = 32;
			//Configurar la grilla
			dim3 tamGrid (1, N); //Grid dimensión
			dim3 tamBlock(chunk2,1, 1); //Block dimensión

			MatrixSumKernel_2<<<tamGrid, tamBlock>>>(M, A_dev, SumPar_dev);
			cudaDeviceSynchronize();
			clockStop("kernel 2");
			
			break;
		}case 3:{
			// configuración de la ejecución
			int chunk2 = 32;
			dim3 tamGrid(1, N); //Grid dimensión
			dim3 tamBlock(chunk2,1,1); //Block dimensión
			// lanzamiento del kernel
			MatrixSumKernel_3<<<tamGrid, tamBlock>>>(M, A_dev, SumPar_dev);
			cudaDeviceSynchronize();
			clockStop("kernel 3");
			
			break;
		}case 4:{
			// configuración de la ejecución
			int chunk2 = 16;
			dim3 tamGrid(N/chunk2, N/chunk2); //Grid dimensión
			dim3 tamBlock(chunk2,1,1); //Block dimensión
			// lanzamiento del kernel
			MatrixSumKernel_4<<<tamGrid, tamBlock>>>(M, A_dev, SumPar_dev);
			cudaDeviceSynchronize();
			clockStop("kernel 4");
	
			break;
		}case 5:{
			// configuración de la ejecución
			int chunk2 = 16;
			dim3 tamGrid(N/chunk2, N/chunk2); //Grid dimensión
			dim3 tamBlock(chunk2,1,1); //Block dimensión
			// lanzamiento del kernel
			MatrixSumKernel_5<<<tamGrid, tamBlock>>>(M, A_dev, SumPar_dev);
			cudaDeviceSynchronize();
			clockStop("kernel 5");
	
			break;
		}case 6:{
			// configuración de la ejecución
			int chunk2 = 32;
			dim3 tamGrid(1, N); //Grid dimensión
			dim3 tamBlock(chunk2,1,1); //Block dimensión
			// lanzamiento del kernel
			MatrixSumKernel_6<<<tamGrid, tamBlock,chunk2>>>(M, A_dev, SumPar_dev);
			cudaDeviceSynchronize();
			clockStop("kernel 6");
	
			break;
		}
	}

	// Traer resultado;
	//clockStart();
	cudaMemcpy(SumPar_hst, SumPar_dev, size2, cudaMemcpyDeviceToHost);
	//clockStop("transf CPU <- GPU");

	// Sumar el vector de resultados parciales;
	float total = 0.0;
	for (int i = 0; i<N ; i++) total += SumPar_hst[i];

	free(SumPar_hst);
	// Free matrices en device
	cudaFree(A_dev); cudaFree(SumPar_dev); 

	return total;
}

float ejecutarCPU(float * A, int N){
	clockStart();
	float result_ref = sum_matrix(A,N);
	clockStop("CPU");	
	return result_ref;
}


int main(int argc, char** argv){
	
	int n;
	int algo;
	
	float *A;
	
	if (argc < 3){
		if (argc < 2){
			printf("Sin Parametros, asume tamaño 1024\n");
			n=1024;
		}else{
			n= atoi(argv[1]);
		}
		
		A = (float *)malloc(n*n*sizeof(float));
		
		ejecutarCPU(A,n);
		sumaColMatriz(n,n,A,0);
		sumaColMatriz(n,n,A,1);
		sumaColMatriz(n,n,A,2);
		sumaColMatriz(n,n,A,3);
		sumaColMatriz(n,n,A,4);
		sumaColMatriz(n,n,A,5);
		sumaColMatriz(n,n,A,6);
		exit(0);
	}
	
	n= atoi(argv[1]);
	algo = atoi(argv[2]);
	
	A = (float *)malloc(n*n*sizeof(float));
	init_matrix(A,n);
	float result_ref = ejecutarCPU(A,n);
	float result_gpu = sumaColMatriz(n,n,A,algo);
	
	if (result_gpu == result_ref){
		printf("\n\nResultado OK!! :)\n\n\n");
	}else{
		printf("\nSegui participando\n\n");
	}
	printf("GPU -> %f \n",result_gpu);
	printf("CPU -> %f \n\n",result_ref);
	free(A);	

	return 0;
}


