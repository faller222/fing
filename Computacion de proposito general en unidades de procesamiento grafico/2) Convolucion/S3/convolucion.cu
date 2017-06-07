#include <cuda.h>
#include <stdio.h>
#include <stdlib.h>
#include "util.h"

#define CHUNK 1024
//#define SIZE_X 4194304
#define SIZE_X 1048576
#define MASK_SIZE 21
#define PARTE 7

#if (PARTE == 3)||(PARTE == 7)
//######### PARA MEMORIA CONSTANTE ######### 
__constant__ int mask_constant[MASK_SIZE];
#endif

void cudaCheck(){
	cudaError_t cudaError;
	cudaError = cudaGetLastError();
	if(cudaError != cudaSuccess)  {
		printf("  cudaGetLastError() returned %d: %s\n", cudaError, cudaGetErrorString(cudaError));
	}else{
		//printf("  todo ok\n" );
	}
}


#if PARTE == 2
__global__ void Kernel_Convolucion_Simple(int * inputArray, int* outputArray, int* mask){
	
	int radio = MASK_SIZE / 2;
	int parcial = 0;
	int indx = threadIdx.x + blockIdx.x * blockDim.x;

	int auxI= indx - radio;
	
	for (int i=0;i<MASK_SIZE;i++){
		if ((auxI >= 0) && (auxI < SIZE_X)){
			 parcial +=  inputArray[auxI] * mask[i];	
		}
		auxI++;
	}
	atomicAdd(&outputArray[indx],parcial);
}
#endif

#if PARTE == 3
__global__ void Kernel_Convolucion_Constante(int * inputArray, int* outputArray){
	
	int radio = MASK_SIZE / 2;
	int parcial = 0;	
	int indx = threadIdx.x + blockIdx.x * blockDim.x;
	for (int i=0;i<MASK_SIZE;i++){
		if (((indx + i - radio) >= 0) && ((indx + i - radio) < SIZE_X)){
			 parcial = parcial + inputArray[indx + i - radio] * mask_constant[i];	
		}
	}
	atomicAdd(&outputArray[indx],parcial);
}
#endif

#if PARTE == 4
__global__ void Kernel_Convolucion_Shared(int * inputArray, int* outputArray, int* mask){
	__shared__ int compartida[CHUNK + MASK_SIZE - 1];
	
	int indx = threadIdx.x + blockIdx.x * blockDim.x;
	int radio = MASK_SIZE / 2;
	
	if(threadIdx.x==0){//si estoy en el primer hilo del bloque
		if(indx == 0){//en el primer bloque
			for(int j=0;j<radio;j++){
				compartida[j]=0;//completo con 0
			}
		}else{//si no es el primero
			for(int j=0;j<radio;j++){
				compartida[j]=inputArray[indx-radio+j];//completo con vecinos
			}
		}
	}else if(threadIdx.x==CHUNK-1){//si estoy en el ultimo hilo del bloque
		if (indx==SIZE_X-1){//en el ultimo bloque
			for(int j=1;j<=radio;j++){
				compartida[threadIdx.x+j+radio]=0;//completo con 0
			}
		}else{//si no es el ultimo
			for(int j=1;j<=radio;j++){
					compartida[threadIdx.x+radio+j]=inputArray[indx+j];//completo con vecinos
			}
		}
	}
	
	compartida[threadIdx.x+radio]=inputArray[indx];

	__syncthreads();

	int parcial = 0;	
	for (int j=0;j<MASK_SIZE;j++){
		parcial = parcial + compartida[threadIdx.x+j] * mask[j];	
	}

	atomicAdd(&outputArray[indx],parcial);
}
#endif



