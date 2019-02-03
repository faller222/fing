(* Nombre: German Faller               *)
(* Cedula: 45203047                    *)
(* Correo: german.faller@fing.edu.uy   *)

(* Nombre: Santiago Ingold             *)
(* Cedula: 50409424                    *)
(* Correo: santiago.ingold@fing.edu.uy *)

(* Ejercicio 1 *)
(* Parte 1 *)
Inductive list (A:Set) : Set :=
  | emptyList: list A
  | consList: A -> list A -> list A.

Inductive bintree (X:Set) : Set :=
  | emptyBinTree :bintree X
  | consBinTree: X -> bintree X -> bintree X -> bintree X.

(* Parte 2 *)
Inductive array (X:Set) : nat -> Set :=
  | empty:array X 0
  | add:forall n:nat, X -> array X n -> array X (S n).

Inductive matrix (X:Set): nat -> nat -> Set :=
  | one_col:forall n:nat, array X n -> matrix X 1 n
  | extend_col:forall n m, array X n -> matrix X m n -> matrix X (S m) n.

(* Parte 3 *)
Inductive leq : nat -> nat -> Prop :=
  | leq0: forall n:nat, leq 0 n
  | leqS: forall n m:nat, leq n m -> leq (S n) (S m).

(* Parte 4 *)
Inductive eq_list (A:Set): list A -> list A -> Prop :=
  | eq_empty: eq_list A (emptyList A) (emptyList A)
  | eq_cons: 
      forall (e1 e2:A)(l1 l2:list A), 
      eq e1 e2 -> eq_list A l1 l2 ->
      eq_list A (consList A e1 l1)(consList A e2 l2).

(* Parte 5 *)
Inductive sorted (X:Set) : ( X -> X -> Prop ) -> list X -> Prop :=
  | sorted_empty: forall R:( X -> X -> Prop ), sorted X R (emptyList X)
  | sorted_one: forall R:( X -> X -> Prop ), forall (e:X), sorted X R (consList X e (emptyList X))
  | sorted_cons:
      forall R:( X -> X -> Prop ),
      forall (e1 e2:X)(l:list X),
      (R e1 e2) -> sorted X R (consList X e1 l) ->
      sorted X R (consList X e2 (consList X e1 l)).

(* Parte 6 *)
Inductive mirror (A : Set) : bintree A -> bintree A -> Prop :=
  | mirror_empty : mirror A (emptyBinTree A) (emptyBinTree A)
  | mirror_node :
      forall (a : A) (t11 t12 t21 t22 : bintree A),
      mirror A t11 t22 ->
      mirror A t12 t21 ->
      mirror A (consBinTree A a t11 t12) (consBinTree A a t21 t22).

(* Parte 7 *)
Inductive isomorfo (X:Set):bintree X -> bintree X -> Prop :=
  | iso_nil: isomorfo X (emptyBinTree X) (emptyBinTree X)
  | iso_cons: 
      forall (a b : X) (t11 t12 t21 t22:bintree X),
      isomorfo X t11 t12 ->
      isomorfo X t21 t22 ->
      isomorfo X (consBinTree X a t11 t21) (consBinTree X b t12 t22).

(* Parte 8 *)
Inductive Gtree (A:Set) : Set :=
  | node: A -> forest A -> Gtree A
with forest (A:Set) : Set :=
  | empty_f : forest A
  | add_tree: Gtree A -> forest A -> forest A.

(* Ejercicio 2 *)
(* Parte 1 *)
Definition Or: bool -> bool -> bool :=
    fun b1 b2:bool => match b1 with
       | false => b2
       | true => true
       end.

Definition And:bool -> bool -> bool :=
    fun b1 b2:bool => match b1 with
       | true => b2
       | false => false
    end.

Definition not:bool -> bool :=
    fun b:bool => match b with
       | true => false
       | false => true
    end.

Definition XOr:bool -> bool -> bool :=
    fun b1 b2:bool => match b1 with
       | true => not b2
       | false => b2
    end.

(* Parte 2 *)
Definition is_nil (A:Set):list A -> bool :=
    fun l:list A => match l with
       | emptyList _ => true
       | _ => false
    end.

