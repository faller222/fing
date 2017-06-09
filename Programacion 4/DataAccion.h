#ifndef DATAACCION_H
#define	DATAACCION_H

#include "DateTime.h"
#include "Utils.h"
#include <string>

using namespace std;

class DataAccion {
public:
    DataAccion();
    DataAccion(DateTime, tipoAccion,string);
    tipoAccion getTipo();
    DateTime getFAccion();
    string getCreador();
    virtual ~DataAccion();
private:
    DateTime fAccion;
    tipoAccion tipo;
    string nName;
};

#endif	/* DATAACCION_H */

