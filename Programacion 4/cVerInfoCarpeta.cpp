#include "cVerInfoCarpeta.h"
#include "cCrearRecurso.h"
#include "DataRecurso.h"
#include "DataUsuario.h"
#include "ExRutaInvalida.h"

#include <string>

#include <vector>

using namespace std;

cVerInfoCarpeta::cVerInfoCarpeta(){};

//destructor privado
cVerInfoCarpeta::~cVerInfoCarpeta() {}

cVerInfoCarpeta* cVerInfoCarpeta::instancia = NULL;

cVerInfoCarpeta* cVerInfoCarpeta::getInstance(){
    if (!instancia)
        instancia= new cVerInfoCarpeta();
    return instancia;
}

void cVerInfoCarpeta::destruir() { //es dueño de la coleccion de UsuarioCarpeta
    if (instancia!=NULL) {
        delete instancia;
	instancia = NULL;
    }
}


vector<DataRecurso> cVerInfoCarpeta::obtenerCarpetas(){
    cCrearRecurso* CCR=cCrearRecurso::getInstance();
    return CCR->obtenerRecursosTipo(CARPETA);
}

DataRecurso cVerInfoCarpeta::seleccionarCarpeta(string rAbs)throw(ExRutaInvalida){
    cCrearRecurso* CCR=cCrearRecurso::getInstance();
    Recurso* rec = CCR->buscarRecurso(rAbs);
    if (rec->getTipo()== ARCHIVO)
        throw ExRutaInvalida();
    this->refCarp =(Carpeta*)CCR->buscarRecurso(rAbs);
    return refCarp->getDataRecurso();
}

DataUsuario cVerInfoCarpeta::obtenerCreador(){
    return this->refCarp->getCreador()->getDataUsuario();
}

vector<DataRecurso> cVerInfoCarpeta::obtenerRecursosContenidos(){
   // cVerInfoUsuario* CVIR=cVerInfoUsuario::getInstance();
  //  return CVIR->obtenerAcciones(refArch->getRutaAbsoluta());
}