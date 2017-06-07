package Interfaces;

import Controladores.CAcceso;
import Controladores.CActDesNoti;
import Controladores.CAgregarCategoria;
import Controladores.CAgregarOrden;
import Controladores.CAgregarProducto;
import Controladores.CAltaUsuario;
import Controladores.CPuntaje;
import Controladores.CReclamo;
import Controladores.CSesion;
import Controladores.CVerCanOrden;
import Controladores.CVerInfoCliente;
import Controladores.CVerInfoProveedor;
import Controladores.CVerModProducto;

public class Fabrica {

    public static IActDesNoti getIAD(){
        return new CActDesNoti();
    }
    
    public static IAgregarCategoria getIAC() {
        return new CAgregarCategoria();
    }

    public static IAgregarOrden getIAO() {
        return new CAgregarOrden();
    }

    public static IAgregarProducto getIAP() {
        return new CAgregarProducto();
    }

    public static IAltaUsuario getIAU() {
        return new CAltaUsuario();
    }

    public static IVerCanOrden getIVCO() {
        return new CVerCanOrden();
    }

    public static IVerInfoCliente getIVC() {
        return new CVerInfoCliente();
    }

    public static IVerInfoProveedor getIVP() {
        return new CVerInfoProveedor();
    }

    public static IVerModProducto getIVMP() {
        return new CVerModProducto();
    }

    public static ISesion getIS() {
        return new CSesion();
    }

    public static IAcceso getIA() {
        return new CAcceso();
    }

    public static IReclamo getIR() {
        return new CReclamo();
    }

    public static IPuntaje getIP() {
        return new CPuntaje();
    }
}
