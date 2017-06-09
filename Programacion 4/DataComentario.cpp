//DataComentario.cpp
#include "Utils.h"
#include "DataComentario.h"
#include <string>
#include <iostream>

using namespace std;

DataComentario::DataComentario(){}

DataComentario::DataComentario(const int id, const string cont, const string usu, const string arch){
    Id=id;
    contenido=cont;      
    usuario=usu;
    archivo=arch;       
}

string DataComentario::getContenido(){
    return contenido;
}

string DataComentario::getUbicacion(){
    return archivo;    
}

string DataComentario::getCreador(){
    return usuario;
}

int DataComentario::getId(){
    return Id;
}
