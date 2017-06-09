#include <iostream>
#include <sstream>
#include <math.h>
#include "FTGLTextureFont.h"
#include "Drawable.h"
#include "Controlador.h"
#include "Fonts.h"
#include "Message.h"
#include "StatePlaying.h"


Controlador* Controlador::instance = NULL;

Controlador* Controlador::getInstance() {
	if (instance == NULL) {
		instance = new Controlador();
	}
	return instance;
}

void Controlador::inicializarSDL() {
	SDL_Init(SDL_INIT_EVERYTHING);
	SDL_GL_SetAttribute(SDL_GL_DOUBLEBUFFER, 1);

	SDL_GL_SetAttribute(SDL_GL_ACCUM_RED_SIZE, 8);
	SDL_GL_SetAttribute(SDL_GL_ACCUM_GREEN_SIZE, 8);
	SDL_GL_SetAttribute(SDL_GL_ACCUM_BLUE_SIZE, 8);
	SDL_GL_SetAttribute(SDL_GL_ACCUM_ALPHA_SIZE, 8);

	SDL_Surface* icono = SDL_LoadBMP("icon/icon.bmp");

	SDL_WM_SetIcon(icono, NULL);
	SDL_ShowCursor(false);

	if (SDL_VideoModeOK(WINDOW_WIDTH, WINDOW_HEIGHT, 32, SDL_HWSURFACE | SDL_OPENGL )) {
		this->screen = SDL_SetVideoMode(WINDOW_WIDTH, WINDOW_HEIGHT, 32, SDL_HWSURFACE | SDL_OPENGL);
	}
	else {
		exit(0);
	}
		

	SDL_WM_SetCaption(WINDOW_CAPTION, NULL);

}

void Controlador::inicializarOpenGL() {
	GLfloat fogColor[4] = { 0,0,0, 1.0f };

	float LightPos[4] = { 1,1,0, 0.0f };
	float Ambient[4] = { 0.5f, 0.5f, 0.5f, 1.0f };
	float difLight0[4] = { 1.0f, 1.0f, 1.0f, 1.0f };
	float specLight0[4] = { 1.0f, 1.0f, 1.0f, 1.0f };

	cargarQueues();
	glClearColor(0.7f, 0.7f, 1.0f, 1);
	glClearDepth(1.0f);

	glEnable(GL_DEPTH_TEST);
	glDepthMask(GL_TRUE);
	glShadeModel(GL_SMOOTH);
	glFogi(GL_FOG_MODE, GL_LINEAR);
	glFogf(GL_FOG_START, 70.0f);     
	glFogf(GL_FOG_END,200.0f);
	glFogfv(GL_FOG_COLOR, fogColor);
	glHint(GL_FOG_HINT, GL_NICEST);
	glFogf(GL_FOG_DENSITY, 1);
	glEnable(GL_FOG);

	glEnable(GL_CULL_FACE);

	glEnable(GL_MULTISAMPLE);
	glHint(GL_MULTISAMPLE_FILTER_HINT_NV, GL_FASTEST);

	glLightfv(GL_LIGHT0, GL_AMBIENT, Ambient);
	glLightfv(GL_LIGHT0, GL_POSITION, LightPos);
	glLightfv(GL_LIGHT0, GL_DIFFUSE, difLight0);
	glLightfv(GL_LIGHT0, GL_SPECULAR, specLight0);

	glLightModeli(GL_LIGHT_MODEL_LOCAL_VIEWER, GL_TRUE);
	glLightModelf(GL_LIGHT_MODEL_COLOR_CONTROL, GL_SEPARATE_SPECULAR_COLOR);
	glEnable(GL_LIGHTING);
	glEnable(GL_LIGHT0);

	glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	glEnable(GL_BLEND);
}

void Controlador::inicializarMaquinaDeEstados() {
	/* Cargo las fuentes*/
	Fonts::getInstance()->registrarFuente("arial16", "..\\ComputacionGraficaLab\\fonts\\arial.ttf", 16);
	Fonts::getInstance()->registrarFuente("arial36", "..\\ComputacionGraficaLab\\fonts\\arial.ttf", 36);

	Message::getInstance();
	
	this->mouseCameraClicking = false;
	this->camera = new Camera(0.0f, 0.0f, 10.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);
	
	/* Inicializo el estado jugando y comienzo el juego */
	StatePlaying* nuevoJuego = StatePlaying::getInstance();
	nuevoJuego->mostrarObstaculos();

	this->estado = nuevoJuego;
	
}

