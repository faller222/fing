//DataUsuario.cpp

#include "DataUsuario.h"
#include "Utils.h"
#include "DateTime.h"
#include <string>


DataUsuario::DataUsuario() {}

DataUsuario::DataUsuario(const string nName, sexoEnum sexo, const DateTime& fNac) {
	this->nickname= nName;
	this->sexo = sexo;
	this->fNac = fNac;
}

string DataUsuario::getNickname() {
	return this->nickname;
}

sexoEnum DataUsuario::getSexo() {
	return this->sexo;
}

DateTime DataUsuario::getFNac() {
	return this->fNac;
}

int DataUsuario::getEdad() {
	DateTime fechaActual;
	return (fechaActual - this->fNac)/360; // edad ->atributo calculado
}
