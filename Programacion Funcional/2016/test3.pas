program test3;
var x : integer;
    i : integer;
begin
   x := 2;
   for i := 1 to 10 do
   begin
      x := (x + 2) * 10
   end;
   x := x - 10
end.
