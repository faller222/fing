:- use_module(graficos).
:- use_module(library(clpfd)).
:- pce_image_directory('./').

:-gr_purgar.



main:-juego(3,T),kalog(T).

% ======================================================
% juegos predefinidos
% ======================================================

juego(1,[
[n,c(9),c(34),c(4),n],
[f(9),_,_,_,n],
[f(13),_,_,_,n],
[f(13),_,_,c(11),c(3)],
[n,f(7),_,_,_],
[n,f(19),_,_,_]
]).
juego(s1,[
[n,c(9),c(34),c(4),n],
[f(9),2,6,1,n],
[f(13),1,9,3,n],
[f(13),6,7,c(11),c(3)],
[n,f(7),4,2,1],
[n,f(19),8,9,2]
]).
juego(2,[
[n,n,n,c(7),c(16)],
[n,n,p(13,6),_,_],
[n,p(11,4),_,_,_],
[f(6),_,_,_,n],
[f(3),_,_,n,n]
]).

juego(3,[
[n,c(9),c(24),n,c(13),c(14),n,n],
[f(4),_,_,f(14),_,_,n,n],
[f(14),_,_,p(5,11),_,_,c(31),n],
[n,p(15,9),_,_,p(11,7),_,_,c(6)],
[f(22),_,_,_,_,p(13,13),_,_],
[f(3),_,_,p(15,15),_,_,_,_],
[n,f(3),_,_,p(11,11),_,_,c(15)],
[n,n,f(8),_,_,f(8),_,_],
[n,n,f(16),_,_,f(16),_,_]
]).

% ======================================================
% lógica principal de juego
% ======================================================

% kalog(+Tablero) <- despliega un Tablero y permite que el usuario humano lo complete.
kalog(Tablero) :-
    tam_tablero(Tablero,F,C),
    gr_crear_tablero(F, C, [boton('Generar',generar),boton('Reiniciar',reiniciar),boton('Resolver',resolver), boton('Salir',salir)], Visual),
    pintar_tablero(Tablero,Visual),
	loop(Visual,Tablero,e(none,none)),
	!,
    gr_destruir(Visual).

% kalog(+Tablero,+Tecnica) <- resuelve el kakuro definido en Tablero con la técnica Tecnica.
% Tecnica puede tener los valores std o clpfd.
%kalog(_,_). Implementado mas abajo (parte 2)

% kalog(+Filas,+Columnas,-Tablero) <- genera un kakuro de tamaño (Filas, Columnas).
%kalog(_,_,_). Implementado en parte 3

% loop principal del juego, el estado guarda información sobre lo que el usuario está haciendo,
% en principio el estado es e(CasilleroSeleccionado,NumeroSeleccionado)
% CasilleroSeleccionado puede ser none o un par (Fila,Columna) que indica la posición en el tablero
% NumeroSeleccionado puede ser none o una tupla (Fila,Columna,Num) que indica la posición y el valor en la lista de números
loop(Visual,Tablero,Estado) :-
    gr_evento(Visual,E),
    procesar_evento(E,Visual,Tablero,NewTablero,Estado,NuevoEstado),
	validar_tablero(Visual,NewTablero),
    !,
    loop(Visual,NewTablero,NuevoEstado).
loop(_,_,_).

% botón salir
procesar_evento(salir,Visual,T,T,Estado,Estado) :-
 !, gr_opciones(Visual, 'Seguro?', ['Si', 'No'], 'No').

% botón generar
procesar_evento(generar, Visual, Tablero,NewTablero, _ , e(none,none)) :-
 !,
 tam_tablero(Tablero,F,C),
 kalog(F,C,NewTablero),
 gr_inicializar_tablero(Visual,F,C),
 pintar_tablero(NewTablero,Visual).
 
% botón reiniciar
procesar_evento(reiniciar, Visual, Tablero,NewTablero, _ , e(none,none)) :-
 !,
 tam_tablero(Tablero,F,C),
 gr_inicializar_tablero(Visual,F,C),
 reiniciar_tablero(Tablero,NewTablero),
 pintar_tablero(NewTablero,Visual).

% boton resolver
procesar_evento(resolver, Visual, T,T, Estado, Estado):-
 !,
 gr_opciones(Visual, 'Tecnica?', [std, clpfd], Res),
 debugger(Res),
 kalog(T,Res),
 pintar_tablero(T,Visual).

