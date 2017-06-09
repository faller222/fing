#ifndef _cVerInfoCarpeta_H_
#define _cVerInfoCarpeta_H_

#include "Utils.h"
#include "iVerInfoCarpeta.h"
#include "ExRutaInvalida.h"
#include "Carpeta.h"

#include <string>
#include <vector>

class cVerInfoCarpeta: public iVerInfoCarpeta {
    private:
        cVerInfoCarpeta();
        ~cVerInfoCarpeta();
        Carpeta* refCarp;
        static cVerInfoCarpeta* instancia;
    public:
        static cVerInfoCarpeta* getInstance();
        void destruir();
        vector<DataRecurso> obtenerCarpetas();
        DataRecurso seleccionarCarpeta(string)throw(ExRutaInvalida);
        DataUsuario obtenerCreador();
        vector<DataRecurso> obtenerRecursosContenidos();
};
#endif //_cVerInfoCarpeta_H_
