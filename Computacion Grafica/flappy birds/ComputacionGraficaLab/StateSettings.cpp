#include <iostream>
#include "FTGLTextureFont.h"
#include "SDL_opengl.h"

#include "Controlador.h"
#include "Hud.h"
#include "Fonts.h"
#include "StatePause.h"
#include "StatePlaying.h"
#include "StateSettings.h"

using namespace std;

StateSettings* StateSettings::instance = NULL;

StateSettings* StateSettings::getInstance() {
	if (instance == NULL) {
		instance = new StateSettings();
	}
	return instance;
}

StateSettings::StateSettings() {
}

void StateSettings::cargarTexturas() {
}

void StateSettings::setStatePlaying(State* juego) {
	this->juegoEnPausa = juego;
}

void StateSettings::procesarEvento(SDL_Event evento) {
	switch (evento.type) {
	case SDL_KEYDOWN:
		switch (evento.key.keysym.sym) {
		case SDLK_ESCAPE:
			// Salgo del menu
			this->reanudarJuego();
			break;
		}
		break;
	}
}

void StateSettings::actualizarLogica() {
	// No actualiza lógica
}

void StateSettings::dibujarFrame(int jitter) {
	float texWidth = 640.0f;
	float texHeight = 480.0f;
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

	FTGLTextureFont* fuenteGrande = (FTGLTextureFont*) Fonts::getInstance()->obtenerFuente("arial36");
	FTGLTextureFont* fuente = (FTGLTextureFont*)Fonts::getInstance()->obtenerFuente("arial24");
	float llx, lly, llz, urx, ury, urz;

	/* Escribo secuencia de comandos*/
	Controlador::getInstance()->activarTexturas();

	glPushMatrix();
	fuenteGrande->BBox("Ajustes gráficos", llx, lly, llz, urx, ury, urz);
	glTranslatef(WINDOW_WIDTH / 2.0f - (urx-llx)/2.0f, WINDOW_HEIGHT - (ury-lly) - 5.0f, 0);
	glColor3f(1.0f, 1.0f, 1.0f);
	fuenteGrande->Render("Ajustes gráficos");
	glPopMatrix();

	float yInicial = WINDOW_HEIGHT - 100.0f;

	const char* textos1[12] = { "Activar antialiasing", "Mostrar cuadros por segundo", "Activar/desactivar iluminacion", "Activar/desactivar niebla",
	"Cambiar entre facetado/interpolado", "Activar/desactivar texturas", "Activar/desactivar wireframe", "Aumentar velocidad del juego",
	"Disminuir velocidad del juego", "Cambiar direccion de la luz", "Cambiar color de la luz ambiente", "Cambiar color de la luz difusa"};

	const char* comandos1[12] = { "A", "F", "L", "N", "S", "T", "W", "+", "-", "1", "2", "3" };

	for (int i = 0; i < 12; i++) {
		glPushMatrix();
		glTranslatef(WINDOW_WIDTH/7.0f, yInicial - ((float)i) * 35.0f, 0);
		glColor3f(1.0f, 1.0f, 1.0f);
		fuente->Render(textos1[i]);
		glPopMatrix();
		glPushMatrix();
		glTranslatef(4.0f*WINDOW_WIDTH/7.0f, yInicial - ((float)i) * 35.0f, 0);
		glColor3f(1.0f, 1.0f, 1.0f);
		fuente->Render(comandos1[i]);
		glPopMatrix();
	}

	glPushMatrix();
	fuenteGrande->BBox("Controles", llx, lly, llz, urx, ury, urz);
	glTranslatef(WINDOW_WIDTH / 2.0f - (urx - llx) / 2.0f, yInicial - 13.0f * 35.0f, 0);
	glColor3f(1.0f, 1.0f, 1.0f);
	fuenteGrande->Render("Controles");
	glPopMatrix();

	yInicial = yInicial - 12.0f * 35.0f - 15.0f - 75.0f;

	const char* textos2[5] = {"Saltar", "Mover la camara", "Pausar el juego", "Abrir opciones de ajuste", "Salir del juego"};

	const char* comandos2[11] = { "ESPACIO, UP", "Boton derecho del mouse + Mover mouse", "P", "ESCAPE", "Q" };

	for (int i = 0; i < 5; i++) {
		glPushMatrix();
		glTranslatef(WINDOW_WIDTH / 7.0f, yInicial - ((float)i) * 35.0f, 0);
		glColor3f(1.0f, 1.0f, 1.0f);
		fuente->Render(textos2[i]);
		glPopMatrix();
		glPushMatrix();
		glTranslatef(4.0f*WINDOW_WIDTH / 7.0f, yInicial - ((float)i) * 35.0f, 0);
		glColor3f(1.0f, 1.0f, 1.0f);
		fuente->Render(comandos2[i]);
		glPopMatrix();
	}

	Controlador::getInstance()->desactivarTexturas();

	Controlador::getInstance()->activarLight();
	glEnable(GL_DEPTH_TEST);

}

void StateSettings::reanudarJuego() {
	Controlador::getInstance()->cambiarEstado(this->juegoEnPausa);
}


