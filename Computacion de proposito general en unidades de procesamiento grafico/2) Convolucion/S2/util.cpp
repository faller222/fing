
#include "util.h"


__int64 ctr1 = 0, ctr2 = 0, freq = 0;

void clockStart(){
	QueryPerformanceCounter((LARGE_INTEGER *)&ctr1);
}

void clockStop(const char * str){
	
	QueryPerformanceCounter((LARGE_INTEGER *)&ctr2);
	QueryPerformanceFrequency((LARGE_INTEGER *)&freq);
	printf("%s --> %fs\n",str,(ctr2 - ctr1) * 1.0 / freq);
	
}

void print_matrix(const double * M, int width){

	for (int i = 0; i<width; i++){
		for (int j = 0; j<width; j++){
			printf("%.2f ", M[i*width+j]);
		}
		printf("\n");
	}
}

double sum_matrix(const double *M, int width){
	
	double suma = 0;

	for (int i = 0; i<width*width; i++)	suma += M[i];
	
	return suma;
}


void clean_matrix(double *M, int width){

	for (int i = 0; i<width*width; i++) M[i]=0;

}

void init_matrix(double *M, int width){
	
	for (int i = 0; i<width*width; i++)	M[i]=rand()%100;
	
}

bool equal_arrays(int * input1, int* intput2, int k)
{
	int i =0;
	bool error = false;
	for(;i<k;i++)
	{
		error = error || (input1[i] != intput2[i]); 
		if (error){
			printf("GPU[%d]=%d\n",i,input1[i]);
			printf("CPU[%d]=%d\n",i,intput2[i]);
		}
	}
	return !error;
}