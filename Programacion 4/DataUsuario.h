#ifndef _DATAUSUARIO_H_
#define _DATAUSUARIO_H_

#include "Utils.h"
#include "DateTime.h"
#include <string>


class DataUsuario {
private:
	string nickname;
	sexoEnum sexo;
	DateTime fNac;
	int edad;

public:
	//Constructor
	DataUsuario();
	DataUsuario(const string, sexoEnum, const DateTime& );

	//gets
	string getNickname();
	sexoEnum getSexo();
	DateTime getFNac();
	int getEdad();
};

#endif // _DATAUSUARIO_H_
