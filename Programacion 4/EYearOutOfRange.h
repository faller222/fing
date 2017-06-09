#ifndef _EYearOutOfRange_H_
#define _EYearOutOfRange_H_

#include <iostream>
#include <exception>

using namespace std;

class EYearOutOfRange : public exception {
public:
	//overriding del metodo what()
    const char* what() const throw() {
        return "Error: anio fuera de rango, debe ser entre 0-9999...\n";
    }
};

#endif // _EYearOutOfRange_H_
