% Funcion encargada de cargar los archivos de datos

function [ind,coord,aristas,costos,delays] = carga(nodosFileName,aristasFileName)

%cargo los archivos
n=int32 (load (nodosFileName));
if size(n)(2)!=3
    error ("Archivo de nodos debe tener 3 columnas");   
end
a=int32 (load (aristasFileName));
if size(a)(2)!=4
    error ("Archivo de aristas debe tener 4 columnas");   
end


%parseo los datos de aristas
aristas=a(:,1:2);
costos=a(:,3);
delays=a(:,4);


%parseo los datos de nodos
coord=n(:,2:3);
ind=n(:,1);

