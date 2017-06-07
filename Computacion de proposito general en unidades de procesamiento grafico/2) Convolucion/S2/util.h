#include <stdio.h>
#include <stdlib.h>
#include <Windows.h>

double sum_matrix(const double *M, int width);
void print_matrix(const double *M, int width);
void clean_matrix(double *M, int width);
void init_matrix(double *M, int width);
void clockStart();	
void clockStop(const char * str);
bool equal_arrays(int * a, int * b, int k);