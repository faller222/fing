#include <cuda.h>
#include <stdio.h>
#include <stdlib.h>
#include "util.h"

void cudaCheck(){
  cudaError_t cudaError;

  cudaError = cudaGetLastError();
  if(cudaError != cudaSuccess)
  {
    printf("  cudaGetLastError() returned %d: %s\n", cudaError, cudaGetErrorString(cudaError));
  }else{
	//printf("  todo ok\n" );
  }
}

__global__ void Kernel_Convolucion_Simple(int * inputArray, int* outputArray, int* mask, int arraySize, int maskSize)
{
	int ratio = maskSize / 2;
	int pValor = 0;	
	int pos = threadIdx.x + blockIdx.x * blockDim.x;
	for (int j=0;j<maskSize;j++){
		if (((pos + j - ratio) >= 0) && ((pos + j - ratio) < arraySize)){
			 pValor = pValor + inputArray[pos + j - ratio] * mask[j];	
		}
		
	}
	atomicAdd(&outputArray[pos],pValor);
}

void Convolucion_C(int * inputArray, int* ouputArray, int * mask, int arraySize,int maskSize)
{
	int i, j;
	for( i = 0; i<arraySize;i++)
	{   
		for( j =0; j<maskSize;j++)
			{      
				int position = i-(int)(maskSize/2) + j;
				if(position>=0 && position<arraySize)
					ouputArray[i] += inputArray[position] * mask[j];
			}       
	}
} 

void ejecutar(int arraySize,int maskSize,int blockSize){
	printf("\nTamaño: array %d, Mask %d, Block %d\n",arraySize,maskSize,blockSize);

	int* inputArray = (int*)malloc(sizeof(int) * arraySize);
	int* outputArray = (int*)malloc(sizeof(int) * arraySize);
	int* outputArray_GPU = (int*)malloc(sizeof(int) * arraySize);
	int* mask = (int*)malloc(sizeof(int) * maskSize);
	int i;
	int * inputArray_k;
	int* outputArray_k;	
	int* mask_k;


//#########################################
//######## RESERVA DE MEMORIA #############
//#########################################
	size_t size = arraySize * sizeof(int);
	size_t sizeMask = maskSize * sizeof(int);
	cudaMalloc(&(inputArray_k),size);
	cudaMalloc(&(outputArray_k),size);
	cudaMalloc(&mask_k,sizeMask);
	
//########## INICIALIZACION ###############
	for(i =0; i<arraySize;i++){
		inputArray[i] = i;
		outputArray[i] = 0;
	}		

	for(i =0; i<maskSize; i++){
		mask[i] = 1;
	}

//########### COPIA A GPU #################             
	clockStart();
	cudaMemcpy(inputArray_k,inputArray,size,cudaMemcpyHostToDevice);	
	cudaMemset(outputArray_k, 0, sizeof(int)*arraySize );
	cudaMemcpy(mask_k, mask, sizeof(int)*maskSize, cudaMemcpyHostToDevice);
	cudaDeviceSynchronize();
	clockStop("\tTranferencias a host");
	
	
//########### CORRE EN GPU ################  	
	clockStart();
	int cantBloques = arraySize / blockSize + (arraySize % blockSize == 0 ? 0 : 1);
	int tamGrid = cantBloques;
	Kernel_Convolucion_Simple<<<tamGrid, blockSize>>>(inputArray_k, outputArray_k, mask_k, arraySize, maskSize);

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
	ejecutar(1048576,7,64);
	ejecutar(1048576,7,128);
	ejecutar(1048576,7,256);
	ejecutar(1048576,7,512);
	ejecutar(1048576,7,1024);		
	ejecutar(4194304,7,64);
	ejecutar(4194304,7,128);
	ejecutar(4194304,7,256);
	ejecutar(4194304,7,512);
	ejecutar(4194304,7,1024);	
	
	return 0;
}
