(* Nombre: German Faller               *)
(* Cedula: 45203047                    *)
(* Correo: german.faller@fing.edu.uy   *)

(* Nombre: Santiago Ingold             *)
(* Cedula: 50409424                    *)
(* Correo: santiago.ingold@fing.edu.uy *)

(* Ejercicio 1 *)
Section Ej1.

Inductive LE : nat -> nat -> Prop :=
  | Le_O : forall n : nat, LE 0 n
  | Le_S : forall n m : nat, LE n m -> LE (S n) (S m).

Inductive Mem (A : Set) (a : A) : list A -> Prop :=
  | here : forall x : list A, Mem A a (a::x)
  | there : forall x : list A, Mem A a x ->
                          forall b : A, Mem A a (b::x).

(* Parte 1 *)
Theorem not_sn_le_o: forall n:nat, ~ LE (S n) O.
Proof.
unfold not; intros.
inversion_clear H.
Qed.

(* Parte 2 *)
Theorem nil_empty: forall (A:Set)(a:A), ~ Mem A a (nil).
Proof.
unfold not; intros.
inversion_clear H.
Qed.

(* Parte 3 *)
Theorem L3: ~ LE 4 3.
Proof.
intro H.
inversion_clear H.
inversion_clear H0.
inversion_clear H.
inversion_clear H0.
Qed.

(* Parte 4 *)
Theorem L4: forall (n:nat), ~ LE (S n) n.
Proof.
unfold not; intros.
induction n; inversion H.
elim (IHn H2).
Qed.

(* Parte 5 *)
Theorem L5: forall (x y z:nat), LE y x -> LE x z -> LE y z.
Proof.
induction x; intros.
- inversion_clear H; assumption.

- inversion H.
  -- constructor.
  -- inversion_clear H0.
     constructor.
     apply ((IHx n m0)H3 H4).
Qed.

(* Parte 6 *)
Theorem L6: forall (A:Set)(l1 l2: list A)(a:A), Mem A a l1 -> Mem A a (l1++l2).
Proof.
induction l1; intros.
inversion H.
inversion_clear H; simpl
 ;constructor.
apply IHl1.
assumption.
Qed.

End Ej1.

(* Ejercicio 2 *)
Section Ej2.
Variable A:Set.
Inductive bintree : Set :=
  | emptyBinTree :bintree
  | consBinTree: A -> bintree -> bintree -> bintree.

Fixpoint mirror (bt:bintree):bintree:=
    match bt with
       | emptyBinTree => bt
       | consBinTree a t1 t2 => consBinTree a (mirror t2) (mirror t1)
    end.

Inductive isomorfo :bintree -> bintree -> Prop :=
  | iso_nil: isomorfo (emptyBinTree) (emptyBinTree)
  | iso_cons: 
      forall (a b : A) (t11 t12 t21 t22:bintree),
      isomorfo t11 t12 ->
      isomorfo t21 t22 ->
      isomorfo (consBinTree a t11 t21) (consBinTree b t12 t22).

(* Parte 1 *)
Theorem P1: forall (a:A)(t1 t2: bintree), ~isomorfo (emptyBinTree)(consBinTree a t1 t2).
Proof.
intros.
intro.
inversion_clear H.
Qed.

(* Parte 2 *)
Theorem P2: forall (a b:A)(t11 t12 t21 t22: bintree), isomorfo (consBinTree a t11 t21) (consBinTree b t12 t22) -> isomorfo t11 t12 /\ isomorfo t21 t22.
Proof.
intros.
inversion H.
split; assumption.
Qed.

(* Parte 3 *)
Theorem P3: forall (t1 t2 t3 : bintree), isomorfo t2 t1 -> isomorfo t1 t3 -> isomorfo t2 t3.
Proof.
induction t1;intros.
inversion_clear H; assumption.

inversion H; inversion H0; constructor.

apply (IHt1_1 t11 t1); assumption.
apply (IHt1_2 t21 t5); assumption.
Qed.

(* Parte 4 *)
Theorem P4:forall (t1 t2: bintree), isomorfo t1 t2 ->isomorfo (mirror t1) (mirror t2).
Proof.
intros.
induction H; simpl; constructor; assumption.
Qed.
End Ej2.

