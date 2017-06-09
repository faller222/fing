#include "Notificacion.h"
#include "DateTime.h"
#include "Archivo.h"
#include "Utils.h"

using namespace std;

        
Notificacion::Notificacion(Archivo* ar, tipoNotificacion tN){
    arch = ar;
    tipo = tN;
    fecha = DateTime();
    leida = false;
}

tipoNotificacion Notificacion::getTipo(){
    return this->tipo;
}

DateTime Notificacion::getFecha(){
    return this->fecha;
}

bool Notificacion::getLeida(){
    return this->leida;
}

DataNotificacion Notificacion::getDataNotificacion(){
    return DataNotificacion(this->fecha, this->tipo, this->arch->getRutaAbsoluta(), this->leida);
}


void Notificacion::setLeida(){
    leida = true;
}
