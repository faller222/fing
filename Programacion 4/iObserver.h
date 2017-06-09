#ifndef _IOBSERVER_H
#define	_IOBSERVER_H

#include "Notificacion.h"

using namespace std;

class iObserver{
public:
    virtual void notificar(Notificacion*)=0;

};


#endif	/* IOBSERVER_H */

