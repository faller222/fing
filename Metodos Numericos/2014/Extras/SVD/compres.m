function [err,comp,O] = compres (K,I)
	
	%I = imread ("fing.bmp");
	
	for i=1:3
		[U(:,:,i),S(:,:,i),V(:,:,i)]=mysvd(I(200:250,200:250,i));
		D(:,:,i)=diag(S(:,:,i));
	end

	%Recortamos las imagenes
	%minimo=min(size(S(:,:,1)));
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
	sizeFinal=prod(size(UT))+prod(size(VT))+prod(size(DT));
	comp=sizeFinal*100/sizeOriginal;
	
	%printf("Relacion de compresion %f%%\n",comp);
	%printf("Error(MSE) %f\n",err);
		
	%image(O)
