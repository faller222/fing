//DataRecurso.cpp

#include "DataRecurso.h"
#include <iostream>
#include "Utils.h"
#include <string>

using namespace std;

DataRecurso::DataRecurso() {}

DataRecurso::DataRecurso(const string name, const string desc,const string rAbs, tipoRecurso T, DateTime fModificacion, DateTime fCreacion) {
    this->nombre= name;
    this->descripcion= desc;
    this->rutaAbs= rAbs;
    this->tipo = T;
    this->fCreacion=fCreacion;
    this->fModificacion=fModificacion;
}

string DataRecurso::getNombre() {
    return this->nombre;
}

string DataRecurso::getDescripcion() {
    return this->descripcion;
}

tipoRecurso DataRecurso::getTipo() {
    return this->tipo;
}
string DataRecurso::getRuta(){
    return this->rutaAbs;
}

DateTime DataRecurso::getfModificacion(){
    return this->fModificacion;
}

DateTime DataRecurso::getfCreacion(){
    return this->fCreacion;
}

DataRecurso::~DataRecurso() {}
