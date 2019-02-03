:- module(graficos,
[

 gr_crear_tablero/4, % +Filas +Columnas +Botones -Ventana
 % Crea la ventana con el tamaño de tablero indicado.

 gr_inicializar_tablero/3, % +Ventana +Filas +Columnas
 % Crea un tablero del tamaño indicado.

 gr_destruir/1, % +Ventana
 % Cierra la ventana e invalida el handle.

 gr_dibujar_casillero/5, %+Ventana  +Fila  +Columna  +SumHoriz  +SumVert
 % Dibuja un casillero negro, potencialmente con restricciones de suma de fila o columna.

 gr_dibujar_numero/4,  % +Ventana +Fila +Col +N
 % Dibuja un número sobre un casillero blanco.

 gr_eliminar_numero/3,  % +Ventana +Fila +Col
 % Elimina un número de un casillero blanco.
 
 gr_marcar_seleccion/3, % +Ventana +Fila +Columna
 % Dibuja el marcador de seleccion en la casilla (Fila, Columna) de la ventana Ventana.

 gr_desmarcar_seleccion/3, % +Ventana +Fila +Columna
 % Remueve el marcador de seleccion en la casilla (Fila, Columna) de la ventana Ventana.
 
 gr_marcar_error/3, % +Ventana +Fila +Columna
 % Dibuja el marcador de error en la casilla (Fila, Columna) de la ventana Ventana.

 gr_evento/2, % +Ventana ?Evento
 % Devuelve en Evento la acción del usuario, que puede ser 
 % click(Fila, Col), salir o
 % el del boton accionado.

 gr_opciones/4, % +Ventana +Pregunta +Opciones ?Respuesta
 % Muestra una ventanita conteniendo Pregunta
 % y un botón por cada elemento de Opciones,
 % para que elija el usuario.
 % Se regresa del predicado cuando el usuario selecciona un botón,
 % devolviendo el elegido en Respuesta
 % El predicado falla si se cierra el dialogo.

 gr_estado/2 , % +Ventana +NuevoEstado
 % Muestra el NuevoEstado de la partida en la parte inferior 
 % de la pantalla.

 gr_purgar/0
 % Cierra todas las ventanas que pueden haber quedado abiertas
 % por fallos del programa. Hack, usar solo en desarrollo.

]).


:- use_module(library(tabular)).
:- use_module(library(autowin)).
:- use_module(library(dragdrop)).

%La clase root.
:- pce_begin_class(my_frame, frame).

variable(queue, any, both).
variable(image, image, both).
variable(dialog, dialog, both).

%Desactivamos que el usuario la cierre.
%En su lugar, mandamos un mensaje salir.
wm_delete(Self) :->
    get(Self, queue, Queue),
    thread_send_message(Queue, salir).

:- pce_end_class.

tam_casilla(F, C) :-
    archivo_gif(blanco,ArchB),
    new(Vacio,image(ArchB)),
    get(Vacio, size, Size),
    get(Size, height, F),
    get(Size, width, C).

click_tablero(Q, Punto) :-
    get(Punto, y, FV),
    get(Punto, x, CV),
    tam_casilla(FC, CC),
    F is 1 + FV // FC,
    C is 1 + CV // CC,
    thread_send_message(Q, click(F, C)).

:- send(class(my_frame), record_instances).
gr_purgar :-
    get(class(my_frame), instances, I),
    send(I, for_all, message(@arg1, destroy)).


gr_destruir(Ventana) :-
    get(Ventana, queue, Q),
    message_queue_destroy(Q),
    send(Ventana, destroy).


gr_add_grafico_casilla(Ventana, F, C, Arch) :-
    add_grafico_casilla(Ventana, F, C, Arch, _).


add_grafico_casilla(Ventana, F, C, Arch, I) :-
    tam_casilla(SF, SC),
    F3 is (F-1)*SF,
    C3 is (C-1)*SC,
    new(Image,image(Arch)),
    get(Ventana, image, I),
    send(I, draw_in, bitmap(Image), point(C3, F3)),
    send(Ventana, flush),
    !.


gr_dibujar_numero(Ventana, Fila, Col, N):-
    archivo_gif(N, ArchN),
    add_grafico_casilla(Ventana, Fila, Col, ArchN, _).


gr_eliminar_numero(Ventana, Fila, Col):-
    archivo_gif(blanco, ArchBlanco),
    add_grafico_casilla(Ventana, Fila, Col, ArchBlanco, _).


sum_max(45).
sum_min(1).
sum_valida(Sum):-
    sum_min(Min),
    sum_max(Max), 
    between(Min,Max,Sum).

archivo_gif(restriccion, 'fondo_gris.gif'):-!.
archivo_gif(blanco, 'fondo_blanco.gif'):-!.
archivo_gif(select, 'select.gif'):-!.
archivo_gif(unselect, 'unselect.gif'):-!.
archivo_gif(error, 'error.gif'):-!.
archivo_gif(x, 'x.gif'):-!.
archivo_gif(N, ArchN):-
    between(1,9,N),!,
    atom_concat(N, '.gif', ArchN).
    

    

tam_texto(Texto, H, W) :-
    get(Texto, size, Size),
    get(Size, height, H),
    get(Size, width, W).

fondo_casillero(ValorH, ValorV, FondoGif):-
    ((sum_valida(ValorH); sum_valida(ValorV); (ValorH is 0, ValorV is 0)) -> 
        TipoFondo= restriccion; TipoFondo=blanco),        
    archivo_gif(TipoFondo, FondoGif).