pair<string, float*> Controlador::getNextVector(int vecId){
	pair<string, float*> retVal;

	if (vecId == LIGHT_POSITION){

		retVal = positions.front();
		positions.pop();
		positions.push(retVal);

	}
	else if (vecId == AMBIENT_COLOR){

		retVal = ambientColors.front();
		ambientColors.pop();
		ambientColors.push(retVal);

	}
	else if (vecId == DIFFUSE_COLOR){

		retVal = diffuseColors.front();
		diffuseColors.pop();
		diffuseColors.push(retVal);

	}

	return retVal;

};




void Controlador::procesarEventos() {
	ostringstream ss;

	SDL_Event evento;
	while (SDL_PollEvent(&evento)) {
		this->estado->procesarEvento(evento);
		// Manejo eventos no manejados por ning�n estado
		switch (evento.type) {
		case SDL_KEYDOWN:
			switch (evento.key.keysym.sym) {				
			case SDLK_q:
				SDL_Quit();
				exit(1);
				break;			
			}
		break;
		case SDL_MOUSEBUTTONDOWN:
			switch (evento.button.button) {
			case SDL_BUTTON_RIGHT:
				this->mouseCameraClicking = true;
				break;
			}
			break;
		case SDL_MOUSEBUTTONUP:
			switch (evento.button.button) {
			case SDL_BUTTON_RIGHT:
				this->mouseCameraClicking = false;
				this->camera->setRotationFromMouseRelativePosition(0.0f, 0.0f);
				break;
			}
			break;
		case SDL_MOUSEMOTION:
			if (this->mouseCameraClicking) {
				// Muevo la c�mara
				float posX = this->camera->getPosX();
				float posY = this->camera->getPosY();
				float posZ = this->camera->getPosZ();
				// Magnitud de la rotaci�n calculada a partir de la distancia del mouse al centro de la ventana

				float mousex = 0.5f * evento.motion.xrel;
				float mousey = 0.5f * evento.motion.yrel;
				camera->setRotationFromMouseRelativePosition(mousex, mousey);
			}
			break;
		case SDL_QUIT:
			SDL_Quit();
			exit(0);
		}
	}
}

void Controlador::actualizarLogica() {
	// Para lograr que en todas las m�quinas vaya igual de r�pido
	// tengo que hacer que todas las actualizaciones dependan
	// del tiempo transcurrido desde la �ltima llamada a esta funci�n
	Uint32 currentTime = SDL_GetTicks();
	Uint32 tiempo = currentTime - this->lastTime;

	// La l�gica del juego es tal que deber�a actualizarse
	// this->velocidadJuego veces por segundo. Entonces, si llamo a actualizar l�gica
	// tiempo/(1000/this->fps) veces se va a actualizar la cantidad de veces necesarias
	// para mantener la velocidad constante en cualquier computadora

	float temp = ((float) tiempo / 1000.0f) * (float) this->velocidadJuego;
	int cantVecesActualizar = (int) temp;

	if (cantVecesActualizar > 0) {
		this->lastTime = currentTime;

		for (int i = 0; i < cantVecesActualizar; i++) {
			this->estado->actualizarLogica();
		}
	}
}


void Controlador::dibujarFrame() {
	// Llamo al estado correspondiente a que dibuje el frame

	// Calculo FPS
	this->fps = 1000.0f / (SDL_GetTicks() - this->tiempoFrameAnterior);
	this->tiempoFrameAnterior = SDL_GetTicks();

	glClear(GL_ACCUM_BUFFER_BIT);
	for (int jitter = 0; jitter < this->antialiasing; jitter++) {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		this->estado->dibujarFrame(jitter);
		Message::getInstance()->dibujarMensaje();

		if (this->showFps) {
			Controlador::getInstance()->getEstado()->setProjectionMatrix(M_PROJ_ORTHO, 0);
			glEnable(GL_TEXTURE_2D);
			glPushMatrix();
			glTranslatef(WINDOW_WIDTH - 75.0f, 5.0f, 0.0f);
			glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
			FTGLTextureFont* fuente = Fonts::getInstance()->obtenerFuente("arial16");
			ostringstream ss;
			ss << "FPS " << (int) this->fps;
			fuente->Render(ss.str().c_str());
			glPopMatrix();
			glDisable(GL_TEXTURE_2D);
		}

		if (this->antialiasing != AA_NO)
			glAccum(GL_ACCUM, 1.0f /(float) this->antialiasing);
	}

	if (this->antialiasing != AA_NO) {
		glAccum(GL_RETURN, 1.0);
		glFlush();
	}

	SDL_GL_SwapBuffers();
}

int Controlador::getNewTextureNumber() {
	// Hace la suma despu�s de devolver el valor
	return (this->cantTexturas++);
}

