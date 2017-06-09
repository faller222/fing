#ifndef _NOTIFICACION_H_
#define	_NOTIFICACION_H_

#include "DateTime.h"
#include "Archivo.h"
#include "Utils.h"
#include "DataNotificacion.h"

class Notificacion {
private:
        tipoNotificacion tipo;
        DateTime fecha;
        bool leida;
        Archivo* arch;
public:
        //constructor
        Notificacion(Archivo*, tipoNotificacion);
        //gets
        tipoNotificacion getTipo();
        DateTime getFecha();
        bool getLeida();
        DataNotificacion getDataNotificacion();
        //sets
        void setLeida();
};

#endif	/* NOTIFICACION_H */
 
