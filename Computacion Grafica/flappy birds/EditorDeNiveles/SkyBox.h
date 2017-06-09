#ifndef SKYBOX_H
#define SKYBOX_H

#include "Drawable.h" 
#include "Controlador.h"
class SkyBox : public Drawable{
private:
	float x;
	float y;
	float z;
	int textura;
	Drawable* plane;
	GLfloat* vertices;
	GLfloat* texvertices;
	int textureId[5];
	string* texturePath;

public:
	SkyBox(string* _texturePath, Drawable* _plane) {
		x = 0;
		y = _plane->getYcoord();
		z = _plane->getZcoord();
		plane = _plane;
		textureId[0] = Controlador::getInstance()->getNewTextureNumber();
		textureId[1] = Controlador::getInstance()->getNewTextureNumber();
		textureId[2] = Controlador::getInstance()->getNewTextureNumber();
		textureId[3] = Controlador::getInstance()->getNewTextureNumber();
		textureId[4] = Controlador::getInstance()->getNewTextureNumber();

		texturePath = _texturePath;
	};
	void updatePosition();

	float getXcoord();
	float getYcoord();
	float getZcoord();
	void setXcoord(float _x);
	void setYcoord(float _y);
	void setZcoord(float _z);
	void changeChar(Drawable*);
	void cargarTextura();

	void draw();
};

#endif SKYBOX_H