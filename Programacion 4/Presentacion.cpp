//Presentacion.cpp

#include "iAltaUsuario.h"
#include "iCrearRecurso.h"
#include "iIngresarComentario.h"
#include "iSuscripcion.h"
#include "iVerInfoUsuario.h"
#include "iVerInfoArchivo.h"
#include "iModificarArchivo.h"
#include "iAgregarColaborador.h"
#include "iVerInfoCarpeta.h"
#include "Presentacion.h"
#include "Factory.h"
#include "Utils.h"
#include "DateTime.h"
#include "DataUsuario.h"
#include "DataRecurso.h"
#include <iostream>
#include <string>
#include <map>

using namespace std;

void Presentacion::pedirNName(string &nNameUsuario){
    cout << "Ingrese nickname: ";
    cin >> nNameUsuario;
}

void Presentacion::pideOtro(char &Res){
    string r;
    cout << "Nickname no disponible\n";
    cout << "Desea ingresar un nuevo nickname? [Y/N]\n";
    cin >> r;
    while(!(r[0]=='Y' || r[0]=='y' || r[0]=='N' || r[0]=='n') || (r.size()>1) ){
        cout << "Opcion Incorrecta. ";
        cout << "Desea ingresar un nuevo nickname? [Y/N]\n";
        cin >> r;
    }
    Res = r[0];
}

void Presentacion::pedirSexo(sexoEnum& S){
    string r;
    cout << "Ingrese el sexo.\n";
    cout << " Masculino-M , Femenino-F \nOpcion: ";
    cin >> r;
    while(!(r[0]=='M' || r[0]=='m' || r[0]=='F' || r[0]=='f') || (r.size()>1) ){
         cout << "Opcion Incorrecta";
        cout << " Masculino-M , Femenino-F \nOpcion: ";
        cin >> r;
    }
    if (r[0]=='M'||r[0]=='m')
        S= MASCULINO;
    else
        S= FEMENINO;
}

void Presentacion::pedirFecha(DateTime& dT){
    int year;
    int month;
    int day;
    cout << "Ingresaar Fecha nacimiento:\n";
    cout << "YYYY: ";
    cin >> year;
    cout << "MM: ";
    cin >> month;
    cout << "DD: ";
    cin >> day;
    dT = DateTime(year, month, day);
}

void Presentacion::auxAltaUsuario(){
    LimPan();
    cout << "----ALTA USUARIO----\n\n";
}

void Presentacion::AltaUsuario(){
    iAltaUsuario* IAU=Factory::getAltaUsuario();
    string nNameUsuario;
    sexoEnum sexoUsuario;
    DateTime fechaN;
    bool disponible;
    char resp;
        try{
            do {
                Presentacion::auxAltaUsuario();
                Presentacion::pedirNName(nNameUsuario);
                disponible = IAU->ingresarNickname(nNameUsuario);
                if (!disponible)
                    Presentacion::pideOtro(resp);
            }while ( !disponible & (resp=='Y'|| resp=='y'));

            if (disponible)
            {
                Presentacion::auxAltaUsuario();
                Presentacion::pedirSexo(sexoUsuario);
                Presentacion::auxAltaUsuario();
                Presentacion::pedirFecha(fechaN);
                IAU->altaUsuario (sexoUsuario , fechaN);
                Presentacion::auxAltaUsuario();
                cout << "Se ha dado de alta al usuario con nickname: " << nNameUsuario << "\n";
            }
            cout << "----FIN ALTA USUARIO----\n\n\n";
            } catch (exception& e) {
                cout << e.what() << "\n\n";
            }
}

void Presentacion::pedirTipo(tipoRecurso& T){
    string r;
    cout << "Que recurso desea crear?\n";
    cout << " Archivo-1 , Carpeta-2\nOpcion: ";
    cin >> r;
    while(!(r[0]=='1' || r[0]=='2' ) || (r.size()>1) ){
        cout << "Opcion Incorrecta";
        cout << " Archivo-1 , Carpeta-2\nOpcion: ";
        cin >> r;
    }
    if (r[0]=='1')
        T= ARCHIVO;
    else
        T=CARPETA;
}

