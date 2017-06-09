#ifndef _ExYaExisteRecurso_H_
#define _ExYaExisteRecurso_H_

#include <iostream>
#include <exception>

using namespace std;

class ExYaExisteRecurso : public exception {
public:
	//overriding del metodo what()
    const char* what() const throw() {
        return "Error: el recurso ya existe";
    }
};

#endif // _ExFormatoIncorrectoNickname_H_
