program test6err;
var i  : integer;
    j  : integer;
    b1 : boolean;
    b2 : boolean;
    a  : array [1..5] of array [1..5] of boolean;
begin
   b1 := true;
   b2 := b1;

   for b2 := false to b1 do
   begin
      b1 := not a;
      for j := 1 to 5 do
      begin
	 b2 := not b2;
	 a[i] := not (b1 and b2)
      end
   end
end.
