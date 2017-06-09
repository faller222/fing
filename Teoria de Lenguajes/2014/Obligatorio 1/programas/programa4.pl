print("EMPRESA                       |DESTINO                       |H.SAL|H.LLE|DIAS\n");
while(<>){
	#Empresa;Origen;Depto.Origen;Destino;Depto.Destino;H.Salida;H.Llegada;Recorrido;Dias;Lugar;Depto.Lugar;Hora;F.Inicio;F.Fin	
	/^([^;]+);([^;]+);[^;]+;([^;]+);[^;]+;([^;]+);([^;]+);[^;]+;([^;]+);([^;]+);[^;]+;([^;]+);[^;]+;[^;]+/;
	$emp=$1;
	$org=$2;
	$dest=$3;
	$hsda=$4;
	$hLlda=$8;
	$dias=$6;
	$lug=$7;
	if($org=~/^MONTEVIDEO/){
		if($lug=~/^SAN\sJOSE/){		
			if($dias=~/(Do|Fe)/){
				$hsda=~/^(\d{2}):(\d{2})/;#parseo la hora Salida
				if(($1>=1)&&(($1<13)||(($1==13)&&($2<=30)))){ 
						printf("%-30s",$emp);						
						print("|");
						printf ("%-30s",$dest);
						print("|$hsda\|$hLlda\|$dias\n");												
				}
			}
		}
	}		
	
}