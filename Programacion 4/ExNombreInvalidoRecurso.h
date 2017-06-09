#ifndef _ExNombreInvalidoRecurso_H_
#define _ExNombreInvalidoRecurso_H_

#include <iostream>
#include <exception>

using namespace std;

class ExNombreInvalidoRecurso : public exception {
public:
	//overriding del metodo what()
    const char* what() const throw() {
        return "Error: el nombre del recurso debe tener de 1 a 8 caracteres de longitud, no puede contener el caracter '/' y no puede ser 'raiz' ";
    }
};

#endif // _ExNombreInvalidoRecurso_H_
