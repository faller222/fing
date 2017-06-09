#include <iostream>
#include "Fonts.h"

Fonts* Fonts::instance = NULL;

Fonts::Fonts() {
	this->fuentes = new map<char*, FTFont*>();
	this->currentSize = 0u;
}

Fonts* Fonts::getInstance() {
	if (instance == NULL) {
		instance = new Fonts();
	}
	return instance;
}

void Fonts::registrarFuente(char* nombre, char* ruta, unsigned int size, int type) {
	FTFont* fuente = NULL;
	if (type == 0) {
		// Usar FTGLTextureFont
		fuente = new FTGLTextureFont(ruta);
	}
	else if (type == 1) {
		// Usar FTGLExtrudeFont
		fuente = new FTGLExtrdFont(ruta);
		fuente->Depth(10.0f);
	}

	fuente->UseDisplayList(true);
	fuente->FaceSize(size);

	// Esto fuerza la precarga de las texturas
	fuente->Render("Hola!");

	(*this->fuentes)[nombre] = fuente;
}

FTFont* Fonts::obtenerFuente(char* nombre) {
	FTFont* returnValue = this->fuentes->at(nombre);
	return returnValue;
}