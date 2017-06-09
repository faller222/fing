SET PATH=D:\jdk1.6.0_32\bin;%PATH%
SET CLASSPATH=.;D:\teoleng\entorno\java_cup_jlex;%CLASSPATH%
echo off
cls

echo Generando Parser
java java_cup.Main -expect 0 < Sintactico.sin

echo off
pause
