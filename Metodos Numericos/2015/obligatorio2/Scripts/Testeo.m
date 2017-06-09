%testeo de los codigos

function [Se,Ie,Re,Spc,Ipc,Rpc] = Testeo(So,Io,Ro,b,a,t,n)

close all
    tic
    [Se,Ie,Re]=SIREuler(So,Io,Ro,b,a,t,n);
    toc
    
    tic
    [Spc,Ipc,Rpc]=SIRPC(So,Io,Ro,b,a,t,n);
    toc
    
    h=t/n;
    tp=0:h:t;
    
    figure 3
    hold on
    plot(tp,Se,'r')
    plot(tp,Spc)
    legend('Metodo de Euler','Metodo PC')
    title('Suceptibles')
    xlabel('Tiempo')
    ylabel('Poblacion')
    hold off
    
    figure 4
    hold on
    plot(tp,Ie,'r')
    plot(tp,Ipc)
    legend('Metodo de Euler','Metodo PC')
    title('Infectados')
    xlabel('Tiempo')
    ylabel('Poblacion')
    hold off
    
    figure 5
    hold on
    plot(tp,Re,'r')
    plot(tp,Rpc)
    legend('Metodo de Euler','Metodo PC')
    title('Retirados')
    xlabel('Tiempo')
    ylabel('Poblacion')
    hold off
    
 difS=Se-Spc;
 difI=Ie-Ipc;
 difR=Re-Rpc;
 
 MdS=max(difS);
 MdI=max(difI);
 Mdr=max(difR);