#include <math.h>
#include <time.h>

#include "Controlador.h"
#include "Floor.h"
#include "ParticleSystem.h"
#include "Plane.h"
#include "Quaternions.h"
#include "StatePlaying.h"
#include "TextureManager.h"

Plane::Plane(string modelPath, string texPath, Drawable* p) : Modelable(modelPath) {
	this->posX = 0.0f;
	this->posY = 5.0f;
	this->posZ = 0.0f;
	this->velX = PLANE_SPEED;
	this->velY = 0.0f;
	this->textureId = Controlador::getInstance()->getNewTextureNumber();
	this->texturePath = texPath;
	this->motorI = new ParticleSystem(100, 0.5f, 0.5f, 1.5f, 1.0f, 0.25f, 0.05f, 0.05f, 1.0f, -1.0f, -1.0f, true);
	this->motorD = new ParticleSystem(100, 0.5f, 0.5f, 1.5f, 1.0f, 0.25f, 0.05f, 0.05f, 1.0f, -1.0f, -1.0f, true);
	this->explosion = new ParticleSystem(500, 1.0f, 1.0f, 0.15f, 1.0f, 1.0f, 0.2f, 0.2f, 0.0f, 0.0f, 0.0f, true);
	this->estela.clear();
	this->explode = false;
	this->piso = p;
	this->avionAntes = true;
};

void Plane::dibujarEstelaYMotores() {
	// Dibujo los motores del avión
	glPushMatrix();
	glTranslatef(this->posX - 2.5f, this->posY - 0.1f, this->posZ - 0.4f);
	glRotatef(180.0f, 0.0f, 0.0f, 1.0f);
	glRotatef(-90.0f, 0.0f, 1.0f, 0.0f);
	this->motorI->draw();
	glPopMatrix();

	glPushMatrix();
	glTranslatef(this->posX - 2.5f, this->posY - 0.1f, this->posZ + 0.4f);
	glRotatef(180.0f, 0.0f, 0.0f, 1.0f);
	glRotatef(-90.0f, 0.0f, 1.0f, 0.0f);
	this->motorD->draw();
	glPopMatrix();

	// Dibujo la estela del avión
	for (list<PuntoEstela*>::iterator it = this->estela.begin(); it != this->estela.end(); it++) {
		PuntoEstela* actual = (*it);
		glPushMatrix();
		glTranslatef(actual->posX, actual->posY, actual->posZ);
		actual->estela->draw();
		glPopMatrix();
	}
}

void Plane::draw(){

	if (!explode) {
		if (!this->avionAntes) {
			this->dibujarEstelaYMotores();
		}

		TextureManager* TM = TextureManager::Inst();
		if (texturePath != ""){
			TM->BindTexture(textureId);
			Controlador::getInstance()->activarTexturas();
		}
		else glColor3f(0.25f, 0.25f, 0.25f);

		
		GLfloat mat_specular[] = { 1.0f, 1.0f, 1.0f, 1.0f };
		GLfloat mat_ambient[] = { 1.0f, 1.0f, 1.0f, 1.0f };
		GLfloat mat_diffuse[] = { 1.0f, 1.0f, 1.0f, 1.0f };
		GLfloat low_shininess[] = { 100.0f };

		glMaterialfv(GL_FRONT, GL_AMBIENT, mat_ambient);
		glMaterialfv(GL_FRONT, GL_DIFFUSE, mat_diffuse);
		glMaterialfv(GL_FRONT, GL_SPECULAR, mat_specular);
		glMaterialfv(GL_FRONT, GL_SHININESS, low_shininess);

		float maxAngle = 1.0f;
		float ang = ((float)(500-(rand() % 1000))/500.0f)*maxAngle;
		if (Controlador::getInstance()->getEstado() != StatePlaying::getInstance())
			ang = 0.0f;
		Quat q(ang, 0.0f, 0.0f, 1.0f);
		float* m = q.getMatrix();

		for (int i = 0; i < numMeshes; i++){
			glPushMatrix();
			glTranslatef(posX, posY, posZ);

			// Aplico una turbulencia aleatoria alrededor del eje z
			glScalef(5.0f, 5.0f, 5.0f);
			glEnable(GL_RESCALE_NORMAL);
			glRotatef(180.0f, 0.0f, 0.0f, 1.0f);
			glRotatef(-90.0f, 0.0f, 1.0f, 0.0f);
			glMultMatrixf(m);
			glEnableClientState(GL_VERTEX_ARRAY);
			glEnableClientState(GL_TEXTURE_COORD_ARRAY);
			glEnableClientState(GL_NORMAL_ARRAY);

			glVertexPointer(3, GL_FLOAT, 0, vertexA[i]);
			glNormalPointer(GL_FLOAT, 0, vertexN[i]);

			glTexCoordPointer(2, GL_FLOAT, 0, vertexT[i]);


			glDrawArrays(GL_TRIANGLES, 0, numVerts[i]);

			glDisableClientState(GL_NORMAL_ARRAY);
			glDisableClientState(GL_TEXTURE_COORD_ARRAY);
			glDisableClientState(GL_VERTEX_ARRAY);

			glPopMatrix();
		}

		delete m;

		Controlador::getInstance()->desactivarTexturas();
		
		if (this->avionAntes) {
			this->dibujarEstelaYMotores();
		}
		
	}
	else {
		glPushMatrix();
		glTranslatef(this->posX, this->posY, this->posZ);
		glRotatef(180.0f, 0.0f, 0.0f, 1.0f);
		glRotatef(-90.0f, 0.0f, 1.0f, 0.0f);
		this->explosion->draw();
		glPopMatrix();
	}


};

