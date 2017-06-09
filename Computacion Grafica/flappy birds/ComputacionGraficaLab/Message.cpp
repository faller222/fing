#include "Controlador.h"
#include "Fonts.h"
#include "FTGLTextureFont.h"
#include "Message.h"
#include "State.h"

Message* Message::instance = NULL;

Message* Message::getInstance() {
	if (instance == NULL) {
		instance = new Message();
	}
	return instance;
}

Message::Message() {
	this->startTime = 0;
}

void Message::registrarMensaje(string m) {
	this->startTime = SDL_GetTicks();
	this->mensaje = m;
}

void Message::dibujarMensaje() {
	if (this->startTime != 0) {
		Uint32 currentTime = SDL_GetTicks();
		if (currentTime - this->startTime >= 3000) {
			this->startTime = 0;
		}
		else {
			float alfa = 1.0f;
			if (currentTime - this->startTime >= 2000)
				alfa = (1.0f - ((((float)(currentTime - this->startTime) - 2000.0f))*1.0f) / 1000.0f);
			Controlador::getInstance()->getEstado()->setProjectionMatrix(M_PROJ_ORTHO,0);
			glEnable(GL_TEXTURE_2D);
			glPushMatrix();
			glTranslatef(5.0f, 5.0f, 0.0f);
			glColor4f(1.0f, 1.0f, 1.0f, alfa);
			FTGLTextureFont* fuente = (FTGLTextureFont*)Fonts::getInstance()->obtenerFuente("arial16");
			fuente->Render(this->mensaje.c_str());
			glPopMatrix();
			glDisable(GL_TEXTURE_2D);
		}
	}
}