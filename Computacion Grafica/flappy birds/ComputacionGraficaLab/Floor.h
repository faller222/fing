#ifndef FLOOR_H
#define FLOOR_H

#include <string>
#include "Drawable.h"

class Floor : public Drawable {
private:
	char* texName;
	float x;
	float y;
	float z;
	int textura;
	Floor();
	GLfloat* vertices;
	GLfloat* texvertices;

public:
	Floor(char* tex, float _x, float _y, float _z){
		this->texName = tex;
		this->x = _x;
		this->y = _y;
		this->z = _z;
	};
	float getXcoord();
	float getYcoord();
	float getZcoord();
	void setXcoord(float _x);
	void setYcoord(float _y);
	void setZcoord(float _z);

	void cargarTextura();

	void draw();

	void updatePosition();
};

#endif FLOOR_H