#include <stdio.h>
#include <stdlib.h>
#include <Windows.h>

unsigned long long sum_matrix(const unsigned long long *M, int width);
void print_matrix(const unsigned long long *M, int width);
void clean_matrix(unsigned long long *M, int width);
void init_matrix(unsigned long long *M, int width);
void clockStart();	
void clockStop(const char * str);