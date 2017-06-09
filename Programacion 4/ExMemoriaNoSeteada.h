#ifndef _ExMemoriaNoSeteada_H_
#define _ExMemoriaNoSeteada_H_

#include <iostream>
#include <exception>

using namespace std;

class ExMemoriaNoSeteada : public exception {
public:
	//overriding del metodo what()
    const char* what() const throw() {
        return "Error: Memoria no seteada.";
    }
};

#endif // _ExMemoriaNoSeteada_H_
