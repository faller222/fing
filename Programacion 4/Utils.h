#ifndef _Cons_Enu_H_
#define _Cons_Enu_H_

#include <stdlib.h>
#include <string>


#ifdef WIN32
#define LimPan() //system("cls");
#else
#define LimPan() //system("clear");
#endif

using namespace std;


const int Max_User_Length = 80;
const int Max_Ruta_Length = 255;
const int Max_Nom_Recurso = 8;
const int Max_Desc_Recurso = 255;

enum sexoEnum { MASCULINO, FEMENINO };

ostream& operator<< (ostream&, const sexoEnum&);

enum tipoAccion { DESCARGAR, MODIFICAR, CREACION };

ostream& operator<< (ostream&, const tipoAccion&);

enum tipoRecurso { ARCHIVO, CARPETA };

ostream& operator<< (ostream&, const tipoRecurso&);

enum tipoNotificacion { MODIFICACION, COMENTARIO };

ostream& operator<< (ostream&, const tipoNotificacion&);





#endif // _Cons_Enu_H_
