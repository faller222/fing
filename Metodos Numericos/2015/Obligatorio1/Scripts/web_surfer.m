 function [B] = web_surfer(A,p)
	n=max(size(A));
	B=p*A+((1-p)/n)*ones(n,n);
return