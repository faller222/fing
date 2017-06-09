
#include "SkyBox.h"

#include "TextureManager.h"

float SkyBox::getXcoord(){
	return x;
};
float SkyBox::getYcoord(){
	return y;
};
float SkyBox::getZcoord(){

	return z;
};
void SkyBox::setXcoord(float _x){
	x = _x;

};
void SkyBox::setYcoord(float _y){
	y = _y;
}
;
void SkyBox::setZcoord(float _z){
	z = _z;
};


void SkyBox::draw(){
	// Si el avión no se mueve la caja tampoco
	x = plane->getXcoord();

	glTranslatef(x,y,z);
	glScalef(400, 400, 400);

	TextureManager* TM = TextureManager::Inst();

	Controlador::getInstance()->desactivarLight();
	Controlador::getInstance()->activarTexturas();
	Controlador::getInstance()->desactivarNiebla();

	TM->BindTexture(textureId[0]);
	glBegin(GL_QUADS);//Cara frontal
		glTexCoord2f(0, 0); glVertex3f(0.5f, -0.5f, -0.5f);
		glTexCoord2f(0, 1); glVertex3f(0.5f, 0.5f, -0.5f);
		glTexCoord2f(1, 1); glVertex3f(-0.5f, 0.5f, -0.5f);
		glTexCoord2f(1, 0); glVertex3f(-0.5f, -0.5f, -0.5f);
	glEnd();

	TM->BindTexture(textureId[1]);
	glBegin(GL_QUADS);//cara lateral izquierda
		glTexCoord2f(0, 0); glVertex3f(0.5f, -0.5f, 0.5f);
		glTexCoord2f(0, 1); glVertex3f(0.5f, 0.5f, 0.5f);
		glTexCoord2f(1, 1); glVertex3f(0.5f, 0.5f, -0.5f);
		glTexCoord2f(1, 0); glVertex3f(0.5f, -0.5f, -0.5f);
	glEnd();

	TM->BindTexture(textureId[2]);
	glBegin(GL_QUADS);//cara posterior
		glTexCoord2f(0, 0); glVertex3f(-0.5f, -0.5f, 0.5f);
		glTexCoord2f(0, 1); glVertex3f(-0.5f, 0.5f, 0.5f);
		glTexCoord2f(1, 1); glVertex3f(0.5f, 0.5f, 0.5f);
		glTexCoord2f(1, 0); glVertex3f(0.5f, -0.5f, 0.5f);
	glEnd();

	TM->BindTexture(textureId[3]);
	glBegin(GL_QUADS);//cara derecha
		glTexCoord2f(0, 0); glVertex3f(-0.5f, -0.5f, -0.5f);
		glTexCoord2f(0, 1); glVertex3f(-0.5f, 0.5f, -0.5f);
		glTexCoord2f(1, 1); glVertex3f(-0.5f, 0.5f, 0.5f);
		glTexCoord2f(1, 0); glVertex3f(-0.5f, -0.5f, 0.5f);
	glEnd();


	TM->BindTexture(textureId[4]);
	glBegin(GL_QUADS);//techo
		glTexCoord2f(1, 1); glVertex3f(-0.5f, 0.5f, -0.5f);
		glTexCoord2f(1, 0); glVertex3f(0.5f, 0.5f, -0.5f);
		glTexCoord2f(0, 0); glVertex3f(0.5f, 0.5f, 0.5f);
		glTexCoord2f(0, 1); glVertex3f(-0.5f, 0.5f, 0.5f);
	glEnd();
	Controlador::getInstance()->desactivarTexturas();
	Controlador::getInstance()->activarLight();
	Controlador::getInstance()->activarNiebla();


};

void SkyBox::updatePosition(){
};

void SkyBox::cargarTextura(){
	TextureManager* TM = TextureManager::Inst();
	if (texturePath != NULL){
		if (TM->LoadTexture(texturePath[0].data(), textureId[0],true, GL_BGR_EXT)){
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR_MIPMAP_LINEAR);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
			glTexEnvf(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_MODULATE);			
			
		}
		if (TM->LoadTexture(texturePath[1].data(), textureId[1], true, GL_BGR_EXT)){
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR_MIPMAP_LINEAR);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
			glTexEnvf(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_MODULATE);

		}
		if (TM->LoadTexture(texturePath[2].data(), textureId[2], true, GL_BGR_EXT)){
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR_MIPMAP_LINEAR);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
			glTexEnvf(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_MODULATE);

			if (TM->LoadTexture(texturePath[3].data(), textureId[3], true, GL_BGR_EXT)){
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR_MIPMAP_LINEAR);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
			glTexEnvf(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_MODULATE);

		}
			if (TM->LoadTexture(texturePath[4].data(), textureId[4],true, GL_BGR_EXT)){
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR_MIPMAP_LINEAR);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
			glTexEnvf(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_MODULATE);

		}
		}
	}
}