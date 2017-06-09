program test5err;
var x : integer;
    b : boolean;
begin
   readln(x);
   b := x < 10;
   if x then
   begin
      x := x + b
   end
   else
   begin
      y := x - 10
   end
end.
