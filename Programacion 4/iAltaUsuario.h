#ifndef _iAltaUsuario_H_
#define _iAltaUsuario_H_

#include "Utils.h"
#include "DateTime.h"

#include "ExFormatoIncorrectoNickname.h"
#include "ExFechaInvalida.h"

#include <string>

using namespace std;


class iAltaUsuario {
    public:
        virtual bool ingresarNickname(string) throw (ExFormatoIncorrectoNickname)=0;
        virtual void altaUsuario(sexoEnum , const DateTime&) throw (ExFechaInvalida)=0;
        virtual void destruir()=0;
};

#endif // _iAltaUsuario_H_
