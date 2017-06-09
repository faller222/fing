//cIngresarComentario.cpp
#include "Utils.h"

#include "cIngresarComentario.h"
#include "cAltaUsuario.h"
#include "cCrearRecurso.h"

#include "Usuario.h"
#include "DataUsuario.h"
#include "Archivo.h"
#include "Comentario.h"
#include "DataComentario.h"

#include <string.h>
#include <vector>
#include <string>

#include "ExNoExisteUsuario.h"
#include "ExNoExisteComentario.h"
#include "ExNoHayUsuariosRegistrados.h"

using namespace std;

cIngresarComentario::cIngresarComentario(){
}

//inicializo variable estatica para singleton
cIngresarComentario* cIngresarComentario::instancia = NULL;

cIngresarComentario* cIngresarComentario::getInstance(){   
    if (!instancia)
        instancia = new cIngresarComentario();
    return instancia;
}

//destructor privado
cIngresarComentario::~cIngresarComentario() {
	map<int, Comentario*>::iterator it;
	for (it=this->comentarios.begin(); it!=this->comentarios.end(); ++it ) {
		delete (it->second); //elimino el UsuarioCarpeta
	}

	this->comentarios.clear();
}

void cIngresarComentario::destruir() { //es dueño de la coleccion de UsuarioCarpeta
    if (instancia!=NULL) {
        delete instancia;
	instancia = NULL;
    }
}




vector<DataComentario> cIngresarComentario::obtenerComentariosArchivo(const string rAbs) throw (ExRutaInvalida){
    vector<DataComentario> result;
    cCrearRecurso* CCR=cCrearRecurso::getInstance();
    Recurso* rec = CCR->buscarRecurso(rAbs);
    if (rec->getTipo()==CARPETA)
       throw ExRutaInvalida();    
    memArch = (Archivo*)rec;
    map<int, Comentario*>::iterator it;
    DataComentario dC;
    
    for ( it=this->comentarios.begin(); it!= this->comentarios.end(); ++it)
    {
        if (it->second->perteneceA(rAbs)){
           dC = it->second->getDataComentario();
           result.push_back( dC );
            }
    } 
    return result;
}

vector<DataUsuario> cIngresarComentario::obtenerUsuariosRegistrados() throw (ExNoHayUsuariosRegistrados){
    cAltaUsuario* CAU=cAltaUsuario::getInstance();
    return CAU->obtenerUsuariosRegistrados();
}

void cIngresarComentario::seleccionarUsuario(const string nName) throw (ExNoExisteUsuario){
        cAltaUsuario* CAU=cAltaUsuario::getInstance();
        this->memUser=CAU->buscarUsuario(nName);
}

Comentario* cIngresarComentario::buscarComentario(const int id) throw (ExNoExisteComentario){
    map<int, Comentario*>::iterator it;
    Comentario* com=NULL;
    if (this->comentarios.count(id)==0)
        throw ExNoExisteComentario();
    for(it = this->comentarios.begin(); it!=comentarios.end(); it++){
        if(it->second->getArchivo()==memArch)
            if(it->second->getId()==id)
                com=it->second;
    }
    if(com==NULL) throw ExNoExisteComentario();
    return com;
}

void cIngresarComentario::responderComentario(const string contenido,const int idComentario) throw (ExNoExisteComentario){
    cIngresarComentario* CIC=cIngresarComentario::getInstance();
    Comentario* C=CIC->buscarComentario(idComentario);
    if (!C->perteneceA(memArch->getRutaAbsoluta()))
        throw ExNoExisteComentario();
    Comentario* nCom = new Comentario(contenido,memUser,memArch);
    comentarios[nCom->getIdCom()]=nCom;
    C->agregarRespuesta(nCom);
}

void cIngresarComentario::agregarComentarioArchivo(const string contenido){
    Comentario* nCom = new Comentario(contenido,memUser,memArch);
    comentarios[nCom->getIdCom()]=nCom;
}

