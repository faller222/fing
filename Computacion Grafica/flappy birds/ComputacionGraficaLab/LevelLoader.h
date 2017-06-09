#ifndef LEVEL_LOADER_H
#define LEVEL_LOADER_H

#include "Drawable.h"
#include "StatePlaying.h"
#include "tinyxml.h"
#include "Obstacle.h"

class LevelLoader {
public:
	static void crearObstaculosDeXml(StatePlaying* s, string filepath, Drawable* personaje);
};

#endif LEVEL_LOADER_H