#ifndef CAMERA_H
#define CAMERA_H

#include "Drawable.h"
#include "Quaternions.h"

class Camera {
private:
	Drawable* lookingPoint; // El objeto al cual mira la camara, necesito una referencia porque este puede cambiar su posición constantemente

	float cameraPosX, cameraPosY, cameraPosZ;
	float cameraLookX, cameraLookY, cameraLookZ;
	float cameraUpX, cameraUpY, cameraUpZ;
	float oldPosX;
	float oldPosY;
	Quat rotacion;

public:
	Camera(float cpx, float cpy, float cpz, float clx, float cly, float clz, float cux, float cuy, float cuz);
	float getPosX();
	float getPosY();
	float getPosZ();
	void setCamera(float cpx, float cpy, float cpz, float clx, float cly, float clz, float cux, float cuy, float cuz);
	void setCameraPosition(float x, float y, float z);
	void setCameraLookAt(float x, float y, float z);
	void setCameraUp(float x, float y, float z);
	void setCameraLookingPoint(Drawable* lp, float d);
	void lookAt();
	void setRotationFromMouseRelativePosition(float mousex, float mousey);
};

#endif CAMERA_H