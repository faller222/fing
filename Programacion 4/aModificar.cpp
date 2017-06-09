#include "aModificar.h"
#include "Accion.h"
#include "Utils.h"
#include "Usuario.h"
#include "Archivo.h"
#include "DateTime.h"

aModificar::aModificar(Usuario* user, Archivo* arch) {
    refUsuario=user;
    refArchivo=arch;
    fAccion=DateTime();
}


tipoAccion aModificar::getTipo(){
    return MODIFICAR;
}
