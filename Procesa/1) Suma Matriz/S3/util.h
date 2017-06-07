#include <stdio.h>
#include <stdlib.h>
/*#include <Windows.h>*/
#include <sys/time.h>

float sum_matrix(const float *M,  int width);
void print_matrix(const float *M,  int width);
void clean_matrix(float *M,  int width);
void init_matrix(float *M,  int width);
void clockStart();	
void clockStop(const char * str);