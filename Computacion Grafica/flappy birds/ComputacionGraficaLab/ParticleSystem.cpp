#include <time.h>
#include <iostream>

#include <assimp/postprocess.h>
#include "ParticleSystem.h"
#include "TextureManager.h"
#include "Controlador.h"

using namespace std;

bool ParticleSystem::textureLoaded = false;
int ParticleSystem::textureId = 1;
char* ParticleSystem::texturePath = TEXTURE_PARTICLE;

ParticleSystem::ParticleSystem(GLuint cantparticles, float particleWidth, float particleHeight,
	float slowdown, float life, float r, float g, float b, float xg, float yg, float zg, bool infinito){

	this->infinito = infinito;
	this->cantParticles = cantparticles;
	this->slowdown = slowdown;
	this->particle = new particles[cantparticles];
	this->particleHeight = particleHeight;
	this->particleWidth = particleWidth;

	for (GLuint loop = 0; loop < cantparticles; loop++)
	{
		particle[loop].life = life;
		particle[loop].fade = float(rand() % 100) / 1000.0f + 0.003f;
		particle[loop].r = r;
		particle[loop].g = g;
		particle[loop].b = b;
		particle[loop].xi = float((rand() % 60) - 30.0f);
		particle[loop].yi = float((rand() % 60) - 30.0f);
		particle[loop].zi = float((rand() % 60) - 30.0f);
		particle[loop].xg = xg;
		particle[loop].yg = yg;
		particle[loop].zg = zg;
		particle[loop].x = 0.0f;
		particle[loop].y = 0.0f;
		particle[loop].z = 0.0f;
	}
}

void ParticleSystem::cargarTextura() {
	if (textureLoaded == false) {
		textureId = Controlador::getInstance()->getNewTextureNumber();
		TextureManager* TM = TextureManager::Inst();
		if (TM->LoadTexture(this->texturePath, this->textureId, true, GL_BGR_EXT)){
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR_MIPMAP_LINEAR);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_R, GL_REPEAT);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
			glTexEnvf(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_MODULATE);
		}
		this->textureLoaded = true;
	}
}


