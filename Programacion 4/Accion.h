#ifndef ACCION_H
#define	ACCION_H


#include "Archivo.h"
#include "Usuario.h"
#include "DateTime.h"
#include "DataAccion.h"

#include "Utils.h"


class Accion {
    protected:
        DateTime fAccion;
        Usuario* refUsuario;
        Archivo* refArchivo;
    public:
        Accion();
        Usuario* getCreador();
        Archivo* getArchivo();
        bool esAutor(string);
        DataAccion getDataAccion();
        virtual tipoAccion getTipo()=0;
};


#endif	/* ACCION_H */