(* Ejercicio 3 *)
(* Parte 1 *)
Fixpoint sum(n m:nat){struct n}:nat :=
    match n with
       | 0 => m
       | S(k) => S (sum k m)
    end.

(* Parte 2 *)
Fixpoint prod(n m:nat){struct n}:nat :=
    match n with
       | 0 => 0
       | S(k) => sum (prod k m) m
    end.

(* Parte 3 *)
Fixpoint pot(b e:nat):nat :=
    match e with
       | 0 => 1
       | S(k) => prod b (pot b k)
    end.

(* Parte 4 *)
Fixpoint leBool(a b:nat):bool :=
    match a,b with
       | 0, _ => true
       | _, 0 => false
       | S m, S n => leBool m n
    end.

(* Ejercicio 4 *)
(* Parte 1 *)
Fixpoint length(A:Set)(l:list A):nat :=
    match l with
       | emptyList _ => 0
       | consList _ _ k => S(length A k)
    end.

(* Parte 2 *)
Fixpoint append(A:Set)(l1 l2:list A){struct l1}:list A :=
    match l1 with
       | emptyList _ => l2
       | consList _ a k => consList A a (append A k l2)
    end.
Infix "++" := (append _) (at level 60, right associativity).

(* Parte 3 *)
Fixpoint reverse(A:Set)(l:list A){struct l}:list A:=
    match l with
       | emptyList _ => l
       | consList _ a k => append A (reverse A k) (consList A a (emptyList A))
    end.

(* Parte 4 *)
Fixpoint filter(A:Set)(P:A -> bool)(l:list A){struct l}:list A :=
    match l with
       | emptyList _ => l
       | consList _ a k => 
            match (P a) with
               | true => consList A a (filter A P k)
               | false => filter A P k
            end
    end.

(* Parte 5 *)
Fixpoint map(A B:Set)(P:A -> B)(l:list A):list B :=
    match l with
       | emptyList _ => emptyList B
       | consList _ a k => consList B (P a) (map A B P k)
    end.

(* Parte 6 *)
Fixpoint exists_(A:Set)(P:A->bool)(l:list A):bool :=
    match l with
       | emptyList _ => false
       | consList _ a k => 
            match (P a) with
               | true => true
               | false => (exists_ A P k)
            end
    end.

(* Ejercicio 5 *)
(* Parte 1 *)
Fixpoint inverse(A:Set)(b:bintree A):bintree A :=
    match b with
       | emptyBinTree _ => emptyBinTree A
       | consBinTree _ a t1 t2 => (consBinTree A a (inverse A t2) (inverse A t1))
    end.

(* Parte 2 *)
Fixpoint contar_hojas (A:Set)(t:Gtree A):nat:=
    match t with | node _ a f => contar_hojas_b A f end
with contar_hojas_b (A:Set)(f:forest A):nat :=
    match f with
       | empty_f _ => 1
       | add_tree _ t f => (contar_hojas A t) + (contar_hojas_b A f)
    end.

Fixpoint contar_nodos (A:Set)(t:Gtree A):nat:=
    match t with | node _ a f => contar_nodos_b A 0 f end
with contar_nodos_b (A:Set)(acu:nat)(f:forest A){struct f}:nat :=
    match f with
       | empty_f _ => acu
       | add_tree _ t f => (contar_nodos A t) + (contar_nodos_b A 1 f)
    end.

Definition ej5 (A:Set)(t:Gtree A):bool := not (leBool  (contar_hojas A t)  (contar_nodos A t)) .

(* Ejercicio 6 *)
Definition listN := list nat.
Check listN.

Fixpoint eqBool(a b:nat):bool :=
    match a,b with
       | 0, 0 => true
       | S m, S n => eqBool m n
       | _, _ => false
    end.

Definition distBool (a b:nat):bool := not (eqBool a b).

(* Parte 1 *)
Definition member(e:nat)(l:listN):bool :=exists_ nat (eqBool e) l.

(* Parte 2 *)
Definition delete(l:listN)(e:nat):listN :=filter nat (distBool e) l.

