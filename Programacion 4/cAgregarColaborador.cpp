// File:   cAgregarColaborador.cpp

//final 19.19


#include "cAgregarColaborador.h"

#include <string>
#include <map>

#include "DataUsuario.h"
#include "DataRecurso.h"

//constructor
cAgregarColaborador::cAgregarColaborador() {}

//destructor privado
cAgregarColaborador::~cAgregarColaborador() {

	vector<UsuarioCarpeta*>::iterator it;
	for (it=this->userCarps.begin(); it!=this->userCarps.end(); ++it ) {
		delete (*it); //elimino el UsuarioCarpeta
	}

	this->userCarps.clear();
}

//inicializo variable estatica para singleton
cAgregarColaborador* cAgregarColaborador::instanceAC = NULL;

cAgregarColaborador* cAgregarColaborador::getInstance() {
    if (instanceAC==NULL) {
        instanceAC = new cAgregarColaborador();
    }
    return instanceAC;

}

void cAgregarColaborador::destruir() { //es dueño de la coleccion de UsuarioCarpeta
    if (instanceAC!=NULL) {
        delete instanceAC;
	instanceAC = NULL;
    }
}



//interface
vector<DataUsuario> cAgregarColaborador::obtenerUsuariosRegistrados() {
    cAltaUsuario * cAU = cAltaUsuario::getInstance();
    return cAU->obtenerUsuariosRegistrados();
}

map<string,DataRecurso> cAgregarColaborador::obtenerCarpetasColabora(string nickName) throw (ExNoExisteUsuario) {
    map<string,DataRecurso> result;

    //excepcion ExNoExisteUsuario
    cAltaUsuario * cAU = cAltaUsuario::getInstance();
    cAU->obtenerInfoUsuario(nickName).getNickname(); //obtenerInfoUsuario(const string nNameUsuario) throw (ExNoExisteUsuario)

    //agrego las carpetas sobre las que tiene derecho a colaborar
    vector<UsuarioCarpeta*>::iterator it;
    for( it=userCarps.begin(); it!=userCarps.end(); ++it) {
         if ( (*it)->esColaborador(nickName) ) { //&& !(result.find((*it)->getDataCarpeta().getRuta()).end() ) ) {
			vector<DataRecurso> dRHijos = (*it)->obtenerDataCarpetasHijas();

			vector<DataRecurso>::iterator itRes;
			for ( itRes=dRHijos.begin(); itRes!=dRHijos.end(); ++itRes ) {
				result[itRes->getRuta()] = (*itRes);
			}

            result[(*it)->getDataCarpeta().getRuta()] = (*it)->getDataCarpeta(); //getDataCarp me devuelve por copia, no tengo problema de compartir memoria
		}
    }


    //si el usuario es creador de una carpeta -> tiene derechos de colaborador sobre todas sus carpetas hijas
    cCrearRecurso* cCR = cCrearRecurso::getInstance();
    vector <DataRecurso> dRCrea= cCR->obtenerRecursosCreadosTipoUsuario( CARPETA , nickName ); //retorna las carpetas creadas por el usuario con nickname "nickName"
    vector <DataRecurso>::iterator itC;
    for ( itC=dRCrea.begin(); itC!=dRCrea.end(); ++itC ) {
		vector<DataRecurso> dRHijos = cCR->obtenerHijosCarpetaTipo( (*itC).getRuta(), CARPETA ); //quiero las CARPETAs hijas de la carpeta sobre la cual estoy parado
		vector<DataRecurso>::iterator itRes;

		//map<string,DataRecurso>::iterator itVerif = result.find();

		for ( itRes=dRHijos.begin(); itRes!=dRHijos.end(); ++itRes ) {
			result[itRes->getRuta()] = (*itRes);
		}

        result[itC->getRuta()] = (*itC);
    }

	this->carpColab = result; //me lo guardo en memoria-> debo chequear que la ruta que me pasen esté entre las carpetas donde colabora


    return result;
}


void cAgregarColaborador::ingresarRutaRecurso(string rutaAbsoluta) throw (ExRutaInvalida) {
	//debo chequear si la ruta que me pasan, efectivamente es una sobre las cuales el usuario que me pasaron tiene derechos de colaborador
	map<string,DataRecurso>::iterator it = this->carpColab.find(rutaAbsoluta);
	if ( it==this->carpColab.end() || rutaAbsoluta=="raiz" ) throw ExRutaInvalida(); //no quiero poder agregar un colaborador a la raiz desde aca, lo agrego con Iniciar colab Raiz
	this->rutaRecIngresado = rutaAbsoluta;
}

void cAgregarColaborador::altaColaborador(string nickName, bool raiz) throw (ExNoExisteUsuario, ExMemoriaNoSeteada, ExYaTieneDerColaborador) {
    //Pre1: Existe en el sistema una instancia de Usuario con nickname == nNameUsuario -> throw ExNoExisteUsuario
    //Pre2: Existe en la memoria del sistema un valor que representa la rutaAbsoluta de una carpeta -> throw ExMemoriaNoSeteada
	//Pre3: El usuario de Pre1 no tiene derechos de colaborador sobre la carpeta que tiene como rutaAbsoluta this->rutaRecIngresado

	if (raiz) {
		this->rutaRecIngresado = "raiz";
	}

	//Pre1: excepcion ExNoExisteUsuario
    cAltaUsuario * cAU = cAltaUsuario::getInstance();
    cAU->obtenerInfoUsuario(nickName).getNickname(); //obtenerInfoUsuario(const string nNameUsuario) throw (ExNoExisteUsuario)

    //Pre2: excepcion ExMemoriaNoSeteada
    if (this->rutaRecIngresado =="" && raiz==false ) throw ExMemoriaNoSeteada(); //si raiz==true, no me ingresaron ruta

	//Pre3: excepcion ExYaTieneDerColaborador

	map<string,DataRecurso> cColab = this->obtenerCarpetasColabora(nickName);
	map<string,DataRecurso>::iterator it = cColab.find(this->rutaRecIngresado);
	if ( it!=cColab.end() ) throw ExYaTieneDerColaborador(); //esta entre las carpetas que tiene derecho a Colaborar

    cCrearRecurso * cCR = cCrearRecurso::getInstance();

    DateTime fColab; //obtengo la fecha del sistema
	UsuarioCarpeta * uC;
	uC = new UsuarioCarpeta(nickName, this->rutaRecIngresado, fColab);
	this->userCarps.push_back(uC); //agrego UsuarioCarpeta a la lista de UsuarioCarpetas



	//borrar
	//vector<UsuarioCarpeta*>::iterator ituc;
	//for( ituc=userCarps.begin(); ituc!=userCarps.end(); ++ituc) {
	//	cout << (*ituc)->getDataCarpeta().getNombre() << " " << (*ituc)->getDataUsuario().getNickname() << "\n";
	//}
	//borrar

	//se limpia la memoria del sistema:
	this->rutaRecIngresado = "";
	this->carpColab.clear();

}

//otras Funciones
