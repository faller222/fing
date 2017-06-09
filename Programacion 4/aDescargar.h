#ifndef ADESCARGAR_H
#define	ADESCARGAR_H

#include "Accion.h"
#include "Utils.h"

class aDescargar: public Accion {
public:
    aDescargar(Usuario*, Archivo*);
    tipoAccion getTipo();
private:

};

#endif	/* ADESCARGAR_H */

