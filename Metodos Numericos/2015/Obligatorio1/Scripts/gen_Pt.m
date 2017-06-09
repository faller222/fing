 function [Pt] = gen_Pt(A)
	n=max(size(A));
 	Pt=zeros(n,n);
	
	for i=1:n
		aux=sum(A(:,i));
		if(aux!=0)
		  Pt(i,:)=A(:,i)'/aux;
		end
	end;
	Pt=Pt';
	
return