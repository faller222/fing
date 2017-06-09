#ifndef _iVerInfoUsuario_H_
#define _iVerInfoUsuario_H_

#include "Utils.h"
#include "DataAccion.h"
#include "DataUsuario.h"
#include "DataRecurso.h"
#include "ExNoExisteUsuario.h"
#include <string>
#include <map>
#include <vector>

using namespace std;


class iVerInfoUsuario {
    public:

        virtual vector<DataUsuario> obtenerUsuariosRegistrados()=0;
        virtual DataUsuario obtenerInfoUsuario(const string)  throw ( ExNoExisteUsuario )=0;
        virtual vector<DataRecurso> obtenerRecursosCreados()=0;
        virtual map<string, DataRecurso> obtenerCarpetasColabora()=0;
        virtual vector<DataAccion> obtenerAcciones()=0;
        virtual void destruir()=0;

};

#endif // _iVerInfoUsuario_H_
