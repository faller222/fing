#include <vector>

#include "LevelLoader.h"

using namespace std;

void LevelLoader::crearObstaculosDeXml(StatePlaying* s, string filepath, Drawable* personaje) {
	TiXmlDocument doc(filepath);
	doc.LoadFile();

	TiXmlHandle hDoc(&doc);
	TiXmlElement *pRoot;

	string id;
	float x;
	float y;
	Obstacle* obs;

	vector<Obstacle*> obstaculos;

	pRoot = doc.FirstChildElement("level");
	pRoot = pRoot->FirstChildElement("obstacle");

	while (pRoot) {
		id = pRoot->Attribute("obsId");
		x = (float)atof(pRoot->Attribute("x"));
		y = (float)atof(pRoot->Attribute("y"));	
		obs = new Obstacle (x, y, 0.0f, personaje);
		s->manageObject("obstaculo" + id, obs);
		obstaculos.push_back(obs);
		pRoot = pRoot->NextSiblingElement();
	}
	// En este momento, x tiene la posición del último obstaculo
	for (vector<Obstacle*>::iterator it = obstaculos.begin(); it != obstaculos.end(); it++) {
		Obstacle* o = (Obstacle*) *it;
		o->setLastObstaclePosition(x);
	}
}