#if PARTE == 5
__global__ void Kernel_Convolucion_SharedCache(int * inputArray, int* outputArray, int* mask){
	__shared__ int compartida[CHUNK];
	
	int indx = threadIdx.x + blockIdx.x * blockDim.x;
	int radio = MASK_SIZE / 2;

	compartida[threadIdx.x]=inputArray[indx];
	__syncthreads();

	int parcial = 0;
	int auxI= indx - radio;
	//int auxT= threadIdx.x - radio;
	for (int i=0;i<MASK_SIZE;i++){
		if (((auxI) >= 0) && ((auxI) < SIZE_X)){
			parcial += inputArray[auxI] * mask[i];
			/*if (((auxT) >= 0) && ((auxT) < CHUNK)){
				parcial += compartida[auxT] * mask[i];	
			}else{
				parcial += inputArray[auxI] * mask[i];
			}*/
		}
		//auxT++;
		auxI++;
	}
	atomicAdd(&outputArray[indx],parcial);
}
#endif

#if PARTE == 6
__global__ void Kernel_Convolucion_Shared2D(int * inputArray, int* outputArray, int* mask){
	__shared__ int compartida[CHUNK + MASK_SIZE - 1];
	
	int indx = threadIdx.x + blockIdx.x * blockDim.x;
	int radio = MASK_SIZE / 2;
	
	if(threadIdx.x==0){//si estoy en el primer hilo del bloque
		if(indx == 0){//en el primer bloque
			for(int j=0;j<radio;j++){
				compartida[j]=0;//completo con 0
			}
		}else{//si no es el primero
			for(int j=0;j<radio;j++){
				compartida[j]=inputArray[indx-radio+j];//completo con vecinos
			}
		}
	}else if(threadIdx.x==CHUNK-1){//si estoy en el ultimo hilo del bloque
		if (indx==SIZE_X-1){//en el ultimo bloque
			for(int j=1;j<=radio;j++){
				compartida[threadIdx.x+j+radio]=0;//completo con 0
			}
		}else{//si no es el ultimo
			for(int j=1;j<=radio;j++){
					compartida[threadIdx.x+radio+j]=inputArray[indx+j];//completo con vecinos
			}
		}
	}
	
	compartida[threadIdx.x+radio]=inputArray[indx];

	__syncthreads();

	int parcial = 0;	
	for (int j=0;j<MASK_SIZE;j++){
		parcial = parcial + compartida[threadIdx.x+j] * mask[j];	
	}

	atomicAdd(&outputArray[indx],parcial);
}
#endif


#if PARTE == 7
__global__ void Kernel_Convolucion_Shared_Compartida(int * inputArray, int* outputArray){
	__shared__ int compartida[CHUNK + MASK_SIZE - 1];
	
	int indx = threadIdx.x + blockIdx.x * blockDim.x;
	int radio = MASK_SIZE / 2;
	
	if(threadIdx.x==0){//si estoy en el primer hilo del bloque
		if(indx == 0){//en el primer bloque
			for(int j=0;j<radio;j++){
				compartida[j]=0;//completo con 0
			}
		}else{//si no es el primero
			for(int j=0;j<radio;j++){
				compartida[j]=inputArray[indx-radio+j];//completo con vecinos
			}
		}
	}else if(threadIdx.x==CHUNK-1){//si estoy en el ultimo hilo del bloque
		if (indx==SIZE_X-1){//en el ultimo bloque
			for(int j=1;j<=radio;j++){
				compartida[threadIdx.x+j+radio]=0;//completo con 0
			}
		}else{//si no es el ultimo
			for(int j=1;j<=radio;j++){
					compartida[threadIdx.x+radio+j]=inputArray[indx+j];//completo con vecinos
			}
		}
	}
	
	compartida[threadIdx.x+radio]=inputArray[indx];

	__syncthreads();

	int parcial = 0;	
	for (int j=0;j<MASK_SIZE;j++){
		parcial = parcial + compartida[threadIdx.x+j] *  mask_constant[j];	
	}

	atomicAdd(&outputArray[indx],parcial);
}
#endif

void Convolucion_C(int * inputArray, int* ouputArray, int * mask){
	int i, j;
	for( i = 0; i<SIZE_X;i++){   
		for( j =0; j<MASK_SIZE;j++){      
			int position = i-(int)(MASK_SIZE/2) + j;
			if(position>=0 && position<SIZE_X)
				ouputArray[i] += inputArray[position] * mask[j];
		}       
	}
} 

