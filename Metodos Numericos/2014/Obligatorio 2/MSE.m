function ret=MSE (M1,M2)
	diff=M1-M2;
	cuad=diff.*diff;
	ret = sum(sum(sum(cuad)))/prod(size(cuad));
endfunction