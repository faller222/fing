#ifndef EDITOR_H
#define EDITOR_H



class Editor{

private :
	int idObstaculo;
	int idFile;

public:
	Editor(){ idObstaculo = 0; idFile = 0; }
	void escribirXML(int x, int y);
	void borrarUltimoObstaculo();
	void crearXML();

};



#endif EDITOR_H