(* Nombre: German Faller               *)
(* Cedula: 45203047                    *)
(* Correo: german.faller@fing.edu.uy   *)

(* Nombre: Santiago Ingold             *)
(* Cedula: 50409424                    *)
(* Correo: santiago.ingold@fing.edu.uy *)

(* Practica 1 *)

Require Import Classical.
Check classic.

Section P1.
Variables A B C:Prop.

(* Ej 1.1 *)
Theorem e11: A->A.
Proof.
intro.
assumption.
Qed.

(* Ej 1.2 *)
Theorem e12: A->B->A.
Proof.
intros.
apply H.
Qed.

(* Ej 1.3 *)
Theorem e13: (A->(B->C))->(A->B)->(A->C).
Proof.
intros.
apply H.
assumption.
apply H0.
assumption.
Qed.

(*Ej 2.1 *)
Theorem e21: (A->B)->(B->C)->A->C.
Proof.
intros H1 H2 H3 .
apply H2.
apply H1.
assumption.
Qed.

(*Ej 2.2 *)
Theorem e22: (A->B->C)->B->A->C.
Proof.
intros.
apply H.
assumption.
assumption.
Qed.

(*Ej 3.1 *)
Theorem e31_1: A->A->A.
Proof.
intros.
assumption.
Qed.

Theorem e31_2: A->A->A.
Proof.
intros.
apply H.
Qed.

(* Ej 3.2 *)
Theorem e32_1: (A->B->C)->A->(A->C)->B->C.
Proof.
intros.
apply H1.
assumption.
Qed.

Theorem e32_2: (A->B->C)->A->(A->C)->B->C.
Proof.
intros.
apply H.
assumption.
assumption.
Qed.

(* Ej 4.1 *)
Theorem e41: A -> ~~A.
Proof.
intros.
intro.
apply H0.
assumption.
Qed.

Theorem frute: ~~A -> A.
Proof.
intros.
elim (classic A);intro.
assumption.
elim(H H0).
Qed.

(* Ej 4.2 *)
Theorem e42: A -> B -> (A /\ B).
Proof.
intros.
split.
assumption.
assumption.
Qed.

(* Ej 4.3 *)
Theorem e43: (A->B->C) -> (A/\B->C).
Proof.
intros.
apply H.
apply H0.
apply H0.
Qed.

(* Ej 4.4 *)
Theorem e44: A->(A\/B).
Proof.
intro.
left.
apply H.
Qed.

(* Ej 4.5 *)
Theorem e45: B->(A\/B).
Proof.
intro.
right.
assumption.
Qed.

(* Ej 4.6 *)
Theorem e46: (A \/ B) -> (B \/ A).
Proof.
intro.
elim H;
intro.
right.
assumption.
left.
assumption.
Qed.

(* Ej 4.7 *)
Theorem e47: (A->C)->(B->C)->A\/B->C.
Proof.
intros.
elim H1.
assumption.
assumption.
Qed.

(* Ej 4.8 *)
Theorem e48: False->A.
Proof.
intro.
elim H.
Qed.

(* Ej 5.1 *)
Theorem e51: (A->B)-> ~B-> ~A.
Proof.
unfold not.
intros.
apply H0.
apply H.
assumption.
Qed.

(* Ej 5.2 *)
Theorem e52: ~(A/\~A).
Proof.
unfold not.
intro.
elim H.
intros.
apply H1.
assumption.
Qed.

(* Ej 5.3 *)
Theorem e53: (A->B)-> ~(A/\~B).
Proof.
unfold not.
intros.
elim H0.
intros.
apply H2.
apply H.
assumption.
Qed.

(* Ej 5.4 *)
Theorem e54: (A/\B)->~(A->~B).
Proof.
unfold not.
intros.
elim H0.
apply H.
apply H.
Qed.

(* Ej 5.5 *)
Theorem e55: (~A /\ ~~A) -> False.
Proof.
unfold not.
intros.
elim H.
intros.
apply H.
assumption.
Qed.

(* Ej 6.1 *)
Theorem e61: (A\/B) -> ~(~A/\~B).
Proof.
unfold not.
intros.
elim H0.
intros.
elim H.
assumption.
assumption.
Qed.

(* Ej 6.2 *)
Theorem e62: A\/B <-> B\/A.
Proof.
split; intro; elim H;intro;[right | left | right | left];
assumption.
Qed.

(* Ej 6.3 *)
Theorem e63: A\/B -> ((A->B)->B).
Proof.
intros.
elim H.
apply H0.
intro.
assumption.
Qed.

End P1.


Section Logica_Clasica.
Variables A B C: Prop.


