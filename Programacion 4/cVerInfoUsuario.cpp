#include "cVerInfoUsuario.h"
#include "cCrearRecurso.h"
#include "cAltaUsuario.h"
#include "cAgregarColaborador.h"

#include "Usuario.h"
#include "Recurso.h"
#include "Archivo.h"
#include "Carpeta.h"
#include "Accion.h"

#include "DataRecurso.h"
#include "DataUsuario.h"
#include "DataAccion.h"
#include "aCrear.h"
#include "aModificar.h"
#include "aDescargar.h"

#include <string>
#include <map>
#include <vector>

using namespace std;

cVerInfoUsuario::cVerInfoUsuario(){};

//inicializo variable estatica para singleton
cVerInfoUsuario* cVerInfoUsuario::instancia = NULL;

cVerInfoUsuario* cVerInfoUsuario::getInstance(){
    if(!instancia)
        instancia=new cVerInfoUsuario();
    return instancia;
}

//destructor privado
cVerInfoUsuario::~cVerInfoUsuario() {
	vector<Accion*>::iterator it;
	for (it=this->acciones.begin(); it!=this->acciones.end(); ++it ) {
		delete (*it); //elimino el UsuarioCarpeta
	}

	this->acciones.clear();
}

void cVerInfoUsuario::destruir() { //es dueño de la coleccion de UsuarioCarpeta
    if (instancia!=NULL) {
        delete instancia;
	instancia = NULL;
    }
}



vector<DataUsuario> cVerInfoUsuario::obtenerUsuariosRegistrados(){
    cAltaUsuario* CAU=cAltaUsuario::getInstance();
    return CAU->obtenerUsuariosRegistrados();
}

DataUsuario cVerInfoUsuario::obtenerInfoUsuario(const string nNameUsuario)  throw ( ExNoExisteUsuario ){
    this->nName=nNameUsuario;
    cAltaUsuario* CAU=cAltaUsuario::getInstance();
    return CAU->obtenerInfoUsuario(nNameUsuario);
}

vector<DataRecurso> cVerInfoUsuario::obtenerRecursosCreados(){
    cCrearRecurso* CCR=cCrearRecurso::getInstance();
    return CCR->obtenerRecursosCreados(this->nName);
}

map<string, DataRecurso> cVerInfoUsuario::obtenerCarpetasColabora(){
    cAgregarColaborador* CAC=cAgregarColaborador::getInstance();
    return CAC->obtenerCarpetasColabora(this->nName);
}

vector<DataAccion> cVerInfoUsuario::obtenerAcciones(){
    string nNameUsuario=this->nName;
    vector<DataAccion> listaDataAcciones;
    vector<Accion*>::iterator it;
    DataAccion dA;
    for ( it=this->acciones.begin(); it!= this->acciones.end(); ++it) { //recorro el hash de acciones
        if ((*it)->esAutor(nNameUsuario)){
            dA = (*it)->getDataAccion();
            listaDataAcciones.push_back( DataAccion(dA) ); //void push_back (const value_type& val); Adds a new element at the end of the vector, after its current last element.
        }
    }
    return listaDataAcciones;
}

vector<DataAccion> cVerInfoUsuario::obtenerAcciones(Archivo* arch){
    vector<DataAccion> listaDataAcciones;
    vector<Accion*>::iterator it;
    DataAccion dA;
    for ( it=this->acciones.begin(); it!= this->acciones.end(); ++it) { //recorro el hash de acciones
        if ((*it)->getArchivo()==arch){
            dA = (*it)->getDataAccion();
            listaDataAcciones.push_back( DataAccion(dA) ); //void push_back (const value_type& val); Adds a new element at the end of the vector, after its current last element.
        }
    }
    return listaDataAcciones;
}

void cVerInfoUsuario::CrearAccion(tipoAccion tA,Archivo* refA,Usuario* refU){
    Accion* accion;
    switch (tA) {
        case CREACION: accion=new aCrear(refU, refA);
        break;
        case DESCARGAR: accion=new aDescargar(refU, refA);
        break;
        case MODIFICAR: accion=new aModificar(refU, refA);
        break;
    };
    this->acciones.push_back( accion );
}