(* Parte 3 *)
Fixpoint insert_sorted(e:nat)(l:listN):listN :=
    match l with
       | emptyList _ => consList nat e l
       | consList _ a k => match leBool a e with
                            | true => consList nat a (insert_sorted e k)
                            | false => consList nat e l
                           end
    end.

Fixpoint insert_sort (l:listN):listN:=
    match l with
       | emptyList _ => l
       | consList _ a k => insert_sorted a (insert_sort k)
    end.
(* TODO: Generalice las funciones anteriores para que 
puedan ser aplicadas a listas de cualquier tipo. *)

(* Ejercicio 7 *)
(* Parte 1 *)
Inductive Exp (A:Set) : Set :=
  | nodeE: A -> Exp A
  | addE: Exp A -> Exp A -> Exp A
  | prodE: Exp A -> Exp A -> Exp A
  | minusE: Exp A -> Exp A.

(* Parte 2 *)
Definition ExpNat := Exp nat.
Fixpoint eval_exp (e:ExpNat):nat:=
    match e with
       | nodeE _ a => a
       | addE _ e1 e2 => eval_exp(e1) + eval_exp(e2)
       | prodE _ e1 e2 => eval_exp(e1) * eval_exp(e2)
       | minusE _ e1 => eval_exp(e1)
    end.

(* Parte 3 *)
Definition ExpBool := Exp bool.
Fixpoint eval_expB (e:ExpBool):bool:=
    match e with
       | nodeE _ a => a
       | addE _ e1 e2 => Or (eval_expB e1) (eval_expB e2)
       | prodE _ e1 e2 => And (eval_expB e1) (eval_expB e2)
       | minusE _ e1 => not (eval_expB e1)
    end.

(* Ejercicio 8 *)
(* Parte 1 *)
Lemma OrAsoc : forall a b c : bool, Or a (Or b c) = Or (Or a b) c.
Proof.
intros; case a; case b; simpl; reflexivity.
Qed.

Lemma AndAsoc : forall a b c : bool, And a (And b c) = And (And a b) c.
Proof.
intros; case a; case b; simpl; reflexivity.
Qed.

Lemma OrConm : forall a b : bool, Or a b = Or b a.
Proof.
intros; case a; case b;  simpl; reflexivity.
Qed.

Lemma AndConm : forall a b : bool, And a b = And b a.
Proof.
intros; case a; case b; simpl; reflexivity.
Qed.

(* Parte 2 *)
Lemma LAnd : forall a b : bool, And a b = true <-> a = true /\ b = true.
Proof.
intros a b; case a; case b; split; intro; simpl; tauto.
Qed.

(* Parte 3 *)
Lemma LOr1 : forall a b : bool, Or a b = false <-> a = false /\ b = false.
Proof.
intros a b; case a; case b; split; intro; simpl; tauto.
Qed.

(* Parte 4 *)
Lemma LOr2 : forall a b : bool, Or a b = true <-> a = true \/ b = true.
Proof.
intros a b; case a; case b; split; intro; simpl; tauto.
Qed.

(* Parte 5 *)
Lemma LXor : forall a b : bool, XOr a b = true <-> a <> b.
Proof.
intros a b; case a; case b; simpl in *;
split; intro H;
try intro;
try discriminate;
try reflexivity.

elim H; reflexivity.
elim H; reflexivity.
Qed.

(* Parte 6 *)
Lemma LNot : forall b : bool, not (not b) = b.
Proof.
intro b; case b; simpl; reflexivity.
Qed.

(* Ejercicio 9 *)
(* Parte 1 *)
Theorem SumO : forall n : nat, sum n 0 = n /\ sum 0 n = n.
Proof.
induction n; simpl.
split;reflexivity.
split;[|reflexivity].
elim IHn; intros H H2.
rewrite H.
reflexivity.
Qed.

(* Parte 2 *)
Theorem SumS : forall n m : nat, sum n (S m) = sum (S n) m.
Proof.
intros; induction n; simpl;[|
rewrite IHn; simpl];reflexivity.
Qed.

(* Parte 3 *)
Theorem SumAsoc : forall n m p : nat, sum n (sum m p) = sum (sum n m) p.
Proof.
intros; induction n; simpl;[|
rewrite IHn];reflexivity.
Qed.

