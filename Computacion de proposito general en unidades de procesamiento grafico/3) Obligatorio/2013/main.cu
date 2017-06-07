
#include "cuda_runtime.h"
#include "device_launch_parameters.h"
#include <math.h>

#include <stdio.h>

#include "CImg.h"

using namespace cimg_library;

#define K 10
#define W 3
#define DIM_BLOQUE 32


__global__ void nlm_kernel(float* inputArray_GPU,float* outputArray_GPU, int width, int height)

{
	__shared__ int arrayincom[((K+W)*2+DIM_BLOQUE)*((K+W)*2+DIM_BLOQUE)];

	int x=blockIdx.x * blockDim.x +threadIdx.x;
	int y=blockIdx.y * blockDim.y +threadIdx.y;
	int pos = y*(blockDim.x*gridDim.x)+ x;

	int x_mas=x+(K+W);
	int x_menos=x-(K+W);
	int y_mas=y+(K+W);
	int y_menos=y-(K+W);

	if((x+blockIdx.x)<x_mas){
		arrayincom[(x+blockIdx.x)*(blockDim.x*gridDim.x)+y]=inputArray_GPU[(x+blockIdx.x)*(blockDim.x*gridDim.x)+y];
		if((y+blockIdx.y)<y_mas){
			//COPIO PUNTA BAJA
		}
		if((y-blockIdx.y)>y_menos){
			//COPIO PUNTA ALTA
		}
	}
	if((x-blockIdx.x)>x_menos){
		 // COPIO MEDIO
		if((y+blockIdx.y)<y_mas){
			//COPIO PUNTA BAJA
		}
		if((y-blockIdx.y)>y_menos){
			//COPIO PUNTA ALTA
		}
	}

	if((y+blockIdx.y)<y_mas){
			//COPIO MEDIO 
	}
	if((y-blockIdx.y)>y_menos){
			//COPIO PUNTA ALTA
	}
	arrayincom[threadIdx.x+ratio]=inputArray[pos];
	
	 __syncthreads();

	int pValor = 0;	
	for (int j=0;j<MASK_SIZE;j++){
			 pValor = pValor + arrayincom[threadIdx.x+j] * mask[j];	
	}

	atomicAdd(&outputArray[pos],pValor);
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
		input[i] = trunc(input[i]*norma);
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

void nlm_CPU(float * inputImage, float* outputImage, int width, int height, float sigma)
{
	int largo_arreglo=width*height;
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
									dist += pow( (inputImage[(px + wx)*height + (py + wy)] - inputImage[(sx + wx)*height  +(sy + wy)]) ,2 );
							}
						}
						peso = exp(-dist/pow(sigma,2));
						suma += inputImage[sx*height+sy] * peso;
						consta += peso;
						dist=0;
						//peso=0;
					}
				}
			}

			outputImage[px*height + py] = suma / consta;
			suma=0;
			consta=0;
			
		}
	}
} 


int main()
{
    

	float sigma =0.9;
	
	CImg<float> image("img\\fing.pgm");
	float * img_matrix = image.data();


	CImg<float> imageOut("img\\fing.pgm");
	float * img_matrixOut = imageOut.data();
	
	int width=image.width();
	int height=image.height();
	int k=10;
	int w=3;

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
		cudaMemcpy(&inputArray_GPU[offset],&img_matrix[i*width],width,cudaMemcpyHostToDevice);
		offset+=width+(k+w);
	}
    
	//Configurar la grilla
	dim3 tamGrid (width/32, height/32); //Grid dimensión
	dim3 tamBlock(32, 32); //Block dimensión
	//int tamBlock = 32*32;
	//int tamGrid = width*height/tamBlock;

	nlm_kernel<<<tamGrid, tamBlock>>>(inputArray_GPU, outputArray_GPU,k ,w ,width, height);
	



	// GPU END




	//

	//filtrar
		
	//nlm_CPU(img_matrix, img_matrixOut, image.width(), image.height(), 10, 5, sigma);
	


	//	...
	//
	
	//nlm_DesNormal(img_matrixOut,255.0,image.width()*image.height());
	//nlm_ImprimirValores(img_matrixOut,image.width()*image.height());

	CImgDisplay main_disp(image,"Fing");
	
	CImgDisplay main_disp1(imageOut,"FingNuevo");
		
	while (!main_disp.is_closed()) {
		main_disp.wait();
	}

	

	return 0;
}


