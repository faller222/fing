% Funcion encargada de adaptar los datos de entrada a estructuras propias

function [xmin, fmin, status, extra] = toGLPK(mAdy, vecInd, costos, especiales,sense=1)

  cantNodos=max(size(mAdy));
  cantAristas=max(size(vecInd));
  cantVariables=2*cantAristas;
  
  newCostos=[costos;costos];

  matriz=zeros(0,cantVariables);
  b=zeros(0,0);

  lb = zeros(0,0);
  ub = zeros(0,0);

  vartype="";
  ctype = "";
  % If SENSE is 1, the problem is a minimization.
  % sense = 1;

  % Error and warning messages only are shown
  param.msglev = 1;
  param.itlim = 10000;

  % balance de flujo en cada nodo
  for nodo=1:cantNodos
    if (nodo>max(size(especiales))) || (especiales(nodo,2)!=0)
      
      vector=zeros(1,cantVariables);

      for i=1:cantNodos
        if (mAdy(nodo,i)!=0)
          %flujo entrante
          vector(mAdy(nodo,i))=1;
          %menos flujo saliente
          vector(mAdy(i,nodo))=-1;
        endif
      endfor
      
      matriz = [ matriz ; vector] ;
      if (nodo<=max(size(especiales))) && (especiales(nodo,2)>0) 
        % Igualar estas a cantidad de caminos de las fuentes
        b = [ b ; especiales(nodo,2)];
      else
        % Igualar estas a cantidad de caminos de las fuentes
        b = [ b ; 0];
      endif
      % Balance de flujo es igualdad estricta
      ctype=strcat (ctype,"S");
    endif
  endfor
  % end/balance de flujo para cada nodo

  % Construyendo las caracteristicas de las variables
  for arista=1:cantVariables
    vartype=strcat (vartype,"I");
    lb = [ lb ; 0];
    ub = [ ub ; 1];
  endfor

  % Calculo con glpk
  [xmin, fmin, status, extra] =  glpk (newCostos, matriz, b, lb, ub, ctype, vartype, sense, param);
  