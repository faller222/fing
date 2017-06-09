program test4;
var x : integer;
    i : integer;
begin
   x := 2;
   i := 1;
   while (i < 10) or (i = 10) do
   begin
      x := (x + 2) * 10;
      i := i + 1
   end;
   x := x - 10
end.
