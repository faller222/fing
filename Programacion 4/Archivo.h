#ifndef ARCHIVO_H
#define	ARCHIVO_H

#include "Recurso.h"
//#include "Usuario.h"
#include "DataRecurso.h"
#include "Utils.h"
#include <string>
#include <set>
//#include "iObserver.h"

class iObserver;
class Usuario;
class Archivo : public Recurso {
public:
        Archivo(const string,const string, const string,  Usuario*);
        ~Archivo();
        tipoRecurso getTipo();
        DataRecurso getDataRecurso();
        void setDescripcion(const string);
        void obtenerHijosRecursivo( vector<Recurso*>& );
        vector<Recurso*> obtenerHijosDirectos();
        //De observer
        void agregarObserver(iObserver*);
        void quitarObserver(iObserver*);
        void modificar(tipoNotificacion);
        int getContador();
private:
        set<iObserver*> observers;
        int contComent;
};

#endif	/* ARCHIVO_H */

