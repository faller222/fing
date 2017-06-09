#ifndef CONTROLADOR_H
#define CONTROLADOR_H

#include "SDL.h"
#include "SDL_opengl.h"

#include "Camera.h"
#include "Constants.h"
#include <queue>
#include <utility>

class State;

using namespace std;

class Controlador {
private:
	SDL_Surface* screen;
	int cantTexturas;
	State* estado;

	/* Camara */
	Camera* camera;
	bool mouseCameraClicking;

	float velocidadJuego;
	Uint32 lastTime;
	Uint32 tiempoFrameAnterior;
	float fps;

	static Controlador* instance;

	/* Banderas */
	bool wireframe;
	bool texturas;
	bool smooth;
	bool showFps;
	bool lighting;
	int antialiasing;

	Controlador() {
		this->cantTexturas = 0;
		this->velocidadJuego = 50;
		this->lastTime = SDL_GetTicks();
		this->tiempoFrameAnterior = SDL_GetTicks();
		this->lighting = true;
		this->wireframe = false;
		this->smooth = true;
		this->texturas = true;
		this->showFps = false;
		this->antialiasing = AA_NO;
	};
	
	queue<pair<string, float*>> positions;
	queue<pair<string, float*>> ambientColors;
	queue<pair<string, float*>> diffuseColors;
	void cargarQueues();
	pair<string, float*> getNextVector(int vecId);

public:
	static Controlador* getInstance();

	void inicializarSDL();
	void inicializarOpenGL();
	void inicializarMaquinaDeEstados();

	void activarTexturas();
	void desactivarTexturas();
	void activarLight();
	void desactivarLight();

	void toggleFps();
	void toggleWireframe();
	void toggleSmoothFlat();
	void toggleAntialiasing();
	void cambiarEstado(State* nuevo);
	void procesarEventos();
	void dibujarFrame();
	void actualizarLogica();

	State* getEstado();

	int getNewTextureNumber();

	Camera* getCamera();
	void setCameraLookingPoint(Drawable* d, float distancia);
};

#endif CONTROLADOR_H