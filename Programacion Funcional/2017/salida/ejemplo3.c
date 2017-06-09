#include <stdio.h>
int _foo(int _x,int _y){
return (_y?(_x * _x):(_x + 2)); };
int main() {
printf("%d\n",((24 + _foo(2,1)) + _foo(3,0))); }