void Presentacion::pedirNombreRec(string& nRecurso,tipoRecurso T){
    if (T==ARCHIVO)
        cout << "Nombre del Archivo: ";
    else
        cout << "Nombre de la Carpeta: ";
    cin >> nRecurso;
    cin.ignore();
}

void Presentacion::pedirDescripcion(string& descripcion){
    cout << "Descripcion: ";
    getline(cin,descripcion);
}

void Presentacion::pedirUsuario(string& nName, vector<DataUsuario> Datos){
    vector<DataUsuario>::iterator it;
    cout << "Listando los Usuarios registrados:\n";
    cout << "Nickname\tSexo\\n\n";
    for ( it=Datos.begin(); it!=Datos.end() ; ++it)
        cout << it->getNickname() <<"\t\t"<< it->getSexo()<<"\n";
    cout << "\nIngrese el nickname del usuario: ";
    cin >> nName;
}

void Presentacion::pedirRuta(string& rutaRec){
    cout << "Ingrese la ubicacion \n(ej.: raiz/Carpeta1/...) : ";
    cin >> rutaRec;
}

void Presentacion::auxCrearRecurso(){
    LimPan();
    cout << "----CREAR RECURSO----\n\n";
}

void Presentacion::CrearRecurso(){
    iCrearRecurso* ICR=Factory::getCrearRecurso();

    string nombreRec;
    string descripcion;
    string nName;
    string rutaRec;
    tipoRecurso t;
    vector<DataUsuario> dataUsuarios;
        try
        {
            auxCrearRecurso();
            pedirTipo(t);
            auxCrearRecurso();
            pedirNombreRec(nombreRec,t);
            auxCrearRecurso();
            pedirDescripcion(descripcion);
            ICR->ingresarDatosRecurso(nombreRec, descripcion, t);
            dataUsuarios = ICR->obtenerUsuariosRegistrados();
            auxCrearRecurso();
            pedirUsuario(nName,dataUsuarios);
            ICR->seleccionarUsuario(nName);
            auxCrearRecurso();
            pedirRuta(rutaRec);
            ICR->ingresarRutaRecurso(rutaRec);
            ICR->altaRecurso();
            cout << "Se ha dado de alta al recurso " <<nombreRec<<" en "<<rutaRec<< "\n";
            cout << "----FIN CREAR RECURSO----\n\n\n";
        } catch (exception& e) {
                cout << e.what() << "\n\n";
        }
}

void Presentacion::mostrarComentarios(vector<DataComentario> Coments){
    vector<DataComentario>::iterator it;
    cout << "Listando los Comentarios Realizados:\n";
    cout << "Ubicacion\t\tUsuario\tId\tComentario\n\n";
    for ( it=Coments.begin(); it!=Coments.end() ; ++it)
        cout << it->getUbicacion() <<"\t"<<it->getCreador()<<"\t"<<it->getId()<<"\t"<<it->getContenido()<<"\n";
}

void Presentacion::preguntaResponde(char& Res){
    string r;
    cout << "Desea responder algun Comentario? [Y/N]\n";
    cin>>r;
    while(!(r[0]=='Y' || r[0]=='y' || r[0]=='N' || r[0]=='n') || (r.size()>1) ){
        cout << "Opcion Incorrecta. ";
        cout << "Desea responder algun Comentario? [Y/N]\n";
        cin >> r;
    }
    Res = r[0];
}

void Presentacion::preguntaCualResponde(int& Res){
    cout << "\nIndique el Id del comentario a responder: ";
    cin >> Res;
}

void Presentacion::pedirComentario(string& comentario){
    cout << "Comentario: ";
    getline(cin,comentario);
    getline(cin,comentario);
}

void Presentacion::auxIngresarComentario(){
    LimPan();
    cout << "----INGRESAR COMENTARIO----\n\n";
}