(* Parte 4 *)
Theorem SumConm : forall n m : nat, sum n m = sum m n.
Proof.
intros; induction n.
elim (SumO m); intros; rewrite H; rewrite H0; assumption.
rewrite SumS; simpl; rewrite IHn; reflexivity.
Qed.

(* Ejercicio 10 *)
(* Parte 1 *)
Lemma ProdAux : forall n m:nat, (sum (prod m n) m) = (prod m (S n)).
Proof.
intros; induction m; simpl.
- reflexivity.
- rewrite <- IHm.
  rewrite (SumS (sum (prod m n) m) n).
  rewrite (SumS (sum (prod m n) n) m).
  simpl.
  rewrite <- (SumAsoc (prod m n) m n).
  rewrite <- (SumAsoc (prod m n) n m).
  rewrite (SumConm  n m).
  reflexivity.
Qed.

Lemma ProdConm : forall n m : nat, prod n m = prod m n.
Proof.
intros; induction n; simpl.
induction m; simpl; [|rewrite <- IHm;simpl]; reflexivity.

rewrite <- (ProdAux n m).
rewrite IHn.
reflexivity.
Qed.

(* Parte 3 *)
Lemma ProdDistr : forall n m p : nat, prod n (sum m p) = sum (prod n m) (prod n p).
Proof.
intros; induction n; simpl.
- reflexivity.
- rewrite IHn.
  rewrite <- (SumAsoc (prod n m) (prod n p) (sum m p)).
  rewrite (SumConm (prod n p) (sum m p)).
  rewrite <- (SumAsoc m p (prod n p)).
  rewrite (SumConm p (prod n p)).
  rewrite <- (SumAsoc (prod n m) m (sum (prod n p) p)).
  reflexivity.
Qed.

(* Parte 2 *)
Lemma ProdAsoc : forall n m p : nat, prod n (prod m p) = prod (prod n m) p.
Proof.
intros; induction n; simpl.
- reflexivity.
- rewrite IHn.
  rewrite (ProdConm (sum (prod n m) m) p).
  rewrite ProdDistr.
  rewrite ProdConm.
  rewrite (ProdConm m p).
  reflexivity.
Qed.

(* Ejercicio 11 *)
(* Parte 1 *)
Lemma L1 : forall (A : Set) (l : list A), append A l (emptyList A) = l.
Proof.
induction l ; simpl; [|rewrite IHl];
reflexivity.
Qed.

(* Parte 2 *)
Lemma L2 : forall (A : Set) (l : list A) (a : A), ~(consList A a l) = emptyList A.
Proof.
discriminate.
Qed.

(* Parte 3 *)
Lemma L3 : forall (A : Set) (l m : list A) (a : A),
              consList A a (append A l m) = append A (consList A a l) m.
Proof.
reflexivity.
Qed.

(* Parte 4 *)
Lemma L4 : forall (A : Set) (l m : list A),
              length A (append A l m) = sum (length A l) (length A m).
Proof.
intros A l m.
induction l; simpl; [|rewrite IHl];
reflexivity.
Qed.

(* Parte 5 *)
Lemma L5 : forall (A : Set) (l : list A), length A (reverse A l) = length A l.
Proof.
induction l; simpl.
- reflexivity.
- rewrite L4.
  rewrite IHl.
  rewrite SumConm.
  simpl.
  reflexivity.
Qed.

(* Ejercicio 12 *)
(* Parte 1 *)
Theorem L7 : forall (A B : Set) (l m : list A) (f : A -> B), 
          map A B f (append A l m) = append B (map A B f l) (map A B f m).
Proof.
intros.
induction l; simpl.
reflexivity.

rewrite IHl.
reflexivity.
Qed.

(* Parte 2 *)
Theorem L8 : forall (A : Set) (l m : list A) (P : A -> bool),
          filter A P (append A l m) = append A (filter A P l) (filter A P m).
Proof.
intros.
induction l;simpl.
reflexivity.
case (P a); rewrite IHl; reflexivity.
Qed.

