echo off
SET num="1"
cls
echo ¿Cual Vas a Probar?
set /P num=
perl programas\programa%num%.pl < entradas\entrada%num%.txt > salidas\salida%num%.txt

diff  salidas\salida%num%.txt  salidas_oficiales\salida%num%.txt


echo Fin
pause