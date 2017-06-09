#ifndef STATEWAITING_H
#define STATEWAITING_H

#include "State.h"

class StateWaiting : public State {
	State* juegoAEmpezar;
	static StateWaiting* instance;
	StateWaiting();
	float alfa; // Alfa del cuadrado
	float sumAlfa;

public:
	static StateWaiting* getInstance();

	void procesarEvento(SDL_Event);
	void dibujarFrame(int);
	void cargarTexturas();
	void actualizarLogica();
	void setStatePlaying(State* juego);
	void nuevoJuego();
	void verOpciones();
};

#endif STATEWAITING_H