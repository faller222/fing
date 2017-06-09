#ifndef MODELABLE_H
#define MODELABLE_H

#include <string>
#include <assimp/Importer.hpp>      // C++ importer interface
#include <assimp/scene.h> 
#include <assimp/postprocess.h>

using namespace std;

class Modelable{


protected:
	float** vertexT;
	float** vertexA;
	int* numVerts;
	int numMeshes;
	float** vertexN;
public:

	Modelable(string pFile);
	Modelable(){};
	static aiVector3D CalcNormal(aiVector3D v1, aiVector3D v2, aiVector3D v3);

};

#endif MODELABLE_H