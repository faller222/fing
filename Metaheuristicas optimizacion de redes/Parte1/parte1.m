function [] = parte1(which=1)

  % Datos comunes a las 3 problemas planteados
 [ind,coord,aristas,costos,delays]=carga("../datos/nodos.txt","../datos/aristas.txt");
 [mAdy,vecInd] = adaptador(ind,aristas);

  %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
  % Por Costos
  %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
  if (which==1)
    c=costos;
    especiales=[1,0;2,0;3,0;4,2;5,2;6,3;7,2];
    [xmin, fmin, status, extra]=toGLPK(mAdy, vecInd, c,especiales);

    % Transforma el vector de soluciones en una lista de aristas
    estructura=transformar(xmin,vecInd);
    showCostos(xmin, costos, delays);
    % Se escrien las aristas en un archivo
    save -text ../out/parte1Costos.txt estructura;
    graficar(estructura,coord);
  endif

  %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
  % Por Delays
  %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
  if (which==2)
    c=delays;
    especiales=[1,0;2,0;3,0;4,2;5,2;6,3;7,2];
    [xmin, fmin, status, extra]=toGLPK(mAdy, vecInd, c,especiales);

    % Procesamiento y graficas
    estructura=transformar(xmin,vecInd);
    showCostos(xmin, costos, delays);
    save -text ../out/parte1Delays.txt estructura;
    graficar(estructura,coord);
  endif

  %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
  % Camino mas corto
  %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
  if (which==3)
    c=costos;
    especiales=[1,0;2,0;3,0;4,1;5,1;6,1;7,1];
    [xmin, fmin, status, extra]=toGLPK(mAdy, vecInd, c,especiales);

    % Procesamiento y graficas
    estructura=transformar(xmin,vecInd);
    showCostos(xmin, costos, delays);
    save -text ../out/parte1CostoCMC.txt estructura;
    graficar(estructura,coord);
  endif
endfunction
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% FUNCIONES AUXILIARES - transformar
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
function [out] = transformar(in,vecInd)
  % Se transforma el vector de soluciones en una lista de aristas
  m=max(size(vecInd));
  out=zeros(0,2);
  for x=1:size(in)(1)
    if (in(x)==1)
      tmp=zeros(1,2);
      tmp(1)=vecInd(mod(x,m),1);
      tmp(2)=vecInd(mod(x,m),2);
      out=[out;tmp];
    endif
  endfor
endfunction
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% FUNCIONES AUXILIARES - graficar
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
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% FUNCIONES AUXILIARES - costos
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
function [] = showCostos(in, costos, delays)
    Acostos=0;
    Adelays=0;
    m=max(size(costos));
  
    for x=1:size(in)(1)
      if (in(x)==1)
        Acostos=Acostos+costos(mod(x,m));
        Adelays=Adelays+delays(mod(x,m));
      endif
    endfor
    Acostos
    Adelays
endfunction
