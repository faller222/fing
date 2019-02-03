(* Nombre: German Faller               *)
(* Cedula: 45203047                    *)
(* Correo: german.faller@fing.edu.uy   *)

(* Nombre: Santiago Ingold             *)
(* Cedula: 50409424                    *)
(* Correo: santiago.ingold@fing.edu.uy *)

Add LoadPath "/home/santiago/fing/CFPTT/practicos2018/Practico7/bibliotecas".

Require Export Maps.

Section State.

(** Identificadores de OSs e Hypercalls *)

Parameter os_ident : Set.
Parameter os_ident_eq : forall oi1 oi2 : os_ident, {oi1 = oi2} + {oi1 <> oi2}.

Parameter Hyperv_call: Set.


(* Memoria y direcciones *)

(* Direcciones Virtuales. *)
Parameter vadd: Set.
Parameter vadd_eq : forall va1 va2 : vadd, {va1 = va2} + {va1 <> va2}.

(** Direcciones de Máquina. *)
Parameter madd :  Set.
Parameter madd_eq : forall ma1 ma2 : madd, {ma1 = ma2} + {ma1 <> ma2}.

(** Direcciones Físicas : 
Los sitemas operativos utilizan este tipo de direcciones para ver regiones de memoriea
contigua. Estos no ven direcciones de máquina. *)
Parameter padd: Set.
Parameter padd_eq : forall pa1 pa2 : padd, {pa1 = pa2} + {pa1 <> pa2}.

(** Memory values. *)
Parameter value: Set.
Parameter value_eq:forall val1 val2 : value, {val1 = val2} + {val1 <> val2}.


(* Environment *)
Record context : Set :=
  Context
    {(** una dirección virtual es accesible, i.e. no está reserveda 
         por el Hypervisor *)
       ctxt_vadd_accessible: vadd -> bool;
     (** guest Oss (Confiable/No Confiable) **)
       ctxt_oss : os_ident -> bool
    }.

Inductive exec_mode : Set :=
    | exec_usr : exec_mode
    | exec_svc : exec_mode.

Inductive os_activity : Set :=
   | os_act_run : os_activity
   | os_act_wait : os_activity.

Record os : Set :=
  OS
      {
        curr_page:padd;
        hcall:option Hyperv_call;
      }.

Inductive content : Set :=
  |RW : option value -> content
  |PT : mapping vadd madd -> content
  |Other.

Inductive page_owner : Set :=
	| Hyp : page_owner
	| Os : os_ident -> page_owner
	| No_Owner : page_owner.

Record page: Set :=
  Page
    {page_content : content;
     page_owned_by : page_owner}.

Definition oss_map := mapping os_ident os.
Definition map_physical_machine_addr := mapping padd madd.
Definition system_memory := mapping madd page.
Definition hypervisor_map := mapping os_ident (mapping padd madd).

Record state : Set :=
  State
    {
      active_os:os_ident;
      aos_exec_mode:exec_mode;
      aos_activity:os_activity;
      oss:oss_map;
      hypervisor:hypervisor_map;
      memory:system_memory;
    }.

End State.