int main() {
	int* inputArray = (int*)malloc(sizeof(int) * SIZE_X);
	int* outputArray = (int*)malloc(sizeof(int) * SIZE_X);
	int* outputArray_GPU = (int*)malloc(sizeof(int) * SIZE_X);
	int* mask = (int*)malloc(sizeof(int) * MASK_SIZE);
	int i;
	int * inputArray_k;
	int* outputArray_k;	
#if PARTE != 3
	int* mask_k;	
#endif

//#########################################
//######## RESERVA DE MEMORIA #############
//#########################################
	size_t size = SIZE_X * sizeof(int);
	cudaMalloc(&(inputArray_k),size);
	cudaMalloc(&(outputArray_k),size);
#if PARTE != 3
	//cudaMalloc de la mascara
	size_t sizeMask = MASK_SIZE * sizeof(int);
	cudaMalloc(&mask_k,sizeMask);
#endif

//########## INICIALIZACION ###############
	for(i =0; i<SIZE_X;i++)	{
		inputArray[i] = i;
		outputArray[i] = 0;
	}		

	for(i =0; i<MASK_SIZE; i++)	{
		mask[i] = 5;
	}
	
//########### CORRE EN CPU ################
	clockStart();
	Convolucion_C(inputArray, outputArray, mask);
	clockStop("CPU");

//########### COPIA A GPU #################             	   
	clockStart();
   cudaMemcpy(inputArray_k,inputArray,size,cudaMemcpyHostToDevice);		
   cudaMemset(outputArray_k, 0, sizeof(int)*SIZE_X );

#if (PARTE == 3)||(PARTE == 7)
	//copia a memoria constante   
	cudaMemcpyToSymbol(mask_constant, mask, MASK_SIZE*sizeof(int));
#else
	cudaMemcpy(mask_k, mask, sizeof(int)*MASK_SIZE, cudaMemcpyHostToDevice);
#endif
	cudaDeviceSynchronize();
	clockStop("Tranferencias a host");
	
//########### CORRE EN GPU ################  	
	clockStart();
	int cantBloques = SIZE_X / CHUNK + (SIZE_X % CHUNK == 0 ? 0 : 1);
	int tamGrid = cantBloques;
	int tamBlock = CHUNK;
#if PARTE == 2
	Kernel_Convolucion_Simple<<<tamGrid, tamBlock>>>(inputArray_k, outputArray_k, mask_k);
#endif
#if PARTE == 3
	Kernel_Convolucion_Constante<<<tamGrid, tamBlock>>>(inputArray_k, outputArray_k);
#endif
#if PARTE == 4
	Kernel_Convolucion_Shared<<<tamGrid, tamBlock>>>(inputArray_k, outputArray_k, mask_k);
#endif	
#if PARTE == 5
	Kernel_Convolucion_SharedCache<<<tamGrid, tamBlock>>>(inputArray_k, outputArray_k, mask_k);
#endif
#if PARTE == 6
	Kernel_Convolucion_Shared2D<<<tamGrid, tamBlock>>>(inputArray_k, outputArray_k, mask_k);
#endif
#if PARTE == 7
	Kernel_Convolucion_Shared_Compartida<<<tamGrid, tamBlock>>>(inputArray_k, outputArray_k);
#endif


	cudaDeviceSynchronize();
	clockStop("GPU");
	cudaCheck();
	
//########### RETORNO A CPU ################ 
	clockStart();
	cudaMemcpy(outputArray_GPU,outputArray_k,size,cudaMemcpyDeviceToHost);	
	cudaFree(inputArray_k);
	cudaFree(outputArray_k);
	clockStop("Tranferencias host a CPU");	
	
//########### CHECK ALL OK ################# 	
	if(equal_arrays(outputArray_GPU,outputArray, SIZE_X))
		printf("Enhorabuena\n");
    else
		printf("Rayos y centellas\n");
	
	free(inputArray);
	free(outputArray_GPU);
	free(outputArray);
	
	return 0;
}
