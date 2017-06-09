//cCrearRecurso.cpp

#include "cCrearRecurso.h"
#include "Usuario.h"
#include "Recurso.h"
#include "Archivo.h"
#include "Carpeta.h"

#include "cAltaUsuario.h"
#include "cAgregarColaborador.h"

#include <string>
#include <map>
#include <vector>

#include "DataRecurso.h"
#include "DataUsuario.h"

#include "ExNombreInvalidoRecurso.h"
#include "ExNoExisteUsuario.h"
#include "ExRutaInvalida.h"
#include "ExYaExisteRecurso.h"
#include "ExNoHayUsuariosRegistrados.h"

using namespace std;

cCrearRecurso::cCrearRecurso() {
    Recurso * raiz = new Carpeta();
    recursos[(string)"raiz"]=raiz;
}

//destructor privado
cCrearRecurso::~cCrearRecurso() {
	map<string, Recurso*>::iterator it;
	for (it=this->recursos.begin(); it!=this->recursos.end(); ++it ) {
		delete (it->second); //elimino el UsuarioCarpeta
	}

	this->recursos.clear();
}

//inicializo variable estatica para singleton
cCrearRecurso* cCrearRecurso::instancia = NULL;

cCrearRecurso* cCrearRecurso::getInstance(){
        if (!instancia)
            instancia = new cCrearRecurso();
    return instancia;
}

void cCrearRecurso::destruir() { //es dueño de la coleccion de UsuarioCarpeta
    if (instancia!=NULL) {
        delete instancia;
	instancia = NULL;
    }
}


void cCrearRecurso::esValidoNombre(const string recName) throw (ExNombreInvalidoRecurso) {
    int largo =recName.size();
    bool res;

    if(largo>Max_Nom_Recurso) throw ExNombreInvalidoRecurso();

    res=(recName!="raiz");

    char aux;
    int i=0;

    while ((i<largo)&&res)
    {
        aux=recName[i];
        res = res && (aux!='/');
        i++;
    }
    
    if(!res) throw ExNombreInvalidoRecurso();

}

//Implementa la interface
    void cCrearRecurso::ingresarDatosRecurso(const string Name,const string Descr, tipoRecurso Ti) throw (ExNombreInvalidoRecurso) {
        cCrearRecurso::esValidoNombre(Name); //throw ExNombreInvalidoRecurso
        this->Nombre=Name;
        this->Desc=Descr;
        this->Tipo=Ti;

    };

    vector<DataUsuario> cCrearRecurso::obtenerUsuariosRegistrados() throw (ExNoHayUsuariosRegistrados)
    {
        cAltaUsuario* CAU=cAltaUsuario::getInstance();
        vector<DataUsuario> dataUsuarios = CAU->obtenerUsuariosRegistrados();
        return dataUsuarios;
    }

    void cCrearRecurso::seleccionarUsuario(const string nName) throw (ExNoExisteUsuario){
        cAltaUsuario* CAU=cAltaUsuario::getInstance();
        this->refCreador=CAU->buscarUsuario(nName);
    }

    void cCrearRecurso::ingresarRutaRecurso(const string rutaPadre) throw (ExRutaInvalida){
        map<string, Recurso*>::iterator it;
        it = this->recursos.find(rutaPadre);
        if (this->recursos.count(rutaPadre)==0)
            throw ExRutaInvalida();
        if (it->second->getTipo()==ARCHIVO)
            throw ExRutaInvalida();
        this->refPadre=(Carpeta*)it->second;
    };