(* Parte 3 *)
Lemma  L9 : forall (A : Set) (l : list A) (P : A -> bool),
          filter A P (filter A P l) = filter A P l.
Proof.
intros A l P.
induction l.
simpl; reflexivity.

case_eq (P a); intro Pval;
repeat (simpl; rewrite Pval).

rewrite  IHl; reflexivity.
assumption.
Qed.

(* Parte 4 *)
Theorem L10 : forall (A : Set) (l m n : list A),
          append A l (append A m n) = append A (append A l m) n.
Proof.
intros.
induction l; simpl;[|rewrite IHl];
reflexivity.
Qed.

(* Ejercicio 13 *)
Fixpoint filterMap (A B : Set) (P : B -> bool) (f : A -> B)
(l : list A) {struct l} : list B :=
    match l with
       | emptyList _ => emptyList B
       | consList _ a l1 =>
            match P (f a) with
               | true => consList B (f a) (filterMap A B P f l1)
               | false => filterMap A B P f l1
            end
  end.

Lemma FusionFilterMap : forall (A B : Set) (P : B -> bool) (f : A -> B) (l : list A),
                                           filter B P (map A B f l) = filterMap A B P f l.
Proof.
intros.
induction l; simpl;[|rewrite IHl];
reflexivity.
Qed.

(* Ejercicio 14 *)
Lemma E14 : forall (A: Set)(b:bintree A), mirror A b (inverse A b).
Proof.
intros.
induction b; simpl;
constructor; assumption.
Qed.

(* Ejercicio 15 *)
(* Parte 1 *)
Definition id (A:Set)(a:bintree A) := a.

Lemma E15a : forall (A: Set)(b:bintree A), isomorfo A b (id A b).
Proof.
intros.
induction b; simpl;
constructor; assumption.
Qed.

(* Parte 2 *)
Lemma E15Reflex : forall (A: Set)(b:bintree A), isomorfo A b b.
Proof.
intros.
induction b; simpl;
constructor; assumption.
Qed.

Lemma E15Symme : forall (A: Set)(a b:bintree A), isomorfo A a b -> isomorfo A b a.
Proof.
intros A a b Hr.
elim Hr; constructor; assumption.
Qed.

(* Ejercicio 16 *)
Inductive tree (X:Set) : Set :=
  | emptyTree : X -> tree X
  | consTree: tree X -> tree X -> tree X.

(* Parte 1 *)
Fixpoint mapTree(A B:Set)(P:A -> B)(t:tree A):tree B :=
    match t with
       | emptyTree _ a => emptyTree B (P a)
       | consTree _ t1 t2 => consTree B (mapTree A B P t1) (mapTree A B P t2)
    end.

(* Parte 2 *)
Fixpoint contHojas (A:Set)(t:tree A):nat:=
    match t with
       | emptyTree _ _ => 1
       | consTree _ t1 t2 => sum (contHojas A t1) (contHojas A t2)
    end.

(* Parte 3 *)
Lemma E16a : forall (A B: Set)(P:A -> B)(t:tree A), contHojas A t = contHojas B (mapTree A B P t).
Proof.
intros.
induction t;simpl.
reflexivity.
rewrite IHt1.
rewrite IHt2.
reflexivity.
Qed.

(* Parte 4 *)
Fixpoint hojas (A:Set)(t:tree A):list A:=
    match t with
       | emptyTree _ a => consList A a (emptyList A)
       | consTree _ t1 t2 => append A (hojas A t1) (hojas A t2)
    end.

Lemma E16b : forall (A: Set)(t:tree A), contHojas A t = length A (hojas A t).
Proof.
intros.
induction t;simpl.
reflexivity.
rewrite IHt1.
rewrite IHt2.
rewrite (L4 A (hojas A t1) (hojas A t2)).
reflexivity.
Qed.

(* Ejercicio 17 *)
(* Parte 1 *)
Inductive posfijo (X:Set) : list X -> list X -> Prop :=
  | posfijoB : forall l: list X, posfijo X l l
  | posfijoI : forall (l1 l2: list X) (a : X), posfijo X l1 l2 -> posfijo X l1 (consList X a l2).

