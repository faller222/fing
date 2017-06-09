#include <stdlib.h>
#include <string>
#include "Utils.h"

using namespace std;


ostream& operator<< (ostream &o, const sexoEnum &sex){
  if (sex==MASCULINO)  
    o <<(string)"MASCULINO";
  else
    o <<(string)"FEMENINO";   
 return o;  
};

ostream& operator<< (ostream &o, const tipoRecurso &tRec){
  if (tRec==ARCHIVO)  
    o <<(string)"ARCHIVO";
  else
    o <<(string)"CARPETA"; 
  return o;
};

ostream& operator<< (ostream &o, const tipoNotificacion &tNot){
  if (tNot==MODIFICACION)
    o <<(string)"MODIFICACION";
  else
    o <<(string)"COMENTARIO";
  return o;
};

ostream& operator<< (ostream &o, const tipoAccion &tAcc){
  switch (tAcc) {
    case CREACION: o <<(string)"CREACION";
    break;
    case MODIFICAR: o <<(string)"MODIFICAR";
    break;
    case DESCARGAR: o <<(string)"DESCARGAR";
    break;
  }
  return o;
};