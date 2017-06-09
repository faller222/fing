#ifndef _EDayOutOfRange_H_
#define _EDayOutOfRange_H_

#include <iostream>
#include <exception>

using namespace std;

class EDayOutOfRange : public exception {
public:
	//overriding del metodo what()
    const char* what() const throw() {
        return "Error: dia fuera de rango, debe ser entre 1-30...\n";
    }
};

#endif // _EDayOutOfRange_H_
