(* Nombre: German Faller               *)
(* Cedula: 45203047                    *)
(* Correo: german.faller@fing.edu.uy   *)

(* Nombre: Santiago Ingold             *)
(* Cedula: 50409424                    *)
(* Correo: santiago.ingold@fing.edu.uy *)

Require Import Classical.

Section Ejercicio1.

Variable U  : Set.
Variable A B: U -> Prop.
Variable P Q: Prop.
Variable R S: U -> U -> Prop.

Theorem e11 : (forall x:U, A(x)) -> forall y:U, A(y).
Proof.
intro HforAll.
apply HforAll.
Qed.

Theorem e12 : (forall x y:U, (R x y)) -> forall x y:U, (R y x).
Proof.
intros HforAll x1 x2.
apply HforAll.
Qed.

Theorem e13 : (forall x: U, ((A x)->(B x)))
                        -> (forall y:U, (A y))
                          -> (forall z:U, (B z)).
Proof.
intros Himpl HforAll z.
apply Himpl.
apply HforAll.
Qed.

End Ejercicio1.

(************************************)

Section Ejercicio2.

Variable U  : Set.
Variable A B: U -> Prop.
Variable P Q: Prop.
Variable R S: U -> U -> Prop.

Theorem e21 : (forall x:U, ((A x)-> ~(forall x:U, ~ (A x)))).
Proof.
intros x Ha.
intro HforAll.
absurd (A x).
apply HforAll.
apply Ha.
Qed.

Theorem e22 : (forall x y:U, ((R x y)))-> (forall x:U, (R x x)).
Proof.
intros HforAll x.
apply HforAll.
Qed.

Theorem e23 : (forall x:U, ((P -> (A x))))
                        -> (P -> (forall x: U, (A x))).
Proof.
intros HforAll Hp x.
apply HforAll.
apply Hp.
Qed.

Theorem e24 : (forall x:U, ((A x) /\ (B x)))
                        -> (forall x:U, (A x))
                          -> (forall x:U, (B x)).
Proof.
intros HforAllOr HforAll x.
apply HforAllOr.
Qed.

End Ejercicio2.

(************************************)

Section Ejercicio3.

Variable U   : Set.
Variable A B : U -> Prop.
Variable P Q : Prop.
Variable R S : U -> U -> Prop.

Definition H1 := forall x:U, (R x x).
Definition H2 := forall x y z:U, (R x y) /\ (R x z) -> (R y z).

Theorem e231: H1 /\ H2 -> (forall x:U, (R x x)) /\ 
                           (forall x y:U, (R x y) -> (R y x)) /\ 
                           (forall x y z:U, ((R x y) /\ (R y z)) -> (R x z)).
Proof.
intro Hand.
elim Hand.
intros ID TR.
split.
(*Prueba Identidad*)
apply ID.
split.
(*Prueba Simetria*)
intros x y Hr.
cut (R x y /\ R x x).
intro HandR.
apply (TR x y x).
assumption.
split.
assumption.
apply ID.
(*Prueba Transitiva*)
intros x y z HandR.
apply (TR y x z).
split.
cut (R x y /\ R x x).
intro HandR2.
apply (TR x y x).
assumption.
split.
apply HandR.
apply ID.
apply HandR.
Qed.

Definition Irreflexiva := forall x:U, ~(R x x).
Definition Asimetrica := forall x y:U, (R x y) -> ~(R y x).
 
Lemma e232 : Asimetrica -> Irreflexiva.
Proof.
intros HAsimetrica x Hr.
absurd (R x x).
apply (HAsimetrica).
assumption.
assumption.
Qed.

End Ejercicio3.

(************************************)

Section Ejercicio4.

Variable U : Set.
Variable A : U->Prop.
Variable R : U->U->Prop.

Theorem e41: (exists x:U, exists y:U, (R x y)) -> exists y:U, exists x:U, (R x y).
Proof.
intro Hexi; elim Hexi.
intros x1 Hexi2; elim Hexi2.
intros x2 Hr.
exists x2.
exists x1.
assumption.
Qed.

Theorem e42: (forall x:U, A(x)) -> ~ exists x:U, ~ A(x).
Proof.
intros HforAll Hexi.
elim Hexi.
intros x HnA.
elim HnA.
apply HforAll.
Qed.

Theorem e43: (exists x:U, ~(A x)) -> ~(forall x:U, (A x)).
Proof.
intros Hexi HforAll.
elim Hexi.
intros x HnA.
elim HnA.
apply HforAll.
Qed.

End Ejercicio4.

(************************************)

Section Ejercicio5.

