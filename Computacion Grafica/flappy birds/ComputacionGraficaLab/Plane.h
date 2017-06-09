#ifndef PLANE_H
#define PLANE_H

#include <list>
#include <string>

#include "Drawable.h"
#include "Modelable.h"
#include "Controlador.h"
#include "ParticleSystem.h"

using namespace std;

struct PuntoEstela {
	ParticleSystem* estela;
	float posX;
	float posY;
	float posZ;
};

class Plane : public Drawable, Modelable {
private:
	float posX, posY, posZ;
	float rotation;
	float velX, velY;
	int textureId;
	string texturePath;
	ParticleSystem* motorI;
	ParticleSystem* motorD;
	ParticleSystem* explosion;
	list<PuntoEstela*> estela;
	bool explode;
	Drawable* piso;
	bool avionAntes;

public:

	Plane(string modelPath, string texPath, Drawable* p);
	void updatePosition() ;
	void draw();
	float getXcoord();
	float getYcoord();
	float getZcoord();
	void setXcoord(float _x);
	void setYcoord(float _y);
	void setZcoord(float _z);
	void cargarTextura();
	void dibujarEstelaYMotores();
	void profundidadSistemaAntes();
	void profundidadAvionAntes();

	void saltar();
	void detener();
	void mover();

	void reinicializar();
};

#endif PLANE_H
