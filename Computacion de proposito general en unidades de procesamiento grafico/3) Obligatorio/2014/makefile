all: nvcc
	
nvcc: CLS	
	nvcc -o voronoi main.cu utilImage.cpp -O2 -L/usr/X11R6/lib -lm -lpthread -lX11 -m32 -arch=compute_20 -code=sm_20 
	
check: CLS	
	nvcc -o voronoi main.cu utilImage.cpp -O2 -L/usr/X11R6/lib -lm -lpthread -lX11 -m32 -arch=compute_20 -code=sm_20 -Xptxas -v
	
	
memory: 
	valgrind --leak-check=full -v ./voronoi
	
CLS:
	clear
	clear   
   
clean: rm -rf *.o 