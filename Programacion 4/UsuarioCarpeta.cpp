/*
 * File:   UsuarioCarpeta.cpp
 * Author: Helen
 *
 * Created on 8 de junio de 2013, 08:38 PM
 */


#include "Utils.h"
#include "DateTime.h"
#include "DataRecurso.h"
#include "UsuarioCarpeta.h"
#include "cAltaUsuario.h"
#include "cCrearRecurso.h"

UsuarioCarpeta::UsuarioCarpeta() {}
UsuarioCarpeta::UsuarioCarpeta(string nickName, string rutaRecurso, const DateTime& fColab) {

    cAltaUsuario * cAU = cAltaUsuario::getInstance();
    cCrearRecurso * cCR = cCrearRecurso::getInstance();

    this->usuario = cAU->buscarUsuario(nickName);
    this->carpeta = (Carpeta*) cCR->buscarRecurso(rutaRecurso);
    this->carpeta->setfModif();

    this->fColabora = fColab;
}

bool UsuarioCarpeta::esColaborador(string nickName) {
    return ( this->usuario->getNickname() == nickName );
}

bool UsuarioCarpeta::esUsuarioCreador(string nickName ) {
    return ( this->carpeta->esCreador(nickName) );
}

DataRecurso UsuarioCarpeta::getDataCarpeta() {
    return  this->carpeta->getDataRecurso();
}

DataUsuario UsuarioCarpeta::getDataUsuario() {
    return this->usuario->getDataUsuario();
}

//helen 11-6
vector<DataRecurso> UsuarioCarpeta::obtenerDataCarpetasHijas() {
	return this->carpeta->obtenerDataCarpetasHijas();
}
//helen 11-6

