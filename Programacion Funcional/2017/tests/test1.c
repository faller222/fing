#include <stdio.h>
int _f(int _x,int _y){
int _let1(int _x){
int _let0(int _y){
return ((_y + 2)); };
return ((_x==_y)?_let0(30):10); };
return (_let1((_x>10))); };
int _g(int _x){
return ((_f(_x,0) + _f((_x * 2),1))); };
int main() {
printf("%d\n",_g(10)); }
