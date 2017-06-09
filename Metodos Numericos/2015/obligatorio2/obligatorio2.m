%obligatorio 2


%metodo de euler 

function [S, I, R] = SIR-Euler(So,Io,Ro,a,b,t,n)

%OBS: las unidades del tiempo "t" dependen de las unidades de las tasas de contaguio "a" y "b"



	h=t/n;
	
	S(1)=So;
	I(1)=Io;
	R(1)=Ro;

	for i=1:n
	S(i+1)=S(i)-b*S(i)*I(i)*h;
	I(i+1)=I(i)+I(i)*(b*S(i)-a)*h;
	R(i+1)=R(i)+a*I(i)*h;
	endfor

endfunction

function [s,i,r] = Predictor(So,Io,Ro,a,b,h)

s=So-b*So*Io*h;
i=Io+Io*(b*So-a)*h;
r=Ro+a*Io*h;
endfunction

function [S,I,R] = SIR-PC(So,Io,Ro,a,b,t,n)

%asigancion de datos iniciales

  h=t/n;
  S(1)=So;
	I(1)=Io;
	R(1)=Ro;

  s=So;
  i=Io;
  r=Ro;
  
 for j=1:n
  S(j+1)=S(j)+(h*0.5)*(s+b*S(j)*I(j)*h);
  I(j+1)=I(j)+(h*0.5)*(i+I(j)*(b*S(j)-a)*h);
  R(j+1)=R(j)+(h*0.5)*(r+a*I(j)*h);
 
Predictor(S(j+1),I(j+1),R(j+1),a,b,h);
endfor

endfunction