(* Ejercicio 3 *)
Section Ej3.

Variable A : Set.

Parameter equal : A -> A -> bool.
Axiom equal1 : forall x y : A, equal x y = true -> x = y.
Axiom equal2 : forall x : A, equal x x <> false.

Inductive List : Set :=
  | nullL : List
  | consL : A -> List -> List.

Inductive MemL (a : A) : List -> Prop :=
  | hereL : forall x : List, MemL a (consL a x)
  | thereL : forall x : List, MemL a x -> forall b : A, MemL a (consL b x).

(* Parte 1 *)
Inductive isSet : List -> Prop :=
  | nullS : isSet nullL
  | consS : forall (a:A) (l1:List), ~ (MemL a l1) ->  (isSet l1) -> isSet (consL a l1).

(* Parte 2 *)
Fixpoint deleteAll (a:A) (l1:List) : List  :=
match l1 with
  | nullL => l1
  | consL b l2 =>
            match equal a b with
              | true => deleteAll a l2
              | false => consL b (deleteAll a l2)
            end
end.

(* Parte 3 *)
Lemma DeleteAllNotMember : forall (l : List) (x : A),
                                        ~ MemL x (deleteAll x l).
Proof.
induction l;intros; intro; simpl in *.

- inversion H.

- (* destruct (equal x a).*)
  case_eq (equal x a); intro; rewrite H0 in H.
 -- apply (IHl x); assumption.
 -- inversion H.
  --- rewrite H2 in H0.
      elim (equal2 a); assumption.
  --- apply (IHl x); assumption.
Qed.

(* Parte 4 *)
Fixpoint delete (a:A)(l:List):List :=
match l with
  | nullL => l
  | consL b l2 =>
            match equal a b with
              | true => l2
              | false => consL b (delete a l2)
            end
end.

(* Parte 5 *)
Lemma DeleteNotMember : forall (l : List) (x : A), isSet l ->
                                    ~ MemL x (delete x l).
Proof.
intros; intro.
induction H; simpl in *.
- inversion H0.
- case_eq (equal x a); intro; rewrite H2 in H0.
 -- apply IHisSet.
    assert (x=a).
  --- apply equal1; assumption.
  --- rewrite H3 in H0.
      elim (H H0).
 -- inversion H0.
  --- rewrite H4 in H2.
      elim (equal2 a); assumption.
  --- apply IHisSet; assumption.
Qed.
End Ej3.


(* Ejercicio 4 *)
Section Ej4.

Variable A : Set.

Inductive AB: Set :=
  | null : AB
  | cons: A -> AB-> AB -> AB.

(* Parte 1 *)
Inductive Pertenece : A -> AB -> Prop :=
| PerteneceB : forall (t1 t2 : AB)(a:A), Pertenece a (cons a t1 t2)
| PerteneceI : forall (t1 t2 : AB)(a b:A), Pertenece a t1 \/ Pertenece a t2 -> Pertenece a (cons b t1 t2).

(* Parte 2 *)
Parameter eqGen: A->A->bool.

Fixpoint Borrar (a:A)(ab: AB) : AB :=
match ab with
  | null => ab
  | cons b t1 t2 =>
            match (eqGen a b) with
              | true => null
              | false => cons b (Borrar a t1) (Borrar a t2)
            end
end.

(* Parte 3 *)
Axiom eqGen1: forall (x:A), ~(eqGen x x)=false.

Lemma BorrarNoPertenece: forall (x:AB) (a:A), ~(Pertenece a (Borrar a x)).
Proof.
induction x; unfold not; simpl; intros.
- inversion H.
- case_eq (eqGen a0 a); intro; rewrite H0 in H.
 -- inversion H.
 -- inversion H.
  --- rewrite H3 in H0.
      elim (eqGen1 a); assumption.
  --- inversion H3;[apply (IHx1 a0)|apply (IHx2 a0)];assumption.
Qed.

(* Parte 4 *)
Inductive SinRepetidos: AB->Prop :=
     | SinRepetidosB : SinRepetidos null
     | SinRepetidosI : forall (a:A)(t1 t2:AB),
          SinRepetidos t1 /\ SinRepetidos t2 ->
          ~Pertenece a t1 /\ ~Pertenece a t2 ->
          SinRepetidos (cons a t1 t2).