(* Ej 7.1 *)
Theorem e71: A \/ ~A -> ~~A->A.
Proof.
unfold not.
intros.
elim H.
intro.
assumption.
intros.
elim H0.
assumption.
Qed.

(* Ej 7.2 *)
Theorem e72: A\/~A -> ((A->B) \/ (B->A)).
Proof.
unfold not.
intros.
elim H.
intro.
right.
intro.
assumption.
intro.
left.
intro.
elim H0.
assumption.
Qed.

(* Ej 7.3 *)
Theorem e73: (A \/ ~A) -> ~(A /\ B) -> ~A \/ ~B.
Proof.
unfold not.
intros.
elim H.
intro.
right.
intro.
elim H0.
split.
assumption.
assumption.
unfold not.
intro.
left.
assumption.
Qed.



(* Ej 8.1 *)
Theorem e81: forall A:Prop, ~~A->A.
Proof.
intro.
cut (A0 \/ ~A0).
intros.
elim H; intro.
assumption.
elim H0.
assumption.
exact (classic A0).
Qed.

(* Ej 8.2 *)
Theorem e82: forall A B:Prop, (A->B)\/(B ->A).
Proof.
intros.
cut (A0 \/ ~A0).
unfold not.
intro.
elim H.
intro.
right.
intro.
assumption.
intro.
left.
intro.
elim H0.
assumption.
exact (classic A0).
Qed.

(* Ej 8.3 *)
Theorem e83: forall A B:Prop, ~(A/\B)-> ~A \/ ~B.
Proof.
intro.
intro.
cut (A0 \/ ~A0).
unfold not.
intros.
elim H.
intro.
right.
intro.
elim H0.
split.
assumption.
assumption.
intro.
left.
assumption.
exact (classic A0).
Qed.

End Logica_Clasica.


Section Traducciones1.

(* Ej 9 *)
(* Definiciones *)
Variable N R C U : Prop.

Hypothesis H1 : ~N \/ ~R.
Hypothesis H2 : C \/ ~U.

Theorem ej9 : N /\ U -> C /\ ~R.
Proof.

intro Hand.
elim Hand.
intros HN HU.

split.
elim H2; intro Hnu.
assumption.
elim (Hnu HU).

elim H1; intros.
elim (H HN).
assumption.
Qed.


(* Ej 10 y 11 *)
(* Formalizaciones a cargo del estudiante *)
(* Ej 10 *)
Variable A:Prop.
Theorem e10_1: A->~~A.
Proof.
tauto.
Qed.

Theorem e10_2: A\/~A.
Proof.
tauto.
Qed.

End Traducciones1.


Section Traducciones2.


(* Ej 11 *)
Variable escoces mediasRojas kilt casado saleDomingos:Prop.

Hypothesis R1: ~escoces->mediasRojas.
Hypothesis R2: kilt \/ ~mediasRojas.
Hypothesis R3: casado -> ~saleDomingos.
Hypothesis R4: saleDomingos <-> escoces.
Hypothesis R5: kilt -> (escoces /\ casado).
Hypothesis R6: escoces -> kilt.

Theorem ningunoCumpleTauto: False.
Proof.
tauto.
Show Proof.
Qed.

Theorem ningunoCumple:False.
Proof.
cut ( kilt ).
intro.
absurd saleDomingos.
apply R3.
apply R5.
assumption.
apply R4.
apply R5.
assumption.
elim R2; intro.
assumption.
apply R6.

cut (~~escoces).

cut (escoces \/ ~escoces).
intros.
elim H0; intro.
assumption.
elim H1.
assumption.
exact(classic escoces).

intro.
absurd mediasRojas.
assumption.
apply R1.
exact H0.
Show Proof.
Qed.

End Traducciones2.

Section Traducciones3.
(* Ej 12 *)
(* Definiciones *)
Variable PF:Prop. (* el paciente tiene fiebre *)
Variable PA:Prop. (* el paciente tiene piel amarillenta *)
Variable PH:Prop. (* el paciente tiene hepatitis *)
Variable PR:Prop. (* el paciente tiene rubeola *)

Hypothesis R1: (PF \/ PA) -> (PH \/ PR).
Hypothesis R2: ~PR \/ PF.
Hypothesis R3: (PH /\ ~PR) -> PA.


Theorem ej12: (~PA /\ PF) -> PR.
Proof.
intro.
elim H; intros.
elim (classic PH); intro.

apply NNPP.
intro.
absurd PA.
assumption.
apply R3.
split; assumption.

elim R1;intros.
elim (H2 H3).
assumption.
left.
assumption.
Qed.
End Traducciones3.
