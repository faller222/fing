/*
 * File:   iAgregarColaborador.h
 * Author: Helen
 *
 * Created on 8 de junio de 2013, 04:30 PM
 */
#ifndef IAGREGARCOLABORADOR_H
#define	IAGREGARCOLABORADOR_H

#include "Utils.h"
#include <string>
#include <vector>
#include <map>

#include "DataUsuario.h"
#include "DataRecurso.h"

#include "ExRutaInvalida.h"
#include "ExYaTieneDerColaborador.h"
#include "ExNoExisteUsuario.h"
#include "ExMemoriaNoSeteada.h"

class iAgregarColaborador { //Interafce
public:
    virtual vector<DataUsuario> obtenerUsuariosRegistrados() =0;
    virtual map<string,DataRecurso> obtenerCarpetasColabora(string) throw (ExNoExisteUsuario) =0;
    virtual void ingresarRutaRecurso(string) throw (ExRutaInvalida) =0; 
    virtual void altaColaborador(string , bool) throw (ExNoExisteUsuario, ExMemoriaNoSeteada, ExYaTieneDerColaborador ) =0;
    virtual void destruir()=0;
};

#endif	/* IAGREGARCOLABORADOR_H */
