
#include "DataAccion.h"
#include "DateTime.h"
#include "Utils.h"
#include <string>

DataAccion::DataAccion() {}

DataAccion::DataAccion(DateTime fAccion, tipoAccion tipo, string nName) {
    this->fAccion=fAccion;
    this->tipo=tipo;
    this->nName=nName;
}

tipoAccion DataAccion::getTipo(){
    return this->tipo;
}


DateTime DataAccion::getFAccion(){
    return this->fAccion;
}

string DataAccion::getCreador(){
    return this->nName;
}

DataAccion::~DataAccion() {}


