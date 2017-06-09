#ifndef CONSTANTS_H
#define CONSTANTS_H

#define WINDOW_CAPTION "Flappy Wings!"

#define WINDOW_WIDTH 1366
#define WINDOW_HEIGHT 768

#define M_PROJ_PERSPECTIVE	0
#define M_PROJ_ORTHO		1

#define PERSPECTIVE_ANGLE 45.0f
#define PERSPECTIVE_NEAR  1.0f
#define PERSPECTIVE_FAR 400.0f

#define WORLD_X_DISTANCE 1000.0f
#define WORLD_Z_DISTANCE 1000.0f

#define CAMERA_DISTANCE 20.0f

#define MAX_FPS 500

#define PLANE_SPEED 0.9f
#define PLANE_JUMP_SPEED 1.1f
#define GRAVITY 0.09f

#define LIGHT_POSITION 0
#define AMBIENT_COLOR 1
#define DIFFUSE_COLOR 2

#define OBSTACLE_RADIUS 6.0f

#define AA_NO 1
#define AA_X2 2
#define AA_X4 4
#define AA_X8 8

#define BUILDINGS 7

#define TEXTURE_PARTICLE "textures/Tex_Particle.jpg"
#define TEXTURE_OBSTACLE "textures/Tex_Wall.jpg"

#define LEVEL_PATH "niveles/nivel.xml"

#endif CONSTANTS_H