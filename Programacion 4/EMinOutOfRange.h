#ifndef _EMinOutOfRange_H_
#define _EMinOutOfRange_H_

#include <iostream>
#include <exception>

using namespace std;

class EMinOutOfRange : public exception {
public:
	//overriding del metodo what()
    const char* what() const throw() {
        return "Error: minuto fuera de rango, debe ser entre 0-59...\n";
    }
};

#endif //_EMinOutOfRange_H_
