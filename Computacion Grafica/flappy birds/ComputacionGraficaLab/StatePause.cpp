#include <iostream>
#include "FTGLTextureFont.h"
#include "SDL_opengl.h"

#include "Controlador.h"
#include "Fonts.h"
#include "Hud.h"
#include "StatePause.h"
#include "StatePlaying.h"
#include "TextureManager.h"

using namespace std;

StatePause* StatePause::instance = NULL;

StatePause* StatePause::getInstance() {
	if (instance == NULL) {
		instance = new StatePause();
	}
	return instance;
}

StatePause::StatePause() {
	this->texId = Controlador::getInstance()->getNewTextureNumber();
	this->cargarTexturas();
}

void StatePause::cargarTexturas() {
}

void StatePause::setStatePlaying(State* juego) {
	this->juegoEnPausa = juego;
}

void StatePause::procesarEvento(SDL_Event evento) {
	switch (evento.type) {
	case SDL_KEYDOWN:
		switch (evento.key.keysym.sym) {
		case SDLK_p:
			// Salgo de la pausa
			this->reanudarJuego();
			break;
		}
		break;
	}
}

void StatePause::actualizarLogica() {
	// No actualiza lógica
}

void StatePause::dibujarFrame(int jitter) {
	float texWidth = 256.0f;
	float texHeight = 128.0f;
	float centreX = (float)WINDOW_WIDTH / 2.0f;
	float centreY = (float)WINDOW_HEIGHT / 2.0f;
	float x = centreX - texWidth / 2.0f;
	float y = centreY + texHeight / 2.0f;

	this->juegoEnPausa->dibujarFrame(jitter);

	this->setProjectionMatrix(M_PROJ_ORTHO,jitter);

	glDisable(GL_DEPTH_TEST);
	Controlador::getInstance()->desactivarLight();

	glPushMatrix();
	glBegin(GL_QUADS);
	glColor4f(0.0f, 0.0f, 0.0f, 0.8f);
	glVertex2f(0.0f, 0.0f);
	glVertex2f(WINDOW_WIDTH, 0.0f);
	glVertex2f(WINDOW_WIDTH, WINDOW_HEIGHT);
	glVertex2f(0.0f, WINDOW_HEIGHT);
	glEnd();
	glPopMatrix();

	Controlador::getInstance()->activarTexturas();
	glPushMatrix();
	glTranslatef(WINDOW_WIDTH / 2.0f - 50, WINDOW_HEIGHT / 2.0f + 10.0f, 0);
	glColor3f(0.85f, 0.85f, 0.85f);
	FTGLTextureFont* fuente = (FTGLTextureFont*)Fonts::getInstance()->obtenerFuente("arial36");
	fuente->Render("Pausa");
	glPopMatrix();
	Controlador::getInstance()->desactivarTexturas();

	this->setProjectionMatrix(M_PROJ_PERSPECTIVE,jitter);

	Controlador::getInstance()->activarLight();
	glEnable(GL_DEPTH_TEST);

}

void StatePause::reanudarJuego() {
	Controlador::getInstance()->cambiarEstado(this->juegoEnPausa);
}