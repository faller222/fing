function [S,I,R] = SIR_PC(So,Io,Ro,b,a,t,n)


%asigancion de datos iniciales
  
  
N=So+Io+Ro;
  
 
h=t/n;
 
S(1)=So/N;
  %normalizacion de los valores
I(1)=Io/N;
 
R(1)=Ro/N;



tp=0:h:t;




for k=1:n

 
	%calculo de la derivada en la posicion actual
	
    fs=-b*S(k)*I(k);

	fi=I(k)*(b*S(k)-a);

	fr=a*I(k);

	

	%prediccion (EULER)

	
	s=S(k)+h*fs;
	
	i=I(k)+h*fi;

	r=R(k)+h*fr;

    

	%derivada en el punto siguiente
    
    
	ds=-b*s*i;
    
	di=i*(b*s-a);
    
	dr=a*i;
    
	
	
	%correccion


		S(k+1)=S(k)+(h*0.5)*(fs+ds);
	
		I(k+1)=I(k)+(h*0.5)*(fi+di);
	
		R(k+1)=R(k)+(h*0.5)*(fr+dr);	


    end


	








