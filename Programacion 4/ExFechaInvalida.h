#ifndef _ExFechaInvalida_H_
#define _ExFechaInvalida_H_

#include <iostream>
#include <exception>

using namespace std;

class ExFechaInvalida : public exception {
public:
	//overriding del metodo what()
    const char* what() const throw() {
        return "Error: Fecha posterior al dia de hoy";
    }
};

#endif // _ExFechaInvalida_H_
