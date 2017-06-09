#ifndef OBSTACLE_H
#define OBSTACLE_H

#include "Drawable.h"
#include "Modelable.h"
#include "Controlador.h"
#include <string>

class Obstacle : public Drawable {
private:
	static int textureId;
	static char* texturePath;
	static bool textureLoaded;

	float originX;
	float posX, posY, posZ;
	Drawable* personaje;
	bool mostrar;
	bool collisionCheckingEnabled;
	float lastObstaclePosition;
	void dibujarPared(float y0, float y1, float x1, float x2, float z1, float z2);

public:
	Obstacle (float _x, float _y, float _z, Drawable* p) {
		this->originX = _x;
		this->posX = _x;
		this->posY = _y;
		this->posZ = _z;
		this->personaje = p;
		this->mostrar = false;
		this->collisionCheckingEnabled = true;
	};
	void setLastObstaclePosition(float last);
	void updatePosition();
	void draw();
	float getXcoord();
	float getYcoord();
	float getZcoord();
	void setXcoord(float _x);
	void setYcoord(float _y);
	void setZcoord(float _z);
	void cargarTextura();
	void action();

	void mostrarObstaculos(float mover);
	void ocultarObstaculos();
	void reinicializar();
};

#endif OBSTACLE_H
