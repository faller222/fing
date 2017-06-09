function [Ees,Eei,Eer,Epcs,Epci,Epcr]=Error(So,Io,Ro,t)

N=So+Io+Ro;
a=0.125;
b=0.9;
%resolucion usando metodos implementados en octave
tl=0:0.01:t;
pasos=length(tl);
pazoz=1:pasos;

	
function sir=f(x,t);
a=0.125;
b=0.9;
sir(1)=-b*(x(1))*(x(2));
sir(2)=(x(2))*(b*(x(1))-a);
sir(3)=a*(x(2));
endfunction


x=lsode("f",[So/N,Io/N,Ro/N],tl);


	[Se,Ie,Re] = SIR_Euler(So,Io,Ro,b,a,t,length(tl));

	[Spc,Ipc,Rpc] = SIR_PC(So,Io,Ro,b,a,t,length(tl));

tl(3002)=30+0.01;



for k=1:pasos


Ees(k)=abs(x(k,1)-Se(k));
Eei(k)=abs(x(k,2)-Ie(k));
Eer(k)=abs(x(k,3)-Re(k));

Epcs(k)=abs(x(k,1)-Spc(k));
Epci(k)=abs(x(k,2)-Ipc(k)); 
Epcr(k)=abs(x(k,3)-Rpc(k));


endfor


figure(1)
hold on
plot(pazoz,Ees,'r');
plot(pazoz,Epcs);
title('Error en Suceptibles')
xlabel('Paso')
ylabel('Error')
legend('Metodo de Euler','Metodo PC')



figure(2)
hold on
plot(pazoz,Eei,'r');
plot(pazoz,Epci);
title('Error en Infectados')
xlabel('Paso')
ylabel('Error')
legend('Metodo de Euler','Metodo PC')



figure(3)
hold on
plot(pazoz,Eer,'r');
plot(pazoz,Epcr);
title('Error en Retirados')
xlabel('Paso')
ylabel('Error')
legend('Metodo de Euler','Metodo PC')

endfunction

