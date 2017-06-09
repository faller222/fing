#ifndef _FACTORY_H_
#define _FACTORY_H_

#include "iAltaUsuario.h"
#include "iCrearRecurso.h"
#include "iIngresarComentario.h"
#include "iVerInfoArchivo.h"
#include "iVerInfoCarpeta.h"
#include "iVerInfoUsuario.h"
#include "iModificarArchivo.h"
#include "iAgregarColaborador.h"
#include "iSuscripcion.h"


class Factory
{
    public:
    static iAltaUsuario* getAltaUsuario();
    static iCrearRecurso* getCrearRecurso();
    static iIngresarComentario* getIngresarComentario();
    static iSuscripcion* getAgregarSuscriptor();
    static iVerInfoArchivo* getInfoArchivo();
    static iModificarArchivo* getModificarArchivo();
    static iVerInfoCarpeta* getInfoCarpeta();
    static iVerInfoUsuario* getInfoUsuario();
    static iAgregarColaborador* getIAgregarColaborador();


};

#endif // _FACTORY_H_
