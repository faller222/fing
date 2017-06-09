function [vec,mat] = aux_mat2(tam,P)
    vec= ones(tam,1);
	mat=round((rand(tam,tam)+P)*(0.5));
	
return
