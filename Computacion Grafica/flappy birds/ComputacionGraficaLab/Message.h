#ifndef MESSAGE_H
#define MESSAGE_H

#include "SDL.h"
#include <string>
#include <vector>

using namespace std;

class Message {
private:
	static Message* instance;
	Message();

	Uint32 startTime;

	string mensaje;

public:
	static Message* getInstance();

	void registrarMensaje(string m);
	void dibujarMensaje();
};

#endif MESSAGE_H