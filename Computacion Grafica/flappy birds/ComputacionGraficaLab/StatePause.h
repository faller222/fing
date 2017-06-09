#ifndef STATEPAUSE_H
#define STATEPAUSE_H

#include "State.h"

using namespace std;

class StatePause : public State {
private:
	State* juegoEnPausa;
	int texId;
	static StatePause* instance;
	StatePause();

public:
	static StatePause* getInstance();

	void reanudarJuego();

	void cargarTexturas();
	void setStatePlaying(State* juego);
	void procesarEvento(SDL_Event);
	void dibujarFrame(int);
	void actualizarLogica();
};

#endif STATEPAUSE_H