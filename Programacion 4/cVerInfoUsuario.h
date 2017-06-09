#ifndef _cVerInfoUsuario_H_
#define _cVerInfoUsuario_H_

#include "Utils.h"

#include "iVerInfoUsuario.h"
#include "DataAccion.h"
#include "DataRecurso.h"
#include "DataUsuario.h"

#include "Accion.h"
#include "Usuario.h"
#include "Recurso.h"
#include "Archivo.h"
#include "Carpeta.h"

#include <string>
#include <map>
#include <vector>

using namespace std;

class cVerInfoUsuario: public iVerInfoUsuario {
    private:
        cVerInfoUsuario();
        ~cVerInfoUsuario();
        string nName;
        vector<Accion*> acciones;
        static cVerInfoUsuario* instancia;

    public:
        static cVerInfoUsuario* getInstance();
        void destruir();

        void CrearAccion(tipoAccion,Archivo*,Usuario*);
        vector<DataUsuario> obtenerUsuariosRegistrados();
        DataUsuario obtenerInfoUsuario(const string)  throw (ExNoExisteUsuario);
        vector<DataRecurso> obtenerRecursosCreados();
        map<string, DataRecurso> obtenerCarpetasColabora();
        vector<DataAccion> obtenerAcciones();
        vector<DataAccion> obtenerAcciones(Archivo* arch);
};

#endif
