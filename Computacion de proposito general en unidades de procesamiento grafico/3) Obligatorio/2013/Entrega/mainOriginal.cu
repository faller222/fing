
#include "cuda_runtime.h"
#include "device_launch_parameters.h"
#include <math.h>
#include <stdio.h>
#include <stdlib.h>
#include "CImg.h"

using namespace cimg_library;

#define K 10
#define W 3
#define DIM_BLOQUE 32
#define SIGMA 0.9

char path[20] = "img\\fing_xl.pgm";

__int64 ctr1 = 0, ctr2 = 0, freq = 0;

void clockStart(){
	QueryPerformanceCounter((LARGE_INTEGER *)&ctr1);
}

void clockStop(const char * str){
	
	QueryPerformanceCounter((LARGE_INTEGER *)&ctr2);
	QueryPerformanceFrequency((LARGE_INTEGER *)&freq);
	printf("%s --> %fs\n",str,(ctr2 - ctr1) * 1.0 / freq);
	
}



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



__global__ void nlm_kernel(float* inputArray_GPU,float* outputArray_GPU, int width, int height)

{
	__shared__ float arrayincom[((K/2+W/2)*2+DIM_BLOQUE)*((K/2+W/2)*2+DIM_BLOQUE)];

	int marco_medio=(int)K/2+(int)W/2;
	int marco=marco_medio*2;
	int dim_arraycom=marco_medio*2+DIM_BLOQUE;
	int dim_in_out_array=marco_medio*2+width;

	// COPIO LOS PIXELES QUE LE CORRESPONDEN AL HILO
	
	for(int i=threadIdx.x; i<blockDim.x+marco ; i+=blockDim.x){
		for(int j=threadIdx.y; j<blockDim.y+marco ; j+=blockDim.y){
			arrayincom[i+j*dim_arraycom]=inputArray_GPU[(i+blockIdx.x * blockDim.x)+(j+blockIdx.y * blockDim.y)*dim_in_out_array]; // Revisar bien los indices
		}
	}
	
	// SINCRONIZCO LOS HILOS PARA ASEGURARME QUE YA TENGO TODO CARGADO EN MEMORIA COMPARTIDA
	 __syncthreads();


	// EJECUTO NLM
	
	float suma=0;
	float consta=0;
	float dist=0;
	float peso=0;

	for (int sx = (threadIdx.x+marco_medio) - K/2 ; sx < (threadIdx.x+marco_medio) + K/2 ; sx++){
		for (int sy = (threadIdx.y+marco_medio) - K/2 ; sy < (threadIdx.y+marco_medio) + K/2 ; sy++){
				for (int wx = - W/2 ; wx < W/2 ; wx++){
					for (int wy = - W/2 ; wy < W/2 ; wy++){
							dist +=powf( (arrayincom[((threadIdx.x+marco_medio) + wx) + ((threadIdx.y+marco_medio) + wy)*dim_arraycom] - arrayincom[(sx + wx)  +(sy + wy)*dim_arraycom]) ,2 );
					}
				}
				peso = expf(-dist/powf(SIGMA,2));
				suma += arrayincom[sx+sy*dim_arraycom] * peso;
				consta += peso;
				dist=0;
			}
	}

	outputArray_GPU[(blockIdx.x * blockDim.x +threadIdx.x)+(blockIdx.y * blockDim.y +threadIdx.y)*width] =suma/consta;
	
}


void nlm_Normal(float *input,float norma,int size)
{
	for(int i=0;i<size;i++){
		input[i] = input[i]/norma;
	}
}

void nlm_DesNormal(float *input,float norma,int size)
{
	for(int i=0;i<size;i++){
		input[i] = input[i]*norma;
	}
}

void nlm_ImprimirValores(float *input,int size)
{

	for(int i=0;i<size;i++){
		printf("%f  ",input[i]);
		if (i%1024==0)
			printf("\n");
	}
}


