#ifndef BUILD_H
#define BUILD_H

#include <iostream>
#include <string>
#include "Drawable.h"
#include "Controlador.h"
#include "assimp\postprocess.h"

using namespace std;
class Building : public Drawable {
private:
	float x;
	float y;
	float z;
	int textura;
	Building();
	int textureId[BUILDINGS];
	string* texturePath;
	void singleDraw(float _x,float _y,float _z, float alturaMinima);

public:
	Building(float _x, float _y, float _z,string* texture){
		this->x = _x;
		this->y = _y;
		this->z = _z;
		this->texturePath = texture;
		for (int i = 0; i < BUILDINGS; i++)
			textureId[i] = Controlador::getInstance()->getNewTextureNumber();
	};
	float getXcoord();
	float getYcoord();
	float getZcoord();
	void setXcoord(float _x);
	void setYcoord(float _y);
	void setZcoord(float _z);
	void cargarTextura();
	aiVector3D CalcNormal(aiVector3D v1, aiVector3D v2, aiVector3D v3);
	void draw();

	void updatePosition();
};

#endif BUILD_H