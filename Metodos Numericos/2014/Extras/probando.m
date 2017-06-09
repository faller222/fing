function probando ()
	%100 150 200 250 300 350
	Ks= [1 10 20 30 40 50 60 70 80 90 ]

	I = imread ("fing2.bmp");
	
	for i=1:prod(size(Ks))
		[E(i),C(i)]=compres(Ks(i),I);
	end



close all
hold on
  plot(Ks,E,'r')
  plot(Ks,C,'b')

hold off


