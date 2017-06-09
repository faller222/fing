program test7err;
var i	 : integer;
    j	 : boolean;
    tmp	 : integer;
    size : integer;  
    nums : array [1..10] of integer;
begin
   size := 10;
   for i := 1 to size do
   begin
      readln(tmp);
      nums[i] := tmp
   end;

   for i := 0 to (size - 2) do
   begin
      for j := 2 to (size - i) do
      begin
         if nums[j] < nums[j-1] then
         begin
	    tmp       := nums[j-1];
	    nums[j-1] := nums[j];
	    nums      := tmp
	 end
         else
         begin
         end
      end
   end;

   for i := 1 to size do
   begin
      writeln(nums[i] < 10)
   end

end.
