del *.class
SET No=5
SET PATH=D:\jdk1.6.0_32\bin;%PATH%
SET CLASSPATH=.;D:\teoleng\entorno\java_cup_jlex;%CLASSPATH%

color 0E
echo off
cls

set /p No=Enter Test a realizar: 

color 09
echo 1) Generando Parser
java java_cup.Main -expect 0 < Sintactico.sin
echo. 
echo.
color 0A
echo. 
echo 2) Compilando
javac sym.java parser.java Principal.java
echo. 
echo. 
color 0E
echo. 
echo 3) Ejecutando Test%No%
java Principal < entradas/test%No%.txt > salidas/test%No%.txt
echo. 
echo. 
color 07
echo. 
echo 4) Comparando Salidas
diff  salidas/test%No%.txt salidas_oficiales/test%No%.txt
echo. 
echo. 
pause
del *.class
