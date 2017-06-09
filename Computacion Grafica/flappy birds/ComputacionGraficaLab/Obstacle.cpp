#include <iostream>
#include <stdio.h>

#include "Controlador.h"
#include "Drawable.h"
#include "Hud.h"
#include "Plane.h"
#include "Obstacle.h"
#include "StatePlaying.h"
#include "TextureManager.h"

using namespace std;

bool Obstacle::textureLoaded = false;
int Obstacle::textureId = 1;
char* Obstacle::texturePath = TEXTURE_OBSTACLE;

void Obstacle::dibujarPared(float y0, float y1, float x1, float x2, float z1, float z2) {
	// El punto de referencia de esta función en la altura es siempre el piso en la posición del obstáculo
	aiVector3D v1, v2, v3, normal;
	float normF[3];
	float maxTexWidth = 8.0f;
	float maxTexHeight = 8.0f;

	glColor3f(1.0f, 1.0f, 1.0f);
	
	glBegin(GL_QUADS);
	/* Cara frontal al avión */
	v1.Set(x1, y0, z1); v2.Set(x1, y0, z2); v3.Set(x1, y1, z2);
	normal = Modelable::CalcNormal(v1, v2, v3);
	memcpy(normF, &normal, sizeof(float)* 3);

	glNormal3fv(normF); glTexCoord2f(0.0f, 0.0f); glVertex3f(x1, y0, z1);
	glNormal3fv(normF); glTexCoord2f((z2-z1) / maxTexWidth, 0.0f); glVertex3f(x1, y0, z2);
	glNormal3fv(normF); glTexCoord2f((z2-z1) / maxTexWidth, (y1 - y0) / maxTexHeight); glVertex3f(x1, y1, z2);
	glNormal3fv(normF); glTexCoord2f(0.0f, (y1 - y0) / maxTexHeight); glVertex3f(x1, y1, z1);

	/* Cara detras al avión */
	v1.Set(x2, y0, z1); v2.Set(x2, y1, z1); v3.Set(x2, y1, z2);
	normal = Modelable::CalcNormal(v1, v2, v3);
	memcpy(normF, &normal, sizeof(float)* 3);

	glNormal3fv(normF); glTexCoord2f(0.0f, 0.0f); glVertex3f(x2, y0, z1);
	glNormal3fv(normF); glTexCoord2f(0.0f, (y1 - y0) / maxTexHeight); glVertex3f(x2, y1, z1);
	glNormal3fv(normF); glTexCoord2f((z2 - z1) / maxTexWidth, (y1 - y0) / maxTexHeight); glVertex3f(x2, y1, z2);
	glNormal3fv(normF); glTexCoord2f((z2 - z1) / maxTexWidth, 0.0f); glVertex3f(x2, y0, z2);
	
	/* Cara derecha del avión */
	v1.Set(x1, y0, z2); v2.Set(x2, y0, z2); v3.Set(x2, y1, z2);
	normal = Modelable::CalcNormal(v1, v2, v3);
	memcpy(normF, &normal, sizeof(float)* 3);

	glNormal3fv(normF); glTexCoord2f(0.0f, (y1 - y0) / maxTexHeight); glVertex3f(x1, y0, z2);
	glNormal3fv(normF); glTexCoord2f((x2 - x1) / maxTexWidth, (y1 - y0) / maxTexHeight); glVertex3f(x2, y0, z2);
	glNormal3fv(normF); glTexCoord2f((x2 - x1) / maxTexWidth, 0.0f); glVertex3f(x2, y1, z2);
	glNormal3fv(normF); glTexCoord2f(0.0f, 0.0f); glVertex3f(x1, y1, z2);

	/* Cara izquierda del avión */
	v1.Set(x1, y0, z1); v2.Set(x1, y1, z1); v3.Set(x2, y1, z1);
	normal = Modelable::CalcNormal(v1, v2, v3);
	memcpy(normF, &normal, sizeof(float)* 3);

	glNormal3fv(normF); glTexCoord2f(0.0f, 0.0f); glVertex3f(x1, y0, z1);
	glNormal3fv(normF); glTexCoord2f(0.0f, (y1 - y0) / maxTexHeight); glVertex3f(x1, y1, z1);
	glNormal3fv(normF); glTexCoord2f((x2 - x1) / maxTexWidth, (y1 - y0) / maxTexHeight); glVertex3f(x2, y1, z1);
	glNormal3fv(normF); glTexCoord2f((x2 - x1) / maxTexWidth, 0.0f); glVertex3f(x2, y0, z1);

	/* Cara superior */
	v1.Set(x1, y1, z2);	v2.Set(x2, y1, z2);	v3.Set(x2, y1, z1);
	normal = Modelable::CalcNormal(v1, v2, v3);
	memcpy(normF, &normal, sizeof(float)* 3);

	glTexCoord2f((z2-z1) / maxTexWidth, 0.0f); glVertex3f(x1, y1, z2);
	glTexCoord2f((z2-z1) / maxTexWidth, (x2-x1) / maxTexHeight); glVertex3f(x2, y1, z2);
	glTexCoord2f(0.0f, (x2-x1) / maxTexHeight); glVertex3f(x2, y1, z1);
	glTexCoord2f(0.0f, 0.0f); glVertex3f(x1, y1, z1);

	/* Cara inferior */
	v1.Set(x1, y0, z2); v2.Set(x1, y0, z1); v3.Set(x2, y0, z1);
	normal = Modelable::CalcNormal(v1, v2, v3);
	memcpy(normF, &normal, sizeof(float)* 3);

	glTexCoord2f((z2 - z1) / maxTexWidth, 0.0f); glVertex3f(x1, y0, z2);
	glTexCoord2f(0.0f, 0.0f); glVertex3f(x1, y0, z1);
	glTexCoord2f(0.0f, (x2 - x1) / maxTexHeight); glVertex3f(x2, y0, z1);
	glTexCoord2f((z2 - z1) / maxTexWidth, (x2 - x1) / maxTexHeight); glVertex3f(x2, y0, z2);

	glEnd();
}

