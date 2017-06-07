echo off
set Wimpor=start cmd /c "C:\Program Files\Java\jdk1.7.0_45\bin\wsimport"

set Params=-Xnocompile -keep -verbose

set Dire=http://localhost:20016/

set timeOut=timeout 1

cls

%Wimpor% %Params% %Dire%PBC?wsdl

%timeOut%

%Wimpor% %Params% %Dire%PVP?wsdl

%timeOut%

%Wimpor% %Params% %Dire%PIS?wsdl

%timeOut%

%Wimpor% %Params% %Dire%PIC?wsdl

%timeOut%

%Wimpor% %Params% %Dire%PIP?wsdl

%timeOut%

%Wimpor% %Params% %Dire%PRP?wsdl

%timeOut%

%Wimpor% %Params% %Dire%PIC?wsdl

%timeOut%

%Wimpor% %Params% %Dire%PAD?wsdl

%timeOut%

%Wimpor% %Params% %Dire%PPP?wsdl

%timeOut%

%Wimpor% %Params% %Dire%PCO?wsdl

%timeOut%

%Wimpor% %Params% %Dire%PAU?wsdl

%timeOut%

%Wimpor% %Params% %Dire%PAP?wsdl

%timeOut%

%Wimpor% %Params% %Dire%PAS?wsdl

%timeOut%

cls
