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
 
[s,i,r]=Predictor(S(j+1),I(j+1),R(j+1),a,b,h);
endfor

endfunction