void Presentacion::IngresarComentario(){
    string ruta;
    string nName;
    string Contenido;
    char r;
    int Id;
    vector<DataComentario> coment;
    vector<DataUsuario> user;
    iIngresarComentario* IIC=Factory::getIngresarComentario();

    try{
        auxIngresarComentario();
        pedirRuta(ruta);
        coment=IIC->obtenerComentariosArchivo(ruta);
        if (!coment.empty()){
            auxIngresarComentario();
            mostrarComentarios(coment);
            preguntaResponde(r);
            if (r=='Y'||r=='y')
                preguntaCualResponde(Id);
        }

        user=IIC->obtenerUsuariosRegistrados();

        auxIngresarComentario();
        pedirUsuario(nName,user);

        IIC->seleccionarUsuario(nName);
        auxIngresarComentario();
        pedirComentario(Contenido);


        if (r=='Y'||r=='y')
            IIC->responderComentario(Contenido,Id);
        else
            IIC->agregarComentarioArchivo(Contenido);

        cout << "----FIN INGRESAR COMENTARIO----\n\n\n";
        } catch (exception& e) {
            cout << e.what() << "\n\n";
        }
}

void Presentacion::listarDataRecurso(vector<DataRecurso> dRec){
    vector<DataRecurso>::iterator it;
    cout << "Listando los Recursos:\n";
    cout << " Ubicacion\tDescripcion\n\n";
    for ( it=dRec.begin(); it!=dRec.end() ; ++it)
        cout << it->getRuta() <<"\t\t"<< it->getDescripcion()<< "\n";
}

void Presentacion::listarDataRecurso(map<string, DataRecurso> dRec){
    map<string, DataRecurso>::iterator it;
    cout << "Listando los Recursos:\n";
    cout << " Ubicacion\tDescripcion\n\n";
    for ( it=dRec.begin(); it!=dRec.end() ; ++it)
        cout << it->second.getRuta() <<"\t\t"<< it->second.getDescripcion()<< "\n";
}

void Presentacion::AgregarSuscriptor(){
    iSuscripcion* Sus = Factory::getAgregarSuscriptor();
    string rAbs, usuario;
    vector<DataRecurso> dataArchivos;
    vector<DataUsuario> dataUsers;
    try{
        dataArchivos = Sus->obtenerArchivos();
        listarDataRecurso(dataArchivos);
        pedirRuta(rAbs);
        Sus->seleccionarArchivo(rAbs);
        dataUsers = Sus->obtenerUsuarios();
        pedirUsuario(usuario, dataUsers);
        Sus->seleccionarUsuario(usuario);
        Sus->suscribirUsuarioArchivo();
        cout<< usuario << " se ha suscripto a "<<rAbs<<" correctamente.\n";
    } catch (exception &e) {
        cout << e.what() << "\n\n";
    }
}

void Presentacion::mostrarDataUsuario(DataUsuario dUsu){
    cout << " Informacion del Usuario:\n\n";
    cout<<"NickName: "<<dUsu.getNickname()<<"\n";
    cout<<"Sexo: "<<dUsu.getSexo()<<"\n";
    cout<<"Fecha de Nacimiento: "<<dUsu.getFNac()<<"\n";
    cout<<"Edad: "<<dUsu.getEdad()<<"\n";
}

void Presentacion::mostrarDataRecurso(DataRecurso dRec){
    cout << " Informacion del Recurso:\n\n";
    cout<<"Nombre: "<<dRec.getNombre()<<"\n";
    cout<<"Descripcion: "<<dRec.getDescripcion()<<"\n";
    cout<<"Ruta Completa: "<<dRec.getRuta()<<"\n";
    //HAY QUE AGREGARLE MAS COSAS AL DATA RECURSOOOOOOOOOOOOOOOOOOOOOOO
}

void Presentacion::listarDataAccion(vector<DataAccion> dAcc){
    vector<DataAccion>::iterator it;
    cout << "Listando las Acciones Realizadas:\n";
    cout << " Actor\tFecha\tTipo\n\n";
    for ( it=dAcc.begin(); it!=dAcc.end() ; ++it)
        cout << it->getCreador() <<"\t"<< it->getFAccion()<<"\t"<< it->getTipo()<< "\n";
}

