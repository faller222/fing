#include <iostream>
#include "Fonts.h"

Fonts* Fonts::instance = NULL;

Fonts::Fonts() {
	this->fuentes = new map<char*, FTGLTextureFont*>();
	this->currentSize = 0u;
}

Fonts* Fonts::getInstance() {
	if (instance == NULL) {
		instance = new Fonts();
	}
	return instance;
}

void Fonts::registrarFuente(char* nombre, char* ruta, unsigned int size) {
	FTGLTextureFont* fuente = new FTGLTextureFont(ruta);
	fuente->UseDisplayList(true);
	fuente->FaceSize(size);

	// Esto fuerza la precarga de las texturas
	fuente->Render("Hola!");

	(*this->fuentes)[nombre] = fuente;
}

FTGLTextureFont* Fonts::obtenerFuente(char* nombre) {
	FTGLTextureFont* returnValue = this->fuentes->at(nombre);
	return returnValue;
}