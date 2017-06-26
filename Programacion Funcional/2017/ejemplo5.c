#include <stdio.h>
int _fact(int _x){
return ((_x==0)?1:(_x * _fact((_x - 1)))); };
int main() {
printf("%d\n",_fact(4)); }
