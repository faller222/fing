#ifndef HUD_H
#define HUD_H

#include "SDL.h"
#include <list>

#include "ParticleSystem.h"

using namespace std;

struct EfectoPunto {
	Uint32 startTime;
	float posX;
	float posY;
	ParticleSystem* efecto;
};

class Hud {
private:
	static Hud* instance;
	Hud();

	int puntos;
	int highScore;
	Uint32 tiempoIni;
	Uint32 tiempoActual;
	list<EfectoPunto*> *efectos;
	float angulo;
	float sumaY;

public:
	static Hud* getInstance();

	void drawHUD();
	void masPunto();
	void initGame();
	void finishGame();
	void reinicializarHud();
	int puntajeFinal();
};

#endif HUD_H