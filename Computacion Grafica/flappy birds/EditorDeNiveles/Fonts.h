#ifndef FONTS_H
#define FONTS_H

#include <map>
#include "FTGLTextureFont.h"

using namespace std;

class Fonts {
private:
	static Fonts* instance;
	Fonts();

	map<char*, FTGLTextureFont*> *fuentes;
	unsigned int currentSize;

public:
	static Fonts* getInstance();

	void registrarFuente(char* nombre, char* ruta, unsigned int size);
	FTGLTextureFont* obtenerFuente(char* nombre);

};

#endif FONTS_H