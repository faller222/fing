(* Nombre: German Faller               *)
(* Cedula: 45203047                    *)
(* Correo: german.faller@fing.edu.uy   *)

(* Nombre: Santiago Ingold             *)
(* Cedula: 50409424                    *)
(* Correo: santiago.ingold@fing.edu.uy *)

Section Ejercicio1.
(**)
(**)
(**)
End Ejercicio1.

(************************************)

Section Ejercicio2.
(**)
(**)
(**)
End Ejercicio2.

(************************************)

Section Ejercicio3.
Variable A B C: Set.
Definition apply (f:A->B)(a:A) := (f a).
Definition o (f:A->B)(g:B->C)(a:A) := (g (f a)).
Definition twice (f:A->A)(a:A) := (f (f a)).

Definition applyU (A:Set)(B:Set)(f:A->B)(a:A) := f a.
Definition oU (A:Set)(B:Set)(C:Set)(f:A->B) (g:B->C) (a:A) := (g (f a)).
Definition twiceU (A:Set) (f:A->A) (a:A) := (f (f a)).
End Ejercicio3.

(************************************)

Section Ejercicio4.
Variable A: Set.

Definition id (a:A) := a.

Theorem e4a : forall x:A, (o A A A id id x) = id x.
Proof.
cbv delta.
simpl.
reflexivity.
Qed.


Theorem e4b : forall x:A, (o A A A id id x) = id x.
Proof.
cbv delta.
cbv beta.
reflexivity.
Qed.


Theorem e4c : forall x:A, (o A A A id id x) = id x.
Proof.
compute.
reflexivity.
Qed.

Theorem e4d : forall x:A, (o A A A id id x) = id x.
Proof.
unfold id.
compute.
reflexivity.
Qed.

Theorem e4e : forall x:A, (o A A A id id x) = id x.
Proof.
cbv delta beta.
reflexivity.
Qed.

Theorem e4f : forall x:A, (o A A A id id x) = id x.
Proof.
lazy.
reflexivity.
Qed.
End Ejercicio4.

(************************************)

Section Ejercicio5.

(* 5.1 *)
Definition opI (A:Set)(x:A) := x.
Definition opK (A:Set)(B:Set)(x:A)(y:B) := x.
Definition opS (A:Set)(B:Set)(C:Set)(f: A->B->C) (g: A->B) (x: A):= ((f x) (g x)).
Check opK.

(* 5.2 *)
(* Para formalizar el siguiente lema, determine los tipos ?1 ... ?8 adecuados *)
Lemma e52 : forall A B : Set, opS A (B->A) A (opK A (B->A)) (opK A B) = opI A.
Proof.
cbv delta beta.
reflexivity.
Qed.

End Ejercicio5.

(************************************)

Section Ejercicio6.
Definition N := forall X : Set, X -> (X -> X) -> X.
Definition Zero (X : Set) (o : X) (f : X -> X) := o.
Definition Uno  (X : Set) (o : X) (f : X -> X) := f (Zero X o f).

(* 6.1 *)
Definition Dos  (X:Set) (o:X) (f:X->X) := f (Uno X o f).

(* 6.2 *)
Definition Succ (n:N) (X:Set) (o:X) (f: X -> X) := f (n X o f).

Lemma succUno : Succ Uno = Dos.
Proof.
compute.
reflexivity.
Qed.

(* 6.3 *)
Definition Plus (n m : N) : N
                := fun (X : Set) (o : X) (f : X -> X) => n X (m X o f) f.


Infix "+++" := Plus (left associativity, at level 94).

Lemma suma1: (Uno +++ Zero) = Uno.
Proof.
compute.
reflexivity.
Qed.

Lemma suma2: (Uno +++ Uno) = Dos.
Proof.
compute.
reflexivity.
Qed.

(* 6.4 *)
Definition Prod (n m : N) : N
                := fun (X:Set) (o:X) (f:X->X) => m X o (fun y:X => n X y f).


Infix "**" := Prod (left associativity, at level 666).

(* 6.5 *)
Lemma prod1 : (Uno ** Zero) = Zero.
Proof.
compute.
reflexivity.
Qed.

Lemma prod2: (Uno ** Dos) = Dos.
Proof.
compute.
reflexivity.
Qed.

End Ejercicio6.

(************************************)

Section Ejercicio7.
(* 7.1 *)