void cCrearRecurso::altaRecurso() throw (ExRutaInvalida, ExNoTieneDerechos) {
	//Pre4:El usuario ( que quiero asignar como creador) existente en la memoria del sistema debe ser creador o colaborador de la carpeta con ruta refPadre->getRutaAbsoluta()
        rAbs=refPadre->getRutaAbsoluta()+"/"+Nombre;

	if ( refPadre->getRutaAbsoluta() != "raiz" ) {
            //helen Pre4: ExNoTieneDerechos
		cAgregarColaborador* cAC = cAgregarColaborador::getInstance();
		map<string,DataRecurso> carpColUser = cAC->obtenerCarpetasColabora( this->refCreador->getNickname() ); //throw (ExNoExisteUsuario,ExNoTieneCarpColabora) -> sé que aca no salta xq debería haber saltado antes

                map<string,DataRecurso>::iterator it = carpColUser.find( refPadre->getRutaAbsoluta() );
                if ( it==carpColUser.end() ) throw ExNoTieneDerechos(); //si me devuelve un puntero al final, es porque no tiene derechos sobre la carpeta donde quiere agregar resursos
	}

        if (this->recursos.count(rAbs)!=0)
            throw ExRutaInvalida();

        Recurso* R;

        if (this->Tipo==ARCHIVO)
            R= new Archivo(Nombre,Desc,rAbs,refCreador);
        else
            R= new Carpeta(Nombre,Desc,rAbs,refCreador);
        this->refPadre->agregarHijo(R);//se lo arego al padre
        this->recursos[rAbs] = R;//se lo agrego a toda la coleccion
}



// Otras Funciones
vector<DataRecurso> cCrearRecurso::obtenerRecursosCreados(const string nName){

    vector<DataRecurso> listaDataRecursos;

    map<string, Recurso*>::iterator it;
    DataRecurso dR;

    for ( it=this->recursos.begin(); it!= this->recursos.end(); ++it) {
        if (it->second->esCreador(nName)){
            dR = it->second->getDataRecurso();
            listaDataRecursos.push_back( DataRecurso(dR) );
        }
    }
	return listaDataRecursos;
}


Recurso* cCrearRecurso::buscarRecurso(const string rutaAbs) throw (ExRutaInvalida){
    map<string, Recurso*>::iterator it;
    Recurso* ret=NULL;

    if (this->recursos.count(rutaAbs)!=0){
        it = this->recursos.find(rutaAbs);
        ret=it->second;
    }
    if (ret==NULL)
        throw ExRutaInvalida();
    return ret;
}

DataRecurso cCrearRecurso::obtenerInfoRecurso(const string rAbs) throw (ExRutaInvalida){

    DataRecurso dR=buscarRecurso(rAbs)->getDataRecurso();
     return dR;
}

vector<DataRecurso> cCrearRecurso::obtenerRecursosTipo(tipoRecurso T){
    vector<DataRecurso> lista;

    map<string, Recurso*>::iterator it;
    DataRecurso dR;

    for ( it=this->recursos.begin(); it!= this->recursos.end(); ++it) {
        if (it->second->getTipo()==T){
            dR = it->second->getDataRecurso();
            lista.push_back( DataRecurso(dR) );
        }
    }
    return lista;
}

vector<DataRecurso> cCrearRecurso::obtenerRecursosCreadosTipoUsuario( tipoRecurso T, string nickName) {
    vector<DataRecurso> lista;

    map<string, Recurso*>::iterator it;
    DataRecurso dR;

    for ( it=this->recursos.begin(); it!= this->recursos.end(); ++it) {
        if (it->second->getTipo()==T && it->second->getRutaAbsoluta()!="raiz" && it->second->esCreador(nickName)  ) {
            dR = it->second->getDataRecurso();
            lista.push_back( DataRecurso(dR) );
        }
    }

    return lista;
}

//la uso en cAgregarColaborador
vector<DataRecurso> cCrearRecurso::obtenerHijosCarpetaTipo(string rutaAbs, tipoRecurso T) {
//Pre1: el recurso que me pasan es efectivamente una carpeta
//retorna un set de dataRecurso con los hijos de tipo tipoRecurso de la carpeta pasada por parametro
	Carpeta* c = (Carpeta*) this->buscarRecurso(rutaAbs);
	return c->obtenerDataCarpetasHijas();
}
