#include "Accion.h"
#include "Utils.h"
#include "Usuario.h"
#include "DataAccion.h"

#include <string.h>
#include <iostream>

using namespace std;

Accion::Accion(){}

Usuario* Accion::getCreador(){
    return this->refUsuario;
};

Archivo* Accion::getArchivo(){
    return this->refArchivo;
};

DataAccion Accion::getDataAccion(){
    return DataAccion(fAccion, this->getTipo(), refUsuario->getNickname());
    
}

bool Accion::esAutor(string nName){
    return this->refUsuario->sosVos(nName);
};

