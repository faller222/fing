program test8;
var i	 : integer;
    j	 : integer;
    tmp	 : integer; 
    ind	 : integer;
    size : integer;  
    nums : array [1..10] of integer;
begin
   size := 10;
   for i := 1 to size do
   begin
      readln(tmp);
      nums[i] := tmp
   end;

   for i := 2 to size do
   begin
      ind := nums[i];
      j   := i;
      while (1 < j) and (ind < nums[j-1]) do
      begin
	 nums[j] := nums[j-1];
	 j := j - 1
      end;
      nums[j] := ind
   end;

   for i := 1 to size do
   begin
      writeln(nums[i])
   end

end.
