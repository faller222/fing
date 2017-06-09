#ifndef _ExRutaInvalida_H_
#define _ExRutaInvalida_H_

#include <iostream>
#include <exception>

using namespace std;

class ExRutaInvalida : public exception {
public:
	//overriding del metodo what()
    const char* what() const throw() {
        return "Error: Ruta invalida, no existe Recurso o no es del tipo adecuado";
    }
};

#endif // _ExRutaInvalida_H_
