#ifndef _RECURSO_H_
#define _RECURSO_H_

#include "Utils.h"
//#include "Usuario.h"
#include "DataRecurso.h"
#include "DateTime.h"
#include <string>
#include <vector>

class Usuario;
class Recurso {
protected:
	string nombre;
	string descripcion;
	string rutaAbsoluta;
        DateTime fCrea;
        DateTime fModif;
        DateTime fAcceso;
        Usuario* refCreador;

public:
	Recurso();
        virtual ~Recurso();
        Usuario* getCreador();
        void setCreador(Usuario*);
        string getNombre();
        string getDescripcion();
        string getRutaAbsoluta();
        bool sosVos(string);
        bool esCreador(string);
        void setfModif();
        virtual DataRecurso getDataRecurso()=0;
        virtual tipoRecurso getTipo()=0;
        virtual void obtenerHijosRecursivo( vector<Recurso*>& ) = 0;
        virtual vector<Recurso*> obtenerHijosDirectos() = 0;
};

#endif // _RECURSO_H_