void Presentacion::VerInfoUsuario(){
    cout << "----VER INFO USUARIO----\n\n\n";

    iVerInfoUsuario* VIU = Factory::getInfoUsuario();
    string  usuario;
    DataUsuario Usu;
    vector<DataRecurso> dataRec;
    map<string, DataRecurso> carpCol;
    vector<DataUsuario> dataUsers;
    vector<DataAccion> Acciones;
    try{
        dataUsers = VIU->obtenerUsuariosRegistrados();
        pedirUsuario(usuario, dataUsers);
        Usu=VIU->obtenerInfoUsuario(usuario);
        mostrarDataUsuario(Usu);
        dataRec=VIU->obtenerRecursosCreados();
        if (dataRec.empty())
            cout<<"No ha creado recursos.\n";
        else{
            cout<<"Recursos Creados\n";
            listarDataRecurso(dataRec);
        }
        cout<<"\n";
        carpCol=VIU->obtenerCarpetasColabora();
        if (carpCol.empty())
            cout<<"No Colabora en alguna carpeta.\n";
        else{
            cout<<"Carpetas Colabora\n";
            listarDataRecurso(carpCol);
        }
        cout<<"\n";
        Acciones=VIU->obtenerAcciones();
        if (Acciones.empty())
            cout << "No ha realizado ninguna accion.\n";
        else
            listarDataAccion(Acciones);

cout << "----FIN VER INFO USUARIO----\n\n\n";

    } catch (exception &e) {
        cout << e.what() << "\n\n";
    }

}

void Presentacion::VerInfoCarpeta(){
    iVerInfoCarpeta* VIC = Factory::getInfoCarpeta();
    string  rAbs;
    DataRecurso dRec;
    DataUsuario Creador;
    vector<DataRecurso> dataRec;
    vector<DataRecurso> carpCon;
    try{
        dataRec = VIC->obtenerCarpetas();
        listarDataRecurso(dataRec);
        pedirRuta(rAbs);
        dRec=VIC->seleccionarCarpeta(rAbs);
        mostrarDataRecurso(dRec);
        Creador =VIC->obtenerCreador();
        mostrarDataUsuario(Creador);
//        carpCon=VIC->obtenerRecursosContenidos();
//        if (carpCon.empty())
//            cout<<"Esta carpeta esta vacia.\n";
//        else{
//            cout<<"Recursos Contenidos\n";
//            listarDataRecurso(carpCon);
//        }
    } catch (exception &e) {
        cout << e.what() << "\n\n";
    }

}

void Presentacion::VerInfoArchivo(){
    iVerInfoArchivo* VIA = Factory::getInfoArchivo();
    string  rAbs;
    DataRecurso dRec;
    vector<DataRecurso> dataRec;
    vector<DataAccion> Acciones;
    vector<DataComentario> Comentarios;
    try{
        dataRec = VIA->obtenerArchivos();
        listarDataRecurso(dataRec);
        pedirRuta(rAbs);
        VIA->seleccionarArchivo(rAbs);
        dRec=VIA->obtenerArchivo();
        mostrarDataRecurso(dRec);
        Acciones=VIA->obtenerAcciones();
        if (!Acciones.empty())
                listarDataAccion(Acciones);
        Comentarios=VIA->obtenerComentarios();
        if (!Comentarios.empty())
                mostrarComentarios(Comentarios);

    } catch (exception &e) {
        cout << e.what() << "\n\n";
    }

}

void Presentacion::ModificarArchivo(){
    iModificarArchivo* IMA = Factory::getModificarArchivo();
    string rAbs, usuario,Desc;
    vector<DataRecurso> dataArchivos;
    vector<DataUsuario> dataUsers;
    try{
        dataArchivos = IMA->obtenerRecursosTipo();
        listarDataRecurso(dataArchivos);
        pedirRuta(rAbs);
        IMA->seleccionarArchivo(rAbs);
        dataUsers = IMA->obtenerUsuariosRegistrados();
        pedirUsuario(usuario, dataUsers);
        IMA->seleccionarUsuario(usuario);
        cin.ignore();
        pedirDescripcion(Desc);
        IMA->ingresarDescripcion(Desc);
        cout<< "Se ha Modificado correctamente.\n";
    } catch (exception &e) {
        cout << e.what() << "\n\n";
    }

}

