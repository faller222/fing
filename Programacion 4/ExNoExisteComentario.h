#ifndef _ExNoExisteComentario_H_
#define _ExNoExisteComentario_H_

#include <iostream>
#include <exception>

using namespace std;

class ExNoExisteComentario : public exception {
public:
	//overriding del metodo what()
    const char* what() const throw() {
        return "Error: No Existe Comentario.";
    }
};

#endif // _ExNoExisteComentario_H_
