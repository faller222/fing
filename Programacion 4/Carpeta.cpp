#include "Recurso.h"
#include "Usuario.h"
#include "Carpeta.h"
#include "DataRecurso.h"

#include "Utils.h"
#include <stdio.h>
#include <string>



Carpeta::Carpeta(const string nomb, const string desc, const string rAbs, Usuario* u){
        nombre=nomb;
        descripcion=desc;
        rutaAbsoluta=rAbs;
        this->setCreador(u);
    }

Carpeta::~Carpeta(){
    if(nombre=="raiz")
        delete refCreador;
}

Carpeta::Carpeta(){
        nombre="raiz";
        descripcion="Carpeta raiz";
        rutaAbsoluta="raiz";
        this->refCreador=new Usuario("-",MASCULINO,DateTime());
    }

tipoRecurso Carpeta::getTipo(){
        return CARPETA;
    }

DataRecurso Carpeta::getDataRecurso(){
        return DataRecurso(nombre,descripcion,rutaAbsoluta,CARPETA, fModif, fCrea);
    }

void Carpeta::agregarHijo(Recurso* hijo){
    this->hijos[hijo->getRutaAbsoluta()] = hijo;
    fModif=DateTime();
    }

vector<DataRecurso> Carpeta::obtenerDataHijosDirectos(){
    vector<DataRecurso> lista;

    map<string, Recurso*>::iterator it;
    DataRecurso dR;

    for ( it=this->hijos.begin(); it!= this->hijos.end(); ++it) {
        dR = it->second->getDataRecurso();
        lista.push_back( DataRecurso(dR) );
    }
    return lista;
}

vector<Recurso*> Carpeta::obtenerHijosDirectos(){
    vector<Recurso*> lista;

    map<string, Recurso*>::iterator it;

    for ( it=this->hijos.begin(); it!= this->hijos.end(); ++it) {
        lista.push_back( it->second );
    }
    return lista;
}


void Carpeta::obtenerHijosRecursivo( vector<Recurso*>& tH ) { //me devuelve Todos Los Hijos, Archivos y Carpetas
    //cout << "entra en  Carpeta::obtenerHijosRecursivo\n";
	vector<Recurso*>::iterator it;

    vector<Recurso*> v = this->obtenerHijosDirectos();
    for ( it=v.begin(); it!=v.end(); ++it) {
		//cout << sizeof(tH) << "\n";
        (*it)->obtenerHijosRecursivo(tH);
    }

	tH.push_back(this);
}

vector<Recurso*> Carpeta::obtenerTotalHijos() {
    this->obtenerHijosRecursivo(this->totalHijos);

	//borrar
	//vector<Recurso*>::iterator it;
	//for ( it=totalHijos.begin(); it!=totalHijos.end(); ++it ) {
	//	cout << (*it)->getDataRecurso().getNombre() << "\n";
	//	cout << (*it)->getDataRecurso().getTipo() << "\n";
	//}

	//borrar

    return totalHijos;
}


vector<DataRecurso> Carpeta::obtenerDataCarpetasHijas() {
    vector<DataRecurso> result;
    vector<Recurso*> v = this->obtenerTotalCarpetasHijas();
    vector<Recurso*>::iterator it;

    for (it=v.begin(); it!=v.end(); ++it) {
        result.push_back((*it)->getDataRecurso());
    }
    return result;
}

vector<Recurso*> Carpeta::obtenerTotalCarpetasHijas() {
    vector<Recurso*> result;

    vector<Recurso*> v = this->obtenerTotalHijos();
    vector<Recurso*>::iterator it;
    for (it=v.begin(); it!=v.end(); ++it) {
        if ( (*it)->getDataRecurso().getTipo() == CARPETA ) {
                result.push_back((*it));
        }
    }
    return result;
}
