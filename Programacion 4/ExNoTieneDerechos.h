#ifndef _ExNoTieneDerechos_H_
#define _ExNoTieneDerechos_H_

#include <iostream>
#include <exception>

using namespace std;

class ExNoTieneDerechos : public exception {
public:
	//overriding del metodo what()
    const char* what() const throw() {
        return "Error: El usuario debe ser creador o colaborador de la carpeta, para poder agregar recursos en la misma.";
    }
};

#endif // _ExNoTieneDerechos_H_