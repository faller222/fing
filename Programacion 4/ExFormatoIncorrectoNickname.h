#ifndef _ExFormatoIncorrectoNickname_H_
#define _ExFormatoIncorrectoNickname_H_

#include <iostream>
#include <exception>

using namespace std;

class ExFormatoIncorrectoNickname : public exception {
public:
	//overriding del metodo what()
    const char* what() const throw() {
        return "Error: el nombre del usuario debe unicamente caracteres alfanumericos (A-Z a-z 0-9)";
    }
};

#endif // _ExFormatoIncorrectoNickname_H_
