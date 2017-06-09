#ifndef _COMENTARIO_H_
#define _COMENTARIO_H_

#include "Archivo.h"
#include "Usuario.h"
#include "DataComentario.h"

#include "Utils.h"
#include <map>
#include <string>


using namespace std;

class Comentario {
    public:
        Comentario(const string, Usuario*, Archivo*);
        Comentario(const string, Usuario*, Archivo*, Comentario*);
        ~Comentario();
        bool perteneceA(const string);
        DataComentario getDataComentario();
        int getIdCom();
        int getId();
        void agregarRespuesta(Comentario*);
        Archivo* getArchivo();
    private:
        static int cdrComentario;
    	int idComentario;
    	int idGeneral;
        string contenido;
        Usuario* refUser;
        Archivo* refArch;
        map<int, Comentario*> respuestas; 
};

        
#endif // _COMENTARIO_H_
