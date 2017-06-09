function []=prueba()
	
	Ks= [1 10 20 30 40 50 60 70 80 90 100 ]
	n=prod(size(Ks));

	mat=round(rand(Ks(end),Ks(end)).*200);
	
	
	for i=1:n		
		tic;
		mysvd(mat(1:Ks(i),1:Ks(i)));
		myTimes(i) = toc;
		tic;
		svd(mat(1:Ks(i),1:Ks(i)));
		ocTimes(i) = toc;
	end

close all
hold on
  plot(Ks,myTimes,'r')
  plot(Ks,ocTimes,'b')
hold off
	