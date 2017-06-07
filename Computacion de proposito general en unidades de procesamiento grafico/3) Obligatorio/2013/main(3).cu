
#include "cuda_runtime.h"
#include "device_launch_parameters.h"
#include <math.h>

#include <stdio.h>

#include "CImg.h"

using namespace cimg_library;


void nlm_CPU(float * inputImage, float* outputImage, int width, int height, int K, int W, float sigma)
{
	int largo_arreglo=width*height;
	float suma=0;
	float consta=0;
	float dist=0;

	for (int px = 0 ; px < width ; px++){
		for (int py = 0 ; py < height ; py++){
			for (int sx = px - K/2 ; sx < px + K/2 ; sx++){
				for (int sy = py - K/2 ; sy < py + K/2 ; sy++){
					if ((sx>0)&&(sx<largo_arreglo)&&(sy>0)&&(sy<largo_arreglo)){
						for (int wx = - W/2 ; wx < W/2 ; wx++){
							for (int wy = - W/2 ; wy < W/2 ; wy++){
								if ((wx>0)&&(wx<largo_arreglo)&&(wy>0)&&(wy<largo_arreglo))
									dist += pow( (inputImage[(px + wx)*height + (py + wy)] - inputImage[(sx + wx)*height  +(sy + wy)]) ,2 );
							}
						}
						suma += inputImage[sx*height+sy] * exp(-dist/pow(sigma,2));
						consta += exp(-dist/pow(sigma,2));
						dist=0;
					}
				}
			}

			outputImage[px*height + py] = suma / consta;
			suma=0;
			consta=0;
		}
	}

    /*    int i, j;
        for( i = 0; i<SIZE_X;i++)
        {   
                for( j =0; j<MASK_SIZE;j++)
                        {      
                                int position = i-(int)(MASK_SIZE/2) + j;
                                if(position>=0 && position<SIZE_X)
                                        ouputArray[i] += inputArray[position] * mask[j];
                        }       
        }*/
} 


int main()
{
    
	float sigma =0.9f;

	CImg<float> image("img\\fing.pgm");

	//CImg<float> imageout("img\\fing.pgm");
	
	float * img_matrix = image.data();


	size_t size = image.width()*image.height()*sizeof(float);
	
	//filtrar

	float * img_matrixOut=(float*)malloc(size);


	nlm_CPU(img_matrix, img_matrixOut, image.width(), image.height(), 10, 3, sigma);

	//	...
	//
	//CImg<float> imageOut(img_matrixOut);
	
	CImgDisplay main_disp(image,"Fing");
	//CImgDisplay main_disp1(imageOut,"FingNuevo");
		
	while (!main_disp.is_closed()) {
		main_disp.wait();
	}

	return 0;
}


