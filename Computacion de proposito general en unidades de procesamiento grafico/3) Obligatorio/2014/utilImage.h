#ifndef UTIL_H
#define UTIL_H

#include "CImg.h"
#include <stdio.h>
#include <time.h>
#include <stdlib.h>

#define SIZE_MASK 5
#define MARCO 2 // --> (SIZE_MASK-1)/2

typedef struct timeAux{
	double init;
} timeAux;

timeAux clockStart();

double clockStop(timeAux start);

bool* generarCentros(const int cant ,int x, int y);

/**
Este procedimiento retorna otra imagen con Padding 
y agrega un marco de tamanio la mitad de la mascara 
*/
cimg_library::CImg<float> prepararImagen(const cimg_library::CImg<float> orig);

/**
Este procedimiento retorna otra imagen 
con el valor promedio de los vecinos
PreCondicion: imagen ya preparada
*/
cimg_library::CImg<float> roundImagen(const cimg_library::CImg<float> orig);

/**
Este procedimiento retorna otra imagen 
con voronoi aplicado
*/
cimg_library::CImg<float> voronoi(const cimg_library::CImg<float> orig, const int cant_centros,bool* centros);

/**
	Recorta la imagen desde el origen.
	Pre condicion: size_x < orig.width
	Pre condicion: size_y < orig.height
*/
cimg_library::CImg<float> recortar(const cimg_library::CImg<float> orig, const int size_x, const int size_y);

/**
Este procedimiento guarda la imagen en el lugar del fileName
*/
void guardarImagenC(const cimg_library::CImg<float> source,const char* filename);

/**
Este procedimientos compara dos imagenes
*/
bool checkMatriz(const cimg_library::CImg<float> im1,const cimg_library::CImg<float> im2);

/**
Este procedimientos retorna la diferencia de imagenes, en una imagen
*/
cimg_library::CImg<float> diffImg(const cimg_library::CImg<float> im1, const cimg_library::CImg<float> im2);

#endif
