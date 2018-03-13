function [] = graficar(q4="1",q5="1",q6="1",q7="1")

%cargo los archivos
n=int32 (load ("../datos/nodos.txt"));

a4=int32 (load (["../ind/parte3-4-" q4 ".txt"]).estructura);
a5=int32 (load (["../ind/parte3-5-" q5 ".txt"]).estructura);
a6=int32 (load (["../ind/parte3-6-" q6 ".txt"]).estructura);
a7=int32 (load (["../ind/parte3-7-" q7 ".txt"]).estructura);


aristas=[a4;a5;a6;a7];
%parseo los datos de nodos
coord=n(:,2:3);
ind=n(:,1);


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