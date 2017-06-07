
#include "cuda_runtime.h"
#include "device_launch_parameters.h"
#include <math.h>

#include <stdio.h>

#include "CImg.h"

using namespace cimg_library;


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

void nlm_CPU(float * inputImage, float* outputImage, int width, int height, int K, int W, float sigma)
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
	


	size_t size = image.width()*image.height()*sizeof(float);
	

	// Tenemos que normalizar ver nota al final de la letra de practico

	nlm_Normal(img_matrix,255.0,image.width()*image.height());
	
	

	//

	//filtrar
		
	nlm_CPU(img_matrix, img_matrixOut, image.width(), image.height(), 10, 5, sigma);
	


	//	...
	//
	
	nlm_DesNormal(img_matrixOut,255.0,image.width()*image.height());
	//nlm_ImprimirValores(img_matrixOut,image.width()*image.height());

	CImgDisplay main_disp(image,"Fing");
	
	CImgDisplay main_disp1(imageOut,"FingNuevo");
		
	while (!main_disp.is_closed()) {
		main_disp.wait();
	}

	

	return 0;
}


