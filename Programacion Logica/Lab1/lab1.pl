%######################################################################################
%1) Predicados sobre listas
%largo(+L,?N) ← N es el largo de la lista L.
%Ej.: largo([a,b,c],3).
%
largo(L,N):- largoAc(L,0,N).
    
%largoAc(+L,+Ac,?N) ← N es el largo de la lista L, el predicado es tail-recursive
largoAc([],Ac,Ac).
largoAc([_|L],Ac,N):- NAc is Ac+1, largoAc(L,NAc,N).

%ultimo(?L,?U) ← el último elemento de la lista L unifica con U.
%Ej.: ultimo([pedro,ana,luisa],luisa).
ultimo([H|T],U):-ultimo_(T,H,U).
ultimo_([],U,U).
ultimo_([U|L],_,F):-ultimo_(L,U,F).

%penultimo(?L,?U) ← el penúltimo elemento de la lista L unifica con U.
%Ej.: penultimo([pedro,ana,luisa],ana).
penultimo(L, U) :- L = [_,_|T], penultimo_(T, L, U).
penultimo_([], [U|_], U).
penultimo_([_|T], [_|L], U):-penultimo_(T, L, U).

%diferentes(+L) ← todos los elementos de la lista L son distintos entre sí
%Ej.: diferentes([a,b,c]) 
diferentes([]).
diferentes([H|T]):- notFind(H,T), diferentes(T).

notFind(_,[]).
notFind(E,[H|T]):- E\=H , notFind(E,T).

%diferentes (+L1,+L2) ← L1 y L2 tiene el mismo largo y todos los elementos correspondientes son distintos
%Ej.: diferentes([a,b,c],[1,2,3]); diferentes([1,2,3],[3,1,2]).
diferentes([],[]).
diferentes([H1|T1],[H2|T2]):- H1\=H2 , diferentes(T1,T2).

%helper(+L,?E,?N,?L1,?L2) recibe una lista y retorna el primer elemento, la cantidad de veces que ocurre y las listas particionadas donde L=L1,L2
helper([],_,0,[],[]).
helper([H|T],E,0,[],[H|T]):-H\=E.
helper([E|T],E,N,L1,L2):-helper(T,E,X,Ln1,L2), N is X+1 , L1 = [E|Ln1].

%repeater(?N,+E,?L) recibe un Natural y un elemento y lo repite N veces, Si recibe la lista verifica y cuenta
repeater(N,E,[E|L]):- N\=0, X is N-1, repeater(X,E,L).
repeater(N,E,L):- L\=[], todos_ig(E,L,0,N).
repeater(0,_,[]).

%todos ig verifica que sean todo iguales al elemento pasado y ademas cuenta
todos_ig(E,[E|T],Ac,N):-Acc is Ac+1, todos_ig(E,T,Acc,N).
todos_ig(_,[],Ac,Ac).

%simplificada(+L1,?L2) ← L2 contiene los mismo elementos que L1 y en el mismo orden, pero con una sola instancia de elementos consecutivos repetidos en L1.
%Ej.: simplificada([a,a,a,a,b,c,c,a,d,d,b,b],[a,b,c,a,d,b]).
simplificada([H|T],[E|Ln2]):-helper([H|T],E,_,_,Laux), simplificada(Laux,Ln2).
simplificada([],[]).

%empaquetada(+L1,?L2) ← L2 contiene listas con los elementos consecutivos repetidos (ocurren 1 o más veces consecutivas) de L1, en el mismo orden
%Ej.: empaquetada([a,a,a,a,b,c,c,a,d,d,b,b],[[a,a,a,a],[b],[c,c],[a],[d,d],[b,b]]).
empaquetada([H|T],[Le|Ln2]):-helper([H|T],_,_,Le,Laux), empaquetada(Laux,Ln2).
empaquetada([],[]).

%%(Notar que comprimida y descomprimida representan la misma relación pero tienen distinto modo de instanciación de las variables)