void Controlador::cambiarEstado(State* nuevo) {
	this->estado = nuevo;
}

State* Controlador::getEstado() {
	return this->estado;
}

void Controlador::toggleWireframe() {
	if (this->wireframe == false) {	
		Message::getInstance()->registrarMensaje("Wireframe activado");
		glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
		this->wireframe = true;
	}
	else {
		Message::getInstance()->registrarMensaje("Wireframe desactivado");
		glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
		this->wireframe = false;
	}
	
}

void Controlador::toggleAntialiasing() {
	if (this->antialiasing == AA_NO) {
		Message::getInstance()->registrarMensaje("Antialiasing x2");
		this->antialiasing = AA_X2;
	}
	else if (this->antialiasing == AA_X2) {
		Message::getInstance()->registrarMensaje("Antialiasing x4");
		this->antialiasing = AA_X4;
	}
	else if (this->antialiasing == AA_X4) {
		Message::getInstance()->registrarMensaje("Antialiasing x8");
		this->antialiasing = AA_X8;
	}
	else if (this->antialiasing == AA_X8) {
		Message::getInstance()->registrarMensaje("Antialiasing desactivado");
		this->antialiasing = AA_NO;
	}
}

void Controlador::toggleFps() {
	if (this->showFps == false) {
		this->showFps = true;
	}
	else {
		this->showFps = false;
	}

}

void Controlador::toggleSmoothFlat() {
	if (this->smooth == false) {
		Message::getInstance()->registrarMensaje("Modo de sombreado: Facetado");
		glShadeModel(GL_SMOOTH);
		this->smooth = true;
	}
	else {
		Message::getInstance()->registrarMensaje("Modo de sombreado: Interpolado");
		glShadeModel(GL_FLAT);
		this->smooth = false;
	}

}

void Controlador::activarTexturas() {
	if (this->texturas == true) {
		glEnable(GL_TEXTURE_2D);
	}
}

void Controlador::desactivarTexturas() {
	glDisable(GL_TEXTURE_2D);
}

void Controlador::activarLight() {
	if (this->lighting == true) {
		glEnable(GL_LIGHTING);
		glEnable(GL_LIGHT0);

	}
}

void Controlador::desactivarLight() {
	glDisable(GL_LIGHTING);
	glDisable(GL_LIGHT0);
}

Camera* Controlador::getCamera() {
	return this->camera;
}

void Controlador::setCameraLookingPoint(Drawable* d, float distance) {
	this->camera->setCameraLookingPoint(d, distance);
}


void Controlador::cargarQueues(){


	/*Direcciones de luz */
	float posicion0[4] = { 1.0f, 0.0f, 0.0f, 0.0f };
	float posicion1[4] = { 0.0f, 1.0f, 0.0f, 0.0f };
	float posicion2[4] = { 0.0f, 0.0f, 1.0f, 0.0f };
	float posicion3[4] = { 1.0f, 1.0f, 1.0f, 0.0f };
	float posicion4[4] = { 0.5f, 0.5f, 0.5f, 0.0f };



	float* pos0 = new float[4];
	float* pos1 = new float[4];
	float* pos2 = new float[4];
	float* pos3 = new float[4];
	float* pos4 = new float[4];

	/*copia de vectores */
	memcpy(pos0, posicion0, sizeof(float)* 4);
	memcpy(pos1, posicion1, sizeof(float)* 4);
	memcpy(pos2, posicion2, sizeof(float)* 4);
	memcpy(pos3, posicion3, sizeof(float)* 4);
	memcpy(pos4, posicion4, sizeof(float)* 4);

	/*positions*/
	positions.push(make_pair(string("Dirección de la luz paralela al eje X"), pos0));
	positions.push(make_pair(string("Dirección de la luz paralela al eje Y"), pos1));
	positions.push(make_pair(string("Dirección de la luz paralela al eje Z"), pos2));

	/*diffuse colors*/

	diffuseColors.push(make_pair(string("Luz Difusa Roja"), pos0));
	diffuseColors.push(make_pair(string("Luz Difusa Verde"), pos1));
	diffuseColors.push(make_pair(string("Luz Difusa Azul"), pos2));
	diffuseColors.push(make_pair(string("Luz Difusa Gris"), pos4));

	/*ambient colors*/
	ambientColors.push(make_pair(string("Luz Ambiente Roja"), pos0));
	ambientColors.push(make_pair(string("Luz Ambiente Verde"), pos1));
	ambientColors.push(make_pair(string("Luz Ambiente Azul"), pos2));
	ambientColors.push(make_pair(string("Luz Ambiente Blanca"), pos3));



}
