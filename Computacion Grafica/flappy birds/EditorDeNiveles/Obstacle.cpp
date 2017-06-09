#include <iostream>
#include <stdio.h>
#include "assimp/postprocess.h"

#include "Controlador.h"
#include "Drawable.h"
#include "Obstacle.h"
#include "StatePlaying.h"
#include "TextureManager.h"

using namespace std;

aiVector3D CalcNormal(aiVector3D v1, aiVector3D v2, aiVector3D v3){
	float v1x, v1y, v1z, v2x, v2y, v2z;
	float nx, ny, nz;
	float vLen;

	aiVector3t<float> Result;

	// Calculate vectors
	v1x = v1.x - v2.x;
	v1y = v1.y - v2.y;
	v1z = v1.z - v2.z;

	v2x = v2.x - v3.x;
	v2y = v2.y - v3.y;
	v2z = v2.z - v3.z;

	// Get cross product of vectors
	nx = (v1y * v2z) - (v1z * v2y);
	ny = (v1z * v2x) - (v1x * v2z);
	nz = (v1x * v2y) - (v1y * v2x);

	// Normalise final vector
	vLen = sqrt((nx * nx) + (ny * ny) + (nz * nz));

	Result.x = (float)(nx / vLen);
	Result.y = (float)(ny / vLen);
	Result.z = (float)(nz / vLen);

	return Result;
}

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
	normal = CalcNormal(v1, v2, v3);
	memcpy(normF, &normal, sizeof(float)* 3);

	glNormal3fv(normF); glTexCoord2f(0.0f, 0.0f); glVertex3f(x1, y0, z1);
	glNormal3fv(normF); glTexCoord2f((z2-z1) / maxTexWidth, 0.0f); glVertex3f(x1, y0, z2);
	glNormal3fv(normF); glTexCoord2f((z2-z1) / maxTexWidth, (y1 - y0) / maxTexHeight); glVertex3f(x1, y1, z2);
	glNormal3fv(normF); glTexCoord2f(0.0f, (y1 - y0) / maxTexHeight); glVertex3f(x1, y1, z1);

	/* Cara detras al avión */
	v1.Set(x2, y0, z1); v2.Set(x2, y1, z1); v3.Set(x2, y1, z2);
	normal = CalcNormal(v1, v2, v3);
	memcpy(normF, &normal, sizeof(float)* 3);

	glNormal3fv(normF); glTexCoord2f(0.0f, 0.0f); glVertex3f(x2, y0, z1);
	glNormal3fv(normF); glTexCoord2f(0.0f, (y1 - y0) / maxTexHeight); glVertex3f(x2, y1, z1);
	glNormal3fv(normF); glTexCoord2f((z2 - z1) / maxTexWidth, (y1 - y0) / maxTexHeight); glVertex3f(x2, y1, z2);
	glNormal3fv(normF); glTexCoord2f((z2 - z1) / maxTexWidth, 0.0f); glVertex3f(x2, y0, z2);
	
	/* Cara derecha del avión */
	v1.Set(x1, y0, z2); v2.Set(x2, y0, z2); v3.Set(x2, y1, z2);
	normal = CalcNormal(v1, v2, v3);
	memcpy(normF, &normal, sizeof(float)* 3);

	glNormal3fv(normF); glTexCoord2f(0.0f, (y1 - y0) / maxTexHeight); glVertex3f(x1, y0, z2);
	glNormal3fv(normF); glTexCoord2f((x2 - x1) / maxTexWidth, (y1 - y0) / maxTexHeight); glVertex3f(x2, y0, z2);
	glNormal3fv(normF); glTexCoord2f((x2 - x1) / maxTexWidth, 0.0f); glVertex3f(x2, y1, z2);
	glNormal3fv(normF); glTexCoord2f(0.0f, 0.0f); glVertex3f(x1, y1, z2);

	/* Cara izquierda del avión */
	v1.Set(x1, y0, z1); v2.Set(x1, y1, z1); v3.Set(x2, y1, z1);
	normal = CalcNormal(v1, v2, v3);
	memcpy(normF, &normal, sizeof(float)* 3);

	glNormal3fv(normF); glTexCoord2f(0.0f, 0.0f); glVertex3f(x1, y0, z1);
	glNormal3fv(normF); glTexCoord2f(0.0f, (y1 - y0) / maxTexHeight); glVertex3f(x1, y1, z1);
	glNormal3fv(normF); glTexCoord2f((x2 - x1) / maxTexWidth, (y1 - y0) / maxTexHeight); glVertex3f(x2, y1, z1);
	glNormal3fv(normF); glTexCoord2f((x2 - x1) / maxTexWidth, 0.0f); glVertex3f(x2, y0, z1);

	/* Cara superior */
	v1.Set(x1, y1, z2);	v2.Set(x2, y1, z2);	v3.Set(x2, y1, z1);
	normal = CalcNormal(v1, v2, v3);
	memcpy(normF, &normal, sizeof(float)* 3);

	glTexCoord2f((z2-z1) / maxTexWidth, 0.0f); glVertex3f(x1, y1, z2);
	glTexCoord2f((z2-z1) / maxTexWidth, (x2-x1) / maxTexHeight); glVertex3f(x2, y1, z2);
	glTexCoord2f(0.0f, (x2-x1) / maxTexHeight); glVertex3f(x2, y1, z1);
	glTexCoord2f(0.0f, 0.0f); glVertex3f(x1, y1, z1);

	/* Cara inferior */
	v1.Set(x1, y0, z2); v2.Set(x1, y0, z1); v3.Set(x2, y0, z1);
	normal = CalcNormal(v1, v2, v3);
	memcpy(normF, &normal, sizeof(float)* 3);

	glTexCoord2f((z2 - z1) / maxTexWidth, 0.0f); glVertex3f(x1, y0, z2);
	glTexCoord2f(0.0f, 0.0f); glVertex3f(x1, y0, z1);
	glTexCoord2f(0.0f, (x2 - x1) / maxTexHeight); glVertex3f(x2, y0, z1);
	glTexCoord2f((z2 - z1) / maxTexWidth, (x2 - x1) / maxTexHeight); glVertex3f(x2, y0, z2);

	glEnd();
}

