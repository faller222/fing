//cAltaUsuario.cpp

#include "cAltaUsuario.h"
#include "Usuario.h"
#include "Archivo.h"
#include "Carpeta.h"

#include <string>
#include <map>
#include <vector>

#include "DataRecurso.h"
#include "DataUsuario.h"

#include "ExFormatoIncorrectoNickname.h"
#include "ExNoHayUsuariosRegistrados.h"
#include "ExFechaInvalida.h"
#include "ExNoExisteUsuario.h"



using namespace std;

cAltaUsuario::cAltaUsuario() {}

cAltaUsuario* cAltaUsuario::instancia = NULL;

cAltaUsuario* cAltaUsuario::getInstance(){
        if (!instancia)
            instancia = new cAltaUsuario();
    return instancia;
}

//destructor privado
cAltaUsuario::~cAltaUsuario() {
	map<string, Usuario*>::iterator it;
	for (it=this->usuarios.begin(); it!=this->usuarios.end(); ++it ) {
		delete (it->second); //elimino el Usuario
	}

	this->usuarios.clear();
}

void cAltaUsuario::destruir() {
    if (instancia!=NULL) {
        delete instancia;
	instancia = NULL;
    }
}

bool cAltaUsuario::verificarNickName(string nName){

    int largo = nName.size();
    bool resultado=true;
    int aux;
    int i=0;

    while ((i<largo)&&resultado)
    {
        aux=nName[i];
        resultado = (aux>='a')&&(aux<='z'); //debe estar entre la a y la z
        resultado = resultado ||((aux>='A')&&(aux<='Z')); // si no entre las letras mayusculas
        resultado = resultado ||((aux>='0')&&(aux<='9'));//o entre los numeros
        i++;
    }
    return resultado;
}

bool cAltaUsuario::ingresarNickname(string nNameUsuario ) throw ( ExFormatoIncorrectoNickname ) {
    if (!verificarNickName(nNameUsuario))
        throw ExFormatoIncorrectoNickname();
    bool disponible = ( 0==this->usuarios.count(nNameUsuario) );
    if (disponible) {
       this->nNameUser= nNameUsuario;
    }
	return disponible;
}


void cAltaUsuario::altaUsuario( sexoEnum s , const DateTime& fechaN ) throw (ExFechaInvalida) {
    DateTime hoy;
    if (hoy-fechaN<0)
        throw ExFechaInvalida();
    Usuario * user = new Usuario(nNameUser, s, fechaN );
    this->usuarios[nNameUser] = user;
}

vector<DataUsuario> cAltaUsuario::obtenerUsuariosRegistrados() throw (ExNoHayUsuariosRegistrados) {

    vector<DataUsuario> listaDataUsuarios;
    if (usuarios.empty())
         throw ExNoHayUsuariosRegistrados();

    map<string, Usuario*>::iterator it;
    DataUsuario dU;

    for ( it=this->usuarios.begin(); it!= this->usuarios.end(); ++it) { //recorro el hash de usuarios
        dU = it->second->getDataUsuario();
        listaDataUsuarios.push_back( DataUsuario(dU) );
    }
    return listaDataUsuarios;
}

DataUsuario cAltaUsuario::obtenerInfoUsuario(const string nNameUsuario)  throw (ExNoExisteUsuario){
    return buscarUsuario(nNameUsuario)->getDataUsuario();
}

Usuario* cAltaUsuario::buscarUsuario(const string nNameUsuario)  throw (ExNoExisteUsuario){

    map<string, Usuario*>::iterator it;

    if (this->usuarios.count(nNameUsuario)==0)
        throw ExNoExisteUsuario();
    it = this->usuarios.find(nNameUsuario);
    return it->second;
}

