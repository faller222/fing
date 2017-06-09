#include <sstream>
#include "Building.h"
#include "Floor.h"
#include "Message.h"
#include "Obstacle.h"
#include "SkyBox.h"
#include "StatePlaying.h"
#include "TextureManager.h"
#include "Editor.h"
#include "FTGLTextureFont.h"
#include "Fonts.h"
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
	idObs = 0;

	/* Creación de objetos del juego */

	Floor* piso = new Floor("../ComputacionGraficaLab/textures/Tex_Street.jpg", 0.0f, -2.0f, 0.0f);
	this->manageObject("piso", piso);


	Obstacle* obs = new Obstacle("../ComputacionGraficaLab/textures/Tex_Wall.jpg", 100, 10, 0.0f);
	this->manageObject("personaje", obs);
	obs->mostrar(false);
	crear = false;
	block = false;

	string* texturePathsSky = new string[5]();
	texturePathsSky[0] = "../ComputacionGraficaLab/textures/sky/Tex_Sky0.jpg";
	texturePathsSky[1] = "../ComputacionGraficaLab/textures/sky/Tex_Sky1.jpg";
	texturePathsSky[2] = "../ComputacionGraficaLab/textures/sky/Tex_Sky2.jpg";
	texturePathsSky[3] = "../ComputacionGraficaLab/textures/sky/Tex_Sky3.jpg";
	texturePathsSky[4] = "../ComputacionGraficaLab/textures/sky/Tex_Sky4.jpg";

	skyBox = new SkyBox(texturePathsSky, obs);
	this->manageObject("sol", skyBox);

	string* texturePathsBuildings = new string[BUILDINGS]();

	for (int i = 1; i <= BUILDINGS; i++) {
		ostringstream ss;
		ss << "../ComputacionGraficaLab/textures/buildings/Tex_Building" << i << ".jpg";
		texturePathsBuildings[i - 1] = ss.str();
	}

	Building* edificios = new Building(0.0f, -2.0f, 0.0f, texturePathsBuildings);
	this->manageObject("edificios", edificios);
	
	Controlador::getInstance()->setCameraLookingPoint(obs, CAMERA_DISTANCE);
	Controlador::getInstance()->getCamera()->setCameraPosition(100, 10, CAMERA_DISTANCE);

	/* Cargado de texturas */
	this->cargarTexturas();

}




