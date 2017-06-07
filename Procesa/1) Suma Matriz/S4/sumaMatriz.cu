#include "util.h"

#include "cuda.h"
#include "cuda_runtime.h"
#include "device_launch_parameters.h"


using namespace std;

#define TILE_WIDTH 16


void clockStart();	
void clockStop(const char * str);

unsigned long long int sum_matrix(const unsigned long long int *M, int width);
void print_matrix(const unsigned long long int *M, int width);
void clean_matrix(unsigned long long int *M, int width);
void init_matrix(unsigned long long int *M, int width);

//Kernel

// Suma por columnas de una matriz con un solo bloque
__global__ void MatrixSumKernel_1(int M, unsigned long long int* Md, unsigned long long int* Nd){

// Pvalue es usado para el valor intermedio
  unsigned long long int Pvalue = 0;
  
  int offset = threadIdx.y * M;
  
  for (int k = 0; k < M; k++) {
     Pvalue = Pvalue + Md[offset+k];
  }

  Nd[threadIdx.y] = Pvalue;

}

// Fila de bloques (cada bloque suma varias columnas) - cada trhead suma una columna entera
__global__ void MatrixSumKernel_2(int M, unsigned long long int* Md, unsigned long long int* Nd){

	// calcular id global
	// sumar columna
}

// Un bloque suma varias columnas - cada thread suma parte de la columna, utilizar atomicAdd para sumar
__global__ void MatrixSumKernel_3(int M,int N,unsigned long long int* Md, unsigned long long int* Nd){

	// Pvalue es usado para el valor intermedio
	unsigned long long int Pvalue = 0;
	
	// calcular id global
	int col = blockIdx.x * blockDim.x + threadIdx.x;
	
	int pos = blockIdx.y * (N/gridDim.y);
		
	int pasos = N/gridDim.y;
	
	// Pvalue = sumar(...)
	for (int k = 0 ;k<pasos;k++){
		Pvalue = Pvalue + Md[ col*M + pos + k];
	}

	atomicAdd(&(Nd[0]), Pvalue);
}

//extern "C" 
unsigned long long int sumaColMatriz(int M, int N, unsigned long long int * Mh, int algoritmo){


	switch(algoritmo){
		case 1:
		{
			size_t size = M * N * sizeof(unsigned long long int);
			size_t size2 = N*sizeof(unsigned long long int);

			unsigned long long int* Md, *Nd;

			unsigned long long int *Nh = (unsigned long long int *)malloc(N*sizeof(unsigned long long int));

			// Allocate en device 
			cudaMalloc(&Md, size);
			cudaMalloc(&Nd, size2);

			// Inicializo matrices en el device
			cudaMemcpy(Md, Mh, size, cudaMemcpyHostToDevice);
			cudaMemset(Nd,0, size2);

			//Configurar la grilla
			dim3 tamGrid (1, 1); //Grid dimensión
			dim3 tamBlock(1, N); //Block dimensión

			MatrixSumKernel_1<<<tamGrid, tamBlock>>>(M, Md, Nd);

			// Traer resultado;
			cudaMemcpy(Nh, Nd, size2, cudaMemcpyDeviceToHost);

			// Sumar el vector de resultados parciales;
			unsigned long long int total = 0.0;
			for (int i = 0; i<N ; i++) total += Nh[i];

			// Free matrices en device
			cudaFree(Md); cudaFree(Nd); 

			return total;

		}
		case 2:
		{
			// Fila de bloques (cada bloque suma varias columnas) - cada trhead suma una columna entera
			printf("\n\nNo implementado aún!! :)\n\n\n");

			//...
			break;
		}		
		case 3:
		{
			// Un bloque por columna - cada thread suma parte de la columna, utilizar atomicAdd para sumar
			size_t size = M * N * sizeof(unsigned long long int);
			size_t size2 = N*sizeof(unsigned long long int);

			unsigned long long int* Md, *Nd;

			unsigned long long int *Nh = (unsigned long long int *)malloc(N*sizeof(unsigned long long int));

			// Allocate en device 
			cudaMalloc(&Md, size);
			cudaMalloc(&Nd, size2);

			// Inicializo matrices en el device
			cudaMemcpy(Md, Mh, size, cudaMemcpyHostToDevice);
			cudaMemset(Nd,0, size2);

			
			//Configurar la grilla
			dim3 tamGrid (N/1024, 32); //Grid dimensión
			dim3 tamBlock(1024, 1); //Block dimensión
			clockStart();
			MatrixSumKernel_3<<<tamGrid, tamBlock>>>(M,N, Md, Nd);
			clockStop("GPU Sin Transferencia");
			// Traer resultado;
			cudaMemcpy(Nh, Nd, size2, cudaMemcpyDeviceToHost);

			// Sumar el vector de resultados parciales;
			unsigned long long int total = 0.0;
			for (int i = 0; i<N ; i++) total += Nh[i];

			// Free matrices en device
			cudaFree(Md); cudaFree(Nd); 

			return total;
			

			//...
			break;
		}
	}

}


int main(int argc, char** argv){

	if (argc < 3){
		printf("Uso:\nMatSum n algo(1:3)");
		exit(0);
	}
	
	int n= atoi(argv[1]);
	int algo = atoi(argv[2]);	
	
	unsigned long long int *A = (unsigned long long int *)malloc(n*n*sizeof(unsigned long long int));

	init_matrix(A,n);

	clockStart();
	unsigned long long int result_ref = sum_matrix(A,n);
	clockStop("CPU");
	printf("algo - %i \n",algo);
	clockStart();
	unsigned long long int result_gpu = sumaColMatriz(n,n,A,algo);
	clockStop("GPU");
	if (result_gpu == result_ref)
		printf("\n\nResultado OK!! :)\n\n\n");
	else
		printf("\n\Segui participando\n\n\n");

	free(A);	
	


	return 0;
}


