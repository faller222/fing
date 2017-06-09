#ifndef _DATARECURSO_H_
#define _DATARECURSO_H_

#include "Utils.h"
#include <iostream>
#include <string>
#include "DateTime.h"

using namespace std;

class DataRecurso {
private:
	string nombre;
	string descripcion;
        string rutaAbs;
        tipoRecurso tipo;
        DateTime fModificacion;
        DateTime fCreacion;

public:
	DataRecurso();
	DataRecurso(const string, const string,const string, tipoRecurso, DateTime, DateTime);
        ~DataRecurso();
	string getNombre();
	string getDescripcion();
	DateTime getfModificacion();
	DateTime getfCreacion();
        string getRuta();
        tipoRecurso getTipo();
};

#endif // _DATARECURSO_H_
