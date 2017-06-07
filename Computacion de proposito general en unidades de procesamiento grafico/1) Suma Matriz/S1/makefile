all: 
	clear
	nvcc -c -m32 -arch=compute_20 -code=sm_20 util.cpp kernel.cu
	nvcc kernel.cu -o sumaMatriz util.o -m32 -arch=compute_20 -code=sm_20
   
clean: rm -rf *.o 