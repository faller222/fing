function [err,comp,O] = compres (K,I,mode)

	
	
	%I = imread ("fing.bmp");
	m=size(I,1);
	n=size(I,2);
	
	for i=1:3
	  if (mode==5)
	      [U(:,:,i),S(:,:,i),V(:,:,i)]=mysvd(I(:,:,i));
	  else
	      [U(:,:,i),S(:,:,i),V(:,:,i)]=svd(I(:,:,i));
	  end
	      D(:,:,i)=diag(S(:,:,i));
	end

	%Recortamos las imagenes
	UT=U(:,1:K,:);
	VT=V(:,1:K,:);
	ST=S(1:K,1:K,:);
	DT(:,:,i)=D(1:K,:,i);
	
	for i=1:3
		O(:,:,i)=uint8(UT(:,:,i)*ST(:,:,i)*(VT(:,:,i)'));
	end
	
	%Calculo de error y compresion
	err = MSE(O,I);	
	
	sizeOriginal=prod(size(I));
	
	sizeFinal=(m+n+1)*K*3;
	
	comp=sizeFinal*100/sizeOriginal;
	if(err<0.0028)
		K
	end
