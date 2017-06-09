#include "Controlador.h"
#include "Fonts.h"
#include "Hud.h"
#include "StateGameOver.h"
#include "StatePause.h"
#include "StatePlaying.h"
#include "StateSettings.h"
#include "StateWaiting.h"

#include <stdio.h>
#include <iostream>
#include <iomanip>
#include <sstream>
#include "FTGLTextureFont.h"

using namespace std;

Hud* Hud::instance = NULL;

Hud::Hud()
{
	efectos = new list<EfectoPunto*>();
	efectos->clear();
	reinicializarHud();
	highScore = 0;
}

Hud* Hud::getInstance(){
	if(instance == NULL) {
		instance = new Hud();
	}
	return instance;
}

void Hud::drawHUD() {
	if (Controlador::getInstance()->getEstado() == StatePlaying::getInstance()) {

		Uint32 tiempo_jugado;
		int aux, mili, sec, min, hs;

		// Si estoy jugando actualizo el tiempo actual
		tiempoActual = SDL_GetTicks();

		tiempo_jugado = tiempoActual - tiempoIni;

		mili = tiempo_jugado % 1000;
		aux = tiempo_jugado / 1000; //tengo tiempo en segundos
		sec = aux % 60;
		aux = aux / 60; //tengo tiempo en minutos
		min = aux % 60;
		hs = aux / 60;

		ostringstream tiempo;
		tiempo << "Tiempo  " << hs << ":"
			<< setw(2) << setfill('0') << min << ":"
			<< setw(2) << setfill('0') << sec
			<< "." << mili / 100;

		ostringstream tesPuntos;
		tesPuntos << "Puntaje  " << puntos;

		ostringstream tesPuntajeMaximo;
		tesPuntajeMaximo << "Mejor puntaje  " << highScore;

		glDisable(GL_DEPTH_TEST);
		Controlador::getInstance()->desactivarLight();

		glPushMatrix();
		glBegin(GL_QUADS);
		GLfloat v1[3] = { 0.0f, WINDOW_HEIGHT, 0.0f };
		GLfloat v2[3] = { 0.0f, WINDOW_HEIGHT - 125.0f, 0.0f };
		GLfloat v3[3] = { 280.0f, WINDOW_HEIGHT - 125.0f, 0.0f };
		GLfloat v4[3] = { 280.0f, WINDOW_HEIGHT, 0.0f };

		glColor4f(0.0f, 0.0f, 0.0f, 0.5f);

		glVertex3fv(v1);
		glVertex3fv(v2);
		glVertex3fv(v3);
		glVertex3fv(v4);

		glEnd();
		glPopMatrix();

		FTFont* fuente;
		fuente = (FTGLTextureFont*)Fonts::getInstance()->obtenerFuente("arial32");
		glColor3f(0.85f, 0.85f, 0.85f);

		Controlador::getInstance()->activarTexturas();
		glPushMatrix();
		glTranslatef(10.0f, WINDOW_HEIGHT - 30.0f, 0.0f);
		fuente->Render(tiempo.str().c_str());
		glPopMatrix();

		glPushMatrix();
		glTranslatef(10.0f, WINDOW_HEIGHT - 70.0f, 0.0f);
		fuente->Render(tesPuntos.str().c_str());
		glPopMatrix();

		glPushMatrix();
		glTranslatef(10.0f, WINDOW_HEIGHT - 110.0f, 0.0f);
		fuente->Render(tesPuntajeMaximo.str().c_str());
		glPopMatrix();
		Controlador::getInstance()->desactivarTexturas();

		// Dibujo el efecto actual
		if (!this->efectos->empty()) {
			EfectoPunto* e = this->efectos->front();
			glPushMatrix();
			glTranslatef(e->posX, e->posY, 0.0f);
			if (e->posY <= WINDOW_HEIGHT / 2.0f + 250.0f) {
				e->posY += 30;
				glScalef(100.0f, 100.0f, 100.0f);
			}
			else {
				glScalef(300.0f, 300.0f, 300.0f);
				e->efecto->updatePosition();
			}
			e->efecto->draw();
			glPopMatrix();

			FTFont* fuente2 = (FTGLExtrdFont*)Fonts::getInstance()->obtenerFuente("3Darial96");

			glPushMatrix();
			glColor3f(1.0f, 1.0f, 1.0f);
			float llx, lly, llz, urx, ury, urz;
			fuente2->BBox("+1", llx, lly, llz, urx, ury, urz);
			glTranslatef(WINDOW_WIDTH / 2.0f, WINDOW_HEIGHT / 2.0f, 0.0f);
			glRotatef(this->angulo, 0.0f, 1.0f, 0.0f);
			glTranslatef(-(urx - llx / 2), this->sumaY, 0.0f);
			glScalef(1.5f, 1.5f, 1.5f);

			fuente2->Render("+1");
			glPopMatrix();

			this->angulo += 3.0f;
			this->sumaY += 3.0f;

			if (SDL_GetTicks() - e->startTime >= 1500) {
				this->efectos->pop_front();
				delete e->efecto;
				delete e;
			}
		}

		Controlador::getInstance()->activarLight();
		glEnable(GL_DEPTH_TEST);
	}
}

void Hud::masPunto(){
	this->puntos++;

	// Creo los fuegos artificiales
	float r = (float)(rand() % 100) / 100.0f;
	float g = (float)(rand() % 100) / 100.0f;
	float b = (float)(rand() % 100) / 100.0f;
	float posx = WINDOW_WIDTH / 2.0f;
	float posy = 100.0f;

	ParticleSystem* nuevoEfecto = new ParticleSystem(500, 0.1f, 0.1f, 1.0f, 1.0f, r, g, b, 0.0f, -1.0f, 0.0f, false);

	EfectoPunto* ep = new EfectoPunto();
	ep->startTime = SDL_GetTicks();
	ep->posX = posx;
	ep->posY = posy;
	ep->efecto = nuevoEfecto;
	this->efectos->push_back(ep);

	// Seteo angulo de rotación y altura inicial
	this->angulo = -90.0f;
	this->sumaY = 50.0f;
}

// Esta función se llama cuando arranca el juego
void Hud::initGame() {
	tiempoIni = SDL_GetTicks();
	tiempoActual = SDL_GetTicks();
}

void Hud::finishGame() {
	if (puntos > highScore) {
		highScore = puntos;
	}
}

void Hud::reinicializarHud() {
	tiempoIni = 0;
	tiempoActual = 0;
	puntos = 0;
}

int Hud::puntajeFinal() {
	return this->puntos;
}