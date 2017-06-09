program test10;
var low	 : integer;
    high : integer;
    mid	 : integer; 
    val	 : integer;
    i	 : integer;
    size : integer;  
    nums : array [1..10] of integer;
begin
   size := 10;
   for i := 1 to size do
   begin
      readln(val);
      nums[i] := val
   end;

   readln(val);

   low  := 0;
   high := size;

   while (low < high) or (low = high) do
   begin
      mid := (low + high) div 2;
      
      if nums[mid] < val then
      begin
	 low := mid + 1
      end
      else
      begin
	 high := mid - 1
      end
   end;

   writeln(low)
end.
