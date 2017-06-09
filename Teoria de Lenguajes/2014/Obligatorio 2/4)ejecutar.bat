SET PATH=D:\jdk1.6.0_32\bin;%PATH%
SET CLASSPATH=.;D:\teoleng\entorno\java_cup_jlex;%CLASSPATH%

echo off
cls
echo Ejecutando

java Principal < entradas/test0.txt > salidas/test0.txt
java Principal < entradas/test1.txt > salidas/test1.txt
java Principal < entradas/test2.txt > salidas/test2.txt
java Principal < entradas/test3.txt > salidas/test3.txt
java Principal < entradas/test4.txt > salidas/test4.txt
java Principal < entradas/test5.txt > salidas/test5.txt


pause
