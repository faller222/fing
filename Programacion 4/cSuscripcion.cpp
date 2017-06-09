#include "cSuscripcion.h"
#include "cAltaUsuario.h"
#include "cCrearRecurso.h"
#include "Usuario.h"
#include "Recurso.h"
#include "Archivo.h"
#include "DataRecurso.h"
#include "DataUsuario.h"
#include "iObserver.h"
#include "ExRutaInvalida.h"
#include <string.h>
#include <vector>

using namespace std;

cSuscripcion* cSuscripcion::instancia = NULL;

cSuscripcion::cSuscripcion() {}
cSuscripcion::~cSuscripcion() {}

void cSuscripcion::destruir() {
    if (instancia!=NULL) {
        delete instancia;
	instancia = NULL;
    }
}

cSuscripcion* cSuscripcion::getInstance(){
    if (instancia == NULL)
        instancia = new cSuscripcion();
    return instancia;
};

void cSuscripcion::suscribirUsuarioArchivo(){
    iObserver* o = refUser;
    refArch->agregarObserver(o);
}
void cSuscripcion::seleccionarUsuario(const string nName){
    cAltaUsuario* CAU=cAltaUsuario::getInstance();
    this->refUser = CAU->buscarUsuario(nName);
}
void cSuscripcion::seleccionarArchivo(const string rAbs) throw (ExRutaInvalida){
    cCrearRecurso* CCR=cCrearRecurso::getInstance();
    Recurso* rec = CCR->buscarRecurso(rAbs);
    if (rec->getTipo()== CARPETA)
        throw ExRutaInvalida();
    this->refArch =(Archivo*)CCR->buscarRecurso(rAbs);

}

vector<DataRecurso> cSuscripcion::obtenerArchivos(){
    cCrearRecurso* CCR=cCrearRecurso::getInstance();
    return CCR->obtenerRecursosTipo(ARCHIVO);
}

vector<DataUsuario> cSuscripcion::obtenerUsuarios(){
    cAltaUsuario* CAU=cAltaUsuario::getInstance();
    return CAU->obtenerUsuariosRegistrados();
}

vector<DataNotificacion> cSuscripcion::obtenerNotificaciones(){
   return refUser->obtenerDataNotificaciones();
}