End Ej4.

(* Ejercicio 5 *)
Section Ej5.

(* Parte 1 *)
Definition Var := nat.
Inductive BoolExpr : Set :=
     | varE : Var -> BoolExpr
     | boolE : bool -> BoolExpr
     | andE : BoolExpr -> BoolExpr -> BoolExpr
     | notE : BoolExpr -> BoolExpr.

(* Parte 2 *)
Definition Valor := bool.
Definition Memoria := Var -> Valor.
Definition lookup (m:Memoria)(v:Var) : Valor := m v.

Inductive ACom (A:Set): nat -> Set :=
  | AComB : A -> ACom A 0
  | AComI : forall (n:nat), A -> ACom A n -> ACom A n -> ACom A (S n).


Inductive BEval (m:Memoria):BoolExpr -> Valor -> Prop :=
     | evar : forall (v:Var), BEval m (varE v) (lookup m v)
     | eboolt : BEval m (boolE true) true
     | eboolf : BEval m (boolE false) false
     | eandl  : forall (e1 e2:BoolExpr), BEval m e1 false -> BEval m (andE e1 e2) false
     | eandr  : forall (e1 e2:BoolExpr), BEval m e2 false -> BEval m (andE e1 e2) false
     | eandrl : forall (e1 e2:BoolExpr), BEval m e1 true  /\ BEval m e2 true -> BEval m (andE e1 e2) true
     | enott  : forall e:BoolExpr, BEval m e true -> BEval m (notE e) false
     | enotf  : forall e:BoolExpr, BEval m e false -> BEval m (notE e) true.

(* Parte 3 *)
Lemma e3a: forall (m:Memoria), ~ BEval m (boolE true) false.
Proof.
intros; intro.
inversion H.
Qed.

Lemma e3b: forall (m:Memoria)(e1 e2:BoolExpr)(w:bool),
             BEval m e1 true /\ BEval m e2 w -> BEval m (andE e1 e2) w.
Proof.
intros.
elim H; intros.
destruct w; constructor; assumption.
Qed.

(* Hice esta tactica por que se repetia bastante *)
Ltac myTactic h1 h2 hdes := 
rewrite <- h1 in hdes;
rewrite <- h2 in hdes;
apply hdes;assumption.

(*Mi demostracion*)
Lemma e3c: forall (m:Memoria)(e:BoolExpr)(w1 w2:Valor),
              BEval m e w1 /\ BEval m e w2 -> w1=w2.
Proof.
intros; intuition;
induction e;
inversion H0; inversion H1; intuition.

- rewrite <- H2 in H4; discriminate.
- rewrite <- H2 in H4; discriminate.
- myTactic H6 H2 IHe1.
- myTactic H6 H2 IHe2.
- myTactic H6 H2 IHe1.
- myTactic H6 H2 IHe2.
- myTactic H6 H3 IHe.
- myTactic H6 H3 IHe.
Qed.

(*Copiada del foro y modificada*)
Lemma parteC: forall (m: Memoria)(e: BoolExpr)(w1 w2: Valor),
              BEval m e w1 -> BEval m e w2 -> w1 = w2.
Proof.
induction e; try induction b;
intros; inversion H; inversion H0; intuition.
Qed.

Lemma e3d : forall (m:Memoria)( e1 e2:BoolExpr), 
              BEval m e1 false -> ~ BEval m (notE (andE e1 e2)) false.
Proof.
unfold not; intros.
inversion H0.
inversion H2.
assert(true <> false);[discriminate|apply H6].
apply (e3c m e1 true false).
intuition.
Qed.

Lemma e3d_ : forall (m:Memoria)( e1 e2:BoolExpr), 
              BEval m e1 false -> BEval m (notE (andE e1 e2)) true.
Proof.
intros.
constructor.
constructor.
assumption.
Qed.

(* Parte 4 *)
Fixpoint beval (m: Memoria)(expr: BoolExpr): Valor :=
match expr with
    | varE v => lookup m v
    | boolE b => b
    | notE e => 
                match (beval m e) with
                    | true => false
                    | false => true
                end
    | andE e1 e2 => 
                match (beval m e1) with
                    | false => false
                    | true => (beval m e2)
                end
end.

