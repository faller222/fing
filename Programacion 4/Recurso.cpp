//Recurso.cpp
#include "Recurso.h"
#include "Utils.h"
#include "Usuario.h"

#include <string>
#include <iostream>

using namespace std;

Recurso::Recurso(){}

Recurso::~Recurso(){

}

void Recurso::setCreador(Usuario* u) {
	this->refCreador = u;
}

Usuario* Recurso::getCreador(){
    return this->refCreador;
}

string Recurso::getNombre(){
    return this->nombre;
}

string Recurso::getDescripcion(){
    return this->descripcion;
}

string Recurso::getRutaAbsoluta(){
    return rutaAbsoluta;
}

bool Recurso::sosVos(string ruta){
    return (ruta==rutaAbsoluta);
}

bool Recurso::esCreador(string nName){
    return this->refCreador->sosVos(nName);
}

void Recurso::setfModif(){
    fModif=DateTime();
}