% el evento es un click -> actualizo mensaje y proceso.
procesar_evento(click(Fila,Columna),Visual,T,NT,Estado,NuevoEstado) :-
    sformat(Msg, 'Click en (~w, ~w)', [Fila,Columna]),
    gr_estado(Visual,Msg),
    procesar_click(Fila,Columna,Visual,T,NT,Estado,NuevoEstado).


% click en una casilla, sin número seleccionado
procesar_click(FilaC,ColC,Visual,Tablero,Tablero,e(CasViejo,none),e((FilaC, ColC),none)):-
    casillero_valido(FilaC, ColC, Tablero),
    !,
    desmarcar(Visual, CasViejo),
    gr_marcar_seleccion(Visual, FilaC, ColC).

% click en una casilla, con número seleccionado
procesar_click(FilaC, ColC, Visual,Tablero,NewTablero,e(none,(FNum,CNum,N)),e(none,none)):-
    casillero_valido(FilaC, ColC, Tablero),
    !,
    desmarcar(Visual, (FNum, CNum)),
	(es_casilla_invalida(Tablero,FilaC, ColC),Tablero=NewTablero;
	pintar_tablero(Tablero,Visual),
    procesar_asignar_numero(Visual,FilaC, ColC, N),
	nuevo_valor_celda(FilaC, ColC, Tablero, N, NewTablero)).

% click en número sin casilla seleccionada.
procesar_click(FilaN,ColN,Visual,Tablero,Tablero,e(none,NumOld),e(none,(FilaN,ColN,N))):-
    numero_valido(FilaN, ColN, Tablero, N),
    !,
    desmarcar(Visual, NumOld),
    gr_marcar_seleccion(Visual, FilaN, ColN).

% click en número con casilla seleccionada.
procesar_click(FilaN, ColN, Visual, Tablero, NewTablero,e((FilaC,ColC),none),e(none,none)):-
    numero_valido(FilaN, ColN, Tablero, N),
    !,
    desmarcar(Visual, (FilaC, ColC)),
	(es_casilla_invalida(Tablero,FilaC, ColC),Tablero=NewTablero;
	pintar_tablero(Tablero,Visual),
	procesar_asignar_numero(Visual, FilaC, ColC, N),
	nuevo_valor_celda(FilaC, ColC, Tablero, N, NewTablero)).

% click en cualquier otro lado -> ignorar.
procesar_click(_,_,_,T,T,Estado,Estado).

procesar_asignar_numero(Visual, FilaC, ColC, x):-
    !,
    gr_eliminar_numero(Visual, FilaC, ColC).
procesar_asignar_numero(Visual, FilaC, ColC, N):-
    between(1,9,N),
    gr_dibujar_numero(Visual, FilaC, ColC, N).

%quita la marca de seleccionado de casillas y números.
desmarcar(Visual, (Fila,Columna,_)):-
    !,
    gr_desmarcar_seleccion(Visual, Fila, Columna).
desmarcar(Visual, (Fila,Columna)):-
    !,
    gr_desmarcar_seleccion(Visual, Fila, Columna).
desmarcar(_,_).

% ======================================================
% predicados auxiliares
% ======================================================

%determina si (Fila,Columna) corresponde a un casillero para llenar.
casillero_valido(Fila,Columna,Tablero):-
    tam_tablero(Tablero,MaxF,MaxC),
    between(1,MaxF,Fila),
    between(1,MaxC,Columna).

%determina si (Fila,Columna) corresponde a un número.
numero_valido(Fila, Columna, Tablero, N):-
    tam_tablero(Tablero, MaxF, _),
    Fila is MaxF+2,
    between(1,10,Columna),
    valor_columna(Columna,N).

%devuelve el valor de la columna, o x para eliminar un valor.
valor_columna(N,N):-
    N =< 9.
valor_columna(10,x).

