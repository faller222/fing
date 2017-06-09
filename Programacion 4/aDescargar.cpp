#include "aDescargar.h"
#include "Accion.h"
#include "Utils.h"
#include "Usuario.h"
#include "Archivo.h"
#include "DateTime.h"

aDescargar::aDescargar(Usuario* user, Archivo* arch) {
    refUsuario=user;
    refArchivo=arch;
    fAccion=DateTime();
}

tipoAccion aDescargar::getTipo(){
    return DESCARGAR;
}
