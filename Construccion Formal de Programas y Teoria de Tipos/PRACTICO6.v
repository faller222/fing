(* Nombre: German Faller               *)
(* Cedula: 45203047                    *)
(* Correo: german.faller@fing.edu.uy   *)

(* Nombre: Santiago Ingold             *)
(* Cedula: 50409424                    *)
(* Correo: santiago.ingold@fing.edu.uy *)

Require Import Omega.
Require Extraction.
Require Import FunInd FMapInterface.

(* Lenguaje de extraccion general *)
Extraction Language Haskell.

(* Ejercicio 1 *)
(* Parte 1 *)
Lemma predspec : forall n : nat, {m : nat | n = 0 /\ m = 0 \/ n = S m}.
Proof.
destruct n; [exists 0|exists n]; omega.
Qed.

(* Parte 2 *)
Extraction "XXpredecesor" predspec.

(* Parte 3 *)
(* Ver el archivo generado *)

(* Ejercicio 2 *)
Inductive bintree (X:Set) : Set :=
  | emptyBinTree :bintree X
  | consBinTree: X -> bintree X -> bintree X -> bintree X.

Inductive mirror (A : Set) : bintree A -> bintree A -> Prop :=
  | mirror_empty : mirror A (emptyBinTree A) (emptyBinTree A)
  | mirror_node :
      forall (a : A) (t11 t12 t21 t22 : bintree A),
      mirror A t11 t22 ->
      mirror A t12 t21 ->
      mirror A (consBinTree A a t11 t12) (consBinTree A a t21 t22).

Fixpoint inverse(A:Set)(b:bintree A):bintree A :=
    match b with
       | emptyBinTree _ => emptyBinTree A
       | consBinTree _ a t1 t2 => (consBinTree A a (inverse A t2) (inverse A t1))
    end.

(* Parte 1 *)
Lemma MirrorC: forall (A:Set) (t:bintree A), {t':bintree A|(mirror A t t')}.
Proof.
induction t; [|inversion IHt1; inversion IHt2 ].
exists (emptyBinTree A); constructor.
exists (consBinTree A x x1 x0); constructor; assumption.
Qed.

(* Parte 2 *)
Hint Constructors mirror.
Functional Scheme inverse_ind := Induction for inverse Sort Prop.

Lemma MirrorD : forall (A : Set) (t : bintree A), {t2 : bintree A | mirror A t t2}.
Proof.
intros A t.
exists (inverse A t).
functional induction inverse A t; auto.
Qed.

(* Parte 3 *)
Extraction "XXmirror_function1" MirrorC.
Extraction "XXmirror_function2" MirrorD.

(* Ejercicio 3 *)
Definition Value := bool.

Inductive BoolExpr : Set :=
  | bbool : bool -> BoolExpr
  | band : BoolExpr -> BoolExpr -> BoolExpr
  | bnot : BoolExpr -> BoolExpr.
Inductive BEval : BoolExpr -> Value -> Prop :=
  | ebool : forall b : bool, BEval (bbool b) (b:Value)
  | eandl : forall e1 e2 : BoolExpr, BEval e1 false -> BEval (band e1 e2) false
  | eandr : forall e1 e2 : BoolExpr, BEval e2 false -> BEval (band e1 e2) false
  | eandrl : forall e1 e2 : BoolExpr, BEval e1 true -> BEval e2 true -> BEval (band e1 e2) true
  | enott : forall e : BoolExpr, BEval e true -> BEval (bnot e) false
  | enotf : forall e : BoolExpr, BEval e false -> BEval (bnot e) true.
Fixpoint beval1 (e : BoolExpr) : Value :=
  match e with
    | bbool b => b
    | band e1 e2 =>
            match beval1 e1, beval1 e2 with
              | true, true => true
              | _, _ => false
            end
    | bnot e1 => if beval1 e1 then false else true
  end.
Fixpoint beval2 (e : BoolExpr) : Value :=
  match e with
    | bbool b => b
    | band e1 e2 => match beval2 e1 with
                      | false => false
                      | _ => beval2 e2
                    end
    | bnot e1 => if beval2 e1 then false else true
  end.

(* Parte 1 *)
Functional Scheme beval1_ind:=Induction for beval1 Sort Set.
Functional Scheme beval2_ind:=Induction for beval2 Sort Set.

Lemma beval1C : forall e:BoolExpr, {b:Value |(BEval e b)}.
Proof.
intros.
exists (beval1 e).
functional induction (beval1 e).

- constructor.
- constructor; [rewrite e3 in IHv| rewrite e4 in IHv0]; assumption.
- constructor 3; rewrite e4 in IHv0; assumption.
- constructor; rewrite e3 in IHv; assumption.
- constructor; rewrite e2 in IHv; assumption.
- constructor; rewrite e2 in IHv; assumption.
Qed.