(* Parte 5 *)
Theorem e5 : forall (m:Memoria)(e:BoolExpr), BEval m e (beval m e).
Proof.
induction e; simpl.
- constructor.
- destruct b; constructor.
- case_eq (beval m e1); intros; rewrite H in *.
 -- case_eq (beval m e2); intros;
    rewrite H0 in IHe2;
    constructor; tauto.
 -- constructor; assumption.
- case_eq (beval m e); intros;
  rewrite H in IHe;
  constructor;assumption.
Qed.
End Ej5.

(* Ejercicio 6 *)
Section Ej6.
Require Import Coq.Arith.PeanoNat.
Require Import Coq.Arith.EqNat.

(* Parte 1 *)
Inductive Instr : Set :=
     | skip : Instr
     | var : Var -> BoolExpr -> Instr
     | ite : BoolExpr -> Instr -> Instr -> Instr
     | while : BoolExpr -> Instr -> Instr
     | repeat : nat -> Instr -> Instr
     | begin : LInstr -> Instr
with LInstr : Set :=
     | nullLI : LInstr
     | consLI : Instr -> LInstr -> LInstr.

(* Parte 2 *)
Notation "[]" := nullLI.
Infix ";" := consLI (at level 60, right associativity).

Definition PP (v1 v2 : Var) := begin ( var v1 (boolE true); var v2 (notE (varE v1)); []).

Definition swap (v1 v2 aux:Var) := begin (var aux (varE v1); var v1 (varE v2); var v2 (varE aux); []).

(* Parte 3 *)
Definition update (m:Memoria)(v:Var)(val:Valor):Memoria :=
    fun (v_:Var) => if (Nat.eqb v v_) 
                    then val
                    else lookup m v_.

(* Parte 4 *)
Theorem e64 : forall (m:Memoria)(v:Var)(val:Valor), lookup (update m v val) v = val.
Proof.
intros.
unfold update.
unfold lookup.
elim beq_nat_refl.
reflexivity.
Qed.

(* Parte 5 *)
Theorem e65 : forall (m:Memoria)(v v_:Var)(val:Valor),v <> v_ -> lookup (update m v val) v_ = lookup m v_.
Proof.
intros.
unfold update.
unfold lookup.

case_eq (v =? v_); intros.
absurd (v=v_); [|apply beq_nat_true]; assumption.

reflexivity.
Qed.
End Ej6.

(* Ejercicio 7 *)
Section Ej7.
Notation "[]" := nullLI.
Infix ";" := consLI (at level 60, right associativity).

(* Parte 1 *)
Inductive Execute (m:Memoria): Instr -> Memoria -> Prop :=

  | xAss: forall (e:BoolExpr)(w:Valor)(v:Var), (BEval m e w) -> Execute m (var v e) (update m v w)

  | xSkip: Execute m skip m

  | xIFthen: forall(c:BoolExpr)(p1 p2:Instr)(m1:Memoria),
                      (BEval m c true) /\ (Execute m p1 m1) -> Execute m (ite c p1 p2) m1

  | xIFelse: forall(c:BoolExpr)(p1 p2:Instr)(m2:Memoria),
                      (BEval m c false) /\ (Execute m p2 m2) -> Execute m (ite c p1 p2) m2

  | xWhileTrue: forall(c:BoolExpr)(p:Instr)(m1 m2:Memoria),
                         (BEval m c true) /\ (Execute m p m1) /\ (Execute m1 (while c p) m2)
                            -> Execute m (while c p) m2

  | xWhileFalse: forall(c:BoolExpr)(p:Instr), (BEval m c false) -> (Execute m (while c p) m)

  | xRepeat0: forall(i:Instr), Execute m (repeat 0 i) m

  | xRepeatS: forall(n:nat)(i:Instr)(m1 m2:Memoria),
                        (Execute m i m1) /\ (Execute m1 (repeat n i) m2) 
                            -> (Execute m (repeat (S n) i) m2)

  | xBeginEnd: forall(p:LInstr)(m1:Memoria), (ExecuteList m p m1) -> (Execute m (begin p) m1)

with ExecuteList (m:Memoria): LInstr -> Memoria -> Prop :=

  | xEmptyBlock : ExecuteList m [] m

  | xNext : forall(i:Instr)(li:LInstr)(m1 m2:Memoria),
                (Execute m i m1) /\ (ExecuteList m1 li m2) -> (ExecuteList m (i; li) m2).