%comprimida(+L1,?L2) ← L2 contiene pares formados por los elementos de L1, en el mismo orden y la cantidad de veces consecutivas que ocurren
%comprimida([a,a,a,a,b,c,c,a,d,d,b,b],[p(a,4),p(b,1),p(c,2),p(a,1),p(d,2),p(b,2)]).
comprimida([H|T],[p(E,N)|Ln2]):-helper([H|T],E,N,_,Laux), comprimida(Laux,Ln2).
comprimida([],[]).

%Descomprimida(?L1,+L2) ← L2 contiene pares formados por los elementos de L1, en el mismo orden y la cantidad de veces consecutivas que ocurren
%descomprimida([a,a,a,a,b,c,c,a,d,d,b,b],[p(a,4),p(b,1),p(c,2),p(a,1),p(d,2),p(b,2)]).
descomprimida(R,[p(E,C)|T]):-descomprimida(Ln1,T), repeater(C,E,L1), append(L1,Ln1,R).
descomprimida([],[]).


%######################################################################################
%2) Predicados sobre matrices
%(Se representan las matrices como listas de filas, siendo cada fila una lista de elementos.)

%matrizFija(?M,?N,+E,?A) ← A es una matriz de M filas y N columnas. Cada celda debe tener el
%valor E. La matriz se representa mediante una lista de M filas, donde cada fila es una lista de N celdas.
%Ej.: matrizFija(2,2,-10,[[-10,-10],[-10,-10]]).
matrizFija(M,N,E,A):-M\=0, N\=0, repeater(N,E,L), repeater(M,L,A).
matrizFija(M,N,E,A):-A\=[], repeater(M,L,A), repeater(N,E,L).
matrizFija(0,_,_,[]).
matrizFija(_,0,_,[]).

%valor_celda(+I,+J,+A,?E) ← E es el contenido de la celda (I,J) de la matriz A.
valor_celda(I,J,A,E):- find(I,A,F), find(J,F,E).

find(N,[_|T],E):- find(X,T,E), N is X+1.
find(1,[E|_],E).

%nuevo_valor_celda(+I,+J,+A1,+E,?A2) ← A2 es una matriz que contiene el valor E en la celda (I,J) y en el resto de las celdas contiene los mismos valores que A1.
nuevo_valor_celda(I,J,[H|T],E,[H|Ln]):- nuevo_valor_celda(X,J,T,E,Ln), I is X+1.
nuevo_valor_celda(1,J,[H|T],E,[R|T]):- replace(J,E,H,R).

replace(N,E,[H|T],[H|Ln]):- replace(X,E,T,Ln), N is X+1.
replace(1,E,[_|T],[E|T]).

%cartesiano(+V1,+V2,?M) ← V1 y V2 son vectores de igual dimensión N, M es una matriz NxN de pares (V1i,V2j) como elemento fila i y columna j de la matriz, siendo V1i el i-ésimo elemento de V1 y V2j el j-ésimo elemento de V2
%Ej.: cartesiano([1,2],[a,b],[[(1,a),(1,b)],[(2,a),(2,b)]]).
cartesiano([H|T],V2,[Ln|Mn]):- cartesiano_(H,V2,Ln), cartesiano(T,V2,Mn).
cartesiano([],_,[]).

cartesiano_(E,[H|T],[(E,H)|Ln]):- cartesiano_(E,T,Ln).
cartesiano_(_,[],[]).     

%composicion(+M1,+M2,?M) ← M es la composición elemento a elemento con el el operador '+' de las matrices de igual dimensión M1 y M2
%Ej.: composicion([[1,2],[3,4]],[[a,b],[c,d]],[[1+a,2+b],[3+c,4+d]]).
composicion([H1|T1], [H2|T2], [Ln|Mn]):- composicion_(H1,H2,Ln), composicion(T1,T2,Mn).
composicion([],_,[]).

composicion_([H1|T1], [H2|T2], [H1+H2|Ln]):- composicion_(T1,T2,Ln).
composicion_(_, [], []).



