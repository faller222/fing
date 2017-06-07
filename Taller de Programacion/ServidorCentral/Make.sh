yes_no()
{
    while true
    do
        echo -n "Enter yes or no:"
        read yesno
        case "$yesno" in
            y | Y | yes | YES | Yes) return 0;;
            n | N | no | NO | No) return 1;;
            *) echo "Answer y,Y,Yes,YES,Yes or n,N,no,NO,No";;
        esac
    done
}


clear

echo "Eliminando archivos anteriores"

ant clean

clear

echo "Compilando"
ant jar

echo ""
echo ""
echo "Desea ejecutar la aplicacion"
if yes_no 
then
  clear
  ant run
fi