(* Parte 2 *)
Theorem e72 : forall(e1 e2:Instr)(m m':Memoria), 
              Execute m (ite (notE (boolE false)) e1 e2) m' 
              -> Execute m (ite (boolE false) e2 e1) m'.
Proof.
intros.
inversion H;
[constructor 4|constructor 3]; (*tengo que explicitarlo por que puede entrar erroneo*)
intuition.
- constructor.
- inversion H5;assumption.
Qed.

(* Parte 3 *)
Theorem e73 : forall(c: bool)(e1 e2:Instr)(m m':Memoria), 
              Execute m (ite (notE (boolE c)) e1 e2) m' 
              -> Execute m (ite (boolE c) e2 e1) m'.
Proof.
intros.
destruct c.

inversion H;
[constructor 4|constructor 3];
intuition.
- inversion H5; assumption.
- constructor.

- apply e72; assumption.
Qed.

(* Parte 4 *)
Theorem e74 : forall(p:Instr)(m m':Memoria), Execute m (while (boolE false) p) m' -> m = m'.
Proof.
intros.
inversion H; intuition.
inversion H4.
Qed.

(* Parte 5 *)
Theorem e75: forall (p:Instr)(c:bool)(m m':Memoria), ExecuteList m (ite (boolE c) p skip; while (boolE c) p ; []) m' ->
                      Execute m (while (boolE c) p) m'.
Proof.
intros.
inversion_clear H; intuition.
inversion_clear H1; intuition.
inversion H2; clear H2; intuition.
rewrite H3 in H1; clear H3.

destruct c.
- apply (xWhileTrue m (boolE true) p m1).
  intuition.
 -- constructor.
 -- inversion_clear H; intuition.
    inversion_clear H.
- inversion_clear H; intuition.
 -- inversion_clear H.
 -- inversion_clear H2; intuition.
Qed.

(* Parte 6 *)
Theorem e76: forall (n:nat)(i:Instr)(m1 m2:Memoria), ExecuteList m1 (i; repeat n i ; []) m2 ->
                      Execute m1 (repeat (S n) i) m2.
Proof.
intros.
inversion_clear H; intuition.
inversion_clear H1; intuition.
inversion H2; clear H2; intuition.
rewrite H3 in H1; clear H3.

apply (xRepeatS m1 n i m0 ); intuition.
Qed.

(* Parte 7 *)
Theorem e77: forall (n1 n2:nat)(i:Instr)(m1 m2 m3:Memoria), 
                      Execute m1 (repeat n1 i) m2 /\ Execute m2 (repeat n2 i) m3 
                        -> Execute m1 (repeat (n1+n2) i) m3.
Proof.
induction n1;
intros; intuition; simpl.

- inversion_clear H0; assumption.
- inversion H0; intuition.
  apply (xRepeatS m1 (n1+n2) i m0 ); intuition.
  inversion H0; intuition.
  apply (IHn1 n2 i m0 m2 m3).
  intuition.
Qed.

(* Parte 8 *)
Theorem e78: forall(v1 v2 : Var)(m1 m2:Memoria), v1 <> v2 -> Execute m1 (PP v1 v2) m2 
                      -> lookup m2 v1 = true /\ lookup m2 v2 = false.
Proof.
intros.
inversion_clear H0.
inversion_clear H1; destruct H0.
inversion_clear H1; destruct H2.
inversion H2; rewrite H4 in *; clear H2 H4.

inversion H1; clear H4 e H2 v.
inversion H0; clear H6 e H2 v.
inversion H7; rewrite_all <- H6; clear H6 w0.

split.
- rewrite (e65 (update m1 v1 true) v2 v1 w); intuition.
  rewrite (e64 m1 v1 true).
  reflexivity.
- rewrite (e64 (update m1 v1 true) v2 w).
  inversion H5; rewrite_all <- H8; clear H8 w H2 e.
 -- reflexivity.
 -- rewrite <- H4 in H6.
    inversion H6; rewrite_all H8; clear H8 v.
    rewrite (e64 m1 v1 true).
    reflexivity.
Qed.
End Ej7.