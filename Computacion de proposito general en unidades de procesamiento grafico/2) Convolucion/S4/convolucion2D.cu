#include <cuda.h>
#include <stdio.h>
#include <stdlib.h>
#include "util.h"

#define CHUNK 8
/*
#define SIZE_Y 768
#define SIZE_X 1024*/

#define SIZE_Y 8
#define SIZE_X 8
#define MASK_SIZE 3

void cudaCheck(){
	cudaError_t cudaError;
	cudaError = cudaGetLastError();
	if(cudaError != cudaSuccess)  {
		printf("  cudaGetLastError() returned %d: %s\n", cudaError, cudaGetErrorString(cudaError));
	}else{
		//printf("  todo ok\n" );
	}
}

int getIdx(int x,int y,int sizeY){
	return sizeY*x+y;
}

__device__ int getIdxD(int x,int y,int sizeY){
	return sizeY*x+y;
}

__global__ void Kernel_Convolucion_Shared2D(int * inputArray, int* outputArray, int* mask){
	
	__shared__ int compartida[CHUNK + MASK_SIZE - 1][CHUNK + MASK_SIZE - 1];
	
	
	int Col= blockDim.x*blockIdx.x+threadIdx.x;
	int Row= blockDim.y*blockIdx.y+threadIdx.y;
	printf("Col %d - Row %d \n",Col,Row);
	
	int indx = getIdxD(Col,Row,SIZE_Y);
	
	printf("Col %d - Row %d - IDX: %d\n",Col,Row,indx);
	int radio = MASK_SIZE / 2;
	
	compartida[threadIdx.x][threadIdx.y]=inputArray[indx];

	__syncthreads();

	int parcial = 0;
	int pX = Col-radio;
	int pY = Row-radio;
	for (int i=0;i<MASK_SIZE;i++){
		for (int j=0;j<MASK_SIZE;j++){
			if(pX>=0 && pX<SIZE_X)
				if(pY>=0 && pY<SIZE_Y)
					parcial += inputArray[getIdxD(pX,pY,SIZE_Y)] * mask[getIdxD(i,j,MASK_SIZE)];
			pY++;
		}
		pY = Row-radio;
		pX++;
	}
	atomicAdd(&outputArray[indx],indx);
}


//Chequeada en 8*8 con excel
void Convolucion2D_C(int * inputArray, int* ouputArray, int * mask){
	int x,y,i,j;
	for( x = 0; x<SIZE_X;x++){  
		for( y = 0; y<SIZE_Y;y++){	
			for( i =0; i<MASK_SIZE;i++){
				for( j =0; j<MASK_SIZE;j++){
					int pX = x-(int)(MASK_SIZE/2) + i;
					int pY = y-(int)(MASK_SIZE/2) + j;
					if(pX>=0 && pX<SIZE_X)
						if(pY>=0 && pY<SIZE_Y)
							ouputArray[getIdx(x,y,SIZE_Y)] += inputArray[getIdx(pX,pY,SIZE_Y)] * mask[getIdx(i,j,MASK_SIZE)];
				} 
			}  
		}    
	}
} 

int main() {
	int* inputArray = (int*)malloc(sizeof(int) * SIZE_X * SIZE_Y);
	int* outputArray = (int*)malloc(sizeof(int) * SIZE_X * SIZE_Y);
	int* outputArray_GPU = (int*)malloc(sizeof(int) * SIZE_X * SIZE_Y);
	int* mask = (int*)malloc(sizeof(int) * MASK_SIZE * MASK_SIZE);
	int i;
	int * inputArray_k;
	int* outputArray_k;	
	int* mask_k;	


//#########################################
//######## RESERVA DE MEMORIA #############
//#########################################
	size_t size = SIZE_X * SIZE_Y * sizeof(int);
	size_t sizeMask = MASK_SIZE * MASK_SIZE * sizeof(int);
	
	cudaMalloc(&(inputArray_k),size);
	cudaMalloc(&(outputArray_k),size);
	cudaMalloc(&mask_k,sizeMask);

//########## INICIALIZACION ###############
	for(i =0; i<SIZE_X;i++)	{
		for(int j =0; j<SIZE_Y;j++)	{
			int aux = getIdx(i,j,SIZE_Y);
			inputArray[aux] = (aux)%100;
			outputArray[aux] = 0;
		}
	}	

	for(i =0; i<MASK_SIZE*MASK_SIZE; i++)	{
		mask[i] = 1;
	}
	
//########### CORRE EN CPU ################
	clockStart();
	Convolucion2D_C(inputArray, outputArray, mask);
	clockStop("CPU");
	print_matrix2D(outputArray,SIZE_X,SIZE_Y);

//########### COPIA A GPU #################             	   
	clockStart();
	cudaMemcpy(inputArray_k,inputArray,size,cudaMemcpyHostToDevice);		
	cudaMemset(outputArray_k, 0, sizeof(int)*SIZE_X );
	cudaMemcpy(mask_k, mask, sizeof(int)*MASK_SIZE, cudaMemcpyHostToDevice);
	cudaDeviceSynchronize();
	clockStop("Tranferencias a host");
	
//########### CORRE EN GPU ################  	
	clockStart();
	
	
	int cantBloquesX = SIZE_X / CHUNK + (SIZE_X % CHUNK == 0 ? 0 : 1);
	int cantBloquesY = SIZE_Y / CHUNK + (SIZE_Y % CHUNK == 0 ? 0 : 1);
	
	//Configurar la grilla
	dim3 tamGrid (cantBloquesX, cantBloquesY); //Grid dimensión
	dim3 tamBlock(CHUNK, CHUNK); //Block dimensión
	
	Kernel_Convolucion_Shared2D<<<tamGrid, tamBlock>>>(inputArray_k, outputArray_k, mask_k);

	cudaDeviceSynchronize();
	clockStop("GPU");
	cudaCheck();
	
//########### RETORNO A CPU ################ 
	clockStart();
	cudaMemcpy(outputArray_GPU,outputArray_k,size,cudaMemcpyDeviceToHost);	
	cudaFree(inputArray_k);
	cudaFree(outputArray_k);
	clockStop("Tranferencias host a CPU");	
	print_matrix2D(outputArray_GPU,SIZE_X,SIZE_Y);
	print_matrix2D(inputArray,SIZE_X,SIZE_Y);
	
//########### CHECK ALL OK ################# 	
	if(equal_arrays(outputArray_GPU,outputArray, SIZE_X*SIZE_Y))
		printf("Enhorabuena\n");
    else
		printf("Rayos y centellas\n");
	
	free(inputArray);
	free(outputArray_GPU);
	free(outputArray);
	
	return 0;
}