gr_dibujar_casillero(Ventana, F, C, SumH, SumV) :-
    tam_casilla(SF, SC),
    F3 is (F-1)*SF,
    C3 is (C-1)*SC,
    
    %dibujo el fondo del casillero
    fondo_casillero(SumH, SumV, ArchFondo),
    add_grafico_casilla(Ventana, F, C, ArchFondo, I),

    %dibujo suma horizontal
    (sum_valida(SumH) -> (
        new(TxtH, text(SumH,center)),
        tam_texto(TxtH, Hh, Wh),
        send(I, draw_in, TxtH, point(C3+SC-Wh, F3+(SF-Hh)/2))); true),

    %dibujo suma vertical
    (sum_valida(SumV) -> (
        new(TxtV, text(SumV, center)),
        tam_texto(TxtV, Hv, Wv),
        send(I, draw_in, TxtV, point(C3+(SC-Wv)/2, F3+SF-Hv))); true),

    send(Ventana, flush),
    !.

limpiar_casillas(Ventana,Filas,Cols):-
    between(1, Filas, Fila),
    between(1, Cols, Col),
    gr_dibujar_casillero(Ventana, Fila, Col, -1, -1),
    fail.
limpiar_casillas(_,_,_).



dibujar_numeros(_,_,_,[]):-!.
dibujar_numeros(Ventana,Fila,Col,[N|Ns]):-
    gr_dibujar_numero(Ventana, Fila, Col, N),
    Col2 is Col+1,
    !,
    dibujar_numeros(Ventana,Fila,Col2,Ns).

 




gr_crear_tablero(F, C, Botones, Frame) :-
    message_queue_create(Q),
    new(Frame, my_frame('KAKURO')),
    new(W, auto_sized_dialog),

    send(Frame, can_resize,    @off),
    forall(member(boton(Txt, Val), Botones),
           send(W, append, 
            button(Txt, 
               message(@prolog,
                   thread_send_message,
                   prolog(Q),
                   prolog(Val))))),
    send(W, max_size, size(1000, 1200)),
    new(I, image(kind := pixmap)),
    new(B, bitmap(I)),
    send(B, recogniser, 
         click_gesture(left, '', single, 
                  message(@prolog,
                      click_tablero,
                      prolog(Q),
                      @event?position))),
    send(W, append, B),
    send(W, append, label(reporter)),
    send(Frame, append, W),
    send(Frame, queue, prolog(Q)),
    send(Frame, image, I),
    send(Frame, dialog, W),
    gr_inicializar_tablero(Frame,F,C),
    send(Frame, open).


gr_inicializar_tablero(Ventana, F, C) :-
    tam_casilla(SF, SC),
    MF is SF*(F+2),
    max(C,10,Cols),
    MC is SC*Cols,
    get(Ventana, image, I),
    send(I, resize, MC, MF),
    get(Ventana, dialog, W),
    send(W, redraw),
    send(W, fit),
    FNums is F+2,
    dibujar_numeros(Ventana,FNums,1,[1,2,3,4,5,6,7,8,9]),
    gr_dibujar_numero(Ventana,FNums,10,x),
    limpiar_casillas(Ventana,F,C),
    !.

max(X,Y,X):-
    X > Y,!.
max(X,Y,Y):-
    X =< Y.

gr_desmarcar_seleccion(Ventana, F, C):-
    archivo_gif(unselect, ArchSelect),
    add_grafico_casilla(Ventana, F, C, ArchSelect,_).

gr_marcar_seleccion(Ventana, F, C):-
    archivo_gif(select, ArchUnselect),
    add_grafico_casilla(Ventana, F, C, ArchUnselect,_).

gr_marcar_error(Ventana, F, C):-
    archivo_gif(error, ArchError),
    add_grafico_casilla(Ventana, F, C, ArchError,_).

%%%%%%%%%%%%%%%%%%%%%%%%%


gr_evento(Ventana, Input) :-
    get(Ventana, queue, Q),
    thread_get_message(Q, Aux),
    !,
    Input = Aux.

gr_mensaje(V, Texto) :-
    new(D, dialog('Mensaje')),
    send(D, transient_for, V),
    send(D, append, label(lab, Texto)),
    send(D, append, button(ok,
                   message(D, return, @nil))),
    send(D, default_button, ok), % Ok: default button
    (   get(D, confirm, _Answer) % This blocks!
    ->  send(D, destroy)
    ;   true
    ).

gr_pregunta(V, Preg, Resp) :-
    new(D, dialog('Pregunta')),
    send(D, transient_for, V),
        send(D, append,
             label(lab, Preg)),
    send(D, append,
             new(TI, text_item('', ''))),
        send(D, append,
             button(ok, message(D, return,
                                TI?selection))),
        send(D, default_button, ok), % Ok: default button
        get(D, confirm, Answer),     % This blocks!
        send(D, destroy),
    Answer = Resp.

gr_opciones(V, Texto, Opciones, Resp) :-
    new(D, dialog('Opciones')),
    send(D, transient_for, V),
    send(D, append, label(lab, Texto)),
    forall(member(O, Opciones),
           send(D, append, button(O,
                   message(D, return, O)))),
    get(D, confirm,Answer),
    send(D, destroy),
    Resp = Answer.

gr_estado(MV,NuevoEstado) :-
    send(MV, report, progress,'%s',NuevoEstado).