Lemma beval2C : forall e:BoolExpr, {b:Value |(BEval e b)}.
Proof.
intros.
exists (beval2 e).
functional induction (beval2 e).

- constructor.
- destruct (beval2 e2); [constructor; rewrite e3 in IHv|constructor 3]; assumption.
- constructor; rewrite e3 in IHv; assumption.
- constructor; rewrite e2 in IHv; assumption.
- constructor; rewrite e2 in IHv; assumption.
Qed.

(* Parte 2 *)
Hint Constructors BEval.
Lemma beval1C_2 : forall e:BoolExpr, {b:Value |(BEval e b)}.
Proof.
intros.
exists (beval1 e).
functional induction (beval1 e);

try rewrite e4 in IHv0;
try rewrite e3 in IHv;
try rewrite e2 in IHv;
intuition.
Qed.

Lemma beval2C_2 : forall e:BoolExpr, {b:Value |(BEval e b)}.
Proof.
intros.
exists (beval2 e).
functional induction (beval2 e);

try destruct (beval2 e2);
try rewrite e4 in IHv0;
try rewrite e3 in IHv;
try rewrite e2 in IHv;
intuition.
Qed.

(* Parte 3 *)
Extraction "XXbeval1" beval1C_2.
Extraction "XXbeval2" beval2C_2.

(* Parte 4 *)
Extract Inductive bool => "bool" [ "true" "false" ].
Extraction "XXbeval1_b" beval1C_2.
Extraction "XXbeval2_b" beval2C_2.

(* Ejercicio 4 *)
Section list_perm.

Variable A:Set.
Inductive list : Set :=
  | nil : list
  | cons : A -> list -> list.
Fixpoint append (l1 l2 : list) {struct l1} : list :=
  match l1 with
    | nil => l2
    | cons a l => cons a (append l l2)
  end.
Inductive perm : list -> list ->Prop:=
  |perm_refl: forall l, perm l l
  |perm_cons: forall a l0 l1, perm l0 l1-> perm (cons a l0)(cons a l1)
  |perm_app: forall a l, perm (cons a l) (append l (cons a nil))
  |perm_trans: forall l1 l2 l3, perm l1 l2 -> perm l2 l3 -> perm l1 l3.
Hint Constructors perm.

(* Parte 1 *)
Fixpoint reverse (l:list): list :=
match l with 
 | nil => nil
 | cons a l_ => append (reverse l_)(cons a nil)
end.

Functional Scheme reverse_ind:=Induction for reverse Sort Set.

(* Parte 2 *)
Lemma Ej6_4: forall l: list, {l2: list | perm l l2}.
Proof.
intros; exists (reverse l); functional induction (reverse l).
- apply perm_refl.
- apply (perm_trans (cons a l_) (cons a (reverse l_))(append (reverse l_) (cons a nil))).
 -- apply perm_cons; assumption.
 -- apply perm_app.
Qed.

End list_perm.

(* Ejercicio 5 *)
(* Parte 1 *)
Inductive Le : nat->nat->Prop :=
   | Le0 : forall x:nat, Le 0 x
   | LeS : forall x y:nat, Le x y -> Le (S x) (S y).

Inductive Gt : nat->nat->Prop :=
   | Gt0 : forall x:nat, Gt (S x) 0
   | GtS : forall x y:nat, Gt x y -> Gt (S x) (S y).

(* Parte 2 *)
Fixpoint leBool(a b:nat):bool :=
  match a,b with
     | 0, _ => true
     | _, 0 => false
     | S m, S n => leBool m n
  end.

Functional Scheme leBool_ind:=Induction for leBool Sort Set.
Functional Scheme leBool_rec:=Induction for leBool Sort Set.

Lemma Le_Gt_dec: forall n m:nat, {(Le n m)}+{(Gt n m)}.
Proof.
intros.
functional induction (leBool n m).

- left; apply Le0.
- right; apply Gt0.
- intuition; [left|right]; constructor; assumption.
Qed.

(* Parte 3 *)
Lemma le_gt_dec: forall n m:nat, {(le n m)}+{(gt n m)}.
Proof.
intros.
functional induction (leBool n m).

- left; omega.
- right; omega.
- elim IHb; intro; [left|right]; omega.
Qed.

(* Ejercicio 6 *)
Definition spec_res_nat_div_mod (a b:nat) (qr:nat*nat) :=
  match qr with
    (q,r) => (a = b*q + r) /\ r < b
  end.

Require Import DecBool.
Require Import Compare_dec.
Require Import Plus.
Require Import Mult.

Definition nat_div_mod :
    forall a b:nat, not(b=0) -> {qr:nat*nat | spec_res_nat_div_mod a b qr}.
