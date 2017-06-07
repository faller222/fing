#include "util.h"

using namespace std;

	timeval startTime;
	timeval endTime;
	long seconds, useconds;
	double duration;

/*__int64 ctr1 = 0, ctr2 = 0, freq = 0;*/

void clockStart(){
	gettimeofday(&startTime, NULL);
	/*QueryPerformanceCounter((LARGE_INTEGER *)&ctr1);*/
}

void clockStop(const char * str){
		
	gettimeofday(&endTime, NULL);

	seconds  = endTime.tv_sec  - startTime.tv_sec;
	useconds = endTime.tv_usec - startTime.tv_usec;

	duration = seconds + useconds/1000000.0;
	//printf("%5.6f seconds\n", duration);
	
	printf("%s : %fs\n",str,duration);

	/*
	QueryPerformanceCounter((LARGE_INTEGER *)&ctr2);
	QueryPerformanceFrequency((LARGE_INTEGER *)&freq);
	printf("%s : %fs\n",str,(ctr2 - ctr1) * 1.0 / freq);*/
}

void print_matrix(const float * M,  int width){
	for (int i = 0; i<width; i++){
		for (int j = 0; j<width; j++){
			printf("%.2f ", M[i*width+j]);
		}
		printf("\n");
	}
}


float sum_matrix(const float *M,  int width){
	
	float sumaTotal = 0;
	float sumaParcial = 0;
	long int quad = width*width;
	for (long int i = 0; i<quad; i++){
		if(i%width==0){
			sumaTotal += sumaParcial;
			sumaParcial = 0;			
		}		
		sumaParcial += M[i];
	}
	sumaTotal += sumaParcial;

	return sumaTotal;
}


void clean_matrix(float *M,  int width){
	long int quad = width*width;
	for (long int i = 0; i<quad; i++) M[i]=0;
}

void init_matrix(float *M,  int width){
	long int quad = width*width;
	for (long int i = 0; i<quad; i++)	M[i]=rand()%100;
}