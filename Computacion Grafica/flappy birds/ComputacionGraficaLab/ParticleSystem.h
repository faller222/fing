#ifndef PARTICLE_SYSTEM_H
#define PARTICLE_SYSTEM_H

#include <stdio.h>
#include <string>
#include "SDL_opengl.h"

#include "Drawable.h"

using namespace std;

typedef struct {
	float life;
	float fade;
	float r;
	float g;
	float b;
	float x;
	float y;
	float z;
	float xi;
	float yi;
	float zi;
	float xg;
	float yg;
	float zg;
} particles;

class ParticleSystem : public Drawable {
private:
	static bool textureLoaded;
	static char* texturePath;
	static int textureId;

	GLuint cantParticles;
	float slowdown;
	float xspeed;
	float yspeed;
	GLuint col;
	GLuint delay;
	particles *particle;
	float particleWidth;
	float particleHeight;
	bool infinito;

public:
	/* Crea un sistema de partículas con los parámetros dados */
	ParticleSystem(GLuint cantparticles, float particleWidth, float particleHeight,
		float slowdown, float life, float r, float g, float b, float xg, float yg, float zg, bool continuo);

	void draw();
	void cargarTextura();
	void restart();
	void updatePosition();
	float getXcoord() { return 0; };
	float getYcoord() { return 0; };
	float getZcoord() { return 0; };
	void setXcoord(float _x) { };
	void setYcoord(float _y) { };
	void setZcoord(float _z) { };
};

#endif PARTICLE_SYSTEM_H