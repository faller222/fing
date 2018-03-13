% Funcion encargada de adaptar los datos de entrada a estructuras propias 

function [xmin, fmin, status, extra] = toGLPK(mAdy, vecInd, costos)
%function [newCostos, matriz, b, lb, ub, ctype, vartype, sense, param] = toGLPK(mAdy, vecInd, costos)
  
  cantFuetes=4;
  newCostos=[zeros(size(costos)(1)*cantFuetes*2,1);costos];
  
  especiales=[1,0;2,0;3,0;4,2;5,2;6,3;7,2];
  
  cantNodos=max(size(mAdy));
  cantAristas=max(size(vecInd));
  cantVariables=cantAristas*(cantFuetes*2+1);
  
  matriz=zeros(0,cantVariables);
  b=zeros(0,0);
  
  lb = zeros(0,0);
  ub = zeros(0,0);
  
  vartype="";
  ctype = "";
  % If SENSE is 1, the problem is a minimization.
  sense = 1;
  
  % Error and warning messages only are shown
  param.msglev = 1;
  param.itlim = 10000;
   
  
  for (C=0:cantFuetes-1)
    offset=2*cantAristas*C;
    
    % balance de flujo para cada nodo (no sumidero)
    for (nodo=4:cantNodos)
      vector=zeros(1,cantVariables);
      agrego=0;
      
      for (i=1:cantNodos)
        if (mAdy(nodo,i)!=0)
          agrego++;
          %flujo entrante
          vector(offset+mAdy(nodo,i))=1;
          %menos flujo saliente
          vector(offset+mAdy(i,nodo))=-1;   
        endif
      endfor
    
      if (agrego>0)
        %balance de flujo para fuentes
        if ((C+4)==nodo)
          % Si soy fuente de este camino       
          % Igualar estas a cantidad de caminos de las fuentes
          b = [ b ; especiales(nodo,2)];
        else
          % Si soy un nodo comun en este camino      
          % Igualar estas a 0
          b = [ b ; 0];
        endif
        
        matriz = [ matriz ; vector] ;
        % Balance de flujo es igualdad estricta
        ctype=strcat (ctype,"S");
       endif 
    endfor%Nodos
  endfor %Caminos
    
  
  for (arista=1:cantAristas) 
      
      offsetFinal=cantFuetes*2*cantAristas+arista;
      for (C=0:(cantFuetes*2-1))
        offset=cantAristas*C+arista; 
        
        vector=zeros(1,cantVariables);
        vector(offsetFinal)=-1;
        vector(offset)=1;
        
        matriz = [ matriz ; vector] ;
        % Igualar estas a 0
        b = [ b ; 0];
        % las aristas pueden ser usadas o no
        ctype=strcat (ctype,"U");  
      endfor  
  endfor
  
  % Construyendo las caracteristicas de las variables
  for (arista=1:cantVariables)
    vartype=strcat (vartype,"I");
    lb = [ lb ; 0];
    ub = [ ub ; 1];
  endfor


[xmin, fmin, status, extra] =  glpk (newCostos, matriz, b, lb, ub, ctype, vartype, sense, param);
costoMin=fmin
