//Comentario.cpp
#include "Archivo.h"
#include "Recurso.h"
#include "Usuario.h"
#include "DataComentario.h"
#include "Comentario.h"

#include "Utils.h"
#include "cVerInfoUsuario.h"
#include <map>
#include <string>


using namespace std;

int Comentario::cdrComentario=0;

Comentario::Comentario(const string cont, Usuario* U, Archivo* A){
    cdrComentario++;
    idGeneral=cdrComentario;
    idComentario=A->getContador();
    contenido=cont;
    refUser=U;
    refArch=A;
    A->modificar(COMENTARIO);
}

Comentario::~Comentario(){
    cdrComentario=0;
}

bool Comentario::perteneceA(const string rAbs){
    return refArch->sosVos(rAbs);    
}

DataComentario Comentario::getDataComentario(){
    string usu = refUser->getNickname();
    string arch = refArch->getRutaAbsoluta();
    return DataComentario(idComentario,contenido,usu,arch);    
}

int Comentario::getIdCom(){
    return idGeneral;
}

int Comentario::getId(){
    return idComentario;
}

void Comentario::agregarRespuesta(Comentario* coment){
        respuestas[coment->idComentario]=coment;
}

Archivo* Comentario::getArchivo(){
    return refArch;
}