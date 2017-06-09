
#include "Recurso.h"
#include "Archivo.h"
#include "Usuario.h"
#include "DataRecurso.h"
#include "Utils.h"
#include "iObserver.h"
#include "cVerInfoUsuario.h"

#include <string>
#include <set>

using namespace std;

Archivo::Archivo(const string nomb, const string desc, const string rAbs, Usuario* u){
    contComent=0;
    nombre=nomb;
    descripcion=desc;
    rutaAbsoluta=rAbs;
    this->setCreador(u);
    cVerInfoUsuario* VIU=cVerInfoUsuario::getInstance();
    VIU->CrearAccion(CREACION,this,u);
}
Archivo::~Archivo(){

}

tipoRecurso Archivo::getTipo(){
    return ARCHIVO;
}
DataRecurso Archivo::getDataRecurso(){
    return DataRecurso(nombre,descripcion,rutaAbsoluta,ARCHIVO, fModif, fCrea);
}

void Archivo::setDescripcion(const string Desc){
    descripcion=Desc;
}

void Archivo::obtenerHijosRecursivo(vector<Recurso*>& tH) {
	//tH.push_back(this);
}

vector<Recurso*> Archivo::obtenerHijosDirectos() {
	vector<Recurso*> v;
	if (v.empty()) cout<< "Archivo::obtenerHijosDirectos() es vacio\n";
	return v;
}

void Archivo::agregarObserver(iObserver* o){
    observers.insert(o);
}

void Archivo::quitarObserver(iObserver* o){
    observers.erase(o);
}

void Archivo::modificar(tipoNotificacion tNot){
    Notificacion* n;
    set<iObserver*>::iterator it;
    for (it = observers.begin(); it != observers.end(); ++it ){
        n = new Notificacion(this, tNot);
        (*it)->notificar(n);
    }
}

int Archivo::getContador(){
    contComent++;
    return contComent;
}