void Plane::cargarTextura(){
	TextureManager* TM = TextureManager::Inst();
	if (texturePath != ""){
		if (TM->LoadTexture(texturePath.data(), textureId, true, GL_RGB)){
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR_MIPMAP_LINEAR);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
			glTexEnvf(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_MODULATE);
			motorI->cargarTextura();
			motorD->cargarTextura();
			explosion->cargarTextura();
		}
	}
}
float Plane::getXcoord() {
	return posX;
};

float Plane::getYcoord() {
	return posY;
};

float Plane::getZcoord() {
	return posZ;
};

void Plane::setXcoord(float _x){
	this->posX = _x;
}

void Plane::setYcoord(float _y){
	this->posY = _y;
}

void Plane::setZcoord(float _z){
	this->posZ = _z;
}

void Plane::mover() {
	posX += velX;

	this->motorI->updatePosition();
	this->motorD->updatePosition();
	// Dibujo la estela del avión
	for (list<PuntoEstela*>::iterator it = this->estela.begin(); it != this->estela.end(); it++) {
		PuntoEstela* actual = (*it);
		actual->estela->updatePosition();
	}
	
	/* Agrego un punto de estela */
	ParticleSystem* nueva = new ParticleSystem(10, 1.0f, 1.0f, 10.0f, 1.0f, 0.02f, 0.02f, 0.02f, 0.0f, 0.0f, 0.0f, true);
	PuntoEstela* nuevoPuntoEstela = new PuntoEstela();
	nuevoPuntoEstela->estela = nueva;
	nuevoPuntoEstela->posX = posX - 5.0f;
	nuevoPuntoEstela->posY = posY;
	nuevoPuntoEstela->posZ = posZ - 1.0f;
	this->estela.push_back(nuevoPuntoEstela);

	nueva = new ParticleSystem(10, 1.0f, 1.0f, 10.0f, 1.0f, 0.02f, 0.02f, 0.02f, 0.0f, 0.0f, 0.0f, true);
	nuevoPuntoEstela = new PuntoEstela();
	nuevoPuntoEstela->estela = nueva;
	nuevoPuntoEstela->posX = posX - 5.0f;
	nuevoPuntoEstela->posY = posY;
	nuevoPuntoEstela->posZ = posZ + 1.0f;
	this->estela.push_back(nuevoPuntoEstela);

	/* Borro los últimos puntos de estela si son demasiados */
	while (this->estela.size() > 30) {
		PuntoEstela* front = this->estela.front();

		this->estela.pop_front();

		delete front->estela;
		delete front;
	}

}

void Plane::updatePosition(){
	if (!explode) {
		if (posY > this->piso->getYcoord()){
			velY -= GRAVITY;

			mover();
			posY += velY;
		}
		else {
			// Colisión detectada
			StatePlaying::getInstance()->colisionDetectada();
		}
	}
	else {
		this->explosion->updatePosition();
	}
};

// Esta función se llama cuando se produjo el evento de salto detectado por el controlador
void Plane::saltar() {
	if (!explode) {
		velY = PLANE_JUMP_SPEED;
		posY += velY;
	}
}

void Plane::detener() {
	velY = 0.0f;
	velX = 0.0f;

	/* Borro todos los puntos de estela */
	while (this->estela.size() > 0) {
		PuntoEstela* front = this->estela.front();

		this->estela.pop_front();

		delete front->estela;
		delete front;
	}

	/* Explosión! */
	this->explode = true;
}

void Plane::reinicializar() {
	this->posY = 5.0f;
	this->velX = PLANE_SPEED;
	this->velY = 0.0f;
	this->explode = false;
	this->explosion->restart();
}

void Plane::profundidadAvionAntes() {
	this->avionAntes = true;
}

void Plane::profundidadSistemaAntes() {
	this->avionAntes = false;
}