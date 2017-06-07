#include <cuda.h>
#include <stdio.h>
#include <stdlib.h>
#include "util.h"

#define MASK_SIZE 21

__constant__ int mask_constant[MASK_SIZE];

void cudaCheck()
{
  cudaError_t cudaError;

  cudaError = cudaGetLastError();
  if(cudaError != cudaSuccess)
  {
    printf("  cudaGetLastError() returned %d: %s\n", cudaError, cudaGetErrorString(cudaError));
  }else{
	//printf("  todo ok\n" );
  }
}

__global__ void Kernel_Convolucion_Constante(int * inputArray, int* outputArray, int arraySize,int maskSize)
{
	
	int ratio = maskSize / 2;
	int pValor = 0;	
	int pos = threadIdx.x + blockIdx.x * blockDim.x;
	for (int j=0;j<maskSize;j++){
		if (((pos + j - ratio) >= 0) && ((pos + j - ratio) < arraySize)){
			 pValor = pValor + inputArray[pos + j - ratio] * mask_constant[j];	
		}
	}
	atomicAdd(&outputArray[pos],pValor);
}


void ejecute(int arraySize,int maskSize, int blockSize){

	printf("\nSizes: array %d, Mask %d, Block %d\n",arraySize,maskSize,blockSize);

	int* inputArray = (int*)malloc(sizeof(int) * arraySize);
	int* outputArray = (int*)malloc(sizeof(int) * arraySize);
	int* outputArray_GPU = (int*)malloc(sizeof(int) * arraySize);
	int* mask = (int*)malloc(sizeof(int) * MASK_SIZE);
	int i;
	int* inputArray_k;
	int* outputArray_k;	

//#########################################
//######## RESERVA DE MEMORIA #############
//#########################################
	size_t size = arraySize * sizeof(int);
	cudaMalloc(&(inputArray_k),size);
	cudaMalloc(&(outputArray_k),size);

//########## INICIALIZACION ###############
	for(i =0; i<arraySize;i++)	{
		inputArray[i] = i;
		outputArray[i] = 0;
	}		

	for(i =0; i<MASK_SIZE; i++)	{
		mask[i] = 5;
	}

//########### COPIA A GPU #################  
	clockStart();
	cudaMemcpy(inputArray_k,inputArray,size,cudaMemcpyHostToDevice);	
	cudaMemset(outputArray_k, 0, sizeof(int)*arraySize );	
	cudaMemcpyToSymbol(mask_constant, mask, MASK_SIZE*sizeof(int));
	cudaDeviceSynchronize();
	clockStop("\tTranferencias a host");
	
//########### CORRE EN GPU ################ 

	clockStart();
	int cantBloques = arraySize / blockSize + (arraySize % blockSize == 0 ? 0 : 1);
	int tamGrid = cantBloques;
	int tamBlock = blockSize;

	Kernel_Convolucion_Constante<<<tamGrid, tamBlock>>>(inputArray_k, outputArray_k,arraySize,maskSize);
	cudaDeviceSynchronize();
	clockStop("\tGPU");
	cudaCheck();
	
//########### RETORNO A CPU ################ 
	clockStart();
	cudaMemcpy(outputArray_GPU,outputArray_k,size,cudaMemcpyDeviceToHost);	
	cudaFree(inputArray_k);
	cudaFree(outputArray_k);
	clockStop("\tTranferencias host a CPU");	
	
	free(inputArray);
	free(outputArray_GPU);
	free(outputArray);

}




int main() {
	ejecute(4194304,5,256);
	ejecute(4194304,7,256);
	ejecute(4194304,21,256);
	ejecute(1048576,5,256);
	ejecute(1048576,7,256);
	ejecute(1048576,21,256);
	 	
	return 0;
}