Infix "<<"  := (posfijo _) (at level 70, right associativity).


(* Parte 2 *)
Lemma Posf1 : forall (X:Set)(l1 l2 l3 : list X), l2 = l3++l1 -> l1 << l2.
Proof.
intros X l1 l2 l3 H.
rewrite H.
clear H.
induction l3;simpl;
constructor.
assumption.
Qed.

Lemma Posf2 : forall (X:Set)(l1 l2 : list X),  l1 << l2 -> exists (l3:list X),l2 = (l3++l1) .
Proof.
intros.

induction H.
- exists (emptyList X).
  constructor.
- elim IHposfijo; intros.
  exists (consList X a x).
  rewrite H0. 
  simpl; reflexivity.
Qed.

(* Parte 3 *)
Lemma Posf3 : forall (X:Set)(l1 l2 l3: list X), l2 << l1 ++ l2.
Proof.
intros.
induction l1; simpl; constructor.
exact IHl1.
Qed.

Lemma Posf_a : forall (A : Set) (l1 l2 : list A),  l1 ++ l2 = emptyList A -> l1 = (emptyList A) /\ l2 = (emptyList A).
Proof.
destruct l1; intros.

split; simpl in H.
- reflexivity.
- assumption.
- discriminate H. 
Qed.

Lemma size: forall (A : Set) (a:A) (l1 l2 : list A),  l2 = consList A a l1 -> ~leq (length A l2) (length A l1).
Proof.
intros.
rewrite H.
simpl.
intro.

inversion_clear H0.

induction m;simpl in *.
inversion H1.

apply IHm.
inversion H1.
assumption.
Qed.

Lemma Posf_b : forall (A : Set) (l1 l2 : list A),  l2 = l1 ++ l2 -> l1 = (emptyList A).
Proof.
intros A l1 l2.

case_eq l1.
trivial.

simpl.
intros x l0 Hcons Happ.
elim (size A x (l0 ++ l2) l2).
assumption.
rewrite (L4 A l0 l2).

rewrite (SumConm (length A l0) (length A l2)).

induction (length A l2); 
simpl; constructor.

exact IHn.
Qed.

Lemma Posf4 : forall (X:Set)(l1 l2 : list X), l1 << l2 /\ l2 << l1 -> l1 = l2.
Proof.
intros X l1 l2 Hand.
induction Hand.


elim (Posf2 X  l1 l2 ); [intros l1_comp Hp2a|assumption].
elim (Posf2 X  l2 l1 ); [intros l2_comp Hp2b|assumption].

rewrite Hp2a in Hp2b.

rewrite (L10 X l2_comp l1_comp l1) in Hp2b.

cut((l2_comp ++ l1_comp)=emptyList X); [intro|
elim (Posf_b X (l2_comp ++ l1_comp) l1); [reflexivity|assumption]
].
elim (Posf_a X l2_comp  l1_comp); [intros|assumption].

rewrite H3 in Hp2a.
symmetry; assumption.
Qed.


Lemma Posf5 : forall  (X:Set)(l1 l2 l3 : list X), l1 << l2 /\ l2 << l3 -> l1 << l3.
Proof.
intros.
elim H;intros.


elim (Posf2 X  l1 l2 ); [intros l1_comp Hp2a|assumption].
elim (Posf2 X  l2 l3 ); [intros l2_comp Hp2b|assumption].

rewrite Hp2a in Hp2b.

rewrite L10 in Hp2b.
apply (Posf1 X l1 l3 (l2_comp ++ l1_comp)).
assumption.
Qed.

(* Parte 4 *)
Fixpoint ultimo(A:Set)(l:list A):list A :=
    match l with
        | emptyList _ => l
        | consList _ a (emptyList _) => l
        | consList _ a k => ultimo A k
    end.

(* Parte 5 *)
Lemma Ej17 : forall (A:Set)(l:list A), (ultimo A l) << l.
Proof.
intros.
induction l;[|destruct l];constructor.
assumption.
Qed.

(* Ejercicio 18 *)
(* Parte 1 *)
Inductive ABin (A B:Set) : Set :=
  | emptyAB : B -> ABin A B
  | consAB: A -> ABin A B -> ABin A B -> ABin A B.

