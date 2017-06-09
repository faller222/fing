program ejemplo3;
var i : integer;
    x : integer;
begin
   readln(x);
   
   if x < 1 then
   begin
      writeln(0)
   end
   else
   begin
      for i := 1 to x do
      begin
	 writeln(i)
      end	 
   end
end.
