#ifndef _ExNoExisteUsuario_H_
#define _ExNoExisteUsuario_H_

#include <iostream>
#include <exception>

using namespace std;

class ExNoExisteUsuario : public exception {
public:
	//overriding del metodo what()
    const char* what() const throw() {
        return "Error: No existe dicho usuario.";
    }
};

#endif // _ExNoExisteUsuario_H_