void nlm_CPU(float * inputImage, float* outputImage, int width, int height)
{
	
	float suma=0;
	float consta=0;
	float dist=0;
	float peso=0;

	for (int px = 0 ; px < width ; px++){
		for (int py = 0 ; py < height ; py++){
			for (int sx = px - K/2 ; sx < px + K/2 ; sx++){
				for (int sy = py - K/2 ; sy < py + K/2 ; sy++){
					if ((sx>0)&&(sx<width)&&(sy>0)&&(sy<height)){
						for (int wx = - W/2 ; wx < W/2 ; wx++){
							for (int wy = - W/2 ; wy < W/2 ; wy++){
								if (((px+wx>0)&&(px+wx<width)&&(py+wy>0)&&(py+wy<height))&&((sx + wx>0)&&(sx + wx<width)&&(sy + wy>0)&&(sy + wy<height)))
									dist += pow( (inputImage[(px + wx) + (py + wy)*width] - inputImage[(sx + wx)  +(sy + wy)*width]) ,2 );
							}
						}
						peso = exp(-dist/pow(SIGMA,2));
						suma += inputImage[sx+sy*width] * peso;
						consta += peso;
						dist=0;
					}
				}
			}

			outputImage[py*width + px] = suma / consta;
			suma=0;
			consta=0;
			
		}
	}
} 


int main()
{


	/* CARGA IMAGEN */

	//CImg<float> image("img\\fing.pgm");

	CImg<float> image(path);
	float * img_matrix = image.data();
	
	CImg<float> imageOutGPU(path);
	float * img_matrixOutGPU = imageOutGPU.data();

	CImg<float> imageOutCPU(path);
	float * img_matrixOutCPU = imageOutCPU.data();

	/* FIN CARGA */

	int width=image.width();
	int height=image.height();
	int k=K/2;
	int w=W/2;

	size_t size = width*height*sizeof(float);
	size_t size2=((k+w)*2+height)*((k+w)*2+width)*sizeof(float);
	

	// Tenemos que normalizar ver nota al final de la letra de practico

	nlm_Normal(img_matrix,255.0,width*height);
	
	// GPU BEGIN

	float* inputArray_GPU;
	float* outputArray_GPU;
	cudaMalloc(&(inputArray_GPU),size2);
	cudaMalloc(&(outputArray_GPU),size);

	/////////////////////////////////////
	//copiar datos de entrada a la GPU
	///////////////////////////////////////
	cudaMemset(outputArray_GPU, 0, size);
	
	cudaMemset(inputArray_GPU, 0, size2);
	

	// Copiamos a memoria de GPU teniendo encuenta que es 2*(k+w) mas grande
	int offset=(k+w)*(width+(k+w)*2);
	
	for (int i=0; i<height; i++){
		offset+=(k+w);
		cudaMemcpy(&inputArray_GPU[offset],&img_matrix[i*width],width*sizeof(float),cudaMemcpyHostToDevice);
		offset+=width+(k+w);
	}
	

	//Configurar la grilla
	dim3 tamGrid (width/DIM_BLOQUE, height/DIM_BLOQUE); //Grid dimensión
	dim3 tamBlock(DIM_BLOQUE, DIM_BLOQUE); //Block dimensión

	clockStart();	
	
	nlm_kernel<<<tamGrid, tamBlock>>>(inputArray_GPU, outputArray_GPU,width,height);
	
	cudaDeviceSynchronize();

	clockStop("GPU");
	cudaCheck();
	
	cudaMemcpy(img_matrixOutGPU,outputArray_GPU,size,cudaMemcpyDeviceToHost);
	
	
	nlm_DesNormal(img_matrixOutGPU,255.0,imageOutGPU.width()*imageOutGPU.height());


	cudaFree(outputArray_GPU);
	// GPU END

	
	// CPU BEGIN


	clockStart();	

	nlm_CPU(img_matrix, img_matrixOutCPU, image.width(), image.height());
	
	clockStop("CPU");

	nlm_DesNormal(img_matrixOutCPU,255.0,imageOutCPU.width()*imageOutCPU.height());

	// CPU END
	
	
	

	CImgDisplay main_disp(image,"Fing");
	
	CImgDisplay main_disp1(imageOutGPU,"Fing - GPU");

	CImgDisplay main_disp2(imageOutCPU,"Fing - CPU");
		
	while (!main_disp.is_closed()) {
		main_disp.wait();
	}

	return 0;
}


