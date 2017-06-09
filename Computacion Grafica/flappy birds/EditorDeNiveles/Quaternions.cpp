#include <iostream>

#include <stdio.h>
#include <math.h>
#include "Quaternions.h"

using namespace std;

Quat::Quat(float ang, float x, float y, float z) {
	setValues(ang, x, y, z);
}

Quat::Quat() {
	this->setValues(0.0f, 0.0f, 1.0f, 0.0f);
}

void Quat::setValues(float ang, float x, float y, float z) {
	float angle = ang*PI / 180.0f;
	angle /= 2.0;

	// Normalizo el eje de rotación
	float h = sqrt(x*x + y*y + z*z);
	x /= h;
	y /= h;
	z /= h;

	float sinAngle = sin(angle);

	// Creo el vector
	this->q[0] = cos(angle); // w
	this->q[1] = sinAngle*x; // x
	this->q[2] = sinAngle*y; // y
	this->q[3] = sinAngle*z; // z

	// Normalizo el cuaternión
	h = sqrt(q[0] * q[0] + q[1] * q[1] + q[2] * q[2] + q[3] * q[3]);
	q[0] /= h;
	q[1] /= h;
	q[2] /= h;
	q[3] /= h;
}

Quat Quat::operator* (Quat & s) {
	Quat result;

	/* Primer vector*/
	float a = this->q[0]; float b = this->q[1]; float c = this->q[2]; float d = this->q[3];

	/* Segundo vector */
	float e = s.q[0]; float f = s.q[1]; float g = s.q[2]; float h = s.q[3];

	result.q[0] = a*e - b*f - c*g - d*h;
	result.q[1] = b*e + a*f + c*h - d*g;
	result.q[2] = a*g - b*h + c*e + d*f;
	result.q[3] = a*h + b*g - c*f + d*e;

	/* Normalizo */
	float m = sqrt(result.q[0] * result.q[0] +
		result.q[1] * result.q[1] +
		result.q[2] * result.q[2] +
		result.q[3] * result.q[3]);

	result.q[0] /= m;
	result.q[1] /= m;
	result.q[2] /= m;
	result.q[3] /= m;

	return result;
}

float* Quat::getMatrix() {
	float w = this->q[0];
	float x = this->q[1];
	float y = this->q[2];
	float z = this->q[3];

	float* matrix = new float[16];

	matrix[0] = 1.0f - 2.0f * (y*y + z*z);
	matrix[1] = 2.0f * (x*y + w*z);
	matrix[2] = 2.0f * (x*z - w*y);
	matrix[3] = 0.0f;
	matrix[4] = 2.0f * (x*y - w*z);
	matrix[5] = 1.0f - 2.0f * (x*x + z*z);
	matrix[6] = 2.0f * (y*z + w*x);
	matrix[7] = 0.0f;
	matrix[8] = 2.0f * (x*z + w*y);
	matrix[9] = 2.0f * (y*z - w*x);
	matrix[10] = 1.0f - 2.0f * (x*x + y*y);
	matrix[11] = 0.0f;
	matrix[12] = 0.0f;
	matrix[13] = 0.0f;
	matrix[14] = 0.0f;
	matrix[15] = 1.0f;

	return matrix;
}