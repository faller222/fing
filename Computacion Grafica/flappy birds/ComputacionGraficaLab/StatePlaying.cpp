#include "Building.h"
#include "Floor.h"
#include "Hud.h"
#include "LevelLoader.h"
#include "Obstacle.h"
#include "ParticleSystem.h"
#include "Plane.h"
#include "SkyBox.h"
#include "StateGameOver.h"
#include "StatePause.h"
#include "StatePlaying.h"
#include "StateSettings.h"
#include "TextureManager.h"

using namespace std;

StatePlaying* StatePlaying::instance = NULL;

StatePlaying* StatePlaying::getInstance() {
	if (instance == NULL) {
		instance = new StatePlaying();
	}
	return instance;
}

StatePlaying::StatePlaying() {
	this->managedObjects = new map<string, Drawable*>();

	/* Creación de objetos del juego */

	Floor* piso = new Floor("textures/Tex_Street.jpg", 0.0f, -2.0f, 0.0f);
	this->manageObject("piso", piso);

	Plane* avion = new Plane("models/Model_Eagle.3ds", "textures/Tex_Eagle.jpg", piso);
	this->manageObject("personaje", avion);

	string* texturePathsSky = new string[5]();
	texturePathsSky[0] = "textures/sky/Tex_Sky0.jpg";
	texturePathsSky[1] = "textures/sky/Tex_Sky1.jpg";
	texturePathsSky[2] = "textures/sky/Tex_Sky2.jpg";
	texturePathsSky[3] = "textures/sky/Tex_Sky3.jpg";
	texturePathsSky[4] = "textures/sky/Tex_Sky4.jpg";

	SkyBox* skyBox = new SkyBox(texturePathsSky, avion);
	this->manageObject("sol", skyBox);

	string* texturePathsBuildings = new string[BUILDINGS]();

	for (int i = 1; i <= BUILDINGS; i++) {
		ostringstream ss;
		ss << "textures/buildings/Tex_Building" << i << ".jpg";
		texturePathsBuildings[i - 1] = ss.str();
	}

	Building* edificios = new Building(0.0f, -2.0f, 0.0f, texturePathsBuildings);
	this->manageObject("edificios", edificios);

	LevelLoader::crearObstaculosDeXml(this, LEVEL_PATH, (*this->managedObjects)["personaje"]);

	Controlador::getInstance()->setCameraLookingPoint(avion, CAMERA_DISTANCE);

	/* Cargado de texturas */
	this->cargarTexturas();

}

void StatePlaying::pausarJuego() {
	StatePause* pausa = StatePause::getInstance();
	pausa->setStatePlaying(this);
	Controlador::getInstance()->cambiarEstado(pausa);
}

void StatePlaying::verOpciones() {
	StateSettings* settings = StateSettings::getInstance();
	settings->setStatePlaying(this);
	Controlador::getInstance()->cambiarEstado(settings);
}

void StatePlaying::terminarJuego() {
	StateGameOver* st = StateGameOver::getInstance();
	Controlador::getInstance()->cambiarEstado(st);
}

void StatePlaying::procesarEvento(SDL_Event evento) {
	switch (evento.type) {
	case SDL_KEYDOWN:
		switch (evento.key.keysym.sym) {
		case SDLK_SPACE:
		case SDLK_UP:
			// Llamo al personaje, evento de jump
			this->personaje->saltar();
			break;
		case SDLK_p:
			// Pausa
			this->pausarJuego();
			break;
		case SDLK_ESCAPE:
			// Opciones
			this->verOpciones();
			break;
		}
		break;
	}
}

void StatePlaying::actualizarLogica() {
	for (map<string, Drawable*>::iterator iter = managedObjects->begin(); iter != managedObjects->end(); iter++) {
		Drawable* object = iter->second;
		object->updatePosition();
	}
}

void StatePlaying::dibujarFrame(int jitter) {

	map<string, Drawable*>::iterator iter;
	this->setProjectionMatrix(M_PROJ_PERSPECTIVE,jitter);	

	Controlador::getInstance()->getCamera()->lookAt();

	for (iter = managedObjects->begin(); iter != managedObjects->end(); iter++) {
		Drawable* object = (iter->second);

		glPushMatrix();
		if (iter->first != "personaje") {
			float xper = this->personaje->getXcoord();
			float xobj = object->getXcoord();

			/* Piso infinito */
			if (iter->first == "piso") {
				if (xper - xobj >= (WORLD_X_DISTANCE / 4.0f)) {
					glTranslatef(WORLD_X_DISTANCE / 4.0f, 0.0f, 0.0f);
					object->setXcoord(object->getXcoord() + (WORLD_X_DISTANCE / 4.0f));
				}
			}

			/* Edificios infinitos */
			if (iter->first == "edificios") {
				if (xper - xobj >= (15.0f * 13.0f)) {
					object->draw();
					glTranslatef(15.0f * 13.0f, 0.0f, 0.0f);
					object->setXcoord(object->getXcoord() + (15.0f * 13.0f));
				}
			}

			if (iter->first.find("obstaculo") != string::npos) {
				if (abs(xobj - xper) <= 100.0f) {
					object->draw();
				}
			}
			else {
				object->draw();					
			}

		}
		glPopMatrix();

	}

	/* Dibujo último el avión (con el sistema de partículas!)*/
	glPushMatrix();
	this->personaje->draw();
	glPopMatrix();
}

void StatePlaying::manageObject(string name, Drawable* obj) {
	(*this->managedObjects)[name] = obj;
	if (name == "personaje") {
		// Se asume que este es el personaje principal
		this->personaje = (Plane*) obj;
	}
};

void StatePlaying::unManageObject(string name) {
	this->managedObjects->erase(name);
};


void StatePlaying::cargarTexturas() {
	map<string, Drawable*>::iterator iter;
	for (iter = managedObjects->begin(); iter != managedObjects->end(); iter++) {
		Drawable* object = (iter->second);
		object->cargarTextura();
	}
}

void StatePlaying::colisionDetectada() {
	Plane* avion = (Plane*) this->personaje;
	avion->detener();

	// Finalizo el timer
	Hud::getInstance()->finishGame();

	// Cambio estado
	StateGameOver::getInstance()->iniciarTimer();
	Controlador::getInstance()->cambiarEstado(StateGameOver::getInstance());
}

void StatePlaying::mostrarObstaculos() {
	map<string, Drawable*>::iterator iter;
	for (iter = managedObjects->begin(); iter != managedObjects->end(); iter++) {
		if (iter->first.find("obstaculo") != string::npos) {
			Drawable* object = (iter->second);
			((Obstacle*)object)->mostrarObstaculos(this->personaje->getXcoord());
		}
	}
}

void StatePlaying::ocultarObstaculos() {
	map<string, Drawable*>::iterator iter;
	for (iter = managedObjects->begin(); iter != managedObjects->end(); iter++) {
		if (iter->first.find("obstaculo") != string::npos) {
			Drawable* object = (iter->second);
			((Obstacle*)object)->ocultarObstaculos();
		}
	}
}

Plane* StatePlaying::getPersonaje() {
	return this->personaje;
}

void StatePlaying::reinicializarJuego() {
	// Reinicializo al avión
	this->personaje->reinicializar();

	// Reinicializo los obstaculos
	map<string, Drawable*>::iterator iter;
	for (iter = managedObjects->begin(); iter != managedObjects->end(); iter++) {
		if (iter->first.find("obstaculo") != string::npos) {
			Drawable* object = (iter->second);
			((Obstacle*)object)->reinicializar();
		}
	}

	// Desactivo la visibilidad de los obstaculos
	this->ocultarObstaculos();
}