(* Nombre: German Faller               *)
(* Cedula: 45203047                    *)
(* Correo: german.faller@fing.edu.uy   *)

(* Nombre: Santiago Ingold             *)
(* Cedula: 50409424                    *)
(* Correo: santiago.ingold@fing.edu.uy *)

Add LoadPath "/home/santiago/fing/CFPTT/practicos2018/Practico7/bibliotecas".

Require Export Maps.
Require Export State.

Section Actions.

(* Parte 2 *)
Inductive Action : Set :=
  | read: vadd -> Action
  | write: vadd -> value-> Action
  | chmod: Action.

(* Parametros *)
Parameter ctxt : context.
Parameter va : vadd.
Parameter s: state.
Parameter s': state.
Parameter sysmem:system_memory.
Parameter ma:madd.
Parameter val:value.
Parameter a:Action.

(* Parte 3 *)
(* Definiciones previas *)
Definition os_accessible va := ctxt_vadd_accessible ctxt va = true.

Definition va_mapped_to_ma s va ma : Prop :=
  exists ma1 : madd,
    (exists map_pa_ma :(mapping padd madd), Value _ map_pa_ma = map_apply os_ident_eq (hypervisor s) (active_os s) /\
    exists so: os, Value _ so = map_apply os_ident_eq (oss s) (active_os s) /\
    Value _ ma1 = map_apply padd_eq (map_pa_ma) (curr_page so))
  /\
  exists map_va_ma :(mapping vadd madd), 
    (exists p: page, Value _ p = map_apply madd_eq (memory s) ma1 /\ PT map_va_ma = (page_content p)) /\
    Value _ ma = map_apply vadd_eq map_va_ma va.

Definition is_RW sysmem ma : Prop := 
match map_apply madd_eq sysmem ma with
  | Value _ p => match page_content p with
	  | RW _ => True
	  | _ => False
	end
  | _ => False
end.

Definition check_running_execMode_state s s' (em :exec_mode) :Prop := 
	active_os s = active_os s' /\
	aos_exec_mode s' = em /\
	aos_activity s' = os_act_run /\
	oss s = oss s' /\
	hypervisor s = hypervisor s' /\
	memory s = memory s'.

Definition check_relation_state s s' (idx :madd) :Prop := 
  active_os s = active_os s' /\
  aos_exec_mode s = aos_exec_mode s' /\
  aos_activity s = aos_activity s' /\
  oss s = oss s' /\
  hypervisor s = hypervisor s' /\
  forall ma :madd, ma = idx \/ map_apply madd_eq (memory s) ma = map_apply madd_eq (memory s') ma.

(* Pre y post *)
Definition pre_read s va:=
  os_accessible va /\ 
  aos_activity s = os_act_run /\ 
	exists ma:madd, va_mapped_to_ma s va ma /\ 
  is_RW (memory s) ma.

