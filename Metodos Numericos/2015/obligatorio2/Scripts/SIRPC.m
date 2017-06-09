function [S,I,R] = SIRPC(So,Io,Ro,b,a,t,n)
 

%asigancion de datos iniciales
  
  N=So+Io+Ro;
  
  h=t/n;
  S(1)=So/N;
  I(1)=Io/N;
  R(1)=Ro/N;   
 
 for j=1:n
 % ver http://calculuslab.deltacollege.edu/ODE/7-C-2/7-C-2-h.html  
 %y' = f(x, y)
 %yn + (h/2) (f(xn, yn) + f(xn + h, yn +  h f(xn, yn))) 

  [dS,dI,dR] = derivada(S(j),I(j),R(j),a,b); %f(xn, yn)
 
  [s,i,r]=Predictor2(S(j),I(j),R(j),a,b,h); %f(xn + h, yn +  h f(xn, yn))
 
 
    S(j+1)=S(j)+h*0.5*(dS+s);
	I(j+1)=I(j)+h*0.5*(dI+i);
    R(j+1)=R(j)+h*0.5*(dR+r);
    
end

tp=0:h:t;



hold on
plot(tp,S,'r');

plot(tp,I,'g');

plot(tp,R);
legend('Suceptibles','Infectados','Retirados')
xlabel('Tiempo')
ylabel('Poblacion')
hold off

end

function [s,i,r] = Predictor2(Sn,In,Rn,a,b,h)
%Paso actual mas h por la derivada en el punto
    [dS,dI,dR] = derivada(Sn,In,Rn,a,b);
    
    s=Sn +h*(dS);
    i=In +h*(dI);
    r=Rn +h*(dR);
    
    s=dS;
    i=dI;
    r=dR;
end

%las derivadas del paso actual
function [dS,dI,dR] = derivada(Sn,In,Rn,a,b)
    dS=(-b*Sn*In);
    dI=(+In*(b*Sn-a));
    dR=(+a*In);
end