void ParticleSystem::draw() {
	glDisable(GL_DEPTH_TEST);
	Controlador::getInstance()->desactivarLight();

	glBlendFunc(GL_SRC_ALPHA, GL_ONE);
	
	glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
	glHint(GL_POINT_SMOOTH_HINT, GL_NICEST);
	Controlador::getInstance()->activarTexturas();
	
	TextureManager::Inst()->BindTexture(this->textureId);
	glPushMatrix();
	for (GLuint loop = 0; loop < cantParticles; loop++) {
		float x = particle[loop].x;
		float y = particle[loop].y;
		float z = particle[loop].z;
		glColor4f(particle[loop].r, particle[loop].g, particle[loop].b, particle[loop].life);
		glPushMatrix();
		glBegin(GL_QUADS);

		glTexCoord2f(1.0f, 1.0f); glVertex3f(x + (this->particleWidth / 2.0f), y + (this->particleHeight / 2.0f), z);
		glTexCoord2f(1.0f, 0.0f); glVertex3f(x + (this->particleWidth / 2.0f), y - (this->particleHeight / 2.0f), z);
		glTexCoord2f(0.0f, 0.0f); glVertex3f(x - (this->particleWidth / 2.0f), y - (this->particleHeight / 2.0f), z);
		glTexCoord2f(0.0f, 1.0f); glVertex3f(x - (this->particleWidth / 2.0f), y + (this->particleHeight / 2.0f), z);

		glTexCoord2f(1.0f, 1.0f); glVertex3f(x - (this->particleWidth / 2.0f), y - (this->particleHeight / 2.0f), z);
		glTexCoord2f(0.0f, 1.0f); glVertex3f(x + (this->particleWidth / 2.0f), y - (this->particleHeight / 2.0f), z);
		glTexCoord2f(0.0f, 0.0f); glVertex3f(x + (this->particleWidth / 2.0f), y + (this->particleHeight / 2.0f), z);
		glTexCoord2f(1.0f, 0.0f); glVertex3f(x - (this->particleWidth / 2.0f), y + (this->particleHeight / 2.0f), z);
		
		glTexCoord2f(1.0f, 1.0f); glVertex3f(x, y - (this->particleHeight / 2.0f), z - (this->particleWidth / 2.0f));
		glTexCoord2f(0.0f, 1.0f); glVertex3f(x, y - (this->particleHeight / 2.0f), z + (this->particleWidth / 2.0f));
		glTexCoord2f(0.0f, 0.0f); glVertex3f(x, y + (this->particleHeight / 2.0f), z + (this->particleWidth / 2.0f));
		glTexCoord2f(1.0f, 0.0f); glVertex3f(x, y + (this->particleHeight / 2.0f), z - (this->particleWidth / 2.0f));

		glTexCoord2f(1.0f, 1.0f); glVertex3f(x, y + (this->particleHeight / 2.0f), z + (this->particleWidth / 2.0f));
		glTexCoord2f(1.0f, 0.0f); glVertex3f(x, y - (this->particleHeight / 2.0f), z + (this->particleWidth / 2.0f));
		glTexCoord2f(0.0f, 0.0f); glVertex3f(x, y - (this->particleHeight / 2.0f), z - (this->particleWidth / 2.0f));
		glTexCoord2f(0.0f, 1.0f); glVertex3f(x, y + (this->particleHeight / 2.0f), z - (this->particleWidth / 2.0f));

		glTexCoord2f(1.0f, 1.0f); glVertex3f(x - (this->particleHeight / 2.0f), y, z - (this->particleWidth / 2.0f));
		glTexCoord2f(0.0f, 1.0f); glVertex3f(x - (this->particleHeight / 2.0f), y, z + (this->particleWidth / 2.0f));
		glTexCoord2f(0.0f, 0.0f); glVertex3f(x + (this->particleHeight / 2.0f), y, z + (this->particleWidth / 2.0f));
		glTexCoord2f(1.0f, 0.0f); glVertex3f(x + (this->particleHeight / 2.0f), y, z - (this->particleWidth / 2.0f));

		glTexCoord2f(1.0f, 1.0f); glVertex3f(x + (this->particleHeight / 2.0f), y, z + (this->particleWidth / 2.0f));
		glTexCoord2f(1.0f, 0.0f); glVertex3f(x - (this->particleHeight / 2.0f), y, z + (this->particleWidth / 2.0f));
		glTexCoord2f(0.0f, 0.0f); glVertex3f(x - (this->particleHeight / 2.0f), y, z - (this->particleWidth / 2.0f));
		glTexCoord2f(0.0f, 1.0f); glVertex3f(x + (this->particleHeight / 2.0f), y, z - (this->particleWidth / 2.0f));

		glEnd();
		glPopMatrix();
	}
	glPopMatrix();

	glColor3f(1.0f, 1.0f, 1.0f);

	Controlador::getInstance()->desactivarTexturas();
	
	glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	Controlador::getInstance()->activarLight();
	glEnable(GL_DEPTH_TEST);
}

void ParticleSystem::updatePosition() {
	for (GLuint loop = 0; loop < cantParticles; loop++) {
		if (this->infinito || (!this->infinito && particle[loop].life >= 0)) {
			particle[loop].x += particle[loop].xi / (slowdown * 1000);
			particle[loop].y += particle[loop].yi / (slowdown * 1000);
			particle[loop].z += particle[loop].zi / (slowdown * 1000);

			particle[loop].xi += particle[loop].xg;
			particle[loop].yi += particle[loop].yg;
			particle[loop].zi += particle[loop].zg;

			particle[loop].life -= particle[loop].fade;

			if (particle[loop].life < 0.0f && this->infinito) {
				particle[loop].life = 1.0f;
				particle[loop].fade = float(rand() % 100) / 1000.0f + 0.003f;
				particle[loop].x = 0.0f;
				particle[loop].y = 0.0f;
				particle[loop].z = 0.0f;
				particle[loop].xi = float((rand() % 60) - 30.0f);
				particle[loop].yi = float((rand() % 60) - 30.0f);
				particle[loop].zi = float((rand() % 60) - 30.0f);
			}
		}
	}
}

void ParticleSystem::restart() {

	for (GLuint loop = 0; loop < cantParticles; loop++)
	{
		particle[loop].life = 1.0f;
		particle[loop].x = 0.0f;
		particle[loop].y = 0.0f;
		particle[loop].z = 0.0f;
		particle[loop].xi = float((rand() % 60) - 30.0f);
		particle[loop].yi = float((rand() % 60) - 30.0f);
		particle[loop].zi = float((rand() % 60) - 30.0f);
	}
}