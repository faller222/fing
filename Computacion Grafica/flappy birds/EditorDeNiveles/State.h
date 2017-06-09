#ifndef STATE_H
#define STATE_H

#include "SDL.h"
#include "SDL_opengl.h"
#include "Constants.h"
#include <math.h>
#include "AntialiasingSys.h"
#include "jitter.h"
#include "Controlador.h"

class State {
	int currentProjectionMode;
public:
	/* No hay necesidad de sobreescribirlas */
	virtual int getProjectionMatrix() {
		return this->currentProjectionMode;
	}
	


	virtual void setProjectionMatrix(int type,int jitter) {

		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();

		this->currentProjectionMode = type;

		if (type == M_PROJ_PERSPECTIVE) {
			Controlador::getInstance()->activarLight();
			GLfloat aspect_ratio = (float)WINDOW_WIDTH / (float)WINDOW_HEIGHT;
			accPerspective(PERSPECTIVE_ANGLE, aspect_ratio, PERSPECTIVE_NEAR, PERSPECTIVE_FAR, j8[jitter].x, j8[jitter].y, 0.0, 0.0, 1.0);
		}
		else if (type == M_PROJ_ORTHO) {
			Controlador::getInstance()->desactivarLight();
			gluOrtho2D(0, WINDOW_WIDTH, 0, WINDOW_HEIGHT);
		}

		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
	}

	/* Hay que sobreescribirlas*/
	virtual void procesarEvento(SDL_Event) = 0;
	virtual void dibujarFrame(int) = 0;
	virtual void actualizarLogica() = 0;
	virtual void cargarTexturas() = 0;

	/* Eventos de cambio de estado. Los sobreescriben los estados que toman la transición por el evento. */
	virtual void nuevoJuego() {};
	virtual void empezarJuego() {};
	virtual void pausarJuego() {};
	virtual void reanudarJuego() {};
	virtual void terminarJuego() {};
	virtual void verOpciones() {};
	virtual void salirOpciones() {};
};

#endif STATE_H