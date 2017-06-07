PAU="PAU"
PIC="PIC"
PAS="PAS"
PAP="PAP"
PIS="PIS"
PRP="PRP"
PIP="PIP"
PVP="PVP"
PBC="PBC"
PCO="PCO"
PPP="PPP"
PAD="PAD"
#Address="pcunix145"
Address="localhost"
Puerto="20016"
clear
yes_no()
{
    while true
    do
        echo -e -n "\033[1m Enter yes or no: \033[0m"      
        read yesno
        case "$yesno" in
            y | Y | yes | YES | Yes) return 0;;
            n | N | no | NO | No) return 1;;
            *) echo "Answer y,Y,Yes,YES,Yes or n,N,no,NO,No";;
        esac
    done
}

echo -e -n "\033[1m Desea Cambiar la URL? \033[31m"

echo -e "Actual: http://$Address:$Puerto/ \033[0m"

if yes_no 
then
  clear
  echo -e -n "\033[1m Ingrese nombre de la maquina publicando servicios:"
  read Address
  clear
  echo -e -n "Ingrese el puerto: \033[0m"
  read Puerto
fi


for WS in $PAU $PIC $PAS $PAP $PIS $PRP $PIP $PVP $PBC $PCO $PPP $PAD
do
  clear
  echo -e  "\033[1m \033[31m Importando $WS \033[0m"
  wsimport -Xnocompile -keep -verbose "http://$Address:$Puerto/$WS?wsdl"
done
clear
echo -e "\033[5m \033[1m Fin... \033[0m"


