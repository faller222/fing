
#ifndef ACREAR_H
#define	ACREAR_H

#include "Accion.h"
#include "Utils.h"
#include "DataAccion.h"

class aCrear: public Accion {
public:
    aCrear(Usuario* , Archivo* );
    tipoAccion getTipo();
private:

};

#endif	/* ACREAR_H */

