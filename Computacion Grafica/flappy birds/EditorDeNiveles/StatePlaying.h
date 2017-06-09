#ifndef STATEPLAYING_H
#define STATEPLAYING_H

#include <map>
#include <string>

#include "Camera.h"
#include "Drawable.h"
#include "State.h"
#include "Obstacle.h"
#include "SkyBox.h"
using namespace std;

class StatePlaying : public State {
private:
	map<string, Drawable*> *managedObjects;
	Obstacle* personaje;
	static StatePlaying* instance;
	StatePlaying();
	int idObs;
	SkyBox* skyBox;
	void guardarXML();
	bool crear;
	void mostrarPanel(int);
	void borrarObstaculos();
	bool block;

public:
	static StatePlaying* getInstance();

	/* De State*/
	void procesarEvento(SDL_Event);
	void dibujarFrame(int);
	void actualizarLogica();
	void verOpciones(){};

	void manageObject(string name, Drawable* obj);
	void unManageObject(string name);
	void cargarTexturas();
	void colisionDetectada();
	void mostrarObstaculos();
	void ocultarObstaculos();

	Obstacle* getPersonaje();

	void reinicializarJuego();
};

#endif STATEPLAYING_H