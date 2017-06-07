#include "cuda_runtime.h"
#include "device_launch_parameters.h"
#include <stdio.h>
#include "CImg.h"
#include "utilImage.h"
#include "voronoi.cu"

using namespace cimg_library;

int main(int argc, char** argv){
	timeAux start = clockStart(); 
	bool CPU= false;
	int cual =  1 ;
	int cantCentros =  30000;
	char* name = "img\/fing.pgm";
	if(argc>1){
		cual =  atoi(argv[1]);		
	}else{
		printf ("1 - CPU\n2 - GPU\n_: ");
		scanf ("%d",&cual);
		CPU = (cual==1);

		printf ("Que imagen? \n\t1-L\n\t2-XL\n\t3-XXL\n\t_: ");
		scanf ("%d",&cual);  
		printf ("Ingrese la cantidad de centros: ");
		scanf ("%d",&cantCentros);
		start = clockStart(); 
	}

	if(cual==2){
		name = "img\/fing_xl.pgm";
	}else{
		if(cual==3){
			name = "img\/fing_xxl.pgm";
		}else{
			cantCentros =  30000;
		}
	}


	if(argc>2){
		cantCentros =  atoi(argv[2]);
	}
	if(argc>3){
		CPU= true;
	}
	
	CImg<float> orig(name);
	CImg<float> suave;
	CImg<float> voro;
	if(CPU){
		voro = voronoi_CPU(orig,suave,cantCentros);
		//guardarImagenC(voro,"CPU - P2 - Voronoi.png");
	}else{
		voro = voronoi_GPU(orig,suave,cantCentros);
		//guardarImagenC(voro,"GPU - P2 - Voronoi.png");
	}
	double time = clockStop(start);	
	printf("Todo el proceso (%fs)\n",time);

#if defined (_WIN32)
	CImgDisplay main_dispOri(orig,"Original");
	CImgDisplay main_dispSuave(suave,"Suavizada");
	CImgDisplay main_dispVoro(voro,"Voronoi");
	while (!(main_dispVoro.is_closed()||main_dispSuave.is_closed()||main_dispOri.is_closed()));
#else
	guardarImagenC(suave,"Suavizada.png");
	guardarImagenC(voro,"Voronoi.png");
#endif	
	
	
	return 0;
}
