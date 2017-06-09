program test5;
var x : integer;
    b : boolean;
begin
   readln(x);
   b := x < 10;
   if b then
   begin
      x := x + 1
   end
   else
   begin
      x := x - 10
   end
end.