Variable nat      : Set.
Variable S        : nat -> nat.
Variable a b c    : nat.
Variable odd even : nat -> Prop.
Variable P Q      : nat -> Prop.
Variable f        : nat -> nat.

Theorem e51: forall x:nat, exists y:nat, (P(x)->P(y)).
Proof.
intro x.
exists x.
intro; assumption.
Qed.

Theorem e52: exists x:nat, (P x)
                            -> (forall y:nat, (P y)->(Q y))
                               -> (exists z:nat, (Q z)).
Proof.
exists (a).
intros Hp HforAll.
exists (a).
apply HforAll.
assumption.
Qed.

Theorem e53: even(a) -> (forall x:nat, (even(x)->odd (S(x)))) -> exists y: nat, odd(y).
Proof.
intro H_even.
intro HforAll.
exists (S a).
apply HforAll.
apply H_even.
Qed.

Theorem e54: (forall x:nat, P(x) /\ odd(x) ->even(f(x)))
                            -> (forall x:nat, even(x)->odd(S(x)))
                            -> even(a)
                            -> P(S(a))
                            -> exists z:nat, even(f(z)).
Proof.
intros HforAll HforAll2 Heven Hps.
exists (S a).
apply HforAll.
split.
assumption.
apply HforAll2.
assumption.
Qed.

End Ejercicio5.

(************************************)

Section Ejercicio6.

Variable nat : Set.
Variable S   : nat -> nat.
Variable le  : nat -> nat -> Prop.
Variable f   : nat -> nat.
Variable P   : nat -> Prop.

Axiom le_n: forall n:nat, (le n n).
Axiom le_S: forall n m:nat, (le n m) -> (le n (S m)).
Axiom monoticity: forall n m:nat, (le n m) -> (le (f n) (f m)).


Lemma le_x_Sx: forall x:nat, (le x (S x)).
Proof.
intro x.
apply (le_S x x).
apply le_n.
Qed.

Lemma le_x_SSx: forall x:nat, (le x (S (S x))).
Proof.
intro x.
apply (le_S x (S x)).
apply (le_S x x).
apply le_n.
Qed.

Theorem T1: forall a:nat, exists b:nat, (le (f a) b).
Proof.
intro a.
exists (f a).
apply le_n.
Qed.

Theorem T1_a: forall a:nat, exists b:nat, (le (f a) b).
Proof.
intro a.
exists ((S (S (S (S (S (f a))))))).
repeat apply le_S.
apply le_n.
Qed.

Theorem T1_b: forall a:nat, exists b:nat, (le (f a) b).
Proof.
intro a.
exists ((S (S (S (S (S (f a))))))).
do 5 apply le_S.
apply le_n.
Qed.

End Ejercicio6.

(************************************)

Section Ejercicio7.

Variable U   : Set.
Variable A B : U -> Prop.

Theorem e71: (forall x:U, ((A x) /\ (B x)))
                       -> (forall x:U, (A x)) /\ (forall x:U, (B x)).
Proof.
intro; split; do 2 apply H.
Qed.

Theorem e72: (exists x:U, (A x \/ B x))->(exists x:U, A x )\/(exists x:U, B x).
Proof.
intro Hexi; elim Hexi; intros x Hor; elim Hor; intro Hx; [left | right]; exists x; assumption.
Qed.

Theorem e73: (forall x:U, A x) \/ (forall y:U, B y) -> forall z:U, A z \/ B z.
Proof.
intro H_forallxy; intro z; elim H_forallxy; intro Hforall; [left | right]; apply Hforall.
Qed.

End Ejercicio7.

(************************************)

Section Ejercicio8.

Variable U : Set.
Variable R : U -> U -> Prop.
Variable T V : U ->Prop.

Theorem ej81: (exists y:U, forall x:U, (R x y)) -> forall x:U, exists y:U, (R x y).
Proof.
intro HExi.
elim HExi.
intros x1 HforAll x2.
exists x1.
apply (HforAll x2).
Qed.

Theorem T282:(exists y:U, True)/\(forall x:U, (T x) \/ (V x)) ->
                        (exists z:U, (T z)) \/ (exists w:U, (V w)). 
Proof.
intro Hpre; elim Hpre.
intros Hexi HforAll.
elim Hexi.
intros x Htrue.

elim (HforAll x); 
intro Hx; [left | right]; 
exists x; assumption.
Qed.

(* Parte 2.8.3
Es necesario el "(exists y:U, True)" dado que en la logica
constructiva una estructura del tipo "forall x -> exists y" 
requiere de la existencia de al menos un elemento x, por lo 
que no es valido para conjuntos vacios.
*)

End Ejercicio8.

(************************************)

Section Ejercicio9.

Variables U : Set.
Variables A : U -> Prop.

