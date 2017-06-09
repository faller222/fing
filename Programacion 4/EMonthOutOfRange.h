#ifndef _EMonthOutOfRange_H_
#define _EMonthOutOfRange_H_

#include <iostream>
#include <exception>

using namespace std;

class EMonthOutOfRange : public exception {
public:
	//overriding del metodo what()
    const char* what() const throw() {
        return "Error: mes fuera de rango, debe ser entre 1-12...\n";
    }
};

#endif // _EMonthOutOfRange_H_
