#ifndef _cIngresarComentario_H_
#define _cIngresarComentario_H_

#include "Utils.h"

#include "iIngresarComentario.h"

#include "Usuario.h"
#include "DataUsuario.h"
#include "Archivo.h"
#include "Comentario.h"
#include "DataComentario.h"

#include <string.h>
#include <vector>
#include <string>

#include "ExRutaInvalida.h"
#include "ExNoExisteUsuario.h"
#include "ExNoExisteComentario.h"
#include "ExNoHayUsuariosRegistrados.h"

using namespace std;

class cIngresarComentario : public iIngresarComentario {
private:
    cIngresarComentario();
    ~cIngresarComentario();
    Usuario* memUser;
    Archivo* memArch;
    map<int, Comentario*> comentarios;

    static cIngresarComentario* instancia;

public:
         static cIngresarComentario* getInstance();
         void destruir();

         vector<DataComentario> obtenerComentariosArchivo(const string)throw (ExRutaInvalida);
         vector<DataUsuario> obtenerUsuariosRegistrados() throw (ExNoHayUsuariosRegistrados);
         void seleccionarUsuario(const string) throw (ExNoExisteUsuario);
         void responderComentario(const string,const int) throw (ExNoExisteComentario);
         void agregarComentarioArchivo(const string);
         
         Comentario* buscarComentario(const int) throw (ExNoExisteComentario);
};
#endif // _cIngresarComentario_H_