intros. unfold spec_res_nat_div_mod. induction a.

- exists (0,0). omega.
- inversion IHa.
  destruct x as (q, r).
  case (lt_dec (S r) b); intro;
  [exists (q, S r)|exists (S q, 0)]; intuition.
  inversion H1.
  rewrite(mult_succ_r b q).
  omega.
Defined.

Extraction "natdivmod" nat_div_mod.

(* Ejercicio 7 *)
Section Ej7.

Inductive tree (A:Set) : Set :=
  | leaf : tree A
  | node : A -> tree A -> tree A -> tree A.

Inductive tree_sub (A:Set) (t:tree A) : tree A -> Prop :=
  | tree_sub1 : forall (t':tree A) (x:A), tree_sub A t (node A x t t')
  | tree_sub2 : forall (t':tree A) (x:A), tree_sub A t (node A x t' t).

Theorem well_founded_tree_sub : forall A:Set, well_founded (tree_sub A).
Proof.
intros.
unfold well_founded.

induction a; constructor; intros; inversion H; assumption.
Qed.

End Ej7.

(* Ejercicio 8 *)
Section Ej8.

(* Parte 1 *)
Fixpoint size (be:BoolExpr):nat:=
  match be with
    | bbool b => 1
    | band e1 e2 => (size e1) + (size e2)
    | bnot e1 => 1 + (size e1)
  end.

Definition elt (e1 e2 : BoolExpr) := size e1 < size e2.

Require Import Wf_nat.
Require Import Inverse_Image.

(* Parte 2 *)
(* demostrar que elt es bien fundado *)
Theorem well_founded_elt : well_founded (elt).
Proof.
   unfold elt.
   apply wf_inverse_image.
   apply lt_wf.
Qed.

End Ej8.

(* Ejercicio 9 *)
Section Ej9.
Require Import List.

Inductive perm2 : list nat -> list nat ->Prop:=
  |perm2_refl: forall l, perm2 l l
  |perm2_cons: forall a l0 l1, perm2 l0 l1-> perm2 (a::l0)(a::l1)
  |p2_ccons: forall a b l, (perm2 (a::b::l) (b::a::l))
  |perm2_trans: forall l1 l2 l3, perm2 l1 l2 -> perm2 l2 l3 -> perm2 l1 l3.
Hint Constructors perm2.

Fixpoint insert_sorted(e:nat)(l:list nat):list nat :=
    match l with
       | nil => e::l
       | a::k => match le_gt_dec e a with
                    | left _ =>  e::l
                    | right _ => a::(insert_sorted e k)
                 end
    end.

Fixpoint insert_sort (l:list nat):list nat:=
    match l with
       | nil => l
       | cons a k => insert_sorted a (insert_sort k)
    end.

Inductive sorted (X:Set) : ( X -> X -> Prop ) -> list X -> Prop :=
  | sorted_empty: forall R:( X -> X -> Prop ), sorted X R nil
  | sorted_one: forall R:( X -> X -> Prop ), forall (e:X), sorted X R (e::nil)
  | sorted_cons:
      forall R:( X -> X -> Prop ),
      forall (e1 e2:X)(l:list X),
      (R e1 e2) -> sorted X R (e2::l) ->
      sorted X R (e1::e2::l).
Hint Constructors sorted.

Lemma aux2: forall (l:list nat) (x:nat), perm2 (x::l) (insert_sorted x l).
Proof.
induction l;simpl;auto.

intros; destruct (le_gt_dec x a); auto.

apply perm2_trans with (a::x::l); auto.
Qed.

Lemma aux3: forall (l:list nat) (x:nat), sorted nat le l -> sorted nat le (insert_sorted x l).
Proof.

induction l; simpl; auto.


intros; inversion H; simpl.

- destruct (le_gt_dec x a);
  apply sorted_cons;try omega;
  apply sorted_one.
- destruct (le_gt_dec x a).
  apply sorted_cons;[omega|];
  apply sorted_cons; auto; omega.
 -- rewrite <- H1 in IHl.
    simpl in IHl.
    assert (sorted nat le ( if le_gt_dec x e2 then x :: e2 :: l0 else e2 :: insert_sorted x l0)).
    apply (IHl x);auto.
  --- destruct (le_gt_dec x e2);
         apply sorted_cons; auto; omega.
Qed.

Theorem SORT: forall l:(list nat), {s:(list nat) | (sorted nat le s) /\ (perm2 l s)}.
Proof.
induction l.
exists nil; auto.
elim IHl; intros l' [H0 Hi].
exists (insert_sorted a l'); split.
apply aux3; auto.
apply perm2_trans with (a::l'); auto.
apply aux2.
Qed.

End Ej9.