Definition post_read s (va:vadd) (s':state) := s = s'.

Definition pre_write s va (val:value) :=
  os_accessible va /\ 
  aos_activity s = os_act_run /\ 
	exists ma:madd, va_mapped_to_ma s va ma /\ 
  is_RW (memory s) ma.

Definition post_write s va val s':= 
  exists ma : madd, va_mapped_to_ma s va ma /\ 
	(exists p:page, Value _ p = map_apply madd_eq (memory s) ma /\
		memory s' = map_add madd_eq (memory s) ma (Page (RW (Some val)) (page_owned_by p))) /\
	check_relation_state s s' ma.

Definition pre_chmod s :=
  aos_activity s = os_act_wait /\ 
	exists op_sys :os, Value _ op_sys = map_apply os_ident_eq (oss s) (active_os s) /\ 
	hcall op_sys = None.

Definition post_chmod s s' :=
  (ctxt_oss ctxt (active_os s) = true /\
    check_running_execMode_state s s' exec_svc)
	\/
	(ctxt_oss ctxt (active_os s) = false /\
    check_running_execMode_state s s' exec_usr).

Definition Pre s a : Prop :=
match a with
  | read va => pre_read s va
  | write va val => pre_write s va val
  | chmod => pre_chmod s
end.

Definition Post s a s': Prop :=
match a with
  | read va => post_read s va s'
  | write va val => post_write s va val s'
  | chmod => post_chmod s s'
end.

(* Parte 5 *)
Definition is_Owner sysmem (id_os : os_ident) ma: Prop :=
match map_apply madd_eq sysmem ma with
  | Value _ p => 
    match page_owned_by p with
      | Os id_os => True
      | _ => False
    end
  | _ => False
end.

Definition valid_state_propIII s := 
((aos_activity s = os_act_wait) \/
 (aos_activity s = os_act_run /\
  ctxt_oss ctxt (active_os s) = true)) -> aos_exec_mode s = exec_svc.

Definition valid_state_propV s := 
forall (id_os :os_ident)(pa :padd)(map_pa_ma : mapping padd madd),
  Value _ map_pa_ma = map_apply os_ident_eq (hypervisor s) id_os ->
    (exists ma :madd, Value _ ma = map_apply padd_eq map_pa_ma pa /\ 
     is_Owner (memory s) id_os ma)/\
     forall (pa1 pa2 :padd), 
       map_apply padd_eq map_pa_ma pa1 = map_apply padd_eq map_pa_ma pa2 -> pa1 = pa2.

Definition valid_state_propVI s :=
forall (id_os :os_ident)(ma : madd) (p :page) (map_va_ma : mapping vadd madd),
  Value _ p = map_apply madd_eq (memory s) ma /\
  PT map_va_ma = page_content p /\
  page_owned_by p = Os id_os /\
  (forall (va1:vadd)(ma1:madd),
     Value _ ma1 = map_apply vadd_eq map_va_ma va1 /\
     (exists p1:page, Value _ p1 = map_apply madd_eq (memory s) ma1 ->
      ((ctxt_vadd_accessible ctxt va1 = true /\
        page_owned_by p1 = Os id_os ) \/
        (ctxt_vadd_accessible ctxt va1 = false /\
         page_owned_by p1 = Hyp))
      )
  ).

Definition valid_state s := valid_state_propIII s /\ valid_state_propV s /\ valid_state_propVI s.

(* Parte 4 *)
Inductive one_step_execution s a s' :Prop := 
 | oneStepExec : valid_state s /\ Pre s a /\ Post s a s' -> one_step_execution s a s'.



(* Parte 6 *)
Lemma propIII_read : forall s s', forall a, (one_step_execution s a s') -> (valid_state_propIII s').
Proof.
intros.
destruct a0.
(* read *)
- elim H.
  intros.
  elim H0.
  intros.
  inversion_clear H1.
  elim H2.
  intros.
  elim H5.
  assumption.

(* write *)
-
elim H;intro Hs0;clear H.

inversion_clear Hs0.
inversion_clear H0.
intro aos_activity.

inversion_clear H.
inversion_clear H3.
inversion_clear H2.
elim H3;intros aux existsP;clear H3 aux.
elim existsP;clear existsP;intros existsP checkRel.
elim existsP;intros p Vmadd;clear existsP.
elim Vmadd;clear Vmadd;intros Vmadd mems'0.
elim mems'0;clear mems'0.
elim checkRel;intros activeOs execMode.
elim H0.

-- symmetry.
   apply execMode.

-- elim execMode;clear execMode;intros aux stateAct;clear aux.
   elim stateAct;clear stateAct;intros stateAct aux.
   elim aos_activity;intro stateAct';rewrite stateAct;
   [left;assumption|right].
   split;[
          elim stateAct'; intros;assumption
         |rewrite activeOs;inversion_clear stateAct';assumption].

(* chmod *)
- intro osAct.

  inversion_clear H.
  elim H0;intros validS0 prePostS0;clear H0.
  elim prePostS0;intros preS0 postS0;clear prePostS0.
  inversion_clear validS0.
  elim H0;intros;clear H0 H1 H2.
  inversion_clear preS0.
  elim H1;intros SO valOS;clear H1.
  elim valOS;intros;clear valOS H1 H2.
  inversion_clear postS0.

-- elim H1;intros aux CheckRun;clear H1 aux.
   inversion_clear CheckRun.
   elim H2;intros;clear H2 H4.
   assumption.

-- elim H1;intros ctxtOSs checkRun;clear H1.
   inversion_clear checkRun.
   elim H2;intros aux OSAct;clear H2 aux.
   elim OSAct;clear OSAct;intros OSActRun aux;clear aux.
   elim osAct;intro OSActWait.

--- absurd (aos_activity s'0 = os_act_run);[rewrite OSActWait;discriminate|assumption].

--- elim OSActWait;intros aux ctxtOS ;clear OSActWait aux.
    absurd (true = false).
    discriminate.
    rewrite <- ctxtOS.
    rewrite <- ctxtOSs.
    rewrite H1.
    reflexivity.
Qed.

(* Parte 7 *)
Lemma read_isolation : forall s s', forall va, (one_step_execution s (read va) s') ->
        exists ma, (va_mapped_to_ma s va ma) /\ 
        exists pg:page, Value _ pg = map_apply madd_eq (memory s) ma /\
        page_owned_by pg = Os (active_os s).
Proof.
intros s s' va step_exec.

inversion_clear step_exec.
inversion_clear H.
inversion_clear H1.
inversion_clear H0.
inversion_clear H3.
inversion H2; clear H2.
inversion_clear H.
inversion_clear H5.
elim H6;intros ma maAndRW;clear H6.

exists ma.
rewrite <- H3.
split;[apply maAndRW|].

inversion_clear maAndRW.
inversion_clear H5.
inversion_clear H7.
inversion_clear H8.
elim H7;intros;clear H7.
elim H8;intros;clear H8.

exists x1. 
elim (H4 (active_os s) ma x1 x0);intros maddx1 PTx0. 
split;[assumption|apply PTx0].
Qed.

End Actions.
