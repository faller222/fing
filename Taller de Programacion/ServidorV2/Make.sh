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
pathTomcat="/ens/devel01/tpgr16/apache-tomcat-"

clear
echo "Eliminando archivos anteriores"
ant clean

echo ""
echo ""
echo "Desea correr el WsImport"
if yes_no 
then
  cd src/java
  clear
  sh WsImport.sh
  cd ../..
fi

echo ""
echo ""
echo "Compilando"
ant dist

clear
echo -e "Que numero de usuario sos? tprogXXX"
read user

echo "Desea levantar el Tomcat?"
if yes_no 
then  
   sh "$pathTomcat$user/bin/startup.sh"
fi

echo ""
echo ""
echo "Deploy"

cp dist/ServidorWebV2.war "$pathTomcat$user/webapps"


echo ""
echo ""
echo "Desea ejecutar la aplicacion?"
if yes_no 
then
  clear
   echo -e "En que maquina estas? pcunixXXX"
   read num
  firefox -new-tab "https://pcunix$num:8443"
fi
