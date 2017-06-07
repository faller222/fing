#include "cuda.h"
#include <stdio.h>
#include <stdlib.h>
#include "util.h"
#include "cuda_runtime.h"
#include "device_launch_parameters.h"

#define CHUNK 1024
//#define SIZE_X 4194304
#define SIZE_X 1048576
#define MASK_SIZE 21
#define PARTE 4

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

#if PARTE == 3



__global__ void Kernel_Convolucion_Constante(int * inputArray, int* outputArray)
{
	int ratio = MASK_SIZE / 2;
	int pValor = 0;	
	int pos = threadIdx.x + blockIdx.x * blockDim.x;
	for (int j=0;j<MASK_SIZE;j++){
		if (((pos + j - ratio) >= 0) && ((pos + j - ratio) < SIZE_X)){
			 pValor = pValor + inputArray[pos + j - ratio] * mask_constant[j];	
		}
		
	}	
	atomicAdd(&outputArray[pos],pValor);
}

#endif

__global__ void Kernel_Convolucion_Shared(int * inputArray, int* outputArray, int* mask)

{
	__shared__ int arrayincom[CHUNK + MASK_SIZE - 1];

	int pos = threadIdx.x + blockIdx.x * blockDim.x;
	int ratio = MASK_SIZE / 2;



	if(threadIdx.x==0){
		if(pos == 0){
			for(int j=0;j<ratio;j++){
				arrayincom[j]=0;
			}
		}else{
			for(int j=0;j<ratio;j++){
				arrayincom[j]=inputArray[pos-ratio+j];
			}
		}
	}else if(threadIdx.x==CHUNK-1){
		if (pos==SIZE_X-1){
			for(int j=1;j<=ratio;j++){
				arrayincom[threadIdx.x+j+ratio]=0;
			}
		}else{
			for(int j=1;j<=ratio;j++){
					arrayincom[threadIdx.x+ratio+j]=inputArray[pos+j];
			}
		}
	}
		
	arrayincom[threadIdx.x+ratio]=inputArray[pos];
	
	 __syncthreads();

	int pValor = 0;	
	for (int j=0;j<MASK_SIZE;j++){
			 pValor = pValor + arrayincom[threadIdx.x+j] * mask[j];	
	}

	atomicAdd(&outputArray[pos],pValor);
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
   cudaMemcpyToSymbol(mask_constant, mask, MASK_SIZE*sizeof(int));
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
	printf("Convolucion_Constante\n");
	Kernel_Convolucion_Constante<<<tamGrid, tamBlock>>>(inputArray_k, outputArray_k);
#else

#if PARTE == 4
	printf("Convolucion_Shared\n");

	Kernel_Convolucion_Shared<<<tamGrid, tamBlock>>>(inputArray_k, outputArray_k, mask_k);
#else
	printf("Convolucion_Simple\n");
	Kernel_Convolucion_Simple<<<tamGrid, tamBlock>>>(inputArray_k, outputArray_k, mask_k);
	cudaFree(mask_k);
#endif
#endif	

	

	cudaDeviceSynchronize();
	
	clockStop("GPU");
	cudaCheck();
	clockStart();

	cudaMemcpy(outputArray_GPU,outputArray_k,size,cudaMemcpyDeviceToHost);	
	
	cudaFree(inputArray_k);
	cudaFree(outputArray_k);
	
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
