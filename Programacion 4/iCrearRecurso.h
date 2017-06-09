#ifndef _iCrearRecurso_H_
#define _iCrearRecurso_H_

#include "Utils.h"

#include "DataUsuario.h"
#include "DataRecurso.h"

#include <string.h>
#include <vector>
#include <string>

#include "ExNombreInvalidoRecurso.h"
#include "ExNoExisteUsuario.h"
#include "ExRutaInvalida.h"
#include "ExYaExisteRecurso.h"
#include "ExNoHayUsuariosRegistrados.h"
#include "ExNoTieneDerechos.h"
#include "ExMemoriaNoSeteada.h"

using namespace std;

class iCrearRecurso {
    public:
        virtual void ingresarDatosRecurso(const string,const string, tipoRecurso)throw (ExNombreInvalidoRecurso)=0;
        virtual vector<DataUsuario> obtenerUsuariosRegistrados()throw (ExNoHayUsuariosRegistrados)=0;
        virtual void seleccionarUsuario(const string)throw (ExNoExisteUsuario)=0;
        virtual void ingresarRutaRecurso(const string)throw (ExRutaInvalida)=0;
        virtual void altaRecurso()throw (ExRutaInvalida, ExNoTieneDerechos)=0;
        virtual void destruir()=0;
};

#endif // _iCrearRecurso_H_
