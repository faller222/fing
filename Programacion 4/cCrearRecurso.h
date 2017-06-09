#ifndef _cCrearRecurso_H_
#define _cCrearRecurso_H_

#include "Utils.h"
#include "iCrearRecurso.h"

#include "DataRecurso.h"
#include "DataUsuario.h"
#include "DateTime.h"


#include "Usuario.h"
#include "Recurso.h"
#include "Archivo.h"
#include "Carpeta.h"

#include <string>
#include <map>
#include <vector>

#include "ExNombreInvalidoRecurso.h"
#include "ExNoExisteUsuario.h"
#include "ExRutaInvalida.h"
#include "ExYaExisteRecurso.h"
#include "ExNoHayUsuariosRegistrados.h"

using namespace std;


class cCrearRecurso : public iCrearRecurso {
private:
    cCrearRecurso();
    ~cCrearRecurso();
    //Memoria del Sistema
    map<string, Recurso*> recursos;
    string rAbs;
    string Nombre;
    string Desc;
    tipoRecurso Tipo;
    bool setMemoria[3];

    static cCrearRecurso* instancia;

    Carpeta* refPadre;
    Usuario* refCreador;
    //Auxiliar
    static void esValidoNombre(const string) throw (ExNombreInvalidoRecurso);
public:

    static cCrearRecurso* getInstance();
    void destruir();
    
    //Implementa la interface
    void ingresarDatosRecurso(const string,const string, tipoRecurso) throw (ExNombreInvalidoRecurso);
    vector<DataUsuario> obtenerUsuariosRegistrados() throw (ExNoHayUsuariosRegistrados);
    void seleccionarUsuario(const string)throw (ExNoExisteUsuario);
    void ingresarRutaRecurso(const string) throw (ExRutaInvalida);
    void altaRecurso()throw (ExRutaInvalida, ExNoTieneDerechos);
// Otras Funciones
    vector<DataRecurso> obtenerRecursosCreados(const string);
    Recurso* buscarRecurso(const string)throw (ExRutaInvalida);
    DataRecurso obtenerInfoRecurso(const string) throw (ExRutaInvalida);
    vector<DataRecurso> obtenerRecursosTipo(tipoRecurso);
    vector<DataRecurso> obtenerRecursosCreadosTipoUsuario( tipoRecurso, string );
	vector<DataRecurso> obtenerHijosCarpetaTipo(string, tipoRecurso); //retorna un set de dataRecurso con los hijos de tipo tipoRecurso de la carpeta pasada por parametro
};

#endif // _cCrearRecurso_H_
