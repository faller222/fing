#include "Floor.h"
#include "Controlador.h"
#include "TextureManager.h"
#include <stdio.h>
#include <iostream>

using namespace std;

void Floor::cargarTextura() {
	this->textura = Controlador::getInstance()->getNewTextureNumber();
	TextureManager* TM = TextureManager::Inst();
	if (!TM->LoadTexture(this->texName, textura, true, GL_BGR_EXT))
		printf("No encontro Textura chequear Floor.Cpp linea 9\n");
	else{
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR_MIPMAP_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_MIRRORED_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_R, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexEnvf(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_MODULATE);
	}
	
	/* Creo el modelo y lo cargo */
	struct vert {
		float x;
		float y;
		float z;
	} v;

	struct texvert {
		float x;
		float y;
	} tv;

	v.y = this->getYcoord();

	float xstart = -WORLD_X_DISTANCE / 2.0f;
	float zstart = -WORLD_Z_DISTANCE / (80.0f / 3.0f);

	this->vertices = new float[40 * 3 * 4 * 3];
	this->texvertices = new float [40 * 3 * 4 * 2];

	for (int i = 0; i < 3; i++) {
		for (int j = 0; j < 40; j++) {
			v.x = xstart; v.z = zstart;
			tv.x = 0.0f; tv.y = 0.0f;
			memcpy(vertices, &v, sizeof(float)* 3);
			memcpy(texvertices, &tv, sizeof(float)* 2);
			texvertices += 2;
			vertices += 3;

			v.x = xstart; v.z = zstart + 25.0f;
			tv.x = 0.0f; tv.y = 1.0f;
			memcpy(vertices, &v, sizeof(float)* 3);
			memcpy(texvertices, &tv, sizeof(float)* 2);
			texvertices += 2;
			vertices += 3;

			v.x = xstart + 25.0f; v.z = zstart + 25.0f;
			tv.x = 1.0f; tv.y = 1.0f;
			memcpy(vertices, &v, sizeof(float)* 3);
			memcpy(texvertices, &tv, sizeof(float)* 2);
			texvertices += 2;
			vertices += 3;

			v.x = xstart + 25.0f; v.z = zstart;
			tv.x = 1.0f; tv.y = 0.0f;
			memcpy(vertices, &v, sizeof(float)* 3);
			memcpy(texvertices, &tv, sizeof(float)* 2);
			texvertices += 2;
			vertices += 3;
			
			xstart += 25.0f;
		}

		xstart = -WORLD_X_DISTANCE / 2.0f;
		zstart += 25.0f;
	}

	vertices -= 40 * 3 * 4 * 3;
	texvertices -= 40 * 3 * 4 * 2;
}

/* Falta implementar estas tres */
float Floor::getXcoord(){
	return this->x;
}

float Floor::getYcoord(){
	return this->y;
}

float Floor::getZcoord(){
	return this->z;
}

void Floor::setXcoord(float _x){
	this->x = _x;
}

void Floor::setYcoord(float _y){
	this->y = _y;
}

void Floor::setZcoord(float _z){
	this->z = _z;
}

void Floor::draw(){

	TextureManager* TM = TextureManager::Inst();
	if (TM->BindTexture(this->textura)) {
		Controlador::getInstance()->activarTexturas();

		GLfloat mat_specular[] = { 1.0, 1.0, 1.0, 1.0 };
		GLfloat mat_ambient[] = { 1, 1, 1, .0 };
		GLfloat mat_diffuse[] = { 1, 1, 1, 1.0 };
		GLfloat low_shininess[] = { 15 };

		glMaterialfv(GL_FRONT, GL_AMBIENT, mat_ambient);
		glMaterialfv(GL_FRONT, GL_DIFFUSE, mat_diffuse);
		glMaterialfv(GL_FRONT, GL_SPECULAR, mat_specular);
		glMaterialfv(GL_FRONT, GL_SHININESS, low_shininess);

		glTranslated(this->x, this->y, this->z);

		glColor3f(1.0f, 1.0f, 1.0f);
		glNormal3f(0.0f, 1.0f, 0.0f);
		
		glEnableClientState(GL_VERTEX_ARRAY);
		glEnableClientState(GL_TEXTURE_COORD_ARRAY);

		glVertexPointer(3, GL_FLOAT, 0, this->vertices);
		glTexCoordPointer(2, GL_FLOAT, 0, this->texvertices);
		glDrawArrays(GL_QUADS, 0, 40*3*4);

		glDisableClientState(GL_TEXTURE_COORD_ARRAY);
		glDisableClientState(GL_VERTEX_ARRAY);
		
		Controlador::getInstance()->desactivarTexturas();
	}
}

void Floor::updatePosition(){

};

