
#include "Modelable.h"


aiVector3D Modelable::CalcNormal(aiVector3D v1, aiVector3D v2, aiVector3D v3){


	float v1x, v1y, v1z, v2x, v2y, v2z;
	float nx, ny, nz;
	float vLen;

	aiVector3t<float> Result;

	// Calculate vectors
	v1x = v1.x - v2.x;
	v1y = v1.y - v2.y;
	v1z = v1.z - v2.z;

	v2x = v2.x - v3.x;
	v2y = v2.y - v3.y;
	v2z = v2.z - v3.z;

	// Get cross product of vectors
	nx = (v1y * v2z) - (v1z * v2y);
	ny = (v1z * v2x) - (v1x * v2z);
	nz = (v1x * v2y) - (v1y * v2x);

	// Normalise final vector
	vLen = sqrt((nx * nx) + (ny * ny) + (nz * nz));

	Result.x = (float)(nx / vLen);
	Result.y = (float)(ny / vLen);
	Result.z = (float)(nz / vLen);

	return Result;
}

Modelable::Modelable(string pFile){
	float* vertexAtemp;
	float* vertexTtemp;
	float* vertexNtemp;


	Assimp::Importer importer;
	const aiScene* scene = importer.ReadFile(pFile,
		aiProcess_CalcTangentSpace |
		aiProcess_Triangulate |
		aiProcess_JoinIdenticalVertices |
		aiProcess_SortByPType);

	numMeshes = scene->mNumMeshes;
	vertexA = new float*[numMeshes];
	vertexT = new float*[numMeshes];
	numVerts = new int[numMeshes];
	vertexN = new float*[numMeshes];
	for (int h = 0; h < numMeshes; h++){


		aiMesh *mesh = scene->mMeshes[h]; // primer malla


		numVerts[h] = mesh->mNumFaces * 3;

		vertexAtemp = new float[mesh->mNumFaces * 3 * 3]; // el total de caras por tres vertices ( está triangulado) por tres coordenadas.
		vertexTtemp = new float[mesh->mNumFaces * 3 * 2];
		vertexNtemp = new float[mesh->mNumFaces * 3 * 3];

		for (unsigned int i = 0; i < mesh->mNumFaces; i++)//se recorre todas las caras de la malla
		{
			const aiFace& face = mesh->mFaces[i]; // accedo a la cara i, es un conjunto de indices

			for (int j = 0; j < 3; j++)// proceso los 3 vertices del face ( es un triangulo)
			{
				if (mesh->HasTextureCoords(0)){
					aiVector3D uv = mesh->mTextureCoords[0][face.mIndices[j]];
					memcpy(vertexTtemp, &uv, sizeof(float)* 2);
					vertexTtemp += 2;
				}

				aiVector3D pos = mesh->mVertices[face.mIndices[j]]; // accesdo a las coordenadas de la cara i
				memcpy(vertexAtemp, &pos, sizeof(float)* 3);// pos es un vector
				vertexAtemp += 3;
			}

			aiVector3D normal = CalcNormal(mesh->mVertices[face.mIndices[0]], mesh->mVertices[face.mIndices[1]], mesh->mVertices[face.mIndices[2]]);
			memcpy(vertexNtemp, &normal, sizeof(float)* 3);
			vertexNtemp += 3;
			memcpy(vertexNtemp, &normal, sizeof(float)* 3);
			vertexNtemp += 3;
			memcpy(vertexNtemp, &normal, sizeof(float)* 3);
			vertexNtemp += 3;
		}

		vertexNtemp -= mesh->mNumFaces * 3 * 3;
		vertexAtemp -= mesh->mNumFaces * 3 * 3;
		vertexTtemp -= mesh->mNumFaces * 3 * 2;

		vertexN[h] = vertexNtemp;
		vertexA[h] = vertexAtemp;
		vertexT[h] = vertexTtemp;

	}
}

	




