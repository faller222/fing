#ifndef CMODIFICARARCHIVO_H
#define	CMODIFICARARCHIVO_H

#include "iModificarArchivo.h"
#include "Archivo.h"
#include "Usuario.h"
#include "DataUsuario.h"
#include "DataRecurso.h"
#include "ExNoExisteUsuario.h"
#include "ExRutaInvalida.h"
#include "Utils.h"

#include <vector>
#include <string>

using namespace std;

class cModificarArchivo: public iModificarArchivo {
    private:
        cModificarArchivo();
        ~cModificarArchivo();
        Archivo* refArchivo;
        Usuario* refUsuario;
        static cModificarArchivo* instancia;
        
    public:
        static cModificarArchivo* getInstance();
        void destruir();

        vector<DataUsuario> obtenerUsuariosRegistrados();
        void seleccionarUsuario(const string)  throw ( ExNoExisteUsuario );
        vector<DataRecurso> obtenerRecursosTipo();
        void seleccionarArchivo(const string)throw(ExRutaInvalida);
        void ingresarDescripcion(const string);
};


#endif	/* CMODIFICARARCHIVO_H */

