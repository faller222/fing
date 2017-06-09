/* 
 * File:   ExYaEsColaborador.h
 * Author: Helen
 *
 * Created on 8 de junio de 2013, 08:19 PM
 */

#ifndef EXYATieneDerCOLABORADOR_H
#define	EXYATieneDerCOLABORADOR_H

#include <iostream>
#include <exception>

using namespace std;

class ExYaTieneDerColaborador : public exception {
public:
	//overriding del metodo what()
    const char* what() const throw() {
        return "Error: El usuario ya es colaborador de la carpeta.";
    }
};

#endif	/* EXYATieneDerCOLABORADOR_H */

