//Usuario.cpp

#include "Usuario.h"
#include "iObserver.h"
#include "Notificacion.h"

#include "Utils.h"
#include "DateTime.h"
#include "DataUsuario.h"
#include <string>
#include <vector>


Usuario::Usuario(const string nName, sexoEnum sexo, const DateTime& fNac) {
	this->nickname= nName;
        this->sexo = sexo;
	this->fNac = fNac;
}

//gets
string Usuario::getNickname() {
	return this->nickname;
}

sexoEnum Usuario::getSexo() {
	return this->sexo;
}

DateTime Usuario::getFNac() {
	return this->fNac;
}

int Usuario::getEdad() {
	DateTime fechaActual;
	return (fechaActual - this->fNac)/360; // edad ->atributo calculado
}

DataUsuario Usuario::getDataUsuario(){
    return DataUsuario(this->nickname,this->sexo,this->fNac);
}

bool Usuario::sosVos(const string nName){
    return (nName==nickname);
}

void Usuario::notificar(Notificacion* noti){
    notificaciones.push_back(noti);
}

vector<DataNotificacion> Usuario::obtenerDataNotificaciones(){
    vector<DataNotificacion> noti;
    vector<Notificacion*>::iterator it;
    for(it=this->notificaciones.begin(); it!=this->notificaciones.end(); ++it) {
        noti.push_back((*it)->getDataNotificacion());
        (*it)->setLeida();
    }
    return noti;
}


Usuario::~Usuario(){
    	vector<Notificacion*>::iterator it;
	for (it=this->notificaciones.begin(); it!=this->notificaciones.end(); ++it ) {
		delete (*it); //elimino las notificaciones
	}

	this->notificaciones.clear();
}
