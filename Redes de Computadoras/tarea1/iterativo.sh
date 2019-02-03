#!/bin/bash
buscarIp()
{
		#se ejecuta el comando Dig
		com_dig="@$s1 +norecurse $s2"
		echo "$com_dig"
		#com_dig="@$1 +norecurse $2"

		#Analizo si es de tipo A
		resul_dig=$(dig $com_dig)
		es_A=$(echo "$resul_dig" | awk '/;; ANSWER SECTION:/,/^$/')
		tipo_esA=$(echo "$es_A" | awk '$1 !~ /;;/{ print $4}')

		IFS=' ' read -r -a tipos_esA <<< $tipo_esA

		url_dig_autho=$s2

		if [ -n "$es_A" -a "${tipos_esA[0]}" == "A" ]
		then
        	encontro_ip=1
        	ip=$(echo "$es_A" | awk '$1 !~ /;;/{ print $5}')
		else
			es_AUTHO=$(echo "$resul_dig"| awk '/;; AUTHORITY SECTION:/,/^$/')
			if [ -n "$es_AUTHO" ]
			then
        		termino=0
  	      		if [ $p -le $cant_param_url ]
  	      		then	
					((++p))
					url_dig_autho=$url_dig_con
				fi
        		url_dig_autho=$url_dig_con
        		#Recorro todos las URL que me devuelve
        		tipo_autho=$(echo "$es_AUTHO" | awk '$1 !~ /;;/{ print $4}')
				url_autho=$(echo "$es_AUTHO" | awk '$1 !~ /;;/{ print $5}')

				IFS=' ' read -r -a tipos_autho <<< $tipo_autho
				IFS=' ' read -r -a urls_autho <<< $url_autho

				cant_serv_autho=${#tipos_autho[*]}
				serv_autho_consul=1
				while [ $encontro_ip -eq 0 -a $serv_autho_consul -le $cant_serv_autho ]
					do
						url_dig_autho=$s2
						if [ $p -le $cant_param_url ]
  	      				then	
							url_ag_autho="${url[$cant_param_url-$p]}"
							url_ag_con="${url[$cant_param_url-$p]}"
							url_dig_autho="$url_ag_autho.$url_dig_autho"
						fi

						if [ "${tipos_autho[$serv_autho_consl]}" == "NS" ]
						then
							es_addi=$(echo "$resul_dig" | awk '/;; ADDITIONAL SECTION:/,/^$/')
							y=${urls_autho[$serv_autho_consl]}

							if [ -n "$es_addi" ]
							then
								ip_auth=$(echo "$es_addi" | awk '$1 !~ /;;/ && $4 != "AAAA"{print $1 " " $5}')
								ip_auth=$(echo "$ip_auth" | awk "/$y/,/^$/")
								ip_auth=$(echo "$ip_auth" | awk '{print $2}')
								IFS=' ' read -r -a parametros <<< $ip_auth

								s1=${parametros[0]}
								s2=$url_dig_autho
								buscarIp 
								#buscarIp ${parametros[0]} $url_dig_autho
							else
 
								final=0
								buscarIp_serv_raiz $y $url_dig_autho

								url_dig_autho=$aux_y
								final=1

								s1=$reg_A
								s2=$url_dig_autho
								buscarIp 

								#buscarIp $reg_A $url_dig_autho
							fi
						else
							if [ "${tipos_autho[$serv_autho_consl]}" == "SOA" ]
								then
								if [ $p -le $cant_param_url ]
	  	      					then

	  	      						s1=$s1
									s2=$url_dig_autho
									buscarIp 
	  	      						#buscarIp 
	  	      					else
									termino=1
									es_SOA=$es_AUTHO
								fi
							fi
						fi
						((serv_autho_consul++))
					done
			fi
		fi

}

buscarIp_serv_raiz(){

#conservo variables

aux_y=$s2


#servidores raiz los obtuvimos desde la pagina https://www.iana.org/domains/root/servers
ips_servidores_raiz=(198.41.0.4)
	#199.9.14.201 192.33.4.12 199.7.91.13 192.203.230.10 192.5.5.241
	#						 192.112.36.4 198.97.190.53 192.36.148.17 192.58.128.30 193.0.14.129 199.7.83.42 202.12.27.33)



cant_serv_raiz=${#ips_servidores_raiz[*]}
serv_raiz_consul=0
encontro_ip=0

#Obtener la url y los parametros
ruta=$1
aux_url=$(echo "$ruta" | awk '{ gsub(/www./,""); print }')
aux_url=$(echo "$aux_url" | awk '{ gsub(/\./," "); print }')

IFS=' ' read -r -a url <<< "$aux_url"
cant_param_url=${#url[*]}

p=1
url_dig_con=""


while [ $encontro_ip -eq 0 -a $serv_raiz_consul -lt $cant_serv_raiz ]
	do
		if [ $p -le $cant_param_url ]
  	    	then	
				url_ag_con="${url[$cant_param_url-$p]}"
				url_dig_con="$url_ag_con.$url_dig_con"
			fi

		ip_serv="${ips_servidores_raiz[$serv_raiz_consul]}"

		s1=$ip_serv
		s2=$url_dig_con
		buscarIp 
		#buscarIp $ip_serv $url_dig_con

		((serv_raiz_consul++))

		
	done

if [ $final -eq 1 ]
then

	if [ $encontro_ip -eq 0 ]
	then
		if [ $termino -eq 1 ]
		then
			reg_SOA=$(echo "$es_SOA" | awk '$1 !~ /;;/{ print $4 " "$5" "$6}')
			echo "NO se encontro la IP"
			echo "Se obtuvo la siguiente informacion: $reg_SOA"
		else
			echo "NO SE ENCONTRO LA IP"
		fi
	else
		reg_A=$(echo "$es_A" | awk '$1 !~ /;;/{ print $5}')
		echo "SE ENCONTRO LA IP" 
		echo "IP: $reg_A"
	fi
else
	reg_A=$(echo "$es_A" | awk '$1 !~ /;;/{ print $5}')
fi

}


final=1
echo 'Se realiza la consulta iterativa usando dig'
#read ruta
buscarIp_serv_raiz $1