%######################################################################################
%3) Cuadrados latinos
%latino(+N,+E,?Lat) ← Lat es un cuadrado latino de orden N sobre el conjunto de elementos E
%ej.: latino(2,[a,b],[[a,b],[b,a]]).
%Se debe realizar pruebas para N creciente, comenzando en 2 y terminando en el máximo que su implementación pueda tolerar.
latino(N, E,Res):- latino_shuffle(N,E,S), trasponer(S,T), permutar(T,Res).
latino(0, _,[]).

%Genera un cuadrado latino solo haciendo shift del conjunto inicial
latino_base(N, E,[E|L]):-N\=0, X is N-1, shiftL(E,En), latino_base(X,En,L).
latino_base(0, _,[]).

latino_shuffle(N, E,Res):- latino_base(N,E,B), permutar(B,Res).

latino_shuffle_traspose(N, E,Res):- latino_shuffle(N,E,S), trasponer(S,Res).

%shiftL(+E,?S) aplica un shiftLeft al vector de entrada.
shiftL([H|T],S):-shiftL_(H,T,S).
shiftL_(H,[E|T],[E|S]):-shiftL_(H,T,S).
shiftL_(H,[],[H]).

%shiftR(+E,?S) aplica un shift right al vector de entrada.
shiftR(E,[H|S]):-shiftR_(H,E,S).
shiftR_(H,[E|T],[E|S]):-shiftR_(H,T,S).
shiftR_(H,[H],[]).

permutar([H|T], R) :- permutar(T, L), remove(H, R, L).
permutar([], []).
remove(E, [E|T], T).
remove(E, [H|T], [H|R]) :- remove(E, T, R).

validate([H|T]):- diferentes(H), validate_(H,T), validate(T).
validate([]).
validate_(E,[H|T]):- diferentes(E,H) , validate_(E,T).
validate_(_,[]).

reverse(L,R):-reverse_(L,[],R).
reverse_([H|T],Ac,Rev):- reverse_(T,[H|Ac], Rev).
reverse_([],Ac,Ac).

trasponer(M, [Ts|Tss]) :- slice(M, Ts, Mn), trasponer(Mn, Tss).
trasponer([[]|_], []).

%slice saca una rebanada de la matriz quedandose con los primeros elementos de cada fila y el resto de la matriz por otro lado
slice([[F|Os]|Rest], [F|Fs], [Os|Oss]) :- slice(Rest, Fs, Oss).
slice([], [], []).
%######################################################################################
%4) Cuadrados greco-latinos
%cuadrado_GL(+N,+L1,+L2,?C) ← C es un cuadrado grecolatino de orden N, siendo L1 y L2 los elementos de S y de T respectivamente
%ej.: cuadrado_GL(3,[a,b,c],[1,2,3],[[a+1,b+2,c+3],[c+2,a+3,b+1],[b+3,c+1,a+2]]).
%Se debe realizar pruebas para N creciente, comenzando en 2 y terminando en el máximo que su implementación pueda tolerar.
cuadrado_GL(N,L1,L2,C):- latino(N,L1,Lat1), latino(N,L2,Lat2), composicion(Lat1,Lat2,C), validate_gl(C).

validate_gl([[F|_]|Fs]):-slice(Fs,_,Resto), validate_gl(Resto), validate_gl_(F,Resto).
validate_gl([]).
validate_gl_(E,[F|_]):-notFind(E,F).
validate_gl_(_,[]).








%######################################################################################
%5)
%Work in progress
prueba_matriz(M,N,Res):-matrizFija(M,N,e,A),setAllValues(3,3,A,Res).
setAllValues(M,N,A,Res):-M\=0, N\=0,Nn is N-1, setAllValues(M,Nn,A,Nueva), setAllValuesRow(M,N,Nueva,Res).
setAllValues(_,0,A,A).

setAllValuesRow(M,N,A,Nueva):-M\=0,Mn is M-1, setAllValuesRow(Mn,N,A,Res), nuevo_valor_celda(M,N,Res,(M + N),Nueva).
setAllValuesRow(0,_,A,A).

