#ifndef _USUARIO_H_
#define _USUARIO_H_

#include "Utils.h"
#include "DateTime.h"
#include "DataUsuario.h"
#include "iObserver.h"
#include <string>
#include <vector>

//#include "Notificacion.h"

class Notificacion;

class Usuario: public iObserver {
private:
	string nickname;
	sexoEnum sexo;
	DateTime fNac;
        vector<Notificacion*> notificaciones;
public:
	//Constructores
	Usuario(const string, sexoEnum, const DateTime& );

	//gets
	string getNickname();
	sexoEnum getSexo();
	DateTime getFNac();
	int getEdad();

	DataUsuario getDataUsuario();
	bool sosVos(const string);

        //De observer
        void notificar(Notificacion*);
        vector<DataNotificacion> obtenerDataNotificaciones();
        //Destructor
        ~Usuario();
};


#endif // _USUARIO_H_