Definition Bool := forall A:Set , A -> A -> A.
Definition t (A:Set)(tru _:A) := tru.
Definition f (A:Set)(_ fls:A) := fls.

(* 7.2 *)
Definition If (b th e:Bool)(A:Set) := b (A -> A -> A) (th A)(e A ).

(* 7.3 *)
Definition Not (b:Bool)(A:Set) := b (A -> A -> A) (f A)(t A).

Lemma CorrecNot : (Not t) = f /\ (Not f) = t.
Proof.
split;
reflexivity.
Qed.

(* 7.4 *)
Definition And (a b:Bool) (A:Set):= b (A -> A -> A) (a (A -> A -> A) (t A) (f A)) (f A).
Definition And' (a b:Bool) (A:Set):= a (A -> A -> A) (b (A -> A -> A) (t A) (f A)) (f A).


(* 7.5 *)
Infix "&" := And (left associativity, at level 666).

Lemma CorrecAnd : (t & t) = t /\ (f & t) = f /\ (t & f) = f.
Proof.
compute.
split;[ | split];reflexivity.
Qed.

End Ejercicio7.

(************************************)

(* Ejercicio8 *)
Section ArrayNat.
Parameter ArrayNat : forall n:nat, Set.
Parameter empty    : ArrayNat 0.
Parameter add      : forall n:nat, nat -> ArrayNat n -> ArrayNat (n + 1).

(* 8.1 *)
Check(add 0 (S 0) empty).

(* 8.2 *)
Definition ceros := add 1 0 (add 0 0 empty).
Check ceros.
Definition L4 := add 3 (1) (add 2 (0) (add 1 (1) (add 0 (0) empty))).
Check L4.

(* 8.3 *)
Parameter Concat : forall n m:nat, ArrayNat n -> ArrayNat m -> ArrayNat (plus n m).

(* 8.4 *)
Parameter Zip : forall n:nat, ArrayNat n -> ArrayNat n -> (nat->nat->nat) -> ArrayNat n.

(* 8.5 *)
Check ArrayNat.

(* 8.6 *)
Parameter Array' : forall (A:Set) (n:nat), Set.
Check Array'.
Parameter empty' : forall A:Set, Array' A 0.
Parameter add'   : forall (A:Set) (n:nat), A -> Array' A n -> Array' A (n+1).
Parameter Zip'   : forall (A:Set) (n:nat), Array' A n -> Array' A n -> (A->A->A) -> Array' A n.

(* 8.7 *)
Parameter ArrayBool : forall (n:nat), Array' bool n .

End ArrayNat.

(************************************)

Section Ejercicio9.
Parameter MatrizNat : forall (f c:nat), Set.
Parameter prod : forall (f i c: nat), MatrizNat f i -> MatrizNat i c -> MatrizNat f c.
Parameter es_id : forall (f c:nat), MatrizNat f c -> bool.
Parameter ins_fila : forall (f c:nat), MatrizNat f c -> ArrayNat c -> MatrizNat (S f) c.
Parameter ins_columna : forall (f c: nat), MatrizNat f c -> ArrayNat f -> MatrizNat f (S c).
Check MatrizNat.

End Ejercicio9.

(************************************)

Section Ejercicio10.

Parameter Array : Set -> nat -> Set.
Parameter emptyA : forall X : Set, Array X 0.
Parameter addA : forall (X : Set) (n : nat), X -> Array X n -> Array X (S n).

Parameter Matrix : Set -> nat -> Set.
Parameter emptyM : forall X : Set, Matrix X 0.
Parameter addM : forall (X:Set) (n:nat), Matrix X n -> Array X (S n) -> Matrix X (S n).

Definition A1 := addA nat 0 1 (emptyA nat).
Definition A2 := addA nat 1 2 (A1).
Definition A3 := addA nat 2 3 (A2).

Definition M1 := addM nat 0 (emptyM nat) (A1).
Definition M2 := addM nat 1 M1 (A2).
Definition M3 := addM nat 2 M2 (A3).

Check M3.

End Ejercicio10.

(************************************)

Section Ejercicio11.
Parameter ABNat : forall n:nat,Set.
Parameter emptyAB : ABNat 0.
Parameter addAB: forall n:nat, nat -> ABNat n -> ABNat n -> ABNat (S n).

