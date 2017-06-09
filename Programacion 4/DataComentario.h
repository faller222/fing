#ifndef _DATACOMENTARIO_H_
#define _DATACOMENTARIO_H_

#include "Utils.h"
#include <string>

using namespace std;

class DataComentario {
private:
	string contenido;
	int Id;
        string archivo;
        string usuario;

public:
	DataComentario();
	DataComentario(const int, const string, const string, const string);
	string getContenido();
        string getUbicacion();
        string getCreador();
	int getId();
};

#endif // _DATACOMENTARIO_H_
