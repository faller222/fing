#ifndef _iVerInfoArchivo_H_
#define _iVerInfoArchivo_H_

#include "Utils.h"
#include "ExRutaInvalida.h"
#include "DataRecurso.h"
#include "DataAccion.h"
#include "DataComentario.h"

#include <string>
#include <vector>

using namespace std;

class  iVerInfoArchivo {
    public:
        virtual vector<DataRecurso> obtenerArchivos()=0;
        virtual void seleccionarArchivo(string)throw(ExRutaInvalida)=0;
        virtual DataRecurso obtenerArchivo()=0;
        virtual vector<DataAccion> obtenerAcciones()=0;
        virtual vector<DataComentario> obtenerComentarios()=0;
        virtual void destruir()=0;
};

#endif // _iVerInfoArchivo_H_
