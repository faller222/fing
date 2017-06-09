@datos = ();
$actu=";;;;;";
$ante=";;;;;";
$head="";
$OUTPUT_FIELD_SEPARATOR = '\n';
while(<>){
	#Empresa;Origen;Depto.Origen;Destino;Depto.Destino;H.Salida;H.Llegada;Recorrido;Dias;Lugar;Depto.Lugar;Hora;F.Inicio;F.Fin	
	/^([^;]+);([^;]+);[^;]+;([^;]+);[^;]+;([^;]+);([^;]+);[^;]+;([^;]+);([^;]+);([^;]+);([^;]+);[^;]+;[^;]+/;
		
	$emp=$1;
	$org=$2;
	$dest=$3;
	$hsda=$4;
	$hLlda=$5;
	$dias=$6;
	$lug=$7;
	$dlug=$8;
	$hlug=$9;
	
	$actu="$emp;$org;$dest;$hsda;$hLlda;$dias";
	
	if($actu ne $ante){#si dnd estoy parado es distinto al anterior
		$ante=$actu;
		if($head ne ""){#y no lo he imprimido lo imprimo			
			print($head);	
			print @datos;
			print("\n");
			#limpio
			@datos=();
			$head="";
		}
	}
	
	if($org=~/^MONTEVIDEO/ && $dest=~/^COLONIA/){
		print($_);			
		$head="\n$emp-$org-$dest-$dias\n";				
		push(@datos,"\t$hlug-$dlug-$lug\n");								
	}
			
}