program test9;
var i	 : integer;
    j	 : integer;
    num	 : integer;
    size : integer;  
    bin : array [1..10] of integer;
begin
   size := 10;
   readln(num);

   i := 0;
   while not (num = 0) and (i < size) do
   begin
      i := i + 1;
      if (num mod 2) = 0 then
      begin
	 bin[i] := 0
      end
      else
      begin
	 bin[i] := 1
      end;
      num := num div 2
   end;

   for j := i to size do
   begin
      bin[j] := -1
   end;

   for i := 0 to size-1 do
   begin
      writeln(bin[size - i])
   end

end.
