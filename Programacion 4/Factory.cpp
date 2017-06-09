#include "cAltaUsuario.h"
#include "iAltaUsuario.h"
#include "cCrearRecurso.h"
#include "iCrearRecurso.h"
#include "cIngresarComentario.h"
#include "iIngresarComentario.h"
#include "cVerInfoArchivo.h"
#include "iVerInfoArchivo.h"
#include "cVerInfoCarpeta.h"
#include "iVerInfoCarpeta.h"
#include "cVerInfoUsuario.h"
#include "cAgregarColaborador.h"
#include "iVerInfoUsuario.h"
#include "cModificarArchivo.h"
#include "iModificarArchivo.h"
#include "cSuscripcion.h"
#include "iSuscripcion.h"
#include "Factory.h"


iAltaUsuario* Factory::getAltaUsuario(){
        return cAltaUsuario::getInstance();
    }

iCrearRecurso* Factory::getCrearRecurso(){
        return cCrearRecurso::getInstance();
    }

iIngresarComentario* Factory::getIngresarComentario(){
        return cIngresarComentario::getInstance();
}

iSuscripcion* Factory::getAgregarSuscriptor(){
    return cSuscripcion::getInstance();
}

iModificarArchivo* Factory::getModificarArchivo(){
    return cModificarArchivo::getInstance();
}

iVerInfoCarpeta* Factory::getInfoCarpeta(){
    return cVerInfoCarpeta::getInstance();
}

iVerInfoUsuario* Factory::getInfoUsuario(){
    return cVerInfoUsuario::getInstance();
}

iVerInfoArchivo* Factory::getInfoArchivo(){
    return cVerInfoArchivo::getInstance();
}

iAgregarColaborador* Factory::getIAgregarColaborador() {
    return cAgregarColaborador::getInstance();
}
