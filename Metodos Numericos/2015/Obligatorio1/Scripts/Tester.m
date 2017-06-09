function [Diff] = Tester( Iter,Error,Tam )
	%Iter=2000;
	%Error=0.00005;
	%Tam=1000;
	
	fprintf('Instanciando Matriz \n')
	tic
	[vec,mat] = aux_mat2(Tam,0.3);
	PT=gen_Pt(mat);
	WS=web_surfer(PT,0.85);
	toc

	fprintf('Ejecutando Sistema \n')
	tic
	[QQ,WW]=Sistema(vec,WS,Error,Iter);
	toc
	
	fprintf('Ejecutando Potencia \n')
	tic
	[QQ2,WW2]=Potencia(vec,WS,Error,Iter);
	toc
	
	fprintf('Mayor diferencia entre los terminos \n')
	Diff=max(abs(QQ-QQ2))

return