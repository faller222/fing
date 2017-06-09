#ifndef _ExNoHayUsuariosRegistrados_H_
#define _ExNoHayUsuariosRegistrados_H_

#include <iostream>
#include <exception>

using namespace std;

class ExNoHayUsuariosRegistrados : public exception {
public:
	//overriding del metodo what()
    const char* what() const throw() {
        return "Error: No hay usuarios en el sistema.";
    }
};

#endif // _ExNoHayUsuariosRegistrados_H_
