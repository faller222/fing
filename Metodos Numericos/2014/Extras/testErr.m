function []=testErr()
	
	Ks= [1 10 20 30 40 50 60 70 80 90 ]
	
	I = imread ("fing2.bmp");
	
	
	for i=1:prod(size(Ks))
		[errOc(i),ocC(i)]=compres(Ks(i),I,0);
		
		%[errMy(i),myC(i)]=compres(Ks(i),I,5);
		
	end
	

close all
hold on
  %plot(Ks,errMy,'r')
  plot(Ks,errOc,'b')
hold off
	