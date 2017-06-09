function [U,S,V] = mysvd(A)
	A=double(A);
	traspuso=0;
	if(size(A,1)<size(A,2))
		A=A';
		traspuso=1;
	end
	
	[U,B,V] = bidiag(A);
	B=full(B);
	V=V';
	
	n=size(B)(2);
	m=n-1;
	fin=0;
	
	while(fin==0)
		%repaso la supraDiagonal
		for k = 1:m			
			if(abs(B(k,k+1))<=eps*(abs(B(k,k))+abs(B(k+1,k+1))))
				B(k,k+1)=0;			
			end	
		end 
		%divido en 3 bloques
		[B1,B2,B3]=divide(B);
		if((size(B1)==size(B2))&&(prod(size(B2))==0))
			fin=1;
		else
			%calcular i
			i=size(B2)(1);
			while((i>0)&&(B2(i,i)!=0))
				i=i-1;
			end	
			if(i>0)
				[UU,B2,VV]=diag_nulo(B2,i);
			else				
				[UU,B2,VV]=Golub(B2);
				%UU*B2*VV
			end
			%incorporo la sub matriz
			d1=size(B1);
			d2=size(B2)+d1;
			d1=d1.+1;
			auxU=eye(n);
			auxV=eye(n);
			
			auxU(d1(1):d2(1),d1(2):d2(2))=UU;
			auxV(d1(1):d2(1),d1(2):d2(2))=VV;
			
			%B
			U=U*auxU;
			V=auxV*V;
			B(d1(1):d2(1),d1(2):d2(2))=B2;	
			%auxU*B*auxV
			%U*B*V
		end
	end
	
	S=B;

	signo=eye(size(S));
	orden=eye(size(S));
	%acomodo los signos
	for i=1:n
		if(S(i,i)<0)
			signo(i,i)=-1;
		end
	end
	
	U=U*signo;
	S=signo*S;
	
	%ordeno los valores
	for i=1:n-1
		for j=i+1:n
			if(S(i,i)<S(j,j))
				vector=orden(:,i);
				orden(:,i)=orden(:,j);
				orden(:,j)=vector;			
			end	
		end
	end
	
	U=double(U*orden);
	S=double(orden*S*orden);
	V=orden*V;
	
	if(traspuso==1)
		A=A';
		PiBot=U';
		U=V';
		V=PiBot;
		S=S';
	end
	V=double(V');
	
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
function [B1,B2,B3] = divide(B)

	n=size(B)(2);
	m=n-1;
	%divido en 3 bloques
	i=m;
	while((i>0)&&(B(i,i+1)==0))
		i=i-1;
	end
	if(i>0)
		cero=i+2;
	else
		cero=i+1;
	end	
	while((i>0)&&(B(i,i+1)!=0))
		i=i-1;
	end
	num=i+1;
	B3=double(B(cero:n,cero:n));
	B2=double(B(num:cero-1,num:cero-1));
	B1=double(B(1:num-1,1:num-1));
	
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
function [U,D,V] = Golub(B)
	D=double(B);
	n=size(D)(2);
	
	U=double(eye(n));
	V=double(eye(n));
	m=n-1;
	
	condicion=0;
	while(condicion==0)
		T=double(D'*D);
		vp=eig(T(m:n,m:n));
		mu1=abs(vp(1,1)-T(n,n));
		mu2=abs(vp(2,1)-T(n,n));
		if(mu1<mu2)
			mu=double(vp(1,1));
		else
			mu=double(vp(2,1));
		end
		y=double(T(1,1)-mu);
		z=double(T(1,2));
		for k = 1:m			
			[c,s]=csGivens(y,z);
			G=get_g(D,c,s,k,k+1);
			V=double(V*G);
			D=double(D*G);
			y=D(k,k);
			z=D(k+1,k);
			[c,s]=csGivens(y,z);
			G=get_g(D,c,s,k,k+1)';%traspuesto
			U=double(G*U);
			D=double(G*D);
			if(k<=n-2)
				y=D(k,k+1);
				z=D(k,k+2);
			end
		end %for
		%checkeo condicion
		for k = 1:m			
			if(abs(D(k,k+1))<=eps*(abs(D(k,k))+abs(D(k+1,k+1))))
				D(k,k+1)=0;
				condicion=1;
			end	
		end %for
		
	end %while
	U=U';
	V=V';
	
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
function [U,D,V] = diag_nulo(B,i)
	D=double(B);
	n=size(D)(2);
	
	U=double(eye(n));
	V=double(eye(n));

	if(i<n)
		for k=i+1:n
			[c,s]=csGivens(D(k,k),D(i,k));
			G=get_g(D,c,s,k,i)';%traspuesto
			U=double(G*U);
			D=double(G*D);
			D(i,k)=0;
		end
	else
		for k=n-1:-1:1
			[c,s]=csGivens(D(k,k),D(k,n));
			G=get_g(D,c,s,k,n);
			V=double(V*G);
			D=double(D*G);
			D(k,n)=0;
		end
	end
	U=U';
	V=V';
		
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
function [c,s] = csGivens(a,b)

	if(b==0)
		c= 1;
		s= 0;
	else
		if(abs(b)>abs(a))
			t=double(-a/b);
			s=double(1/sqrt(1+t*t));
			c=double(s*t);
		else
			t=double(-b/a);
			c=double(1/sqrt(1+t*t));
			s=double(c*t);
		end
	end
		
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
function G=get_g(B,c,s,i,k)
	
	G=double(eye(size(B)));
	G(i,i)=c;
	G(k,k)=c;
	G(k,i)=-s;
	G(i,k)=s;
