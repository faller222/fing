#include "Editor.h"
#include "tinyxml.h"
#include <string>
using namespace std;


void Editor::crearXML(){

	TiXmlDocument doc;
	TiXmlDeclaration * decl = new TiXmlDeclaration("1.0", "", "");
	doc.LinkEndChild(decl);

	TiXmlElement * element = new TiXmlElement("level");
	doc.LinkEndChild(element);

	doc.SaveFile("level.xml");



};

void Editor::escribirXML(int x, int y){


	TiXmlDocument doc("level.xml");
	doc.LoadFile();

	TiXmlHandle hDoc(&doc);
	TiXmlElement *pRoot;
	TiXmlElement* obstacleEntry;
		
	pRoot = doc.FirstChildElement("level");

	obstacleEntry = new TiXmlElement("obstacle");
	obstacleEntry->SetAttribute("obsId",idObstaculo);
	idObstaculo++;
	obstacleEntry->SetAttribute("x", x);
	obstacleEntry->SetAttribute("y", y);
	pRoot->LinkEndChild(obstacleEntry);


	doc.SaveFile("level.xml");
}

void Editor::borrarUltimoObstaculo(){
	
	TiXmlDocument doc("level.xml");
	doc.LoadFile();

	TiXmlHandle hDoc(&doc);
	TiXmlElement *pRoot;

	pRoot = doc.FirstChildElement("level");
	TiXmlNode* ultimoObstaculo = pRoot->LastChild();
	pRoot->RemoveChild(ultimoObstaculo);

	doc.SaveFile("level.xml");
	};

	





