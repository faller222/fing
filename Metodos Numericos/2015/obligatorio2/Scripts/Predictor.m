function [s,i,r] = Predictor(So,Io,Ro,b,a,h)
    s=So-b*So*Io*h;
    i=Io+Io*(b*So-a)*h;
    r=Ro+a*Io*h;
end