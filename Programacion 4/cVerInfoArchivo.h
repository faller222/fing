#ifndef _cVerInfoArchivo_H_
#define _cVerInfoArchivo_H_

#include "Utils.h"
#include "ExRutaInvalida.h"
#include "Archivo.h"
#include "DataRecurso.h"
#include "iVerInfoArchivo.h"

#include <string>
#include <vector>

using namespace std;

class cVerInfoArchivo: public iVerInfoArchivo {
    private:
       cVerInfoArchivo();
       ~cVerInfoArchivo();
       Archivo* refArch;
       static cVerInfoArchivo* instancia;

    public:
        static cVerInfoArchivo* getInstance();
        void destruir();
        vector<DataRecurso> obtenerArchivos();
        void seleccionarArchivo(string)throw (ExRutaInvalida);
        DataRecurso obtenerArchivo();
        vector<DataAccion> obtenerAcciones();
        vector<DataComentario> obtenerComentarios();  
};

#endif
//_cVerInfoArchivo_H_