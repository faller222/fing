#include <stdio.h>
int _fun(int _x,int _y,int _b){
return ((_x>_y)?(_x!=(_y + _y)):_b); };
int main() {
printf("%d\n",((_fun(4,2,1)==_fun(2,2,0))!=_fun(5,2,0))?10:20); }
