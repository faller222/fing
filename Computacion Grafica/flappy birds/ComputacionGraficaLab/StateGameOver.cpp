#include <iostream>
#include <sstream>

#include "FTGLTextureFont.h"
#include "Fonts.h"
#include "Hud.h"
#include "StateGameOver.h"
#include "StatePlaying.h"
#include "StateSettings.h"
#include "StateWaiting.h"

StateGameOver* StateGameOver::instance = NULL;

StateGameOver::StateGameOver() {
}

StateGameOver* StateGameOver::getInstance() {
	if (instance == NULL) {
		instance = new StateGameOver();
	}
	return instance;
}

void StateGameOver::procesarEvento(SDL_Event evento) {
	Uint32 current = SDL_GetTicks();

	if (current - this->inicio >= 3000) {
		switch (evento.type) {
		case SDL_KEYDOWN:
			switch (evento.key.keysym.sym) {
			case SDLK_SPACE:
				this->nuevoJuego();
				break;
			}
			break;
		}
	}
}

void StateGameOver::actualizarLogica() {
	StatePlaying::getInstance()->actualizarLogica();
}

void StateGameOver::dibujarFrame(int jitter) {
	Uint32 current = SDL_GetTicks();
	float alfa;

	if (current - this->inicio >= 3000) {
		alfa = 0.8f;
	}
	else {
		alfa = ((float)(current - this->inicio) * 0.8f) / 3000.0f;
	}

	StatePlaying::getInstance()->dibujarFrame(jitter);

	// Dibujo el texto
	this->setProjectionMatrix(M_PROJ_ORTHO, jitter);

	glDisable(GL_DEPTH_TEST);
	Controlador::getInstance()->desactivarLight();

	glPushMatrix();
	glBegin(GL_QUADS);
	glColor4f(0.0f, 0.0f, 0.0f, alfa);
	glVertex2f(0.0f, 0.0f);
	glVertex2f(WINDOW_WIDTH, 0.0f);
	glVertex2f(WINDOW_WIDTH, WINDOW_HEIGHT);
	glVertex2f(0.0f, WINDOW_HEIGHT);
	glEnd();
	glPopMatrix();

	FTGLExtrdFont* fuente2 = (FTGLExtrdFont*)Fonts::getInstance()->obtenerFuente("3Darial96");

	Controlador::getInstance()->activarLight();
	Controlador::getInstance()->desactivarNiebla();

	glPushMatrix();
	float llx, lly, llz, urx, ury, urz;
	fuente2->BBox("Game Over!", llx, lly, llz, urx, ury, urz);
	glTranslatef(WINDOW_WIDTH / 2.0f - (urx - llx), WINDOW_HEIGHT / 2.0f + 50.0f, 0.0f);
	glColor3f(1.0f, 1.0f, 1.0f);
	glScaled(2.0f, 2.0f, 2.0f);
	glRotated(-25.0f, 0.0f, 1.0f, 0.0f);
	glNormal3f(-0.423f, 0.0f, 0.906f);
	fuente2->Render("Game Over!");
	glPopMatrix();

	Controlador::getInstance()->activarNiebla();
	Controlador::getInstance()->desactivarLight();

	FTGLTextureFont* fuente = (FTGLTextureFont*)Fonts::getInstance()->obtenerFuente("arial96");

	Controlador::getInstance()->activarTexturas();
	glPushMatrix();
	fuente->BBox("Puntaje Final", llx, lly, llz, urx, ury, urz);
	glTranslatef(WINDOW_WIDTH / 2.0f - (urx - llx) / 2.0f, WINDOW_HEIGHT / 2.0f - 150.0f + 50.0f, 0.0f);
	glColor3f(0.80f, 0.80f, 0.80f);
	fuente->Render("Puntaje Final");
	glPopMatrix();
	glPushMatrix();
	int puntos = Hud::getInstance()->puntajeFinal();
	ostringstream ss;
	ss << puntos;
	fuente->BBox(ss.str().c_str(), llx, lly, llz, urx, ury, urz);
	glTranslatef(WINDOW_WIDTH / 2.0f - (urx - llx) / 2.0f, WINDOW_HEIGHT / 2.0f - 300.0f + 50.0f, 0.0f);
	glColor3f(0.80f, 0.80f, 0.80f);
	fuente->Render(ss.str().c_str());
	glPopMatrix();
	Controlador::getInstance()->desactivarTexturas();

	if (current - this->inicio >= 3000) {
		fuente = (FTGLTextureFont*)Fonts::getInstance()->obtenerFuente("arial32");
		glColor3f(0.85f, 0.85f, 0.85f);

		Controlador::getInstance()->activarTexturas();
		glPushMatrix();
		glTranslatef(WINDOW_WIDTH / 2.0f - 350.0f, 30.0f, 0.0f);
		fuente->Render("Presiona ESPACIO para iniciar una nueva partida");
		glPopMatrix();
		Controlador::getInstance()->desactivarTexturas();
	}

	this->setProjectionMatrix(M_PROJ_PERSPECTIVE,jitter);

	Controlador::getInstance()->activarLight();
	glEnable(GL_DEPTH_TEST);
}

void StateGameOver::cargarTexturas() {
}

void StateGameOver::nuevoJuego() {
	StatePlaying* sp = StatePlaying::getInstance();

	sp->reinicializarJuego();
	Hud::getInstance()->reinicializarHud();
	
	// Reinicio la posición de la cámara
	Controlador::getInstance()->restartCameraPosition();

	// Paso al estado waiting
	Controlador::getInstance()->cambiarEstado(StateWaiting::getInstance());
}

void StateGameOver::verOpciones() {
	StateSettings* settings = StateSettings::getInstance();
	settings->setStatePlaying(this);

	Controlador::getInstance()->cambiarEstado(settings);
}

void StateGameOver::iniciarTimer() {
	this->inicio = SDL_GetTicks();
}