#include "Cargar.h"
#include <string>


void Cargar::cargar(){
    iAltaUsuario* IAU=Factory::getAltaUsuario();
    iCrearRecurso* ICR=Factory::getCrearRecurso();
    iAgregarColaborador* IAC=Factory::getIAgregarColaborador();
    iIngresarComentario* IIC=Factory::getIngresarComentario();
    iSuscripcion* SUS=Factory::getAgregarSuscriptor();
    
   
    cout<<"Creo los Usuarios\n";
    IAU->ingresarNickname((string)"James");
    IAU->altaUsuario(MASCULINO,DateTime(1988,12,28));

    IAU->ingresarNickname((string)"Jennifer");
    IAU->altaUsuario(FEMENINO,DateTime(1990,01,01));

    IAU->ingresarNickname((string)"John");
    IAU->altaUsuario(MASCULINO,DateTime(1980,03,03));
    
    cout<<"Creo algunos recursos\n";
    //DE
    ICR->seleccionarUsuario((string)"John");
    ICR->ingresarDatosRecurso((string)"Deporte",(string)"Almacena información de deportes.",CARPETA);
    ICR->ingresarRutaRecurso((string)"raiz");
    ICR->altaRecurso();
    //JU
    ICR->seleccionarUsuario((string)"John");
    ICR->ingresarDatosRecurso((string)"Juegos",(string)"Almacena informacion de los últimos juegos para PC.",CARPETA);
    ICR->ingresarRutaRecurso((string)"raiz");
    ICR->altaRecurso();
    //PR
    ICR->seleccionarUsuario((string)"John");
    ICR->ingresarDatosRecurso((string)"Proyecto",(string)"Almacena recursos relacionados con proyectos de software.",CARPETA);
    ICR->ingresarRutaRecurso((string)"raiz");
    ICR->altaRecurso();


    cout<<"Creo las Colaboraciones\n";
    //Col1";
    //IAC->ingresarRutaRecurso((string)"raiz");
    IAC->altaColaborador((string)"John",true);
    //Col2";
    IAC->obtenerCarpetasColabora("John");
    IAC->ingresarRutaRecurso("raiz/Deporte");
    IAC->altaColaborador((string)"James",false);
    //Col3";
    IAC->obtenerCarpetasColabora("John");
    IAC->ingresarRutaRecurso((string)"raiz/Juegos");
    IAC->altaColaborador((string)"James",false);
    //Col4";
    IAC->obtenerCarpetasColabora("John");
    IAC->ingresarRutaRecurso((string)"raiz/Juegos");
    IAC->altaColaborador((string)"Jennifer",false);
    //Col5";
    IAC->obtenerCarpetasColabora("John");
    IAC->ingresarRutaRecurso((string)"raiz/Proyecto");
    IAC->altaColaborador((string)"Jennifer",false);
    
    cout<<"Creo otros recursos\n";
    //FB
    ICR->seleccionarUsuario((string)"James");
    ICR->ingresarDatosRecurso((string)"Futbol",(string)"Almacena recursos relacionados con el fútbol.",CARPETA);
    ICR->ingresarRutaRecurso((string)"raiz/Deporte");
    ICR->altaRecurso();
    //CA
    ICR->seleccionarUsuario((string)"James");
    ICR->ingresarDatosRecurso((string)"CopaAm",(string)"Archivo de texto que contiene las últimas noticias de la Copa América.",ARCHIVO);
    ICR->ingresarRutaRecurso((string)"raiz/Deporte/Futbol");
    ICR->altaRecurso();
    //MU
    ICR->seleccionarUsuario((string)"John");
    ICR->ingresarDatosRecurso((string)"Mundial",(string)"Archivo de texto que contiene las últimas noticias de la Copa del Mundo.",ARCHIVO);
    ICR->ingresarRutaRecurso((string)"raiz/Deporte/Futbol");
    ICR->altaRecurso();
    //SA
    ICR->seleccionarUsuario((string)"John");
    ICR->ingresarDatosRecurso((string)"Salud",(string)"Archivo que contiene informacion de la salud en los deportes.",ARCHIVO);
    ICR->ingresarRutaRecurso((string)"raiz/Deporte");
    ICR->altaRecurso();
    //TS
    ICR->seleccionarUsuario((string)"Jennifer");
    ICR->ingresarDatosRecurso((string)"Tesis",(string)"Informe final de la tésis.",ARCHIVO);
    ICR->ingresarRutaRecurso((string)"raiz/Proyecto");
    ICR->altaRecurso();
   
    cout<<"Creo los Comentarios\n";
    //Com1
    IIC->obtenerComentariosArchivo((string)"raiz/Deporte/Futbol/CopaAm");
    IIC->seleccionarUsuario((string)"John");
    IIC->agregarComentarioArchivo((string)"Me da la impresión que le falta info al archivo...");
    //Com2
    IIC->obtenerComentariosArchivo((string)"raiz/Deporte/Futbol/CopaAm");
    IIC->seleccionarUsuario((string)"James");
    IIC->responderComentario((string)"Apenas pueda lo modifico :)",1);
    //Com3
    IIC->obtenerComentariosArchivo((string)"raiz/Deporte/Futbol/CopaAm");
    IIC->seleccionarUsuario((string)"James");
    IIC->responderComentario((string)"Y le agrego la info de años anteriores",1);
    //Com4
    IIC->obtenerComentariosArchivo((string)"raiz/Deporte/Futbol/CopaAm");
    IIC->seleccionarUsuario((string)"John");
    IIC->responderComentario((string)"¡Muchas gracias!",3);
    //Com5
    IIC->obtenerComentariosArchivo((string)"raiz/Deporte/Futbol/CopaAm");
    IIC->seleccionarUsuario((string)"James");
    IIC->responderComentario((string)"¡De nada!",4);
    //Com6  
    IIC->obtenerComentariosArchivo((string)"raiz/Proyecto/Tesis");
    IIC->seleccionarUsuario((string)"John");
    IIC->agregarComentarioArchivo((string)"Faltaron algunas conclusiones...");
    //Com7
    IIC->obtenerComentariosArchivo((string)"raiz/Proyecto/Tesis");
    IIC->seleccionarUsuario((string)"Jennifer");
    IIC->responderComentario((string)"Enseguida las agrego",1);
    //Com8 
    IIC->obtenerComentariosArchivo((string)"raiz/Proyecto/Tesis");
    IIC->seleccionarUsuario((string)"Jennifer");
    IIC->agregarComentarioArchivo((string)"¡Conclusiones agregadas!");
    
    cout<<"Creo las Suscripciones\n";
    //S1
    SUS->seleccionarUsuario((string)"James");
    SUS->seleccionarArchivo((string)"raiz/Deporte/Futbol/CopaAm");
    SUS->suscribirUsuarioArchivo();    
    //S2
    SUS->seleccionarUsuario((string)"Jennifer");
    SUS->seleccionarArchivo((string)"raiz/Proyecto/Tesis");
    SUS->suscribirUsuarioArchivo();
       cout<<"Listo\n\n";
}


