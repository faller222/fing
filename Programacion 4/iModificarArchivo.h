#ifndef IMODIFICARARCHIVO_H
#define	IMODIFICARARCHIVO_H

#include <vector>
#include <string>
#include "DataUsuario.h"
#include "DataRecurso.h"
#include "ExNoExisteUsuario.h"
#include "ExRutaInvalida.h"

using namespace std;

class iModificarArchivo {
public:
        virtual vector<DataUsuario> obtenerUsuariosRegistrados()=0;
        virtual void seleccionarUsuario(const string) throw ( ExNoExisteUsuario )=0;
        virtual vector<DataRecurso> obtenerRecursosTipo()=0;
        virtual void seleccionarArchivo(const string)throw(ExRutaInvalida)=0;
        virtual void ingresarDescripcion(const string)=0;
        virtual void destruir()=0;
};


#endif	/* IMODIFICARARCHIVO_H */

