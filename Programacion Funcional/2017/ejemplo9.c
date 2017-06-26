#include <stdio.h>
int _f(int _x,int _y){
return (_x?(_x + _y):(_x==1)); };
int main() {
printf("%d\n",(0==_f(1,(2 + (1 * 4)),5))); }
