#include <iostream>
#include "SDL_opengl.h"

#include "Plane.h"
#include "Camera.h"

using namespace std;

/* Auxiliares */
struct vec3 {
	float x;
	float y;
	float z;
};

vec3 crossProd(vec3 v1, vec3 v2) {
	vec3 res;
	res.x = v1.y * v2.z - v2.y * v1.z;
	res.y = v2.x * v1.z - v1.x * v2.z;
	res.z = v1.x * v2.y - v2.x * v1.y;
	return res;
}

vec3 normalize(vec3 v) {
	vec3 result;
	float modulo = sqrt(v.x*v.x + v.y*v.y + v.z*v.z);
	result.x = v.x / modulo;
	result.y = v.y / modulo;
	result.z = v.z / modulo;
	return result;
}

vec3 multMatr(vec3 v, float* m) {
	vec3 result;
	result.x = v.x * m[0] + v.y * m[4] + v.z * m[8];
	result.y = v.x * m[1] + v.y * m[5] + v.z * m[9];
	result.z = v.x * m[2] + v.y * m[6] + v.z * m[10];
	return result;
}

/* Cámara */
Camera::Camera(float cpx, float cpy, float cpz, float clx, float cly, float clz, float cux, float cuy, float cuz) {
	this->setCamera(cpx, cpy, cpz, clx, cly, clz, cux, cuy, cuz);
}

void Camera::setCamera(float cpx, float cpy, float cpz, float clx, float cly, float clz, float cux, float cuy, float cuz) {
	this->setCameraPosition(cpx, cpy, cpz);
	this->setCameraLookAt(clx, cly, clz);
	this->setCameraUp(cux, cuy, cuz);
}

void Camera::setCameraPosition(float x, float y, float z) {
	this->cameraPosX = x;
	this->cameraPosY = y;
	this->cameraPosZ = z;
}

void Camera::setCameraLookAt(float x, float y, float z) {
	this->cameraLookX = this->oldPosX = x;
	this->cameraLookY = this->oldPosY = y;
	this->cameraLookZ = z;
}

void Camera::setCameraUp(float x, float y, float z) {
	this->cameraUpX = x;
	this->cameraUpY = y;
	this->cameraUpZ = z;
}

void Camera::setCameraLookingPoint(Drawable* lp, float distancia) {
	this->lookingPoint = lp;

	// Posición inicial de la cámara
	this->setCameraPosition(-(float)distancia, 6.0f, 0.0f);
	this->setCameraLookAt(lp->getXcoord(), lp->getYcoord(), lp->getZcoord());
	this->setCameraUp(0.0f, 1.0f, 0.0f);
}

void Camera::lookAt() {
	
	/* Actualizo el punto hacia el cual mira la cámara */
	this->cameraLookX = this->lookingPoint->getXcoord();
	this->cameraLookY = this->lookingPoint->getYcoord();
	this->cameraLookZ = this->lookingPoint->getZcoord();

	float tempX = this->lookingPoint->getXcoord();
	this->cameraPosX += (tempX - this->oldPosX);
	this->oldPosX = tempX;

	float tempY = this->lookingPoint->getYcoord();
	this->cameraPosY += (tempY - this->oldPosY);
	this->oldPosY = tempY;

	gluLookAt(this->cameraPosX, this->cameraPosY, this->cameraPosZ,
		this->cameraLookX, this->cameraLookY, this->cameraLookZ,
		this->cameraUpX, this->cameraUpY, this->cameraUpZ);
	
}

float Camera::getPosX() {
	return (float) this->cameraPosX;
}

float Camera::getPosY() {
	return (float) this->cameraPosY;
}

float Camera::getPosZ() {
	return (float) this->cameraPosZ;
}

void Camera::setRotationFromMouseRelativePosition(float mousex, float mousey) {
	vec3 camDir, camDirAux, camUp, camRight;

	//Saco los vectores dir, up y right y los normalizo
	camDir.x = this->cameraPosX - this->cameraLookX;
	camDir.y = this->cameraPosY - this->cameraLookY;
	camDir.z = this->cameraPosZ - this->cameraLookZ;
	camDirAux = normalize(camDir);

	camUp.x = this->cameraUpX;
	camUp.y = this->cameraUpY;
	camUp.z = this->cameraUpZ;
	camUp = normalize(camUp);

	camRight = crossProd(camDirAux, camUp);
	camRight = normalize(camRight);

	// Creo primera matriz de rotación, que usa mousex y gira alrededor de up
	Quat rotMouseX(mousex, camUp.x, camUp.y, camUp.z);

	// Creo segunda matriz de rotación, que usa mousey y gira alrededor de right
	Quat rotMouseY(mousey, camRight.x, camRight.y, camRight.z);

	// La rotación final es la composición de rotMouseX y rotMouseY
	rotacion = rotMouseX * rotMouseY;

	// Obtengo la matriz de rotación
	float* matriz = rotacion.getMatrix();

	// Roto el vector de dirección
	camDir = multMatr(camDir, matriz);

	// Recupero la posición de la camara
	this->cameraPosX = this->cameraLookX + camDir.x;
	this->cameraPosY = this->cameraLookY + camDir.y;
	this->cameraPosZ = this->cameraLookZ + camDir.z;

	// Actualizo los otros vectores
	vec3 auxUp = { 0.0f, 1.0f, 0.0f };

	camDir = normalize(camDir);
	camRight = crossProd(camDir, auxUp);
	camUp = crossProd(camRight, camDir);

	this->cameraUpX = camUp.x;
	this->cameraUpY = camUp.y;
	this->cameraUpZ = camUp.z;

	/* Hack para arreglar el problema de profundidad del sistema de partículas y el avión */
	if (this->cameraPosX >= this->cameraLookX) {
		((Plane*)this->lookingPoint)->profundidadSistemaAntes();
	}
	else {
		((Plane*)this->lookingPoint)->profundidadAvionAntes();
	}
}

void Camera::restartPosition() {
	this->setCameraPosition(this->lookingPoint->getXcoord()-(float)CAMERA_DISTANCE, 6.0f, 0.0f);
	this->setCameraLookAt(this->lookingPoint->getXcoord(), this->lookingPoint->getYcoord(), this->lookingPoint->getZcoord());
	this->setCameraUp(0.0f, 1.0f, 0.0f);
	((Plane*)this->lookingPoint)->profundidadAvionAntes();
}