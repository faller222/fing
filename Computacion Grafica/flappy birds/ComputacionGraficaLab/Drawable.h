#ifndef DRAWABLE_H
#define DRAWABLE_H

#include "SDL_opengl.h"

class Drawable {
public:
	// método abstracto para dibujar objeto drawable especifico.
	virtual void draw() = 0;
	virtual void updatePosition() {};
	virtual float getXcoord() = 0;
	virtual float getYcoord() = 0;
	virtual float getZcoord() = 0;
	virtual void setXcoord(float _x) = 0;
	virtual void setYcoord(float _y) = 0;
	virtual void setZcoord(float _z) = 0;
	virtual void cargarTextura() = 0;
};

#endif DRAWABLE_H