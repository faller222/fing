#include <iostream>
#include <time.h>

#include "SDL.h"
#include "SDL_opengl.h"

#include "Controlador.h"

using namespace std;

int main(int argc, char** argv) {
	/* Obtengo instancia de controlador */
	Controlador* c = Controlador::getInstance();

	/* Inicialización de SDL y OpenGL */
	c->inicializarSDL();
	c->inicializarOpenGL();

	/* Inicializo la máquina de estados en el estado inicial */
	c->inicializarMaquinaDeEstados();

	/* Bucle principal*/
	while (1) {
		c->procesarEventos();
		c->actualizarLogica();
		c->dibujarFrame();
	}

	return 0;
}