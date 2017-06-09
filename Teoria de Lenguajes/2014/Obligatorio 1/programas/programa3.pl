while(<>){
	if(/^[^\d]*(598)?[^\d]*(\d{4})[^\d]*(\d{4})[^\d,#]*(\#[^\d]*(\d*)[^\d]*)?/){
		#print($_);
		#print("\n");
		print("+598 ");
		print($2);
		print("-");
		print($3);
		$int=$5;
		if($5=~/^\d+/){
			print(" # ");     
			print($int);
		}
		print("\n");
	}
}