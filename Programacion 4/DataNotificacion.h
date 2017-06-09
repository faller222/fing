#ifndef DATANOTI_H
#define	DATANOTI_H

#include "DateTime.h"
#include "Utils.h"
#include <string>

using namespace std;

class DataNotificacion {
public:
    DataNotificacion(DateTime, tipoNotificacion,string, bool);
    tipoNotificacion getTipo();
    DateTime getFNot();
    string getArchivo();
    bool esLeida();
    ~DataNotificacion();
private:
    DateTime fAccion;
    tipoNotificacion tipo;
    string nArch;
    bool leida;
};

#endif	/* DATANOTI_H */

