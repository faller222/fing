#ifndef _cAltaUsuario_H_
#define _cAltaUsuario_H_

#include "Utils.h"
#include "Usuario.h"
#include "iAltaUsuario.h"

#include "DataUsuario.h"
#include "DateTime.h"

#include "ExFormatoIncorrectoNickname.h"
#include "ExNoHayUsuariosRegistrados.h"
#include "ExFechaInvalida.h"
#include "ExNoExisteUsuario.h"

#include <string>
#include <map>
#include <vector>


using namespace std;

class cAltaUsuario : public iAltaUsuario {
private:
    map<string, Usuario*> usuarios;
    static bool verificarNickName(string);
    string nNameUser;
    cAltaUsuario();
    ~cAltaUsuario();

    static cAltaUsuario* instancia;

public:
    //interface
    static cAltaUsuario* getInstance();
    void destruir();
    
    bool ingresarNickname(string) throw ( ExFormatoIncorrectoNickname );
    void altaUsuario( sexoEnum , const DateTime& ) throw (ExFechaInvalida);
    vector<DataUsuario> obtenerUsuariosRegistrados() throw (ExNoHayUsuariosRegistrados);
    //otras Funciones
    DataUsuario obtenerInfoUsuario(const string)  throw ( ExNoExisteUsuario );
    Usuario* buscarUsuario(const string)  throw ( ExNoExisteUsuario );

};

#endif // _cAltaUsuario_H_
