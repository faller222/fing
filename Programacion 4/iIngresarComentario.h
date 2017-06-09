#ifndef _iIngresarComentario_H_
#define _iIngresarComentario_H_

#include "Utils.h"

#include "DataUsuario.h"
#include "DataComentario.h"

#include <string.h>
#include <vector>
#include <string>

#include "ExRutaInvalida.h"
#include "ExNoExisteUsuario.h"
#include "ExNoExisteComentario.h"
#include "ExNoHayUsuariosRegistrados.h"

using namespace std;

class iIngresarComentario {
    public:
        virtual vector<DataComentario> obtenerComentariosArchivo(const string)throw (ExRutaInvalida)=0;
        virtual vector<DataUsuario> obtenerUsuariosRegistrados() throw (ExNoHayUsuariosRegistrados)=0;
        virtual void seleccionarUsuario(const string) throw (ExNoExisteUsuario)=0;
        virtual void responderComentario(const string,const int) throw (ExNoExisteComentario)=0;
        virtual void agregarComentarioArchivo(const string)=0;
        virtual void destruir()=0;
};

#endif // _iIngresarComentario_H_
