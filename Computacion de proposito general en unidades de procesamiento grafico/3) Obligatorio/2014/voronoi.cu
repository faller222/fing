#ifndef KERNEL_CUDA
#define KERNEL_CUDA

#include <cuda.h>
#include <stdio.h>
#include <stdlib.h>
#include "CImg.h"
#include "utilImage.h"

#define HILOS 32


using namespace cimg_library;
/**
Referencia:
	http://en.wikipedia.org/wiki/CUDA
	http://inf.ufrgs.br/gppd/wsppd/2013/papers/wsppd2013_submission_15.pdf.mod.pdf
*/

/**
	Variables en el kernel
		gridDim // cantidad de bloques
		blockDim //tamaño de bloque, cant de hilos
		threadIdx //inidice de hilo en el bloque, max blockDim-1
		blockIdx //indice de bloque , max gridDim-1 
*/

void cudaCheck(){
	cudaError_t cudaError = cudaGetLastError();
	if(cudaError != cudaSuccess)  {
		printf("  cudaGetLastError() returned %d: %s\n", cudaError, cudaGetErrorString(cudaError));
		exit(0);
	}
}

__global__ void Kernel_Test(){
	printf("\tSay hi form BK(%d,%d) TH(%d,%d)\n",blockIdx.x,blockIdx.y,threadIdx.x,threadIdx.y);
}

__global__ void Kernel_Imagen_Promedio(float* inputArray_k, float* outputArray_k){

	__shared__ float frame[(HILOS + MARCO + MARCO)*(HILOS + MARCO + MARCO)];	
	
	int sizeX = blockDim.x * gridDim.x;
	//int sizeY = blockDim.y * gridDim.y;
	
	int id_hilo = blockDim.x * threadIdx.y + threadIdx.x;
	int cant_hilos = blockDim.y * blockDim.x; 
	
	int size_sh_x = HILOS + MARCO + MARCO;
	int size_shared = size_sh_x * size_sh_x;
	
	int offsetX = blockDim.x * blockIdx.x;
	int offsetY = blockDim.y * blockIdx.y;
	
	int sizeX_marco = sizeX + 2 * MARCO;
	
	int shX;
	int shY;
	
	for (int i = id_hilo ; i < size_shared ; i+=cant_hilos){		
		shX = (i % size_sh_x) + offsetX;
		shY = (i / size_sh_x) + offsetY;
		frame[i] = inputArray_k[ shX  + sizeX_marco * shY];		
	}
	
	__syncthreads();
	
	int acu = 0;
	//#pragma unroll
	for (int y = threadIdx.y ; y < threadIdx.y + SIZE_MASK ; y++){			
		//#pragma unroll
		for (int x = threadIdx.x ; x < threadIdx.x + SIZE_MASK ; x++){ 
			acu += (unsigned char) frame[x + size_sh_x*y];
		}
	}
	acu = acu /(SIZE_MASK * SIZE_MASK);
	outputArray_k[(threadIdx.x + offsetX) + sizeX * (threadIdx.y + offsetY)] =  acu;
}

