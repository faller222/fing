#include "DataNotificacion.h"

    DataNotificacion::DataNotificacion(DateTime F, tipoNotificacion T,string N, bool L){
        fAccion=F;
        tipo=T;
        nArch=N;
        leida=L;   
    };
    
    tipoNotificacion DataNotificacion::getTipo(){
        return tipo;        
    };
    
    DateTime DataNotificacion::getFNot(){
        return fAccion;        
    };
    
    string DataNotificacion::getArchivo(){
        return nArch;
        
    };
    
    bool DataNotificacion::esLeida(){
        return leida;        
    };
    
    DataNotificacion::~DataNotificacion(){
        
    };