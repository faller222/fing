#include <stdio.h>
int _f(int _x,int _b,int _y,int _c){
return (_b?_x:_c?_y:(_x + _y)); };
int _g(int _x,int _y){
return (((_x * 2)<=(_y / 2))); };
int main() {
printf("%d\n",_f(10,_g(10,20),20,_g(10,40))); }
