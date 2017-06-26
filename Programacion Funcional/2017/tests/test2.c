#include <stdio.h>
int _f(int _x){
return ((_x + 2)); };
int main() {
printf("%d\n",((((_f(1) + 2) + 3) + 100) / ((4 + _f(2)) - _f(3)))); }
