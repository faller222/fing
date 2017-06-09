#ifndef _EHourOutOfRange_H_
#define _EHourOutOfRange_H_

#include <iostream>
#include <exception>

using namespace std;

class EHourOutOfRange : public exception {
public:
	//overriding del metodo what()
    const char* what() const throw() {
        return "Error: hora fuera de rango, debe ser entre 0-23...\n";
    }
};

#endif // _EHourOutOfRange_H_
