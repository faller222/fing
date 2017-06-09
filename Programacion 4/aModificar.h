#ifndef AMODIFICAR_H
#define	AMODIFICAR_H

#include "Accion.h"
#include "Utils.h"

class aModificar: public Accion {
public:
    aModificar(Usuario*, Archivo*);
    tipoAccion getTipo();
private:

};

#endif	/* AMODIFICAR_H */

