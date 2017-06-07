#include "utilImage.h"
#include "getTime.h"

#include <algorithm>


#define PAD 32

/**
Referencia:
http://cimg.sourceforge.net/reference/structcimg__library_1_1CImg.html
*/

timeAux clockStart(){
	timeAux res;
	res.init = getRealTime();
	return res;
};

double clockStop(timeAux start){
	return  getRealTime() - start.init;
};


using namespace cimg_library;
int fixPadAt(const int toFix, const int sizePad);

CImg<float> prepararImagen(const CImg<float> orig){
	timeAux start = clockStart();
	
	//tamanios originales
	int size_x = orig.width();
	int size_y = orig.height();
	//nuevos tamanios (Padding)
	int new_size_x = fixPadAt(size_x,PAD);
	int new_size_y =  fixPadAt(size_y,PAD); 
	//nuevos tamanios (Mask)
	new_size_x = new_size_x + 2 * MARCO;
	new_size_y = new_size_y + 2 * MARCO;
 
	CImg<float> result(new_size_x,new_size_y,1,1);
	
	int aux_i;
	int aux_j;
	int aux_val;
	
	cimg_forXY(result,i,j){
		/*Hacemos espejo*/
		if( i < MARCO ){
			aux_i =  MARCO - i;
		}else{
			aux_val = size_x + MARCO;
			/*Hacemos offset*/
			if(i < aux_val ){
				aux_i = i - MARCO;
			}else{
				/*Hacemos espejo inverso*/
				aux_i = 2 * size_x - i + MARCO - 2;
			}
		}
		
		/*Identico para J*/
		if( j < MARCO ){
			aux_j = MARCO - j;
		}else{
			aux_val = size_y + MARCO;
			if(j < aux_val ){
				aux_j = j - MARCO;
			}else{
				aux_j = 2 * size_y - j + MARCO - 2;
			}
		}
		//seteo el valor correspondiente
		if(aux_j==-1||aux_i==-1){
			result(i,j)=0;
		}else{
			result(i,j)=orig(aux_i,aux_j);
		}
	}	
	double time = clockStop(start);
	printf("Preparar Imagen (%fs)\n", time);
	return result;
};

/*Pre condicion: orig ya preparada*/
CImg<float> roundImagen(const CImg<float> orig){
	timeAux start = clockStart(); 
	printf("Ejecutando Suavizado...  ");	
	
	//tamanios originales
	int size_x = orig.width() - SIZE_MASK + 1;
	int size_y = orig.height() - SIZE_MASK + 1;
	
	CImg<float> result(size_x,size_y,1,1);
	
	float aux_val;
	
	cimg_forXY(result,i,j){
		aux_val=0;
		//Recorro vecinos
		for(int x =0 ; x<SIZE_MASK ;x++){
			for(int y =0 ; y<SIZE_MASK ;y++){			
				aux_val+=orig(i + x, j + y);
			}
		}
		aux_val = aux_val / (SIZE_MASK * SIZE_MASK);
		result(i,j) = aux_val;
	}	
	
	double time = clockStop(start);
	printf("Listo (%fs)\n",time);
	return result;
};

