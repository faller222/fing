#ifndef CARGAR_H
#define	CARGAR_H

#include "iAltaUsuario.h"
#include "iCrearRecurso.h"
#include "iIngresarComentario.h"
#include "iVerInfoArchivo.h"
#include "iVerInfoCarpeta.h"
#include "iVerInfoUsuario.h"
#include "iModificarArchivo.h"
#include "iSuscripcion.h"
#include "Factory.h"
#include "DateTime.h"
#include "Utils.h"



class Cargar {
public:
    static void cargar();
};
#endif
