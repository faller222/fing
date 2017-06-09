//main.cpp

#include "Presentacion.h"
#include "Cargar.h"
#include <iostream>
#include <string>

using namespace std;

int main()
{
    string opcion;
    do
    {
        cout << "--MENU--\n";
        cout << "A) Alta Usuario\n";
        cout << "B) Crear Recurso\n";
        cout << "C) Ingresar Comentario\n";
        cout << "D) Agregar Suscriptor\n";
        cout << "E) Ver Informacion de Usuario\n";
        cout << "F) Ver Informacion de Carpeta\n";
        cout << "G) Ver Informacion de Archivo\n";
        cout << "H) Modificar Archivo\n";
        cout << "I) Agregar Colaborador\n";
	cout << "J) Iniciar Colaborador en la Raiz\n";
        cout << "K) Ver Notificaciones\n";
        cout << "L) Cargar\n";
        cout << "S) Salir \n";
        cout << "\nOpcion: ";

        cin>>opcion;
        //getline(cin,opcion);

        switch (opcion[0])
        {
            case 'S': Presentacion::LiberarMemoria();
        break;
            case 'A': Presentacion::AltaUsuario();
        break;
            case 'B': Presentacion::CrearRecurso();
        break;
            case 'C': Presentacion::IngresarComentario();
        break;
            case 'D': Presentacion::AgregarSuscriptor();
        break;
            case 'E': Presentacion::VerInfoUsuario();
        break;
            case 'F': Presentacion::VerInfoCarpeta();
        break;
            case 'G': Presentacion::VerInfoArchivo();
        break;
            case 'H': Presentacion::ModificarArchivo();
        break;
            case 'I': Presentacion::AgregarColaborador();
	break;
            case 'J': Presentacion::IniciarColaboradorRaiz();
	break;
            case 'K': Presentacion::VerNotificaciones();
        break;
            case 'L': {
                 Presentacion::LiberarMemoria();
                 Cargar::cargar();
            }
        break;
            default: cout << "Comando Desconocido.\n" ;
        break;

        }//switch
    }while ( opcion[0]!='S' ); //while

    return 0;
}
