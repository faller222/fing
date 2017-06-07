all: 
	clear
	nvcc convolucion.cu -o Convolucion util.cpp -m32 -arch=compute_20 -code=sm_20
   
test:
	clear
	nvcc convolucion2D.cu -o Convolucion2D util.cpp -m32 -arch=compute_20 -code=sm_20
	
ej1:
	clear
	nvcc ej1Informe.cu -o Ej1Informe util.cpp -m32 -arch=compute_20 -code=sm_20

ej2:
	clear
	nvcc ej2Informe.cu -o Ej2Informe util.cpp -m32 -arch=compute_20 -code=sm_20
	
ej3:
	clear
	nvcc ej2Informe.cu -o Ej3Informe util.cpp -m32 -arch=compute_20 -code=sm_20
   
clean: rm -rf *.o 