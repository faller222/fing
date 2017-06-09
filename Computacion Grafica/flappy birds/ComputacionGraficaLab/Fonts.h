#ifndef FONTS_H
#define FONTS_H

#include <map>
#include "FTGLTextureFont.h"
#include "FTGLExtrdFont.h"

using namespace std;

class Fonts {
private:
	static Fonts* instance;
	Fonts();

	map<char*, FTFont*> *fuentes;
	unsigned int currentSize;

public:
	static Fonts* getInstance();

	void registrarFuente(char* nombre, char* ruta, unsigned int size, int type = 0);
	FTFont* obtenerFuente(char* nombre);

};

#endif FONTS_H