#ifndef _CSUSCRIPCION_H
#define	_CSUSCRIPCION_H

#include <string>
#include "iSuscripcion.h"
#include "Archivo.h"
#include "Usuario.h"
#include "ExRutaInvalida.h"
#include "DataNotificacion.h"

using namespace std;

class cSuscripcion: public iSuscripcion {
private:
    Usuario* refUser;
    Archivo* refArch;
    cSuscripcion();
    ~cSuscripcion();
    static cSuscripcion* instancia;

public:
    static cSuscripcion* getInstance();
    void destruir();

    // Implementa la interface
    void suscribirUsuarioArchivo();
    void seleccionarUsuario(const string);
    void seleccionarArchivo(const string) throw(ExRutaInvalida);
    vector<DataRecurso> obtenerArchivos();
    vector<DataUsuario> obtenerUsuarios();
    vector<DataNotificacion> obtenerNotificaciones();
};

#endif	/* CSUSCRIPCION_H */
