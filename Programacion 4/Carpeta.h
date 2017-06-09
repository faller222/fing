#ifndef CARPETA_H
#define	CARPETA_H

#include "Recurso.h"
#include "Usuario.h"
#include "DataRecurso.h"

#include "Utils.h"
#include <map>
#include <string>


using namespace std;

class Carpeta : public Recurso {
    public:
        Carpeta();
        ~Carpeta();
        Carpeta(const string, const string, const string,  Usuario*);
        tipoRecurso getTipo();
        DataRecurso getDataRecurso();
        void agregarHijo(Recurso*);
        vector<DataRecurso> obtenerDataHijosDirectos();
        vector<DataRecurso> obtenerDataCarpetasHijas();
        vector<Recurso*> obtenerHijosDirectos();
	vector<Recurso*> obtenerTotalHijos(); //me devuelve todos los hijos de la carpeta Archivos y Carpetas
	vector<Recurso*> obtenerTotalCarpetasHijas();
        void obtenerHijosRecursivo( vector<Recurso*>& );

    private:
    	map<string, Recurso*> hijos;
        vector<Recurso*> totalHijos;
};

#endif	/* CARPETA_H */