void Obstacle::draw() {
		TextureManager* TM = TextureManager::Inst();
		if (mostrarV){
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

			dibujarPared(-2.0f, this->posY - OBSTACLE_RADIUS - 5.0f, -5.0f, 5.0f, -5.0f, 5.0f);
			dibujarPared(this->posY - OBSTACLE_RADIUS - 5.0f, this->posY - OBSTACLE_RADIUS, -5.5f, 5.5f, -5.5f, 5.5f);
			dibujarPared(this->posY + OBSTACLE_RADIUS, this->posY + OBSTACLE_RADIUS + 5.0f, -5.5f, 5.5f, -5.5f, 5.5f);
			dibujarPared(this->posY + OBSTACLE_RADIUS + 5.0f, 200.0f, -5.0f, 5.0f, -5.0f, 5.0f);

			glPopMatrix();

			Controlador::getInstance()->desactivarTexturas();
		}
};

void Obstacle::cargarTextura() {
	
	TextureManager* TM = TextureManager::Inst();
	if (TM->LoadTexture(texturePath.data(), textureId, true, GL_BGR_EXT)){
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR_MIPMAP_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_R, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
		glTexEnvf(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_MODULATE);
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
void Obstacle::setMoveD(bool fuck){

	moveD = fuck;
};
void Obstacle::setMoveI(bool shit){
	moveI = shit;
};

void Obstacle::setMoveArriba(bool sucker){
	moveArriba = sucker;
};
void Obstacle::setMoveAbajo(bool cock){
	moveAbajo = cock;
};

void Obstacle::moverD() {
	posX += vel;


}

void Obstacle::moverI() {
	if (posX>=100)
		posX -= vel;


}

void Obstacle::moverArriba() {
	posY += vel;


}
void Obstacle::moverAbajo() {
	if (posY>=10)
		posY -= vel;


}

void Obstacle::updatePosition(){
	if (moveI) {
		moverI();
	}
	else if (moveD){
		moverD();
	}
	else if (moveArriba){
		moverArriba();
	}
	else if (moveAbajo)
		moverAbajo();

};



/* Esta función se llama al arrancar el juego para posicionar todos los obstaculos correctamente */
void Obstacle::mostrarObstaculos(float mover) {
	this->posX += mover;
}

void Obstacle::mostrar(bool bastard) {
	this->mostrarV = bastard;
}

void Obstacle::reinicializar() {
	this->posX = this->originX;
	this->collisionCheckingEnabled = true;
}