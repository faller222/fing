#ifndef _iVerInfoCarpeta_H_
#define _iVerInfoCarpeta_H_

#include "Utils.h"
#include "DataRecurso.h"
#include "DataUsuario.h"
#include "ExRutaInvalida.h"



#include <string>
#include <vector>

using namespace std;


class iVerInfoCarpeta {
    public:
        virtual vector<DataRecurso> obtenerCarpetas()=0;
        virtual DataRecurso seleccionarCarpeta(string)throw(ExRutaInvalida)=0;
        virtual DataUsuario obtenerCreador()=0;
        virtual vector<DataRecurso> obtenerRecursosContenidos()=0;
        virtual void destruir()=0;
};

#endif // _iVerInfoCarpeta_H_
