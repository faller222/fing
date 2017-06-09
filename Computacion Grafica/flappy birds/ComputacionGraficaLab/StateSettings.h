#ifndef STATESETTINGS_H
#define STATESETTINGS_H

#include "State.h"

#include "Constants.h"
using namespace std;

class StateSettings : public State {
private:
	State* juegoEnPausa;
	int texId;
	static StateSettings* instance;
	StateSettings();
	
public:
	static StateSettings* getInstance();

	void reanudarJuego();

	void cargarTexturas();
	void actualizarLogica();
	void setStatePlaying(State* juego);
	void procesarEvento(SDL_Event);
	void dibujarFrame(int jitter);

};

#endif STATESETTINGS_H