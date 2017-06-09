#include "aCrear.h"
#include "Accion.h"
#include "DataAccion.h"
#include "Utils.h"
#include "Usuario.h"
#include "Archivo.h"
#include "DateTime.h"

aCrear::aCrear(Usuario* user, Archivo* arch) {
    refUsuario=user;
    refArchivo=arch;
    fAccion=DateTime();
}

tipoAccion aCrear::getTipo(){
    return CREACION;
}