% obtiene el tamaño de un tablero representado como lista de listas
tam_tablero([Fila|Filas],F,C):-
    length([Filas|Filas],F),
    length(Fila,C).
	
	%======================================================
	% Valida que la celda sea valida para modificar
	
	es_casilla_invalida(Tablero, F, C):-nth1(F,Tablero,Fila),nth1(C,Fila,E),var(E),!,false.
	es_casilla_invalida(Tablero, F, C):-nth1(F,Tablero,Fila),nth1(C,Fila,n),!.
	es_casilla_invalida(Tablero, F, C):-nth1(F,Tablero,Fila),nth1(C,Fila,p(_,_)),!.
	es_casilla_invalida(Tablero, F, C):-nth1(F,Tablero,Fila),nth1(C,Fila,f(_)),!.
	es_casilla_invalida(Tablero, F, C):-nth1(F,Tablero,Fila),nth1(C,Fila,c(_)),!.
	es_casilla_invalida(Tablero, F, C):-nth1(F,Tablero,Fila),nth1(C,Fila,E),nonvar(E),!,false.	
	
	%======================================================
	% Valida el tablero
	
	validar_tablero(Visual,Tablero):-
									transpose(Tablero,Traspuesto),
									validar_tablero(Visual,Traspuesto,1,c),
									validar_tablero(Visual,Tablero,1,f).
	
	% La F esta asociada al indice de la Fila
	validar_tablero(Visual,[Fila|Filas],F,Sense):-
		validar_fila(Visual,F,1,_,0,0,Fila,[],Sense,true),
		NF is F+1,
		validar_tablero(Visual,Filas,NF,Sense).
	
	validar_tablero(_,[],_,_):-!.
	
	validar_fila(Visual,F,C,PosHeader,Acc,Cota,[E|Lista],Resto,Sentido,_):-
		var(E),
		NC is C+1,
		validar_fila(Visual,F,NC,PosHeader,Acc,Cota,Lista,[E|Resto],Sentido,false).
	
	validar_fila(Visual,F,C,PosHeader,Acc,Cota,[n|Lista],_,Sentido,Completo):-
		NC is C+1,
		validar_suma(Visual,F,PosHeader,Acc,Cota,Sentido,Completo),
		validar_fila(Visual,F,NC,_,0,0,Lista,[],Sentido,true).
	
	validar_fila(Visual,F,C,PosHeader,Acc,Cota,[f(NewCota)|Lista],_,f,Completo):-
		NC is C+1,
		validar_suma(Visual,F,PosHeader,Acc,Cota,f,Completo),
		validar_fila(Visual,F,NC,C,0,NewCota,Lista,[],f,true).
	
	validar_fila(Visual,F,C,PosHeader,Acc,Cota,[f(_)|Lista],_,c,Completo):-
		NC is C+1,
		validar_suma(Visual,F,PosHeader,Acc,Cota,c,Completo),
		validar_fila(Visual,F,NC,_,0,0,Lista,[],c,true).
	
	validar_fila(Visual,F,C,PosHeader,Acc,Cota,[c(_)|Lista],_,f,Completo):-
		NC is C+1,
		validar_suma(Visual,F,PosHeader,Acc,Cota,f,Completo),
		validar_fila(Visual,F,NC,_,0,0,Lista,[],f,true).
	
	validar_fila(Visual,F,C,PosHeader,Acc,Cota,[c(NewCota)|Lista],_,c,Completo):-
		NC is C+1,
		validar_suma(Visual,F,PosHeader,Acc,Cota,c,Completo),
		validar_fila(Visual,F,NC,C,0,NewCota,Lista,[],c,true).

	validar_fila(Visual,F,C,PosHeader,Acc,Cota,[p(NewCota,_)|Lista],_,f,Completo):-
		NC is C+1,
		validar_suma(Visual,F,PosHeader,Acc,Cota,f,Completo),
		validar_fila(Visual,F,NC,C,0,NewCota,Lista,[],f,true).

	validar_fila(Visual,F,C,PosHeader,Acc,Cota,[p(_,NewCota)|Lista],_,c,Completo):-
		NC is C+1,
		validar_suma(Visual,F,PosHeader,Acc,Cota,c,Completo),
		validar_fila(Visual,F,NC,C,0,NewCota,Lista,[],c,true).

	validar_fila(Visual,F,C,PosHeader,Acc,Cota,[E|Lista],Resto,Sentido,Completo):-
		NC is C+1,
		NAcc is Acc+E,
		(pertenece(E,Resto,I),gr_marcar_error_sense(Visual, F, C, Sentido) ,Aux is C-I, gr_marcar_error_sense(Visual, F, Aux, Sentido) ; true),
		validar_fila(Visual,F,NC,PosHeader,NAcc,Cota,Lista,[E|Resto],Sentido,Completo).
	
	
	
	validar_fila(Visual,F,_,PosHeader,Acc,Cota,[],_,Sentido,Completo):-!,validar_suma(Visual,F,PosHeader,Acc,Cota,Sentido,Completo).
	
	

	%======================================================
	%nuevo_valor_celda(+I,+J,+A1,+E,?A2) ? A2 es una matriz que contiene el valor E en la celda (I,J) y en el resto de las celdas contiene los mismos valores que A1.
	nuevo_valor_celda(1,J,[H|T],E,[R|T]):- replace(J,E,H,R),!.
	nuevo_valor_celda(I,J,[H|T],E,[H|Ln]):- X is I-1, nuevo_valor_celda(X,J,T,E,Ln).

	replace(1,x,[_|T],[_|T]):-!.
	replace(1,E,[_|T],[E|T]):-!.
	replace(J,E,[H|T],[H|Ln]):- X is J-1,replace(X,E,T,Ln).

	
	%======================================================
	% Metodos para dibujar el tablero

	pintar_tablero(Tablero,Visual):- pintar_tablero_fila(Tablero,1,Visual).
	pintar_tablero_fila([Fila|Filas],F,Visual):- pintar_tablero_elemento(Fila,F,1,Visual),Nf is F + 1,pintar_tablero_fila(Filas,Nf,Visual).
	pintar_tablero_fila([],_,_).

	pintar_tablero_elemento([E|Elems],F,C,Visual):- pintar(E,F,C,Visual), Nc is C + 1, pintar_tablero_elemento(Elems, F, Nc, Visual).
	pintar_tablero_elemento([],_,_,_).

	pintar(E,F,C,Visual):-var(E),gr_eliminar_numero(Visual, F, C),!.
	pintar(n,F,C,Visual):-gr_dibujar_casillero(Visual, F, C, 0, 0),!.
	pintar(f(SumH),F,C,Visual):-gr_dibujar_casillero(Visual, F, C, SumH, 0),!.
	pintar(c(SumV),F,C,Visual):-gr_dibujar_casillero(Visual, F, C, 0, SumV),!.
	pintar(p(SumH,SumV),F,C,Visual):-gr_dibujar_casillero(Visual, F, C, SumH, SumV),!.
	pintar(N,F,C,Visual):-gr_desmarcar_seleccion(Visual, F, C),gr_dibujar_numero(Visual, F, C, N). 
	
	%======================================================
	% Metodos para borrar los valores asignados

	reiniciar_tablero([Fila|Filas],[NFila|NFilas]):- reiniciar_tablero_fila(Fila,NFila),reiniciar_tablero(Filas,NFilas).
	reiniciar_tablero([],[]).

	reiniciar_tablero_fila([E|Elems],[NE|NElems]):- reiniciar(E,NE), reiniciar_tablero_fila(Elems,NElems).
	reiniciar_tablero_fila([],[]).

	reiniciar(E,_):-var(E),!.
	reiniciar(n,n):-!.
	reiniciar(f(SumH),f(SumH)):-!.
	reiniciar(c(SumV),c(SumV)):-!.
	reiniciar(p(SumH,SumV),p(SumH,SumV)):-!.
	reiniciar(_,_). 
	%======================================================
	% Metodos para copiar un tablero

	copy_tablero([Fila|Filas],[CFila|CFilas]):- copy_tablero_fila(Fila,CFila), copy_tablero(Filas,CFilas).
	copy_tablero([],[]).

	copy_tablero_fila([E|Elems],[CE|CElems]):- copy_elemento(E,CE), copy_tablero_fila(Elems,CElems).
	copy_tablero_fila([],[]).

	copy_elemento(E,_):-var(E),!.
	copy_elemento(n,n):-!.
	copy_elemento(f(SumH),f(SumH)):-!.
	copy_elemento(c(SumV),c(SumV)):-!.
	copy_elemento(p(SumH,SumV),p(SumH,SumV)):-!.
	copy_elemento(N,N):-nonvar(N),!.
	
	%======================================================
	% Implementacion custom del transponer
	trasponer([[]|_], []):-!.
	trasponer(M, [Ts|Tss]) :- slice(M, Ts, Mn), trasponer(Mn, Tss).

	%slice saca una rebanada de la matriz quedandose con los primeros elementos de cada fila y el resto de la matriz por otro lado
	slice([], [], []):-!.
	slice([[F|Os]|Rest], [F|Fs], [Os|Oss]) :- slice(Rest, Fs, Oss).
	
	
	%======================================================
	% Implementacion custom del member
	
	pertenece(E,[H|T],NI):-var(H),!, pertenece(E,T,I), NI is I + 1 .
	pertenece(E,[E|_],1):-!.
	pertenece(E,[_|T],NI):-pertenece(E,T,I), NI is I + 1 .
	
	
	no_pertenece(E,L):-pertenece(E,L,_),!,false.
	no_pertenece(_,_):-!.

	%======================================================
	% Metodos auxiliares, para marcar error en caso de no satisfacer la suma

	validar_suma(Visual,FH,CH,Acc,Cota,Sentido,true):-(Acc=Cota; gr_marcar_error_sense(Visual, FH, CH, Sentido)).
	validar_suma(Visual,FH,CH,Acc,Cota,Sentido,false):-(Acc<Cota; gr_marcar_error_sense(Visual, FH, CH, Sentido)).

	%======================================================
	% Metodos auxiliares, para marcar error en cualquier sentido
	
	gr_marcar_error_sense(Visual, F, C, f):- gr_marcar_error(Visual, F, C).
	gr_marcar_error_sense(Visual, F, C, c):- gr_marcar_error(Visual, C, F).
	
	%======================================================
	% Metodos auxiliares, Hack, usar solo en desarrollo.
	
	debugger([E|Es]):-print(E),print(.),debugger(Es).
	debugger(E):-print(E),nl.
	%======================================================
	
	
	
	%======================================================
	%======================================================
	%======================================================
	%======================================================
	% PARTE 2
	%======================================================
	
	
	kalog(T,clpfd):-
		rangos_sumas(T,Rangos,Sumas,f),
		transpose(T,TT),
		rangos_sumas(TT,Rangos2,Sumas2,c),
		%debugger(Rangos2),
		%debugger(Sumas2).
		append(Rangos,Rangos2,Rangos3),
		append(Sumas,Sumas2,Sumas3),
		kalog_clpfd(Rangos3,Sumas3),!.

	kalog(T,std) :-
		rangos_sumas(T,Rangos,Sumas,f),
		trasponer(T,TT),
		rangos_sumas(TT,Rangos2,Sumas2,c),
		append(Rangos,Rangos2,Rangos3),
		append(Sumas,Sumas2,Sumas3),
		kalog_std(Rangos3,Sumas3),!.
		
	kalog_clpfd(Rangos, Sumas) :-
		flatMap(Rangos,AllCells),
		AllCells ins 1..9,
		maplist(kalog_rango, Rangos, Sumas),
		maplist(label, Rangos).

	kalog_rango(Rango, Suma) :-
		all_distinct(Rango),
		sum(Rango, #=, Suma).
		
	kalog_std(Rangos,Sumas) :-
		maplist(kalog_rango_std, Rangos, Sumas),
		maplist(label, Rangos).

	kalog_rango_std(Rango, Suma) :-
		sub_conjunto_tamanio(Rango),
		total(Rango, Suma).

		
	%======================================================
	sub_conjunto_tamanio([]):-!.
	sub_conjunto_tamanio(Res):-
		length(Res,Size),
		numlist(1,9,Set),
		sub_conjunto_tamanio(Set,Size,Res).
	
	sub_conjunto_tamanio(_,0,[]):-!.
	sub_conjunto_tamanio(Set,Size,[E|SubSet]):-
		select(E,Set,R), 
		NSize is Size - 1,
		sub_conjunto_tamanio(R,NSize,SubSet).
	
	%======================================================	
	flatMap([], []):-!.
	flatMap([X], X):-!.
	flatMap([X|Xs], NX):- flatMap(Xs, XX), append(X, XX, NX).

	
	%======================================================
	% total(Rango, Suma)
	total([],0):-!.
	total([X|Xs], S) :- total(Xs,Ns), S is Ns + X.
 
	%======================================================
	% Recorre el tablero por filas, guardando los rangos por filas encontrados como listas de variables anonimas en una lista de rangos.
	% Va guardando tambien las sumas de filas encontradas, de esta forma la primer suma corresponde al primer rango, etc.
	% El resultado en Rangos1 es lo procesado concatenado después de Rangos0, lo mismo para las sumas
	
	rangos_sumas([],[],[],_):-!.
	rangos_sumas([Fila|Filas], R, S, Sentido) :- 
		rangos_sumas_fila(Fila, R1, S1, Sentido), 
		rangos_sumas(Filas, R2, S2, Sentido),
		append(R1,R2,R),append(S1,S2,S).

	rangos_sumas_fila([],[],[],_):-!.
	rangos_sumas_fila(Fila, [Rango|R], [Sum|S], Sentido) :- 
		rangos_sumas_calc(Fila, Rango, Sum, Resto, Sentido),
		rangos_sumas_fila(Resto, R, S, Sentido).

	%======================================================
	
	rangos_sumas_calc([],[],0,[], _):-!.
	rangos_sumas_calc([],[],_,[], _):-!.
	
	rangos_sumas_calc([Vacia|Fila],[Vacia|Rango], Sum, Resto, Sentido):-var(Vacia),!, rangos_sumas_calc(Fila,Rango, Sum, Resto, Sentido). 
	
	rangos_sumas_calc([n|Fila],Rango, Sum, Resto,Sentido):-var(Sum),!,rangos_sumas_calc(Fila,Rango, Sum, Resto, Sentido). %Va consumiendo los n
	rangos_sumas_calc([n|Fila],[], _, Fila, _ ):-!.

	rangos_sumas_calc([f(SumH)|Fila],Rango, Sum, Resto, f):-var(Sum),!,Sum is SumH,rangos_sumas_calc(Fila,Rango, SumH, Resto, f). 
	rangos_sumas_calc([f(_)|Fila],Rango, Sum, Resto, c):-var(Sum),!,rangos_sumas_calc(Fila,Rango, Sum, Resto, c). 
	rangos_sumas_calc([f(SumH)|Fila],[], _, [f(SumH)|Fila], f ):-!. 
	rangos_sumas_calc([f(_)|Fila],[], _, Fila, c ):-!. 

	rangos_sumas_calc([c(SumV)|Fila],Rango, Sum, Resto, c):-var(Sum),!,Sum is SumV, rangos_sumas_calc(Fila,Rango, SumV, Resto, c). 
	rangos_sumas_calc([c(_)|Fila],Rango, Sum, Resto, f):-var(Sum),!,rangos_sumas_calc(Fila,Rango, Sum, Resto, f). 
	rangos_sumas_calc([c(SumV)|Fila],[], _, [c(SumV)|Fila], c ):-!. 
	rangos_sumas_calc([c(_)|Fila],[], _, Fila, f ):-!. 

	rangos_sumas_calc([p(SumH, _)|Fila],Rango, Sum, Resto, f):-var(Sum),!,Sum is SumH,rangos_sumas_calc(Fila,Rango, SumH, Resto, f). 
	rangos_sumas_calc([p(_, SumV)|Fila],Rango, Sum, Resto, c):-var(Sum),!,Sum is SumV,rangos_sumas_calc(Fila,Rango, SumV, Resto, c). 
	rangos_sumas_calc([p(SumH, SumV)|Fila],[], _, [p(SumH, SumV)|Fila], _ ):-!. 


	%======================================================
	%diferentes(+L) ? todos los elementos de la lista L son distintos entre sí
	%Ej.: diferentes([a,b,c]) 
	diferentes([]).
	diferentes([H|T]):- notFind(H,T), diferentes(T).

	%nofFind se cumple si no se encuentra un elemento en una lista
	notFind(_,[]).
	notFind(E,[H|T]):- E\=H , notFind(E,T).
	
	
	%======================================================
	%======================================================
	%======================================================
	%======================================================
	% PARTE 3
	%======================================================
	
:-nl,nl,nl,nl,nl,nl,nl,nl,nl,nl,nl,nl,nl,nl,nl,nl,nl,nl,nl,nl,nl,nl,nl,nl,nl,nl,nl,nl,nl,nl,nl,nl,nl,nl,nl,nl,nl,nl,nl,nl,nl.


% kalog(+Filas,+Columnas,-Tablero) <- genera un kakuro de tamaño (Filas, Columnas).
kalog(F,C,T):- 
	generar_tablero_valido(F,C,T1),
	debugger(tableroGenerado),
	fill(T1),
	debugger(solucionCalculada),
	calcular_cabezales(T1,T2),
	reiniciar_tablero(T2,T).

	
	%======================================================
	% Calcular la suma
	
	calcular_cabezales(T1,T5):-
		calcular_cabezales(T1,T2,f),
		transpose(T2,T3),
		calcular_cabezales(T3,T4,c),
		calcular_cabezales(T3,T4,c),
		transpose(T4,T5).
	
	calcular_cabezales([],[],_):-!.
	calcular_cabezales([R|T],[NR|NT], Sense):-
		calcular_cabezales_fila(R, NR, Sense),
		calcular_cabezales(T, NT, Sense).
	
	calcular_cabezales_fila(Row,T,Sense):-
		calcular_cabezales_fila(Row,T,Sense,_,0,0).
	
	calcular_cabezales_fila([],[],_,n,0,0):-!.
	calcular_cabezales_fila([],[],c,f(RAcc),0,RAcc):-!.
	
	calcular_cabezales_fila([],[],f,f(Acc),Acc,_):-!.
	calcular_cabezales_fila([],[],c,c(Acc),Acc,0):-!.
	calcular_cabezales_fila([],[],c,p(RAcc,Acc),Acc,RAcc):-!.
	
	calcular_cabezales_fila([n|Row],[H|T],Sense,n,0,0):-!,calcular_cabezales_fila(Row,T,Sense,H,0,0).
	calcular_cabezales_fila([n|Row],[H|T],c,f(RAcc),0,RAcc):-!,calcular_cabezales_fila(Row,T,c,H,0,0).
	
	calcular_cabezales_fila([n|Row],[H|T],f,f(Acc),Acc,_):-!, calcular_cabezales_fila(Row,T,f,H,0,_).
	calcular_cabezales_fila([n|Row],[H|T],c,c(Acc),Acc,0):-!, calcular_cabezales_fila(Row,T,c,H,0,0).
	calcular_cabezales_fila([n|Row],[H|T],c,p(RAcc,Acc),Acc,RAcc):-!, calcular_cabezales_fila(Row,T,c,H,0,0).
	
	calcular_cabezales_fila([f(SumF)|Row],[H|T],c,n,0,0):-!, calcular_cabezales_fila(Row,T,c,H,0,SumF).
	calcular_cabezales_fila([f(SumF)|Row],[H|T],c,f(RAcc),0,RAcc):-!, calcular_cabezales_fila(Row,T,c,H,0,SumF).
	calcular_cabezales_fila([f(SumF)|Row],[H|T],c,p(RAcc,Acc),Acc,RAcc):-!, calcular_cabezales_fila(Row,T,c,H,0,SumF).
		
	calcular_cabezales_fila([E|Row],[E|T],Sense,H,Acc,RAcc):-!, NAcc is Acc+E, calcular_cabezales_fila(Row,T,Sense,H,NAcc,RAcc).
	
	
	%======================================================
	% Completar con Numeros
	
	fill(T):- transpose(T,Trans), fill(T,Trans).
	
	fill([],_):-!.
	fill([Row|T],Trans):- fill(Trans,Row,[]), fill(T,Trans).
	
	fill(_,[],_):-!.
	fill([Col|Trans],[E|Row],UR):-
		var(E),
		!,
		get_used(Col,UC),
		usables(UC,UR,Usable),
		select(E,Usable,_),
		fill(Trans,Row,[E|UR]).
	fill([_|Trans],[n|Row],_):-!,fill(Trans,Row,[]).
	
	%======================================================
	% Metodo que retorna los numeros del 1 al 9 que no esten las listas
	
	usables(A,B,Disponibles):-get_random_9(L),usables(A,B,Disponibles,L).
	
	usables(_,_,[],[]):-!.
	usables(A,B,Usables,C):- select(E,C,R), select(E,A,_),!, usables(A,B,Usables,R).
	usables(A,B,Usables,C):- select(E,C,R), select(E,B,_),!, usables(A,B,Usables,R).
	usables(A,B,[E|Usables],C):-!,select(E,C,R), usables(A,B,Usables,R).

	
	%======================================================
	% Metodo para obtener los elementos del ultimo bloque
	% Solo para la construccion del Tablero,
	% tiene en cuenta que este incompleto
	
	get_used(Row,Used):- get_used(Row,[],Used).
	
	get_used([],Used,Used):- !.
	get_used([E|_],Used,Used):- var(E), !.
	get_used([n|T],_,Used):- get_used(T,[],Used), !.
	get_used([E|T],Acc,Used):- get_used(T,[E|Acc],Used), !.
	
	%======================================================
	% METODO PARA SALTEAR RAMAS DE LA RECURSION
	% PARA DESARROLLO SOLAMENTE
	
	:- dynamic iteration_nr/1.

	generar_tablero_valido_r(F,C,T,Skip) :-
		retractall(iteration_nr(_)),
		asserta(iteration_nr(0)),
		generar_tablero_valido(F,C,T,Skip).
		
	generar_tablero_valido(F,C,T,Skip) :-
		generar_tablero_valido(F,C,T),
		iteration_nr(N),
		N2 is N+1,
		retract(iteration_nr(N)),
		asserta(iteration_nr(N2)),
		debugger(N2),
		Skip < N2.     % force backtracking here if counter < Skip

	%======================================================
	
	generar_tablero_valido(F,C,[BlackRow|T]):-
		generar_n_black(C,BlackRow),
		generar_tablero(F,C,T),
		transpose([BlackRow|T],Trans),
		validar_tablero(Trans).

	%======================================================
	% Generacion de filas validas
	
	generar_tablero(1,_,[]):-!.
	generar_tablero(F,C,[Row|T]):-
		NF is F-1,
		generar(C,Row),
		not_all_same(Row), %Con esto evito las Filas de negros
		generar_tablero(NF,C,T).
	
	generar_n_black(0,[]):-!.
	generar_n_black(N,[n|T]):- NN is N-1,generar_n_black(NN,T).
	
	%======================================================
	% Generacion de filas validas
	
	generar(0,[]):-!.
	
	generar(N,NewRow):-
		M is min(N,10),%El grupo tiene como maximo 10.
		get_random_max(M,ValidValues),
		select(Size,ValidValues,_),%Elijo un valor para el tamanio del grupo
		generar_sub(Size,SubRow),
		Rest is N-Size, %Esto es lo que me queda por generar
		generar(Rest,SubRowEnd),
		append(SubRow,SubRowEnd,NewRow).
	
	%======================================================
	% Generador de Sub Filas
	
	generar_sub(0,[]):-!.
	generar_sub(1,[n]):-!.
	generar_sub(2,[n,n]):-!. %Celdas vacias seguidas, 2 minimo
	
	generar_sub(N,[n|SubRow]):-
		NN is N-1,
		bloque_tamanio(NN,SubRow).
	
	bloque_tamanio(0,[]):-!.
	bloque_tamanio(Size,[_|Res]):-
		NSize is Size - 1,
		bloque_tamanio(NSize,Res).
	
	%======================================================
	% Validar que no haya mas de 9 elementos vacios seguidos
	
	validar_tablero([_|T]):-validar_tablero_(T).
	
	validar_tablero_([]):-!.
	validar_tablero_([H|T]):-
		not_all_same(H), %Con esto evito las Filas de negros
		validate_empty_cell_max9_min2(H),
		validar_tablero_(T).
	
	%======================================================
	% Validar que no haya mas de 9 elementos vacios seguidos
		
	validate_empty_cell_max9_min2(List):- validate_empty_cell_max9_min2(List,0).

	validate_empty_cell_max9_min2([],0):- !.
	validate_empty_cell_max9_min2([],Count):-!,Count>0.

	validate_empty_cell_max9_min2([H|T],Count):-
		var(H), 
		!,
		Count<9,
		NCount is Count+1,
		validate_empty_cell_max9_min2(T,NCount).

	validate_empty_cell_max9_min2([n|T],0):- !, validate_empty_cell_max9_min2(T,0).
	validate_empty_cell_max9_min2([n|T],Count):- !,Count>0,validate_empty_cell_max9_min2(T,0).

	%======================================================
	same([]):-!.  
	same([_]):-!.
	same([X,X|T]) :- same([X|T]).

	not_all_same([]):-!.
	not_all_same([_]):- !, false.
	not_all_same([X,_|_]):- var(X), !.
	not_all_same([_,X|_]):- var(X), !.
	not_all_same([X,X|T]):- !, not_all_same([X|T]).
	not_all_same([_,_|_]):- !.
	
	%======================================================
	
	get_random_9(L):-get_random_max(9,L).
	get_random_max(Max,L):-numlist(1,Max,Ll),random_permutation(Ll,L).
	
	down(1,1):-!.
	down(N,N).
	down(N,NN):-Naux is N-1,down(Naux,NN).
	
	betweenR(Min,Max,F):-random(R),F is round(R * (Max - Min)) + Min.
	seed(Max,F):-random(R),F is ceil(R * Max).
	seed(Seed,Max,F):-F is ceil(Seed * Max).
	%======================================================