void Obstacle::draw() {
	if (this->mostrar) {
		TextureManager* TM = TextureManager::Inst();

		Controlador::getInstance()->activarTexturas();

		GLfloat mat_specular[] = { 1.0, 1.0, 1.0, 1.0 };
		GLfloat mat_ambient[] = { 1, 1, 1, .0 };
		GLfloat mat_diffuse[] = { 1, 1, 1, 1.0 };
		GLfloat low_shininess[] = { 15 };

		glMaterialfv(GL_FRONT, GL_AMBIENT, mat_ambient);
		glMaterialfv(GL_FRONT, GL_DIFFUSE, mat_diffuse);
		glMaterialfv(GL_FRONT, GL_SPECULAR, mat_specular);
		glMaterialfv(GL_FRONT, GL_SHININESS, low_shininess);

		glPushMatrix();
		glTranslatef(this->posX, -2.0f, this->posZ);
		TM->BindTexture(this->textureId);

		dibujarPared(-2.0f, this->posY - OBSTACLE_RADIUS - 5.0f, -3.0f, 3.0f, -5.0f, 5.0f);
		dibujarPared(this->posY - OBSTACLE_RADIUS - 5.0f, this->posY - OBSTACLE_RADIUS, -3.5f, 3.5f, -5.5f, 5.5f);
		dibujarPared(this->posY + OBSTACLE_RADIUS, this->posY + OBSTACLE_RADIUS + 5.0f, -3.5f, 3.5f, -5.5f, 5.5f);
		dibujarPared(this->posY + OBSTACLE_RADIUS + 5.0f, 200.0f, -3.0f, 3.0f, -5.0f, 5.0f);

		glPopMatrix();

		Controlador::getInstance()->desactivarTexturas();
	}
};

void Obstacle::cargarTextura() {
	if (textureLoaded == false) {
		textureId = Controlador::getInstance()->getNewTextureNumber();
		TextureManager* TM = TextureManager::Inst();
		if (TM->LoadTexture(texturePath, textureId, true, GL_BGR_EXT)){
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR_MIPMAP_LINEAR);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_R, GL_REPEAT);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
			glTexEnvf(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_MODULATE);
		}
	}
}

float Obstacle::getXcoord() {
	return this->posX;
};

float Obstacle::getYcoord() {
	return this->posY;
};

float Obstacle::getZcoord() {
	return this->posZ;
};

void Obstacle::setXcoord(float _x){
	this->posX = _x;
}

void Obstacle::setYcoord(float _y){
	this->posY = _y;
}

void Obstacle::setZcoord(float _z){
	this->posZ = _z;
}

void Obstacle::action() {
}

void Obstacle::updatePosition() {
	/* Cálculo de colisiones */
	if (this->collisionCheckingEnabled) {
		float xPers = this->personaje->getXcoord();
		float yPers = this->personaje->getYcoord();
		float zPers = this->personaje->getZcoord();

		if (xPers + 5.0f >= this->posX - 3.5f && xPers - 5.0f <= this->posX + 3.5f) {
			if (yPers <= this->posY - OBSTACLE_RADIUS || yPers + 3.0f >= this->posY + OBSTACLE_RADIUS) {
				// Colisión detectada
				StatePlaying::getInstance()->colisionDetectada();
				this->collisionCheckingEnabled = false;
			}
		}
		else if (xPers > this->posX + 3.0f && mostrar) {
			// Aumento el puntaje!
			Hud::getInstance()->masPunto();
			
			// Mando el obstáculo mucho más adelante
			this->posX += this->lastObstaclePosition;
		}
	}
}

/* Esta función se llama al arrancar el juego para posicionar todos los obstaculos correctamente */
void Obstacle::mostrarObstaculos(float mover) {
	this->posX += mover;
	this->mostrar = true;
}

void Obstacle::ocultarObstaculos() {
	this->mostrar = false;
}

void Obstacle::reinicializar() {
	this->posX = this->originX;
	this->collisionCheckingEnabled = true;
}

void Obstacle::setLastObstaclePosition(float last) {
	this->lastObstaclePosition = last;
}