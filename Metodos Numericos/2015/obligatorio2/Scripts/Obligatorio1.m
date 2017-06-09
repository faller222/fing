close all
clear all
alpha=1.2;
cantPasos=6000;
gamma=0.4;
X=[1/3; 1/3; 1/3];
Y=X(:,1);
Salto=0.005;
h=1;

while alpha<5

    for i=1:cantPasos
    % EULER
    G1(i)=alpha*(X(1,i)+X(2,i))-1;
    G2(i)=(alpha-1)*X(1,i)/(1-X(2,i));
    G3(i)=alpha*X(1,i);
    
    Gh(i)=max(G1(i),G2(i));
    Gh(i)=max(Gh(i),G3(i));
    
    Fx(1,i)=(-gamma)*X(1,i)*(Gh(i)-G1(i));
    Fx(2,i)=(-gamma)*X(2,i)*(Gh(i)-G2(i));
    Fx(3,i)=(-gamma)*X(3,i)*(Gh(i)-G3(i));
    
    
    G(i)=(alpha-1)*transpose(X(:,i))*[1 1 1;1 X(1,i)/(1-X(2,i)) 0;0 0 0]*X(:,i);
    
    
    
        
    for j=1:3
        X(j,i+1)=X(j,i)+Salto*Fx(j,i);
    end
    
    if G1==Gh
        X(1,i+1)=X(1,i)-Salto*(Fx(2,i)+Fx(3,i));
    end
    if G2==Gh
        X(2,i+1)=X(2,i)-Salto*(Fx(1,i)+Fx(3,i));
       
    end
    if G3==Gh
        X(3,i+1)=X(3,i)-Salto*(Fx(2,i)+Fx(1,i));
        
    end
            
    
    %Trapecio
    J1(i)=alpha*(Y(1,i)+Y(2,i))-1;
    J2(i)=(alpha-1)*Y(1,i)/(1-Y(2,i));
    J3(i)=alpha*Y(1,i);
    
    Jh(i)=max(J1(i),J2(i));
    Jh(i)=max(Jh(i),J3(i));
    
    J(i)=(alpha-1)*transpose(Y(:,i))*[1 1 1;1 Y(1,i)/(1-Y(2,i)) 0;0 0 0]*Y(:,i);
    
    
    Dy(1,i)=(-gamma)*Y(1,i)*(Jh(i)-J1(i));
    Dy(2,i)=(-gamma)*Y(2,i)*(Jh(i)-J2(i));
    Dy(3,i)=(-gamma)*Y(3,i)*(Jh(i)-J3(i));

    
    %Prediciones
    
    Yp(1,i)=Y(1,i)+Salto*Dy(1,i);
    Yp(2,i)=Y(2,i)+Salto*Dy(2,i);
    Yp(3,i)=Y(3,i)+Salto*Dy(3,i);
    

    %funiones para la correccion
    
    Jp1(i)=alpha*(Yp(1,i)+Yp(2,i))-1;
    Jp2(i)=(alpha-1)*Yp(1,i)/(1-Yp(2,i));
    Jp3(i)=alpha*Yp(1,i);
    
    Jph(i)=max(Jp1(i),Jp2(i));
    Jph(i)=max(Jph(i),Jp3(i));
    
    
    Dpy(1,i)=(-gamma)*Yp(1,i)*(Jph(i)-Jp1(i));
    Dpy(2,i)=(-gamma)*Yp(2,i)*(Jph(i)-Jp2(i));
    Dpy(3,i)=(-gamma)*Yp(3,i)*(Jph(i)-Jp3(i));
    
 
    %Correcion
    
    for j=1:3
        Y(j,i+1)=Y(j,i)+(Salto/2)*(Dy(j,i)+Dpy(j,i));
    end
    
    
    if J1==Jh
        Y(1,i+1)=Y(1,i)-Salto*(Dy(2,i)+Dy(3,i));
    end
    if J2==Jh
        Y(2,i+1)=Y(2,i)-Salto*(Dy(1,i)+Dy(3,i));
    end
    if J3==Jh
        Y(3,i+1)=Y(3,i)-Salto*(Dy(2,i)+Dy(1,i));
        
    end
    
    
    Te(i)=sum(X(:,i));
    Tt(i)=sum(Y(:,i));
    
end
figure(h)
hold on

plot(X(1,:),'b')
plot(X(2,:),'g')
plot(X(3,:),'r')

hold off
grid
title('Evolucion de la poblacion por clase (metodo de euler)')
xlabel('tiempo')
ylabel('Porcion de pares')

figure(h+1)
hold on

plot(Y(1,:),'b')
plot(Y(2,:),'g')
plot(Y(3,:),'r')

hold off
grid   

title('Evolucion de la poblacion por clase (metodo del trapecio)')
xlabel('tiempo')
ylabel('Porcion de pares')


%plotear ganancia por pares
figure(h+2)
hold on

plot(G1,'b')
plot(G2,'g')
plot(G3,'r')

hold off
grid
title('Ganancia por clases en funcion del tiempo (metodo de euler)')
xlabel('Tiempo')
ylabel('Ganancia')


figure(h+3)
hold on

plot(J1,'b')
plot(J2,'g')
plot(J3,'r')

hold off
grid
title('Ganancia por clases en funcion del tiempo (metodo del trapecio)')
xlabel('Tiempo')
ylabel('Ganancia')

%plotear ganancia total

figure(h+4),plot(G,'k')
grid
title('Ganancia Total en funcion del tiempo (metodo de euler)')
xlabel('Tiempo')
ylabel('Ganancia')

figure(h+5),plot(J,'k')
grid
title('Ganancia Total en funcion del tiempo (metodo del trapecio)')
xlabel('Tiempo')
ylabel('Ganancia')

for i=1:3
    E(i,:)=abs(X(i,:)-Y(i,:));
end

figure(h+6)
hold on
plot(E(1,:),'b')
plot(E(2,:),'g')
plot(E(3,:),'r')

hold off
grid
title('Diferencia entre metodos')

alpha=alpha+1.8;
h=h+7; 
end