void Presentacion::AgregarColaborador() {
    iAgregarColaborador* iAC=Factory::getIAgregarColaborador();
    string nName;
    string rutaAbs;

	cout << "----AGREGAR COLABORADOR CARPETA----\n";

	try {
	//Obtener Usuarios registrados
	vector<DataUsuario> dataU = iAC->obtenerUsuariosRegistrados();  // (ExNoHayUsuariosRegistrados)
	Presentacion::pedirUsuario(nName, dataU);
	cout << "Listando carpetas colabora:\n";
			map<string,DataRecurso> dataCarpetas = iAC->obtenerCarpetasColabora(nName);
				map<string,DataRecurso>::iterator it;
				for ( it=dataCarpetas.begin(); it!=dataCarpetas.end() ; ++it ) {
					if ( it->second.getRuta()!="raiz" ) {
						//cout << "Presentacion\n";
						cout << "Ruta carpeta: " << it->second.getRuta() << "\n";
						//cout << it->second.getTipo() <<"\n"; //PA VERIFICAR! BORRARLO
					}
				}

			cout << "Ingrese la Ruta de la carpeta sobre la cual desea colaborar:\n";
			cin >> rutaAbs;

			iAC->ingresarRutaRecurso(rutaAbs);

			nName="";
			cout << "Agregar colaborador:\n";
			Presentacion::pedirUsuario(nName, dataU);

			iAC->altaColaborador(nName, false); //false xq no es en la raiz

		} catch (exception& e) {
			cout << e.what() << "\n\n";
		}

    cout << "----FIN AGREGAR COLABORADOR CARPETA----\n";

}


void Presentacion::IniciarColaboradorRaiz() {
    iAgregarColaborador* iAC=Factory::getIAgregarColaborador();
    string nName;

	cout << "----INICIAR COLABORADOR RAIZ----\n";

	//Obtener Usuarios registrados
	try {
		vector<DataUsuario> dataU = iAC->obtenerUsuariosRegistrados(); // (ExNoHayUsuariosRegistrados)
		Presentacion::pedirUsuario(nName, dataU);

		iAC->altaColaborador( nName , true ); //(ExNoExisteUsuario, ExMemoriaNoSeteada, ExYaEsColaborador)

	} catch (exception& e) {
		cout << e.what() << "\n\n";
	}

	cout << "----FIN INICIAR COLABORADOR RAIZ----\n";
}

void Presentacion::LiberarMemoria(){
    iAgregarColaborador* iAC=Factory::getIAgregarColaborador();
    iModificarArchivo* IMA = Factory::getModificarArchivo();
    iVerInfoArchivo* VIA = Factory::getInfoArchivo();
    iVerInfoCarpeta* VIC = Factory::getInfoCarpeta();
    iVerInfoUsuario* VIU = Factory::getInfoUsuario();
    iSuscripcion* Sus = Factory::getAgregarSuscriptor();
    iIngresarComentario* IIC=Factory::getIngresarComentario();
    iCrearRecurso* ICR=Factory::getCrearRecurso();
    iAltaUsuario* IAU=Factory::getAltaUsuario();

    iAC->destruir();
    IMA->destruir();
    VIA->destruir();
    VIC->destruir();
    VIU->destruir();
    Sus->destruir();
    IIC->destruir();
    ICR->destruir();
    IAU->destruir();

}

void Presentacion::listarNotificaciones(vector<DataNotificacion> dNot){
    vector<DataNotificacion>::iterator it;
    cout << "Listando las Notificaciones:\n";
    cout << " Archivo\tTipo\tFecha\tLeida\n\n";
    for ( it=dNot.begin(); it!=dNot.end() ; ++it) {
        cout << it->getArchivo() <<"\t"<< it->getTipo()<<"\t"<< it->getFNot()<< "\t";
        if(it->esLeida())
            cout << "Si\n";
        else
            cout << "No\n";
    }
}

void Presentacion::VerNotificaciones(){
    try{
        iSuscripcion* Sus=Factory::getAgregarSuscriptor();
        vector<DataUsuario> users;
        vector<DataNotificacion> notif;
        string algunnombre;


        users=Sus->obtenerUsuarios();
        pedirUsuario(algunnombre, users);
        Sus->seleccionarUsuario(algunnombre);

        notif = Sus->obtenerNotificaciones();

        if(notif.empty())
            cout << "No hay notificaciones\n";
        else
            listarNotificaciones(notif);
    }catch(exception& e){
		cout << e.what() << "\n\n";
    }
}
