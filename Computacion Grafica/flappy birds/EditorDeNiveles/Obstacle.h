#ifndef OBSTACLE_H
#define OBSTACLE_H

#include "Drawable.h"
#include "Controlador.h"
#include <string>

class Obstacle : public Drawable {
private:
	float originX;
	float posX, posY, posZ;
	int textureId;
	string texturePath;
	bool collisionCheckingEnabled;
	void dibujarPared(float y0, float y1, float x1, float x2, float z1, float z2);
	bool moveI;
	bool moveD;
	bool moveArriba;
	bool moveAbajo;
	float vel;
	bool mostrarV;

public:
	Obstacle (string texPath, float _x, float _y, float _z) {
		this->originX = _x;
		this->posX = _x;
		this->posY = _y;
		this->posZ = _z;
		this->textureId = Controlador::getInstance()->getNewTextureNumber();
		this->texturePath = texPath;
		this->collisionCheckingEnabled = true;
		this->vel= PLANE_SPEED;
		this->mostrarV = true;
		this->moveAbajo = false;
		this->moveArriba = false;

	};

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
	void moverD();
	void moverI();
	void setMoveD(bool);
	void setMoveI(bool);
	void setMoveArriba(bool sucker);
	void setMoveAbajo(bool sucker);
	void mostrarObstaculos(float mover);
	void mostrar(bool);
	void reinicializar();
	void moverArriba();
	void moverAbajo();

};

#endif OBSTACLE_H
