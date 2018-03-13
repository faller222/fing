function [] = parte3(perturbacion=0,nodo=4)

  % Datos comunes a las 3 problemas planteados
 [ind,coord,aristas,costos,delays,especiales]=carga("../datos/nodos.txt",
                                                    "../datos/aristas.txt",
                                                    "../datos/cantCaminos.txt");
 [mAdy,vecInd] = adaptador(ind,aristas);

 %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
 %%         SIN PERTURBAR   %%%%%%
 %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
 
  % Llamo a GLPK
  [xmin, fmin, status, extra]=toGLPK(mAdy, vecInd, costos, especiales);
  % Procesamiento y graficas
  [estructura,delaysAsociados] = transformar(xmin,vecInd,delays);
  delaysNodos=zeros(1,7);
  delaysNodos(4)=getCostoCamino(4,ind,estructura,especiales,delaysAsociados,coord);
  delaysNodos(5)=getCostoCamino(5,ind,estructura,especiales,delaysAsociados,coord);
  delaysNodos(6)=getCostoCamino(6,ind,estructura,especiales,delaysAsociados,coord);
  delaysNodos(7)=getCostoCamino(7,ind,estructura,especiales,delaysAsociados,coord);
  delaysNodos
 %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
 %%         PERTURBADO      %%%%%%
 %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
 acumuladorAristas=[];
 cantOK=0;
  if perturbacion != 0
    %for inodo=4:7
      cantOK=0;
      cantERR=0;
      %for pp=1:50
        pertus=unifrnd((-perturbacion), perturbacion, size(delays))+1;
        perturbado=round(costos.*pertus);
      
        % Llamp a GLPK
        [xmin, fmin, status, extra]=toGLPK(mAdy, vecInd, perturbado, especiales);
        % Procesamiento y graficas
        try
          [estructura,delaysAsociados] = transformar(xmin,vecInd,delays);
          delayConPerturbaciones=getCostoCamino(nodo,ind,estructura,especiales,delaysAsociados,coord)
          if delaysNodos(nodo)>=delayConPerturbaciones
            %acumuladorAristas=[acumuladorAristas;estructura];
            %break;
            cantOK++;    
          endif
        catch  
            cantERR++;  
        end_try_catch
      %endfor
      %inodo
      cantOK
      %cantERR
    %endfor
    %save -text ../out/parte3Acu.txt acumuladorAristas;
  endif 
    
endfunction
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% FUNCIONES AUXILIARES - transformar
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
function [out,costo] = transformar(in,vecInd,costosOrig=[])
  % Se transforma el vector de soluciones en una lista de aristas
  m=max(size(vecInd));
  out=zeros(0,2);
  costo=[];
  for x=1:size(in)(1)
    if (in(x)==1)
      tmp=zeros(1,2);
      tmp(1)=vecInd(mod(x,m),1);
      tmp(2)=vecInd(mod(x,m),2);
      if size(costosOrig) > 0
        costo=[costo;costosOrig(mod(x,m))];
      endif  
      out=[out;tmp];
    endif
  endfor
endfunction
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% FUNCIONES AUXILIARES - getCostoCamino
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
function [delayMax] = getCostoCamino(fuente,ind,aristas,especiales,delay,coords)
  [mAdy,vecInd] = adaptador(ind,aristas);
  for i=1:size(especiales)(1)
    if i!=fuente && especiales(i,2)!=0
      especiales(i,2)=-1;  
    endif  
  endfor
  [xmin, fmin, status, extra]=toGLPK(mAdy, vecInd, delay, especiales);
  [estructura,delaysAsociados] = transformar(xmin,vecInd,delay);
  total=fmin;
  
  especiales(fuente,2)--;
  [mAdy,vecInd] = adaptador(ind,estructura);
  [xmin, fmin, status, extra]=toGLPK(mAdy, vecInd, delaysAsociados, especiales);
  delayMax=total-fmin;
  %[estructura,costosRecortados] = transformar(xmin,vecInd);
  graficar(estructura,coords)
  save -text ../ind/parte3-7-3.txt estructura
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