Lemma not_ex_not_forall: (~exists x :U, ~A x) -> (forall x:U, A x).
Proof.
intros Hnot x.
elim (classic (A x)); intro;
[| elim Hnot; exists x];assumption.
Qed.

Lemma not_forall_ex_not: (~forall x :U, A x) -> (exists x:U,  ~A x).
Proof.
intro HnotForAll.
elim (classic (exists x:U,  ~A x));intro;
[| elim HnotForAll; intro x; elim (classic (A x)); intro;
[| elim H; exists x]]; assumption.
Qed.

End Ejercicio9.

(************************************)

Section Sec_Peano. 

Variable nat : Set.
Variable  O  : nat.
Variable  S  : nat -> nat.

Axiom disc   : forall n:nat, ~O=(S n).
Axiom inj    : forall n m:nat, (S n)=(S m) -> n=m.
Axiom allNat : forall n: nat, n = O \/ exists m: nat, S m = n.

Variable sum prod : nat->nat->nat.

Axiom sum0   : forall n :nat, (sum n O)=n. (*Con esto decimos que O es 0*)
Axiom sumS   : forall n m :nat, (sum n (S m))=(S (sum n m)).
Axiom prod0  : forall n :nat, (prod n O)=O. (*Con esto decimos que O es 0*)
Axiom prodS  : forall n m :nat, (prod n (S m))=(sum n (prod n m)).

Section Ejercicio10.

Lemma L10_1: (sum (S O) (S O)) = (S (S O)).
Proof.
rewrite -> sumS.
rewrite -> sum0.
reflexivity.
Qed.

Lemma L10_2: forall n :nat, ~(O=n /\ (exists m :nat, n = (S m))).
Proof.
intros n Hor.
elim Hor.
intros Hn Hexi.
elim Hexi.
intros x Hs.
absurd (O = S x); [ apply (disc x) | rewrite Hn; assumption].
Qed.

Lemma prod_neutro: forall n :nat, (prod n (S O)) = n.
Proof.
intro n.
rewrite -> prodS.
rewrite -> prod0.
apply sum0.
Qed.

Lemma diff: forall n:nat, ~(S (S n))=(S O).
Proof.
intros n Hs.
absurd (O=S n); [apply disc | symmetry; apply inj; assumption].
Qed.

Lemma L10_3: forall n: nat, exists m: nat, prod n (S m) = sum n n.
Proof.
intro n.
exists (S O).
rewrite prodS.
rewrite prod_neutro.
reflexivity.
Qed.

Lemma L10_4Aux: forall m n: nat, sum m n = O -> n=O.
Proof.
intros m n Hsum; elim (allNat n); intro Hn0.
assumption.
absurd (O<>sum m n).
intro Hnot; elim Hnot; symmetry; assumption.
elim Hn0; intros x Hs; rewrite <- Hs; rewrite -> sumS; apply disc.
Qed.

Lemma L10_4: forall m n: nat, n <> O -> sum m n <> O.
Proof.
intros m n HnN0 Hsum.
absurd (n=O).
assumption.
apply (L10_4Aux m n Hsum).
Qed.

Lemma L10_5Aux: forall m n: nat, sum m n = O -> m=O.
Proof.
intros m n Hs; elim (allNat n); intros HallNat.

rewrite <- Hs.
rewrite -> HallNat.
symmetry.
apply sum0.

absurd (O<>sum m n).
intro Hnot; elim Hnot; symmetry; assumption.

elim HallNat.
intros x Hsuc.

rewrite <- Hsuc.
rewrite -> sumS.
apply disc.
Qed.

Lemma L10_5: forall m n: nat, sum m n = O -> m = O /\ n = O.
Proof.
intros m n Hs.
split; [ apply (L10_5Aux m n Hs) | apply (L10_4Aux m n Hs) ].
Qed.


End Ejercicio10.

(************************************)

Variable le : nat->nat->Prop.
Axiom leinv: forall n m:nat, (le n m) -> n=O \/
      (exists p:nat, (exists q:nat, n=(S p)/\ m=(S q) /\ (le p q))).

Section Ejercicio11.



Lemma notle_s_o: forall n:nat, ~(le (S n) O).
Proof.
intro n; intro HleS.
elim (leinv (S n) O); intros.

(************************************)
absurd (O = S n).
apply disc.
symmetry; exact H.
(************************************)
elim H; intros x1 Hexiq.

elim (Hexiq ); intros x2 Hand3.

elim Hand3; intros Hsx1 Hand2.

elim Hand2; intros Hsx2.

absurd (O = S x2); [ apply disc | ]; assumption.

(************************************)

assumption.
Qed.

End Ejercicio11.
End Sec_Peano. 
