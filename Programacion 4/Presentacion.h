//Presentacion.h
#ifndef _PRESENTACION_H_
#define _PRESENTACION_H_

#include "Utils.h"
#include "DateTime.h"
#include "DataUsuario.h"
#include "DataComentario.h"
#include "DataRecurso.h"
#include "DataAccion.h"
#include "DataNotificacion.h"
#include <string>
#include <vector>
#include <map>

using namespace std;

class Presentacion {
    public:
        static void AltaUsuario();
        static void CrearRecurso();
        static void IngresarComentario();
        static void AgregarSuscriptor();
        static void VerInfoUsuario();
        static void VerInfoCarpeta();
        static void VerInfoArchivo();
        static void ModificarArchivo();
        static void AgregarColaborador();
	static void IniciarColaboradorRaiz();
        static void VerNotificaciones();
        static void LiberarMemoria();
    private:

        static void auxAltaUsuario();
        static void pedirNName(string&);
        static void pideOtro(char&);
        static void pedirSexo(sexoEnum&);
        static void pedirFecha(DateTime&);
        static void auxCrearRecurso();
        static void pedirTipo(tipoRecurso&);
        static void pedirNombreRec(string&,tipoRecurso);
        static void pedirDescripcion(string&);
        static void pedirUsuario(string&,vector<DataUsuario>);
        static void pedirRuta(string&);
        static void auxIngresarComentario();
        static void preguntaResponde(char &);
        static void preguntaCualResponde(int &);
        static void mostrarComentarios(vector<DataComentario>);
        static void pedirComentario(string&);
        static void listarDataRecurso(vector<DataRecurso>);
        static void listarDataRecurso(map<string, DataRecurso>);
        static void listarDataAccion(vector<DataAccion>);
        static void listarNotificaciones(vector<DataNotificacion>);
        static void mostrarDataRecurso(DataRecurso);
        static void mostrarDataUsuario(DataUsuario);


};

#endif // _pAltaUsuario_H_
