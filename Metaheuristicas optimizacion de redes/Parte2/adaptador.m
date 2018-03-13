% Funcion encargada de adaptar los datos de entrada a estructuras propias 

function [mAdy,vecInd] = adaptador(nodos,aristas)
  
  if (min(size(nodos))!=1)
    error ("nodos debe ser vector fila o columna");   
  endif

  dim=max(size(nodos));
  mAdy=zeros(dim);
  
  if (size(aristas)(2)!=2)
    error ("Aristas debe ser 2 columnas ");   
  endif

  dim=size(aristas)(1);
  vecInd=aristas;
  
  for (i=1:dim)
    mAdy(vecInd(i,1),vecInd(i,2))=i;
    mAdy(vecInd(i,2),vecInd(i,1))=i+dim;
  endfor
endfunction
  