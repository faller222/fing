#include "cuda.h"
#include <stdio.h>
#include <stdlib.h>
#include "util.h"
#include "cuda_runtime.h"
#include "device_launch_parameters.h"

#define CHUNK 1024
#define SIZE_X 4194304

#define MASK_SIZE 7
#define PARTE 2

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

#if PARTE == 3

__global__ void Kernel_Convolucion_Constante(int * inputArray, int* outputArray)
{

}

#endif

__global__ void Kernel_Convolucion_Shared(int * inputArray, int* outputArray, int* mask)
{
	
}


__global__ void Kernel_Convolucion_Simple(int * inputArray, int* outputArray, int* mask)
{
	int ratio = MASK_SIZE / 2;
	int pValor = 0;	
	int pos = threadIdx.x + blockIdx.x * blockDim.x;
	for (int j=0;j<MASK_SIZE;j++){
		if (((pos + j - ratio) >= 0) && ((pos + j - ratio) < SIZE_X)){
			 pValor = pValor + inputArray[pos + j - ratio] * mask[j];	
		}
		
	}
	//outputArray[pos]=pValor;
	atomicAdd(&outputArray[pos],pValor);

}

void Convolucion_C(int * inputArray, int* ouputArray, int * mask)
{
        int i, j;
        for( i = 0; i<SIZE_X;i++)
        {   
                for( j =0; j<MASK_SIZE;j++)
                        {      
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
	int * outputArray_k;	
#if PARTE != 3
	int* mask_k;	
#endif

//cudaMalloc del array de entrada
//cudaMalloc del array de salida
	size_t size = SIZE_X * sizeof(int);
	cudaMalloc(&(inputArray_k),size);
	cudaMalloc(&(outputArray_k),size);
	

	
	
#if PARTE != 3
	//cudaMalloc de la mascara
	size_t sizeMask = MASK_SIZE * sizeof(int);
	cudaMalloc(&mask_k,sizeMask);
#endif

	for(i =0; i<SIZE_X;i++)
	{
		inputArray[i] = i;
		outputArray[i] = 0;
	}		

	for(i =0; i<MASK_SIZE; i++)
	{
		mask[i] = 1;
	}

  clockStart();
	Convolucion_C(inputArray, outputArray, mask);
	clockStop("CPU");
               
	clockStart();
  ///////////////////////////////////////
	//copiar datos de entrada a la GPU
	///////////////////////////////////////
   cudaMemcpy(inputArray_k,inputArray,size,cudaMemcpyHostToDevice);	
	
   cudaMemset(outputArray_k, 0, sizeof(int)*SIZE_X );

#if PARTE == 3
	 //copia a memoria constante
#else
	cudaMemcpy(mask_k, mask, sizeof(int)*MASK_SIZE, cudaMemcpyHostToDevice);
#endif
	cudaDeviceSynchronize();
	clockStop("Tranferencias a host");
	clockStart();
	int cantBloques = SIZE_X / CHUNK + (SIZE_X % CHUNK == 0 ? 0 : 1);

	int tamGrid = cantBloques;
	int tamBlock = CHUNK;
#if PARTE == 3
	Kernel_Convolucion_Constante<<<tamGrid, tamBlock>>>(inputArray_k, outputArray_k);
#else

#if PARTE == 4
	Kernel_Convolucion_Shared<<<tamGrid, tamBlock>>>(inputArray_k, outputArray_k, mask_k);
#else
	Kernel_Convolucion_Simple<<<tamGrid, tamBlock>>>(inputArray_k, outputArray_k, mask_k);
#endif
#endif	

	

	cudaDeviceSynchronize();
	
	clockStop("GPU");
	cudaCheck();
	clockStart();

	cudaMemcpy(outputArray_GPU,outputArray_k,size,cudaMemcpyDeviceToHost);	
	
	cudaFree(inputArray_k);
	cudaFree(outputArray_k);
	cudaFree(mask_k);
  ///////////////////////////////////////
	//traer salida de la GPU
	///////////////////////////////////////
        

	clockStop("Tranferencias host a CPU");	
	
	 if(equal_arrays(outputArray_GPU,outputArray, SIZE_X))
		printf("Enhorabuena");
	 else
		printf("Rayos y centellas");
	 

	 free(outputArray_GPU);
	 free(outputArray);
	 free(inputArray);

	 char character;
	 scanf("%c", &character);
	return 0;
}
