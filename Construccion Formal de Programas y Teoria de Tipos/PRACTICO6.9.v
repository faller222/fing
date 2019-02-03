Require Import List.
Require Import ZArith.
Require Import Omega.

Open Scope Z_scope.


Inductive sorted : list Z -> Prop :=
  | sorted0: sorted nil
  | sorted1: forall z:Z, sorted  (z::nil)
  | sorted2:
      forall (z1 z2:Z)(l:list Z),
      (z1<= z2) -> sorted (z2 :: l) ->
      sorted (z1::z2::l).

Hint Resolve sorted0 sorted1 sorted2: sort.


Lemma sort_2357: sorted (2::3::5::7::nil).
Proof.
(* apply sorted2. *)


auto with sort zarith.
Qed.

Theorem sorted_inv : forall (z:Z) (l:list Z), sorted (z :: l) -> sorted l.
Proof.
intros z l H.
inversion H; auto with sort.
Qed.

Fixpoint nb_occ (z:Z) (l:list Z) {struct l} : nat :=
  match l with
    | nil => O%nat
    | (z' :: l') =>
          match Z_eq_dec z z' with
            | left _ => S (nb_occ z l')
            | right _ => nb_occ z l'
          end
end.

Eval compute in (nb_occ 3 (3 :: 7 :: 3 :: nil)).
Eval compute in (nb_occ 36725 (3 :: 7 :: 3 :: nil)).

Definition equiv (l l':list Z) := forall z:Z, nb_occ z l = nb_occ z l'.

Lemma equiv_refl : forall l:list Z, equiv l l.
Proof.
unfold equiv; trivial.
Qed.

Lemma equiv_sym : forall l l':list Z, equiv l l' -> equiv l' l.
Proof.
unfold equiv; auto.
Qed.

Lemma equiv_trans : forall l l' l'':list Z, equiv l l' -> equiv l' l'' -> equiv l l''.
Proof.
intros l l' l'' H HO z.
eapply trans_eq; eauto.
Qed.

Lemma equiv_cons: forall (z:Z) (l l':list Z), equiv l l' -> equiv (z :: l) (z :: l').
Proof.
intros z l l' H z'.
simpl; case (Z_eq_dec z' z); auto.
Qed.

Lemma equiv_perm : forall (a b:Z) (l l':list Z), equiv l l' -> equiv(a::b::l)(b::a::l').
Proof.
intros a b l l' H z; simpl.
case (Z_eq_dec z a); case (Z_eq_dec z b);
simpl; case (H z); auto.
Qed.

Hint Resolve equiv_cons equiv_refl equiv_perm : sort.

Fixpoint aux (z:Z) (l:list Z) {struct l}: list Z :=
  match l with
    | nil => z :: nil
    | cons z' l' =>
        match Z_le_gt_dec z z' with
          | left _ => z :: z' :: l'
          | right _ => z' :: (aux z l')
        end
  end.

Eval compute in (aux 4 (2 :: 5 :: nil)).

Eval compute in (aux 4 (24 :: 50 ::nil)).

Lemma aux_equiv : forall (l:list Z) (x:Z), equiv (x :: l) (aux x l).
Proof.
induction l as [|a l0 H]; simpl; auto with sort.
intros x; case (Z_le_gt_dec x a);
  simpl; auto with sort.
intro; apply equiv_trans with (a :: x :: l0);
  auto with sort.
Qed.

Lemma aux_sorted : forall (l:list Z) (x:Z), sorted l -> sorted (aux x l).
Proof.
intros l x H. elim H; simpl; auto with sort.


intro z; destruct (Z_le_gt_dec x z); auto with sort zarith.

intros zi z2; destruct (Z_le_gt_dec x z2);
  destruct (Z_le_gt_dec x zi);
 simpl; auto with sort zarith.
Qed.

Definition sort: forall l: list Z, {l' : list Z | equiv l l' /\ sorted l'}.
induction l as [| a l IH1].
exists (nil (A:=Z)); split; auto with sort.
case IH1; intros l' [H0 Hi].
exists (aux a l'); split.
apply equiv_trans with (a :: l'); auto with sort.
apply aux_equiv.
apply aux_sorted; auto.
Defined.

