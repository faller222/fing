#include <stdio.h>
int _f(int _x,int _y,int _z){
int _let0(int _x){
return ((_x + 2)); };
int _let1(int _x){
return ((_x + 1)); };
int _let3(int _x){
int _let2(int _y){
return (((_x + _y) + _z)); };
return (_let2((_y + 4))); };
int _let4(int _z){
return (((_x + _y) + _z)); };
return ((_let3(_let1(_let0((_x + 3)))) + _let4(9))); };
int _g(int _x){
int _let0(int _x){
return ((_x + 3)); };
return (_let0((_x + 2))); };
int main() {
printf("%d\n",(_f(10,5,2) + _g(9))); }
