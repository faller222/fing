#ifndef QUATERNIONS_H
#define QUATERNIONS_H

#define PI 3.14159265359f

class Quat {
private:
	float q[4];
public:
	Quat();
	/* Creates a quaternion representing a rotation of
	ang degrees around the (x,y,z) axis. */
	Quat(float ang, float _x, float _y, float _z);

	void setValues(float ang, float _x, float _y, float _z);

	/* Return the resulting quaternion from multiplying
	two quaternions */
	Quat operator* (Quat & s);

	/* Returns a rotation matrix from Quat q */
	float* getMatrix();
};

#endif QUATERNIONS_H