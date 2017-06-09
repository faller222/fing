#ifndef STATEPLAYING_H
#define STATEPLAYING_H

#include <map>
#include <string>

#include "Camera.h"
#include "Drawable.h"
#include "Plane.h"
#include "State.h"

using namespace std;

class StatePlaying : public State {
private:
	Plane* personaje;
	map<string, Drawable*> *managedObjects;

	static StatePlaying* instance;
	StatePlaying();

public:
	static StatePlaying* getInstance();

	/* De State*/
	void procesarEvento(SDL_Event);
	void dibujarFrame(int);
	void pausarJuego();
	void actualizarLogica();
	void verOpciones();
	void terminarJuego();

	void manageObject(string name, Drawable* obj);
	void unManageObject(string name);
	void cargarTexturas();
	void colisionDetectada();
	void mostrarObstaculos();
	void ocultarObstaculos();

	Plane* getPersonaje();

	void reinicializarJuego();
};

#endif STATEPLAYING_H