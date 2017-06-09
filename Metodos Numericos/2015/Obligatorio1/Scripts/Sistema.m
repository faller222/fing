function [vec_p,val_p] = Sistema( vec, A, min_diff, max_iter )
   
   lambda = 1; %asumo que el valor Propio es 1
   diff = 500;%condicion de parada
   n = max( size( A ) );%tamaño de la matrix de entrada
   
    
   %%aca transformaremos el problema, para la solucion con Gauss-Seidel
   GS_L = eye( n - 1 ) * lambda; %% hacemos lambda por la identidad
   GS_A = A (2:n , 2:n); % me quedo sin la primera fila,
   GS_B = - A (2:n , 1); % la primea columa sera mi B, que la despejamos
   GS_A = GS_A - GS_L; % A - Lambda I
   GS_X = vec(2:n); % asumiremos que el primer elemento del vector es 1 luego
   %%Ya tenemos armado el problema
   
	GS_X_Ant = GS_X;
	acu=0;
	
    while( ( max_iter > 0 ) && ( diff > min_diff ) )
		for i=1:n-1
			acu=0;
			for j=1:n-1
				if(i!=j)
					acu = acu + GS_A( i, j ) * GS_X( j );
				end;
			end;	
			GS_X( i ) = ( GS_B( i ) - acu ) / GS_A( i , i );
		end;
	  
	  %Para los Controles de Corte
      max_iter--;   
      diff=max(abs(GS_X_Ant-GS_X));
      GS_X_Ant=GS_X;	  
	  
    endwhile;
	
	if( max_iter == 0 )
		fprintf('Consumio todas las iteraciones \n')
	end
	%preparamos la respuesta
	vec_p = zeros( n,1 );
	vec_p( 1 ) = 1;% asumimos que el primer elemento del vector es 1
	vec_p( 2 : n ) = GS_X; % por eso es que podemos despejar una fila
	
	val_p=lambda;
	
return