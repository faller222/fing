function [vec_p,val_p] = Potencia( vec, A, min_diff, max_iter )
    
    diff=500;
    vec_ant=vec;
    
    while( ( max_iter > 0 ) && ( diff > min_diff ) )
      
      max_iter--;
      aux_vec = A * vec_ant;
      val_p = max(abs(aux_vec));
      vec_p = aux_vec / val_p;
      
      diff=max(abs(vec_ant-vec_p));
      vec_ant=vec_p;
    endwhile;
	if( max_iter == 0 )
		fprintf('Consumio todas las iteraciones \n')
	end
	vec_p= vec_p / vec_p(1);
	
return
