#include "Building.h"
#include "Controlador.h"
#include "TextureManager.h"
#include <stdio.h>
#include <iostream>
#include "assimp/postprocess.h"

using namespace std;

aiVector3D Building::CalcNormal(aiVector3D v1, aiVector3D v2, aiVector3D v3){

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

float Building::getXcoord(){
	return this->x;
}

float Building::getYcoord(){
	return this->y;
}

float Building::getZcoord(){
	return this->z;
}

void Building::setXcoord(float _x){
	this->x = _x;
}

void Building::setYcoord(float _y){
	this->y = _y;
}

void Building::setZcoord(float _z){
	this->z = _z;
}

void Building::singleDraw(float _x, float _y, float _z, float alturaMinima){

	aiVector3D v1;
	aiVector3D v2;
	aiVector3D v3;
	aiVector3D normal;
	float normF[3];

	glPushMatrix();
	
	//alturaMinima /= 2.0f;

	glColor3f(1.0f, 1.0f, 1.0f);
	
	v1.Set(_x, -2.0f, _z);
	v2.Set(_x, _y, 10.0f + _z);
	v3.Set(_x, _y, _z);

	normal = CalcNormal(v1, v2, v3);
	memcpy(normF, &normal, sizeof(float)* 3);

	glBegin(GL_QUADS);
	glNormal3fv(normF); glTexCoord2f(1.0f, 0.0f);	glVertex3f(_x, -2.0f, _z);
	glNormal3fv(normF); glTexCoord2f(0.0f, 0.0f); glVertex3f(_x, -2.0f, 10.0f + _z);
	glNormal3fv(normF); glTexCoord2f(0.0f, _y / alturaMinima); glVertex3f(_x, _y, 10.0f + _z);
	glNormal3fv(normF); glTexCoord2f(1.0f, _y / alturaMinima); glVertex3f(_x, _y, _z);
	
	v1.Set(10.0f + _x, -2.0f, 10.0f + _z);
	v2.Set(10.0f + _x, -2.0f, _z);
	v3.Set(10.0f + _x, _y, _z);
	
	normal = CalcNormal(v1,v2, v3);
	memcpy(normF, &normal, sizeof(float)* 3);

	glNormal3fv(normF); glTexCoord2f(0.0f, 0.0f); glVertex3f(10.0f + _x, -2.0f, 10.0f + _z);
	glNormal3fv(normF); glTexCoord2f(1.0f, 0.0f); glVertex3f(10.0f + _x, -2.0f, _z);
	glNormal3fv(normF); glTexCoord2f(1.0f, _y / alturaMinima); glVertex3f(10.0f + _x, _y, _z);
	glNormal3fv(normF); glTexCoord2f(0.0f, _y / alturaMinima); glVertex3f(10.0f + _x, _y, 10.0f + _z);

	v1.Set(_x, _y, 10.0f + _z);
	v2.Set(_x, -2.0f, 10.0f + _z);
	v3.Set(10.0f + _x, -2.0f, 10.0f + _z);

	normal = CalcNormal(v1,v2,v3);
	memcpy(normF,&normal,sizeof(float)*3);
	
	glNormal3fv(normF); glTexCoord2f(0.0f, _y / alturaMinima); glVertex3f(_x, _y, 10.0f + _z);
	glNormal3fv(normF); glTexCoord2f(0.0f, 0.0f);	glVertex3f(_x, -2.0f, 10.0f + _z);
	glNormal3fv(normF); glTexCoord2f(1.0f, 0.0f);	glVertex3f(10.0f + _x, -2.0f, 10.0f + _z);
	glNormal3fv(normF); glTexCoord2f(1.0f, _y / alturaMinima); glVertex3f(10.0f + _x, _y, 10.0f + _z);

	v1.Set(_x, -2.0f, _z); v2.Set(_x, _y, _z); v3.Set(10.0f + _x, _y, _z);
	normal = CalcNormal(v1, v2, v3);
	memcpy(normF, &normal, sizeof(float)* 3);

	/* Cara que da a la calle */
	glNormal3fv(normF); glTexCoord2f(0.0f, 0.0f); glVertex3f(_x, -2.0f, _z);
	glNormal3fv(normF); glTexCoord2f(0.0f, _y / alturaMinima); glVertex3f(_x, _y, _z);
	glNormal3fv(normF); glTexCoord2f(1.0f, _y / alturaMinima); glVertex3f(10.0f + _x, _y, _z);
	glNormal3fv(normF); glTexCoord2f(1.0f, 0.0f); glVertex3f(10.0f + _x, -2.0f, _z);

	v1.Set(_x, _y, 10.0f + _z);	v2.Set(10.0f + _x, _y, 10.0f + _z);	v3.Set(10.0f + _x, _y, _z);
	normal = CalcNormal(v1, v2, v3); 
	memcpy(normF, &normal, sizeof(float)* 3);

	glNormal3fv(normF); glVertex3f(_x, _y, 10.0f + _z);
	glNormal3fv(normF); glVertex3f(10.0f + _x, _y, 10.0f + _z);
	glNormal3fv(normF); glVertex3f(10.0f + _x, _y, _z);
	glNormal3fv(normF); glVertex3f(_x, _y, _z);
	glEnd();

	glPopMatrix();
}


void Building::draw(){
	TextureManager* TM = TextureManager::Inst();

	GLfloat mat_specular[] = { 1.0, 1.0, 1.0, 1.0 };
	GLfloat mat_ambient[] = { 1, 1, 1, .0 };
	GLfloat mat_diffuse[] = { 1, 1, 1, 1.0 };
	GLfloat low_shininess[] = { 15 };

	glMaterialfv(GL_FRONT, GL_AMBIENT, mat_ambient);
	glMaterialfv(GL_FRONT, GL_DIFFUSE, mat_diffuse);
	glMaterialfv(GL_FRONT, GL_SPECULAR, mat_specular);
	glMaterialfv(GL_FRONT, GL_SHININESS, low_shininess);

	glTranslated(this->x, this->y, this->z);

	int prueba = 32765;
	float altura = 10.0f;
	float alturaMinima = 12.0f;
	int offset = -250;

	Controlador::getInstance()->activarTexturas();
	for (int j = 1; j < 10; j++)
	{

		for (int i = 0; i < 50; i++)
		{
			altura = (prueba*i*j) % 13 + alturaMinima;

			TM->BindTexture(textureId[(int)altura % BUILDINGS]);
			singleDraw(15.0f * (float)i + offset, altura, -15.0f - 15.0f * (float)j, alturaMinima);
		}
		offset += 7;
	}
	Controlador::getInstance()->desactivarTexturas();
}

void Building::updatePosition(){

};

void Building::cargarTextura(){
	TextureManager* TM = TextureManager::Inst();
	for (int i = 0; i < BUILDINGS; i++) {
		TM->LoadTexture(texturePath[i].data(), textureId[i], true, GL_BGR_EXT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR_MIPMAP_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_MIRRORED_REPEAT);
		glTexEnvf(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_MODULATE);
	}

}