Definition arbolAux := addAB 0 7 emptyAB emptyAB.
Definition arbol := addAB 1 3 arbolAux arbolAux.
Check arbol.
Parameter ABNatU : forall (n:nat)(X:Set), Set.
Parameter emptyABU : forall(X:Set), ABNatU 0 X.
Parameter addABU : forall (n:nat)(X:Set), nat -> ABNatU n X -> ABNatU n X -> ABNatU (S n) X.
End Ejercicio11.

(************************************)

Section Ejercicio12.
Parameter AVLNat : forall n:nat,Set.
Parameter emptyAVL : AVLNat 0.

Parameter addAVL1: forall n:nat, nat -> AVLNat n -> AVLNat n -> AVLNat (S n).
Parameter addAVL2: forall n:nat, nat -> AVLNat n -> AVLNat (S n) -> AVLNat (S(S n)).
Parameter addAVL3: forall n:nat, nat -> AVLNat (S n) -> AVLNat n -> AVLNat (S(S n)).


Definition arbolAVL := addAVL2 0 22 emptyAVL (addAVL1 0 1 emptyAVL emptyAVL).
Check arbolAVL.

Parameter AVLNatU : forall (n:nat)(X:Set), Set.
Parameter emptyAVLU : forall(X:Set), AVLNatU 0 X.

Parameter addAVLU1: forall (n:nat)(X:Set), nat -> AVLNatU n X -> AVLNatU n X -> AVLNatU (S n) X.
Parameter addAVLU2: forall (n:nat)(X:Set), nat -> AVLNatU n X -> AVLNatU (S n) X -> AVLNatU (S(S n)) X.
Parameter addAVLU3: forall (n:nat)(X:Set), nat -> AVLNatU (S n) X -> AVLNatU n X -> AVLNatU (S(S n)) X.
Check addAVLU1.

End Ejercicio12.

(************************************)

Section Ejercicio13.
Variable A B C: Set.

Lemma e13_1 : (A -> B -> C) -> B -> A -> C.
Proof.
exact(fun (f:A->B->C) (b:B) (a:A) => f a b).
Qed.

Lemma e13_2 : (A -> B) -> (B -> C) -> A -> C.
Proof.
exact(fun (f:A->B)(g:B->C) (a:A) => g(f(a))).
Qed.

Lemma e13_3 : (A -> B -> C) -> (B -> A) -> B -> C.
Proof.
exact(fun (f:A->B->C)(g:B->A) (b:B) => f (g b) b).
Qed.

End Ejercicio13.

(************************************)

Section Ejercicio14.
Variable A B C: Prop.

Lemma Ej314_1 : (A -> B -> C) -> A -> (A -> C) -> B -> C.
Proof.
  intros f a g b.
  exact(f a b).
Qed.

Lemma Ej314_2 : A -> ~ ~ A.
Proof.
  unfold not.
  intros.
  exact(H0 H).
Qed.

Lemma Ej314_3 : (A -> B -> C) -> A -> B -> C.
Proof.
   exact(fun (x:A->B->C) => x).
Qed.

Lemma Ej314_4 : (A -> B) -> ~ (A /\ ~ B).
Proof.
  unfold not.
  intros.
  elim H0; intros.
  exact(H2 (H H1)).
Qed.

End Ejercicio14.

(************************************)

Section Ejercicio15.

Variable U : Set.
Variable e : U.
Variable A B : U -> Prop.
Variable P : Prop.
Variable R : U -> U -> Prop.

Lemma Ej315_1 : (forall x : U, A x -> B x) -> (forall x : U, A x) ->
forall x : U, B x.
Proof.
  intros.
  exact(H x (H0 x)).
Qed.

Lemma Ej315_2 : forall x : U, A x -> ~ (forall x : U, ~ A x).
Proof.
  unfold not.
  intros.
  exact(H0 x H).
Qed.

Lemma Ej315_3 : (forall x : U, P -> A x) -> P -> forall x : U, A x.
Proof.
exact( fun(f:forall x : U, P -> A x) (p:P) (y:U) => (f y p)).
Qed.

Lemma Ej315_4 : (forall x y : U, R x y) -> forall x : U, R x x.
Proof.
     exact(fun(f: forall x y : U, R x y) (x:U) => (f x x)).
Qed.

Lemma Ej315_5 : (forall x y: U, R x y -> R y x) ->
                 forall z : U, R e z -> R z e.
Proof.
     exact(fun (f:forall x y:U, R x y -> R y x) (z:U) (r: R e z) => (f e z r)).
Qed.

End Ejercicio15.