(* Parte 2 *)
Fixpoint hojasAB (A B:Set)(t:ABin A B):nat:=
    match t with
       | emptyAB _ _ _ => 1
       | consAB _ _ _ t1 t2 => sum (hojasAB A B t1) (hojasAB A B t2)
    end.

(* Parte 3 *)
Fixpoint internosAB (A B:Set)(t:ABin A B):nat:=
    match t with
       | emptyAB _ _ _ => 0
       | consAB _ _ _ t1 t2 => S (sum (internosAB A B t1) (internosAB A B t2))
    end.

(* Parte 4 *)
Lemma Ej18 : forall (A B:Set)(t:ABin A B), hojasAB A B t = S(internosAB A B t ).
Proof.
induction t; simpl.
trivial.
rewrite IHt1.
rewrite IHt2.
simpl.
rewrite (SumS (internosAB A B t1) (internosAB A B t2)).
trivial.
Qed.

(* Ejercicio 19 *)
Section Ej_19.

Variable A : Set.

Inductive Tree_ : Set :=
  | nullT : Tree_
  | consT : A -> Tree_ -> Tree_ -> Tree_.

(* Parte 1 *)
Inductive isSubTree: Tree_ -> Tree_ -> Prop :=
  | subB : forall (t:Tree_), isSubTree t t
  | subI : forall (a:A)(t t1 t2:Tree_), isSubTree t t1 -> isSubTree t (consT a t1 t2)
  | subD : forall (a:A)(t t1 t2:Tree_), isSubTree t t1 -> isSubTree t (consT a t2 t1).

(* Parte 2 *)
Lemma isSubTreeR : forall (t: Tree_), isSubTree t t.
Proof.
constructor.
Qed.

(* Parte 3 *)
Lemma isSubTreeT : forall (t1 t2 t3:Tree_), isSubTree t1 t2 -> isSubTree t2 t3 -> isSubTree t1 t3.
Proof.
intros.
induction H0;
[|constructor; apply IHisSubTree
|constructor; apply IHisSubTree];
assumption.
Qed.

End Ej_19.

(* Ejercicio 20 *)
(* Parte 1 *)
Inductive ACom (A:Set): nat -> Set :=
  | AComB : A -> ACom A 0
  | AComI : forall (n:nat), A -> ACom A n -> ACom A n -> ACom A (S n).

(* Parte 2 *)
Fixpoint h (A:Set)(n:nat)(t:ACom A n):nat :=
    match t with
       | AComB _ _ => 1
       | AComI _ k _ t1 t2 => h A k t1 + h A k t2
    end.

(* Parte 3 *)
Axiom potO : forall n : nat, pot (S n) 0 = 1.
Axiom potS : forall m: nat, pot 2 (S m) = sum (pot 2 m) (pot 2 m).

Lemma Ej20: forall (A:Set)(n:nat)(t: ACom A n), h A n t = pot 2 n.
Proof.
induction t; simpl.
reflexivity.
rewrite IHt1.
rewrite IHt2.
trivial.
Qed.

(* Ejercicio 21 *)
(* Parte 1 *)
Fixpoint max (n m:nat) := 
    match (leBool m n) with
       | true => n
       | false => m
    end.

Inductive AB (A:Set): nat -> Set :=
  | ABb : AB A 0
  | ABi : forall n m:nat, A -> AB A n -> AB A m -> AB A (max (S n) (S m)).

(* Parte 2 *)
Fixpoint camino (A:Set)(n:nat)(t: AB A n): list A :=
    match t with
       | ABb _ => emptyList A 
       | ABi _ h1 h2 e t1 t2 => 
            match (leBool h2 h1) with
               | true => (consList A e (camino A h1 t1))
               | false => (consList A e (camino A h2 t2))
            end
    end.

(* Parte 3 *)
Lemma Ej21 : forall (A:Set)(n:nat)(t:AB A n), length A (camino A n t) = n.
Proof.
induction t; simpl.
reflexivity.

case_eq (leBool m n);intro; simpl;
[rewrite IHt1|
rewrite IHt2];
reflexivity.
Qed.

