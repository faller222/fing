function [] = graficar(q4="0",q5="0",q6="0",q7="0")

%cargo los archivos
n=int32 (load ("../datos/nodos.txt"));


a4=[];
a5=[];
a6=[];
a7=[];
if(q4!="0")
  a4=int32 (load (["../ind/parte3-4-" q4 ".txt"]).estructura);
endif
if(q5!="0")
a5=int32 (load (["../ind/parte3-5-" q5 ".txt"]).estructura);
endif
if(q6!="0")
a6=int32 (load (["../ind/parte3-6-" q6 ".txt"]).estructura);
endif
if(q7!="0")
a7=int32 (load (["../ind/parte3-7-" q7 ".txt"]).estructura);
endif


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