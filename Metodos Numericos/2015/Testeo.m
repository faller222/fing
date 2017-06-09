%testeo de los codigos

function [Se,Ie,Re,Spc,Ipc,Rpc,Md] = Testeo(So,Io,Ro,b,a,t,n)

close all
    tic
    [Se,Ie,Re]=SIR_Euler(So,Io,Ro,b,a,t,n);
    toc
    
    tic
    [Spc,Ipc,Rpc]=SIR_PC(So,Io,Ro,b,a,t,n);
    toc
    
    h=t/n;
    tp=0:h:t;
    
    
    %ploteo de resultados
    
    
    figure(1)
    hold on           
    plot(tp,Se,'r')
    plot(tp,Ie,'g')
    plot(tp,Re)
    hold off
    legend('Suceptibles','Infectados','Retirados')
    xlabel('Tiempo')
    ylabel('Poblacion')
    title('Metodo de Euler')
    
    figure(2)
    hold on           
    plot(tp,Spc,'r')
    plot(tp,Ipc,'g')
    plot(tp,Rpc)
    hold off
    legend('Suceptibles','Infectados','Retirados')
    xlabel('Tiempo')
    ylabel('Poblacion')
    title('Metodo PC')
    
    figure(3)
    hold on
    plot(tp,Se,'r-')
    plot(tp,Spc,'b-.')
    legend('Metodo de Euler','Metodo PC')
    title('Suceptibles')
    xlabel('Tiempo')
    ylabel('Poblacion')
    hold off
    
    figure(4)
    hold on
    plot(tp,Ie,'r-')
    plot(tp,Ipc,'b-.')
    legend('Metodo de Euler','Metodo PC')
    title('Infectados')
    xlabel('Tiempo')
    ylabel('Poblacion')
    hold off
    
    figure(5)
    hold on
    plot(tp,Re,'r-')
    plot(tp,Rpc,'b-.')
    legend('Metodo de Euler','Metodo PC')
    title('Retirados')
    xlabel('Tiempo')
    ylabel('Poblacion')
    hold off
    
 difS=abs(Se-Spc);
 difI=abs(Ie-Ipc);
 difR=abs(Re-Rpc);
 
 Md(1)=max(difS);
 Md(2)=max(difI);
 Md(3)=max(difR);
 
 paso=0:n;
    figure(6)
    hold on
    plot(paso,difS)
    title('Diferencia Suceptibles')
    xlabel('Paso')
    ylabel('Diferencia')
    hold off
    
    figure(7)
    hold on
    plot(paso,difI)
    title('Diferencia Infectados')
    xlabel('Paso')
    ylabel('Diferencia')
    hold off
    
    figure(8)
    hold on
    plot(paso,difR)
    title('Diferencia Retirados')
    xlabel('Paso')
    ylabel('Diferencia')
    hold off
    