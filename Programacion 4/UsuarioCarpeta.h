/*
 * File:   UsuarioCarpeta.h
 * Author: Helen
 *
 * Created on 8 de junio de 2013, 04:38 PM
 */

#ifndef USUARIOCARPETA_H
#define	USUARIOCARPETA_H

#include "DateTime.h"
#include "DataRecurso.h"

#include "Carpeta.h"
#include "Usuario.h"

#include <vector>

class UsuarioCarpeta {
private:
    DateTime fColabora; //fecha en la que el Usuario usuario, se agrega como colaborador a la Carpeta carpeta
    Carpeta * carpeta;
    Usuario * usuario;

public:
    //constructores
    UsuarioCarpeta();
    UsuarioCarpeta(string, string, const DateTime&);

    bool esColaborador(string);
    bool esUsuarioCreador(string);
    DataRecurso getDataCarpeta();
    DataUsuario getDataUsuario();
    
    //helen 11-6
    vector<DataRecurso> obtenerDataCarpetasHijas();
    //helen 11-6

};

#endif	/* USUARIOCARPETA_H */