CImg<float> voronoi(const CImg<float> orig, const int cant_centros,bool* centros){
	timeAux start = clockStart();
	printf("Ejecutando Voronoi...  ");
	
	int sizeX = orig.width();
	int sizeY = orig.height();
	
	CImg<float> result(sizeX,sizeY,1,1);
	
	int* indicesArrayO=(int *) malloc(sizeX*sizeY*sizeof(int));
	int* indicesArrayU=(int *) malloc(sizeX*sizeY*sizeof(int));
	//inicializacion
	for(int idx=0;idx<sizeX*sizeY;idx++){
		indicesArrayO[idx]=-1;
		indicesArrayU[idx]=-1;
	}
	
	
	
	
	int fin=1;//distinto de 0
	int cenX;
	int cenY;
	int auxX;
	int auxY;
	int aux_dist1;
	int aux_dist2;
	int nid;
	int tid;
	int iteraciones=0;
	
	while(fin){		
		iteraciones++;
		fin=0;
		
		cimg_forXY(result,x,y){
			
			tid = x + sizeX * y;
				
			if(centros[tid]){
				if(indicesArrayO[tid]==-1){
					indicesArrayO[tid]=tid;
					indicesArrayU[tid]=tid;
				}
				
				//calculo de indices central
				cenX = indicesArrayO[tid] % sizeX;
				cenY = indicesArrayO[tid] / sizeX;	
				
				//para cada vecino
				for(int i=(x-1); i<=(x+1);i++){
					for(int j=(y-1); j<=(y+1);j++){
						//dentro de los limites de la imagen
						if( i>=0 && i<sizeX &&  j>=0 && j<sizeY ){
							nid = i + sizeX * j;
							if(indicesArrayO[nid]==-1){
								indicesArrayU[nid]=indicesArrayO[tid];
							}else{
								if(indicesArrayO[nid]!=indicesArrayO[tid]){
									//calculo de indices vecino
									auxX = indicesArrayO[nid] % sizeX;
									auxY = indicesArrayO[nid] / sizeX;	
								
									//calculo distancia al actual(NID)
									aux_dist1= i * i + auxX * auxX - 2 * i * auxX ;
									aux_dist1+= j * j + auxY * auxY - 2 * j * auxY ;
									//calculo distancia al vecino(TID)
									aux_dist2= i * i + cenX * cenX - 2 * i * cenX ;
									aux_dist2+= j * j + cenY * cenY - 2 * j * cenY ;
												
									if(aux_dist1 > aux_dist2){
										indicesArrayU[nid]=indicesArrayO[tid];
									}
								}
							}
						}
					}
				}
				centros[tid]=false;
			}
		}	
		
		cimg_forXY(result,x,y){
			tid = x + sizeX * y;
			int bla;
			if(indicesArrayU[tid]!=indicesArrayO[tid]){
				bla=tid;
				indicesArrayO[tid]=indicesArrayU[tid];
				centros[tid]=true;
				fin++;
			}
			
			indicesArrayU[tid]=indicesArrayO[tid];
			
			cenX = indicesArrayO[tid] % sizeX;
			cenY = indicesArrayO[tid] / sizeX;
			if(indicesArrayO[tid]!=-1){
				result(x,y)=orig(cenX, cenY);
			}		
		}
		
	}
		
	double time = clockStop(start);
	printf("Listo (%fs), %d Iteraciones\n", time,iteraciones);
	return result;
};

/*
	Pre condicion: size_x < orig.width
	Pre condicion: size_y < orig.height
*/
CImg<float> recortar(const CImg<float> orig, const int size_x, const int size_y){
	timeAux start = clockStart();
	CImg<float> result(size_x,size_y,1,1);
	cimg_forXY(result,i,j){
		result(i,j)=orig(i, j);	
	}		
	double time = clockStop(start);
	printf("Recortar Imagen (%fs)\n", time);
	return result;
};



void guardarImagenC(const CImg<float> source,const char* filename){
	timeAux start = clockStart();
	source.save_png(filename);
	double time = clockStop(start);
	printf("Guardar Imagen (%fs)\n", time);
};

bool checkMatriz(const CImg<float> im1, const CImg<float> im2){
	bool result =  im1.is_sameXY(im2);
	int pixDiff=0;
	if(result){
		cimg_forXY(im1,i,j){
			if(im1(i,j)!=im2(i, j)){
				result=false;
				pixDiff++;
			}
		}		
	}
	if(!result){
		printf("Diferencia en %d Pizeles\n",pixDiff);
	}
	return result;
};

CImg<float> diffImg(const CImg<float> im1, const CImg<float> im2){
	CImg<float> result(im1.width(),im1.height(),1,1);
	if(im1.is_sameXY(im2)){
		float aux_val;
		cimg_forXY(result,i,j){
			if(im1(i,j)!=im2(i, j)){
				aux_val=255;
			}else{
				aux_val=0;
			}
			result(i,j) = aux_val;
		}			
	}
	return result;
};

int fixPadAt(const int toFix, const int sizePad){
	int aux = toFix%sizePad;
	if (aux)
		return toFix + sizePad - aux;
	else
		return toFix;
};

bool* generarCentros(const int cant ,int x, int y){
	timeAux start = clockStart();
	printf("Generando %d centros...  ",cant);
	int size = x*y;
	int i;
	int* arr= (int *) malloc(size*sizeof(int));
	
	for( i = 0; i < size; ++i){
		arr[i] = i;
	}
	
	std::random_shuffle(arr, arr+size);

	bool* salida=(bool *) malloc(size*sizeof(bool));
	for ( i = 0; i<size;i++){	
		salida[i]=false;
	}

	int aux;
	for ( i = 0; i<cant;i++){
		salida[ arr[i] ] = true;
	}
	free(arr);

	double time = clockStop(start);
	printf("Listo (%fs)\n",time);
	return salida;
}



