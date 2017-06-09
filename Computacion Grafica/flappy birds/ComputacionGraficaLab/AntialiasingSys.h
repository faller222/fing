#ifndef ANTI_H
#define ANTI_H

#include "SDL.h"
#include "SDL_opengl.h"
#include <math.h>



void accFrustum(GLdouble left, GLdouble right, GLdouble bottom, GLdouble top, GLdouble nearP, GLdouble farP, GLdouble pixdx,
	GLdouble pixdy, GLdouble eyedx, GLdouble eyedy,
	GLdouble focus);

void accPerspective(GLdouble fovy, GLdouble aspect, GLdouble nearP, GLdouble farP, GLdouble pixdx, GLdouble pixdy,
	GLdouble eyedx, GLdouble eyedy, GLdouble focus);




#endif ANTI_H