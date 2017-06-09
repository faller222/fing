/*
 * File:   cAgregarColaborador.h
 * Author: Helen
 *
 * Created on 8 de junio de 2013, 03:47 PM
 */

#ifndef CAGREGARCOLABORADOR_H
#define	CAGREGARCOLABORADOR_H

#include <string>
#include <vector>

#include "Utils.h"
#include "cAltaUsuario.h"
#include "cCrearRecurso.h"
#include "iAgregarColaborador.h"
#include "UsuarioCarpeta.h"

#include "ExRutaInvalida.h"
#include "ExYaTieneDerColaborador.h"
#include "ExNoExisteUsuario.h"
#include "ExMemoriaNoSeteada.h"


#include "DataUsuario.h"
#include "DataRecurso.h"

using namespace std;

class cAgregarColaborador : public iAgregarColaborador { //Singleton
private:
    vector<UsuarioCarpeta*> userCarps; // por fColabora -> es dueño de la coleccion de UsuarioCarpetas
    string rutaRecIngresado; //lo inicializo asi se si fue seteado o no

	map<string,DataRecurso> carpColab;
    //singleton
    static cAgregarColaborador* instanceAC; //atributo privado y de clase para referenciar la instancia unica

    cAgregarColaborador();//constructor privado
    ~cAgregarColaborador();

public:
    //singleton
    static cAgregarColaborador* getInstance();
    void destruir();

    //interface
    vector<DataUsuario> obtenerUsuariosRegistrados();
    map<string,DataRecurso> obtenerCarpetasColabora(string) throw (ExNoExisteUsuario); //Devuelve las carpetas donde tiene DERECHO a colaborar
    void ingresarRutaRecurso(string) throw (ExRutaInvalida);
    void altaColaborador(string, bool) throw (ExNoExisteUsuario, ExMemoriaNoSeteada, ExYaTieneDerColaborador); //bool es para Iniciar Colaborador Raiz

};

#endif	/* CAGREGARCOLABORADOR_H */

