#include "cModificarArchivo.h"
#include "cAltaUsuario.h"
#include "cCrearRecurso.h"
#include "ExNoExisteUsuario.h"
#include "ExRutaInvalida.h"
#include "DataRecurso.h"
#include "Utils.h"
#include "cVerInfoUsuario.h"
#include <vector>
#include <string>

using namespace std;

cModificarArchivo::cModificarArchivo(){};
cModificarArchivo::~cModificarArchivo(){};

cModificarArchivo* cModificarArchivo::instancia = NULL;


cModificarArchivo* cModificarArchivo::getInstance(){
    if(!instancia)
        instancia=new cModificarArchivo();
    return instancia;
}

void cModificarArchivo::destruir() {
    if (instancia!=NULL) {
        delete instancia;
	instancia = NULL;
    }
}

vector<DataUsuario> cModificarArchivo::obtenerUsuariosRegistrados(){
    cAltaUsuario* CAU=cAltaUsuario::getInstance();
    return CAU->obtenerUsuariosRegistrados();
}

void cModificarArchivo::seleccionarUsuario(const string nName)  throw ( ExNoExisteUsuario ){
    cAltaUsuario* CAU=cAltaUsuario::getInstance();
    this->refUsuario = CAU->buscarUsuario(nName);
}

vector<DataRecurso> cModificarArchivo::obtenerRecursosTipo(){
    cCrearRecurso* CCR=cCrearRecurso::getInstance();
    return CCR->obtenerRecursosTipo(ARCHIVO);
}

void cModificarArchivo::seleccionarArchivo(const string arch) throw(ExRutaInvalida){
    cCrearRecurso* CCR=cCrearRecurso::getInstance();
    Recurso* rec=CCR->buscarRecurso(arch);
    if(rec->getTipo()==CARPETA)
        throw ExRutaInvalida();
    this->refArchivo=(Archivo*)rec;
}

void cModificarArchivo::ingresarDescripcion(const string desc){
    this->refArchivo->setDescripcion(desc);
    cVerInfoUsuario* VIU = cVerInfoUsuario::getInstance();
    VIU->CrearAccion(MODIFICAR, refArchivo, refUsuario);
    refArchivo->modificar(MODIFICACION);
    refArchivo->setfModif();
}
