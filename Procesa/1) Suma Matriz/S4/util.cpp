
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

void print_matrix(const unsigned long long * M, int width){

	for (int i = 0; i<width; i++){
		for (int j = 0; j<width; j++){
			printf("%.2f ", M[i*width+j]);
		}
		printf("\n");
	}
}

unsigned long long sum_matrix(const unsigned long long *M, int width){
	
	unsigned long long suma = 0;

	for (int i = 0; i<width*width; i++)	suma += M[i];
	
	return suma;
}


void clean_matrix(unsigned long long *M, int width){

	for (int i = 0; i<width*width; i++) M[i]=0;

}

void init_matrix(unsigned long long *M, int width){
	
	for (int i = 0; i<width*width; i++)	M[i]=rand()%100;
	
}