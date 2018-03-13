
function [] = parte2(which=1)
  
  % Datos comunes a las 3 problemas planteados
 [ind,coord,aristas,costos,delays]=carga("../datos/nodos.txt","../datos/aristas.txt");
 [mAdy,vecInd] = adaptador(ind,aristas);
 
  %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
  %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
  % Por Costos
  if (which==1)
    c=costos; 
    [xmin, fmin, status, extra]=toGLPK(mAdy, vecInd, c);
    % transforma el vector de soluciones en una lista de aristas
    aristasRes=transformar(xmin,vecInd);
    % escribo las aristas en un archivo 
    save -text ../out/parte2Costos.txt aristasRes;
    graficar(aristasRes,coord);
  endif
  
  %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
  %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
  % Por Delays
  if (which==2)
    c=delays;
    [xmin, fmin, status, extra]=toGLPK(mAdy, vecInd, c);
    % transforma el vector de soluciones en una lista de aristas
    aristasRes=transformar(xmin,vecInd);
    % escribo las aristas en un archivo 
    save -text ../out/parte2Delays.txt aristasRes;
    graficar(aristasRes,coord);
  endif
endfunction
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% 
function [out] = transformar(in,vecInd)
  % transforma el vector de soluciones en una lista de aristas
  offset=max(size(vecInd))*8;
  
  out=zeros(0,2);
  for (x=1:max(size(vecInd)))  
    if (in(offset+x)==1)
      tmp=zeros(1,2);
      tmp(1)=vecInd(x,1);
      tmp(2)=vecInd(x,2);
      out=[out;tmp];
    endif
  endfor
endfunction

  
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% 

function [] = graficar(aristas,coord)
  close
  hold on
    plot(coord(8:end,1),coord(8:end,2),"marker","*","color","y","linestyle","none")
    plot(coord(1:3,1),coord(1:3,2),"marker","*","color","b","linestyle","none")
    plot(coord(4:7,1),coord(4:7,2),"marker","*","color","r","linestyle","none")
    
    for x=1:size(aristas)(1)  
      a=aristas(x,1);
      b=aristas(x,2);            
      plot(coord([a,b],1),coord([a,b],2),"marker","none","color","b","linestyle","-")
    endfor
   hold off
endfunction
