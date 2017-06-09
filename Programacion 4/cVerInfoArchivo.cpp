#include "cVerInfoArchivo.h"
#include "cVerInfoUsuario.h"
#include "cIngresarComentario.h"
#include "cCrearRecurso.h"
#include "ExRutaInvalida.h"
#include "DataAccion.h"
#include "DataComentario.h"
#include "DataRecurso.h"
#include <string>
#include <vector>

using namespace std;

cVerInfoArchivo::cVerInfoArchivo(){}

cVerInfoArchivo* cVerInfoArchivo::instancia = NULL;

cVerInfoArchivo* cVerInfoArchivo::getInstance(){
    if (!instancia)
        instancia= new cVerInfoArchivo();
    return instancia;
}

//destructor privado
cVerInfoArchivo::~cVerInfoArchivo() {}

void cVerInfoArchivo::destruir() { //es dueño de la coleccion de UsuarioCarpeta
    if (instancia!=NULL) {
        delete instancia;
	instancia = NULL;
    }
}



vector<DataRecurso> cVerInfoArchivo::obtenerArchivos(){
    cCrearRecurso* CCR=cCrearRecurso::getInstance();
    return CCR->obtenerRecursosTipo(ARCHIVO);
}

void cVerInfoArchivo::seleccionarArchivo(string rAbs)throw(ExRutaInvalida){
    cCrearRecurso* CCR=cCrearRecurso::getInstance();
    Recurso* rec = CCR->buscarRecurso(rAbs);
    if (rec->getTipo()== CARPETA)
        throw ExRutaInvalida();
    this->refArch =(Archivo*)CCR->buscarRecurso(rAbs);
}

DataRecurso cVerInfoArchivo::obtenerArchivo(){
    return refArch->getDataRecurso();
}

vector<DataAccion> cVerInfoArchivo::obtenerAcciones(){
    cVerInfoUsuario* CVIR=cVerInfoUsuario::getInstance();
    return CVIR->obtenerAcciones(refArch);
}

vector<DataComentario> cVerInfoArchivo::obtenerComentarios(){
    cIngresarComentario* CIC=cIngresarComentario::getInstance();
    return CIC->obtenerComentariosArchivo(refArch->getRutaAbsoluta());
}