__global__ void Kernel_Voronoi(float* inputArray_k, float* outputArray_k, bool* cambiosMatriz_k, int* indicesArrayO_k, int* indicesArrayU_k, int* fin){

	//indices
	int x = blockDim.x * blockIdx.x + threadIdx.x;
	int y = blockDim.y * blockIdx.y + threadIdx.y;
	int sizeX = blockDim.x * gridDim.x;
	
	int tid = x + sizeX * y;
	
	int coord;
	
	int aux_dist;
	int nid;
	
	if(cambiosMatriz_k[tid]){
		if(indicesArrayO_k[tid]==-1){
			indicesArrayO_k[tid]=tid;
			indicesArrayU_k[tid]=tid;
		}
		
		//para cada vecino			
		for(int i=(x-1); i<=(x+1);i++){			
			for(int j=(y-1); j<=(y+1);j++){
				
				//dentro de los limites de la imagen
				if( i>=0 && i<sizeX &&  j>=0 && j<(blockDim.y * gridDim.y) ){
					nid = i + sizeX * j;
					if(indicesArrayO_k[nid]==-1){
						indicesArrayU_k[nid]=indicesArrayO_k[tid];
					}else{
						if(indicesArrayO_k[nid]!=indicesArrayO_k[tid]){
							
							//calculo de indices vecino
							//CoordX vecino
							coord = indicesArrayO_k[nid] % sizeX;												
							aux_dist= i * i + coord * coord - 2 * i * coord ;
							//CoordY vecino
							coord = indicesArrayO_k[nid] / sizeX;	
							aux_dist+= j * j + coord * coord - 2 * j * coord ;
							//Calculo de indices Actual
							//CoordX Actual
							coord = indicesArrayO_k[tid] % sizeX;
							aux_dist-= i * i + coord * coord - 2 * i * coord ;
							//CoordY Actual
							coord = indicesArrayO_k[tid] / sizeX;	
							aux_dist-= j * j + coord * coord - 2 * j * coord ;
												
							if(aux_dist > 0){
								indicesArrayU_k[nid]=indicesArrayO_k[tid];
							}
						}
					}
				}
			}
		}
		cambiosMatriz_k[tid]=false;
	}
	
	__syncthreads();
	
	if(indicesArrayU_k[tid]!=indicesArrayO_k[tid]){
		indicesArrayO_k[tid]=indicesArrayU_k[tid];
		cambiosMatriz_k[tid]=true;
		atomicAdd(&fin[0],1);
	}
	indicesArrayU_k[tid]=indicesArrayO_k[tid];
	
	outputArray_k[tid]=inputArray_k[indicesArrayU_k[tid]];	
}


CImg<float> voronoi_CPU(CImg<float> orig, CImg<float> &suave, int cantCentros){
	//Hacemos pading y generamos un recuadro con espejo
	CImg<float> p1  = prepararImagen(orig);

	//tamanios Originales
	int size_x = orig.width();
	int size_y = orig.height();
	//tamanios sin Padding
	int sizeX = p1.width() - SIZE_MASK + 1;
	int sizeY = p1.height() - SIZE_MASK + 1;
	// generamos Centros
	bool* centrosBool = generarCentros(cantCentros,sizeX, sizeY);
	//suavizado
	suave = roundImagen(p1);
	//voronoi
	CImg<float> p3 = voronoi(suave, cantCentros, centrosBool);
	free(centrosBool);
	suave=recortar(suave, size_x, size_y);
	return recortar(p3, size_x, size_y);
}

