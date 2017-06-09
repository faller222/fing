#ifndef _ISUSCRIPCION_H
#define	_ISUSCRIPCION_H

#include <string>
#include <vector>
#include "DataRecurso.h"
#include "DataUsuario.h"
#include "ExRutaInvalida.h"
#include "DataNotificacion.h"

using namespace std;

class iSuscripcion{
public:
    virtual void suscribirUsuarioArchivo()=0;
    virtual void seleccionarUsuario(const string)=0;
    virtual void seleccionarArchivo(const string)throw (ExRutaInvalida)=0;
    virtual vector<DataRecurso> obtenerArchivos()=0;
    virtual vector<DataUsuario> obtenerUsuarios()=0;
    virtual vector<DataNotificacion> obtenerNotificaciones()=0;
    virtual void destruir()=0;
};


#endif	/* ISUSCRIPCION_H */

