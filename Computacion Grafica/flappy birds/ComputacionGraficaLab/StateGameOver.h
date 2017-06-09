#ifndef STATEGAMEOVER_H
#define STATEGAMEOVER_H

#include "SDL.h"
#include "State.h"

class StateGameOver : public State {
private:
	static StateGameOver* instance;
	StateGameOver();

	Uint32 inicio;

public:
	static StateGameOver* getInstance();

	void procesarEvento(SDL_Event);
	void dibujarFrame(int);
	void cargarTexturas();
	void actualizarLogica();
	
	void nuevoJuego();
	void verOpciones();

	void iniciarTimer();
};

#endif STATEGAMEOVER_H