CImg<float> voronoi_GPU(CImg<float> orig, CImg<float> &suave, int cantCentros){	
	//Hacemos pading y generamos un recuadro con espejo
	CImg<float> p1  = prepararImagen(orig);
	float* data=p1.data();
	
	//Variables timming
	timeAux start;
	double time;
	//Variables en el Kernel
	float* inputArray_k;
	float* suaveArray_k;	
	float* voronArray_k;	
	bool* cambiosMatriz_k;
	int* indicesArrayO_k;
	int* indicesArrayU_k;
	int* finVoronoi;

	//tamanios originales
	int size_x_orig = orig.width();
	int size_y_orig = orig.height();
	//tamanios sin Padding
	int sizeX = p1.width() - SIZE_MASK + 1;
	int sizeY = p1.height() - SIZE_MASK + 1;
	//tamanios Kernel
	size_t sizeI = p1.width() * p1.height() * sizeof(float);
	size_t sizeO = sizeX * sizeY * sizeof(float);
	size_t sizeC = sizeX * sizeY * sizeof(bool);
	size_t sizeD = sizeX * sizeY * sizeof(int);
	
	//Resultados
	CImg<float> suavei(sizeX,sizeY,1,1);
	float* suave_data=suavei.data();
	CImg<float> voro(sizeX,sizeY,1,1);
	float* voro_data=voro.data();
	
	// generamos Centros
	bool* centros = generarCentros(cantCentros, sizeX, sizeY);
	
//######## RESERVA DE MEMORIA #############
	start = clockStart();
	cudaMalloc(&(inputArray_k),sizeI);
	cudaMalloc(&(suaveArray_k),sizeO);
	cudaMalloc(&(voronArray_k),sizeO);
	cudaMalloc(&(cambiosMatriz_k),sizeC);
	cudaMalloc(&(indicesArrayO_k),sizeD);
	cudaMalloc(&(indicesArrayU_k),sizeD);
	cudaMalloc(&(finVoronoi),sizeof(int));
	time = clockStop(start);	
	printf("Reserva Memoria en el Device (%fs)\n",time);

//########### COPIA A GPU #################   
	start = clockStart(); 	  
	cudaMemcpy(inputArray_k,data,sizeI,cudaMemcpyHostToDevice);		
	cudaMemset(suaveArray_k, 0, sizeO );
	cudaMemset(voronArray_k, 0, sizeO );
	cudaMemcpy(cambiosMatriz_k,centros,sizeC,cudaMemcpyHostToDevice);
	cudaMemset(indicesArrayO_k,-1,sizeD);
	cudaMemset(indicesArrayU_k,-1,sizeD);
	cudaDeviceSynchronize();
	time = clockStop(start);
	printf("Tranferencia Host -> Device (%fs)\n",time);
	
//####### Configurar la grilla ############  
	start = clockStart(); 	
	int cantBloquesX = sizeX / HILOS;
	int cantBloquesY = sizeY / HILOS;
	dim3 tamGrid (cantBloquesX, cantBloquesY);
	dim3 tamBlock(HILOS, HILOS );

//############# CORRE EN GPU ##############  
	printf("Ejecutando Suavizado...  ");	 
	Kernel_Imagen_Promedio<<<tamGrid, tamBlock>>>(inputArray_k, suaveArray_k);
	cudaDeviceSynchronize();
	time = clockStop(start);
	printf("Listo (%fs)\n",time);
	
	start = clockStart(); 
	printf("Ejecutando Voronoi...  ");
	int iteraciones=0;
	int* fin =(int*)malloc(sizeof(int));
	fin[0]=-4;		
	
	while(fin[0]){
		cudaMemset(finVoronoi,0,sizeof(int));
		Kernel_Voronoi<<<tamGrid, tamBlock>>>(suaveArray_k, voronArray_k,  cambiosMatriz_k,indicesArrayO_k,indicesArrayU_k,finVoronoi);
		cudaDeviceSynchronize();
		cudaMemcpy(fin,finVoronoi,sizeof(int),cudaMemcpyDeviceToHost);	
		cudaCheck();
		iteraciones++;
	}
	time = clockStop(start);
	printf("Listo (%fs) %d Iteraciones\n",time,iteraciones);
	
	
//########### RETORNO A CPU ################ 
	start = clockStart(); 	
	//suavizado
	cudaMemcpy(suave_data,suaveArray_k,sizeO,cudaMemcpyDeviceToHost);
	//Voronoi
	cudaMemcpy(voro_data,voronArray_k,sizeO,cudaMemcpyDeviceToHost);
	time = clockStop(start);
	printf("Tranferencia Device -> Host(%fs)\n",time);
	
//########### LIBERA MEMORIA ############### 	
	
	cudaFree(inputArray_k);
	cudaFree(suaveArray_k);
	cudaFree(voronArray_k);
	cudaFree(cambiosMatriz_k);
	cudaFree(indicesArrayO_k);
	cudaFree(indicesArrayU_k);
	cudaFree(finVoronoi);
	free(centros);

	suave=recortar(suavei, size_x_orig, size_y_orig);
	return recortar(voro, size_x_orig, size_y_orig);
}

#endif