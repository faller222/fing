program test3err;
var x : integer;
    i : integer;
    x : boolean;
    i : boolean; 
begin
   x := 2 + true;
   for i := 1 to 10 do
   begin
      x := (x + 2) * (true and false)
   end;
   x := x - 10
end.
