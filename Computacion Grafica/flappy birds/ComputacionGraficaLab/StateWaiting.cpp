#include <iostream>
#include "FTGLTextureFont.h"

#include "Controlador.h"
#include "Fonts.h"
#include "Hud.h"
#include "StatePlaying.h"
#include "StateSettings.h"
#include "StateWaiting.h"

using namespace std;

StateWaiting* StateWaiting::instance = NULL;

StateWaiting* StateWaiting::getInstance() {
	if (instance == NULL) {
		instance = new StateWaiting();
	}
	return instance;
}

StateWaiting::StateWaiting() {
	this->alfa = 0.85f;
	this->sumAlfa = -0.005f;
}

void StateWaiting::cargarTexturas() {
}

void StateWaiting::setStatePlaying(State* juego) {
	this->juegoAEmpezar = juego;
}



void StateWaiting::procesarEvento(SDL_Event evento) {
	switch (evento.type) {
	case SDL_KEYDOWN:
		switch (evento.key.keysym.sym) {
		case SDLK_SPACE:
			// Iniciar nuevo juego!
			this->nuevoJuego();
			break;
		case SDLK_ESCAPE:
			// Opciones
			this->verOpciones();
			break;
		}
	
		break;
	}
}

void StateWaiting::actualizarLogica() {
	// Solamente actualiza la lógica del avión
	// Hack para que el avión se mueva
	((StatePlaying*)this->juegoAEmpezar)->getPersonaje()->mover();

}

void StateWaiting::dibujarFrame(int jitter) {
	// Tengo que dibujar el estado playing sin actualizar la lógica
	this->juegoAEmpezar->dibujarFrame(jitter);

	this->setProjectionMatrix(M_PROJ_ORTHO,jitter);

	// Dibujo el cuadro
	glDisable(GL_DEPTH_TEST);
	glPushMatrix();
	glBegin(GL_QUADS);

	alfa += sumAlfa;
	if (alfa <= 0.65 || alfa >= 0.85f)
		sumAlfa = -sumAlfa;
	glColor4f(0.0f, 0.0f, 0.0f, alfa);
	glVertex2f(0.0f, 0.0f);
	glVertex2f(WINDOW_WIDTH, 0.0f);
	glVertex2f(WINDOW_WIDTH, 70.0f);
	glVertex2f(0.0f, 70.0f);
	glEnd();
	glPopMatrix();
	glEnable(GL_DEPTH_TEST);

	// Dibujo el texto
	Controlador::getInstance()->activarTexturas();
	glPushMatrix();
	glTranslatef(WINDOW_WIDTH / 2.0f - 300.0f, 25.0f, 0.0f);
	glColor3f(0.75f, 0.75f, 0.75f);
	FTGLTextureFont* fuente = (FTGLTextureFont*)Fonts::getInstance()->obtenerFuente("arial36");
	fuente->Render("¡Presiona ESPACIO para comenzar!");
	glPopMatrix();
	Controlador::getInstance()->desactivarTexturas();
}

void StateWaiting::nuevoJuego() {
	// Muestro los obstaculos
	((StatePlaying*)this->juegoAEmpezar)->mostrarObstaculos();

	// Salto inicial del avión
	((StatePlaying*)this->juegoAEmpezar)->getPersonaje()->saltar();
	Hud::getInstance()->initGame();
	Controlador::getInstance()->cambiarEstado(this->juegoAEmpezar);
}





void StateWaiting::verOpciones() {
	StateSettings* settings = StateSettings::getInstance();
	settings->setStatePlaying(this);
	Controlador::getInstance()->cambiarEstado(settings);
}