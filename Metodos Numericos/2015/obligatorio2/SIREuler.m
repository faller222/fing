%obligatorio 2


%metodo de euler 

function [S, I, R] = SIREuler(So,Io,Ro,b,a,t,n)

%OBS: las unidades del tiempo "t" dependen de las unidades de las tasas de contaguio "a" y "b"

	N=So+Io+Ro;

	h=t/n;
	
	S(1)=So/N;
	I(1)=Io/N;
	R(1)=Ro/N;

	for i=1:n
	S(i+1)=S(i)-b*S(i)*I(i)*h;
	I(i+1)=I(i)+I(i)*(b*S(i)-a)*h;
	R(i+1)=R(i)+a*I(i)*h;
	endfor

endfunction



