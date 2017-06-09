while(<>){
 if(/^(http(s)?\:\/\/)?([^\/,:,\r,\n]*).*/){	
	#print($3);
	$dom=$3;
		if($dom=~/(.com(.uy)?\r?)$/){
			print($dom);
			print("\n");			
		}else{
			#print(" no ->");
			#print($dom);
			#print("\n");
		}	
	}
}