void StatePlaying::guardarXML(){
	Editor* editor = new Editor();
	editor->crearXML();
	map<string, Drawable*>::iterator iter;
	for (iter = managedObjects->begin(); iter != managedObjects->end(); iter++) {
		if (iter->first.find("obstaculo") != string::npos) {
			Drawable* object = (iter->second);
			editor->escribirXML((int)object->getXcoord(), (int)object->getYcoord());
		}

	}
	delete editor;

}
void StatePlaying::borrarObstaculos(){

	map<string, Drawable*>::iterator iter = managedObjects->begin();
	map<string, Drawable*>::iterator itTemp;
	while (iter != managedObjects->end())
	{
			if (iter->first.find("obstaculo") != string::npos) // Criteria checking here
			{
				itTemp = iter;          // Keep a reference to the iter
				++iter;                 // Advance in the map
				delete itTemp->second;
				managedObjects->erase(itTemp);
				
			}else
				++iter;                 // Just move on ...
	}

};
void StatePlaying::procesarEvento(SDL_Event evento) {
	
		switch (evento.type) {
			
			case SDL_KEYDOWN:

				switch (evento.key.keysym.sym) {
				case SDLK_n:{
								borrarObstaculos();
								Obstacle* obs = this->getPersonaje();
								obs->setXcoord(100);
								obs->setYcoord(10);
								obs->setZcoord(0);
								Controlador::getInstance()->setCameraLookingPoint(obs, CAMERA_DISTANCE);
								Controlador::getInstance()->getCamera()->setCameraPosition(100, 10, CAMERA_DISTANCE);
								Message::getInstance()->registrarMensaje("Creado nuevo nivel");
				}
					break;
				case SDLK_UP:
					this->personaje->setMoveArriba(true);
					break;
				case SDLK_DOWN:
					// Pausa
					this->personaje->setMoveAbajo(true);
					break;				
				case SDLK_LEFT:
					this->personaje->setMoveI(true);
					break;
				case SDLK_RIGHT:
					this->personaje->setMoveD(true);
					break;
				case SDLK_o:{
								Obstacle* obs = this->getPersonaje();
								if (!crear){
									obs->mostrar(true);
									crear = true;
								}
								else{
									obs->mostrar(false);
									crear = false;
								}
				}break;
				case SDLK_RETURN:{
									 
										if (crear){
											Obstacle* obs = this->getPersonaje();
											this->personaje->setMoveI(false);
											this->personaje->setMoveD(false);
											this->personaje->setMoveArriba(false);
											this->personaje->setMoveAbajo(false);
											this->unManageObject("personaje");
											ostringstream ss;
											ss << "obstaculo" << idObs;
											this->manageObject(ss.str(), obs);
											idObs++;
											Obstacle* newObs = new Obstacle("../ComputacionGraficaLab/textures/Tex_Wall.jpg", obs->getXcoord(), obs->getYcoord(), 0.0f);
											newObs->mostrar(false);
											Controlador::getInstance()->setCameraLookingPoint(newObs, CAMERA_DISTANCE);
											Controlador::getInstance()->getCamera()->setCameraPosition(obs->getXcoord(), obs->getYcoord(), CAMERA_DISTANCE);
											skyBox->changeChar(newObs);
											this->manageObject("personaje", newObs);
											crear = false;
											newObs->cargarTextura();

											Message::getInstance()->registrarMensaje("Obstaculo creado!");
										}
				}
					break;
				case SDLK_BACKSPACE:{
										Obstacle* Lobs = this->getPersonaje();
										Lobs->mostrar(false);

										if (idObs > 0)
										{
											idObs--;
											ostringstream ss;
											ss << "obstaculo" << idObs;
											Drawable* obs = (*this->managedObjects)[ss.str()];
											Drawable* newObs = this->getPersonaje();
											this->unManageObject(ss.str());
											newObs->setXcoord(obs->getXcoord());
											newObs->setYcoord(obs->getYcoord());
											newObs->setZcoord(obs->getZcoord());
											Controlador::getInstance()->setCameraLookingPoint(newObs, CAMERA_DISTANCE);
											Controlador::getInstance()->getCamera()->setCameraPosition(obs->getXcoord(), obs->getYcoord(), CAMERA_DISTANCE);
											Message::getInstance()->registrarMensaje("Eliminado el último obstaculo");
										}
				}
					break;
				case SDLK_s:{
								this->guardarXML();
								Message::getInstance()->registrarMensaje("Se ha guardado el nivel en el archivo 'nivel.xml'");
				}
					break;

				}
				break;
				
		case SDL_KEYUP:
			switch (evento.key.keysym.sym) {
			default:block = false;
			case SDLK_LEFT:
				this->personaje->setMoveI(false);
				break;
			case SDLK_RIGHT:
				this->personaje->setMoveD(false);
				break;
			case SDLK_UP:
				this->personaje->setMoveArriba(false);
				break;
			case SDLK_DOWN:
				// Pausa
				this->personaje->setMoveAbajo(false);
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
void StatePlaying::mostrarPanel(int jitter){

	this->setProjectionMatrix(M_PROJ_ORTHO, jitter);

	// Dibujo el cuadro
	glDisable(GL_DEPTH_TEST);
	glPushMatrix();
	glBegin(GL_QUADS);
	
	glColor4f(0.0f, 0.0f, 0.0f, 0.5f);
	glVertex2f(0.0f, WINDOW_HEIGHT - 45.0f);
	glVertex2f(WINDOW_WIDTH, WINDOW_HEIGHT - 45.0f);
	glVertex2f(WINDOW_WIDTH, WINDOW_HEIGHT);
	glVertex2f(0.0f, WINDOW_HEIGHT);
	glEnd();
	glPopMatrix();
	glEnable(GL_DEPTH_TEST);

	// Dibujo el texto
	Controlador::getInstance()->activarTexturas();
	glPushMatrix();
	glColor3f(0.75f, 0.75f, 0.75f);
	FTGLTextureFont* fuente = Fonts::getInstance()->obtenerFuente("arial36");


	glPushMatrix();
	glTranslatef(0 * WINDOW_WIDTH / 5 + 10 , WINDOW_HEIGHT - 25, 0);
	glScalef(0.5f, 0.5f, 0.0f);
	fuente->Render("O - Crear");
	glPopMatrix();


	
	glPushMatrix();
	glTranslatef(1 * WINDOW_WIDTH / 5 -80, WINDOW_HEIGHT - 25, 0);
	glScalef(0.5f, 0.5f, 0.0f);
	fuente->Render("ENTER - Insertar");
	glPopMatrix();


	glPushMatrix();
	glTranslatef(2 * WINDOW_WIDTH / 5 -100, WINDOW_HEIGHT - 25, 0);
	glScalef(0.5f, 0.5f, 0.0f);
	fuente->Render("BACKSPACE - Borrar");
	glPopMatrix();

	glPushMatrix();
	glTranslatef(3 * WINDOW_WIDTH / 5 -100, WINDOW_HEIGHT - 25, 0);
	glScalef(0.5f, 0.5f, 0.0f);
	fuente->Render("FLECHAS - Posicionar");
	glPopMatrix();


	glPushMatrix();
	glTranslatef(4*WINDOW_WIDTH/5 -100, WINDOW_HEIGHT - 25, 0);
	glScalef(0.5f, 0.5f, 0.0f);
	fuente->Render("S - Guardar");
	glPopMatrix();

	glPushMatrix();
	glTranslatef(5 * WINDOW_WIDTH / 5 -150, WINDOW_HEIGHT - 25, 0);
	glScalef(0.5f, 0.5f, 0.0f);
	fuente->Render("N - Nuevo Nivel");
	glPopMatrix();



	glPopMatrix();
	Controlador::getInstance()->desactivarTexturas();

};

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
	mostrarPanel(jitter);

}

void StatePlaying::manageObject(string name, Drawable* obj) {
	(*this->managedObjects)[name] = obj;
	if (name == "personaje") {
		// Se asume que este es el personaje principal
		this->personaje = (Obstacle*) obj;
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
	/*map<string, Drawable*>::iterator iter;
	for (iter = managedObjects->begin(); iter != managedObjects->end(); iter++) {
		if (iter->first.find("obstaculo") != string::npos) {
			Drawable* object = (iter->second);
			((Obstacle*)object)->ocultarObstaculos();
		}
	}*/
}

Obstacle* StatePlaying::getPersonaje() {
	return this->personaje;
}

void StatePlaying::reinicializarJuego() {

	// Reinicializo los obstaculos
	map<string, Drawable*>::iterator iter;
	for (iter = managedObjects->begin(); iter != managedObjects->end(); iter++) {
		if (iter->first.find("obstaculo") != string::npos) {
			Drawable* object = (iter->second);
			((Obstacle*)object)->reinicializar();
		}
	}

	// Desactivo la visibilidad de los obstaculos
}