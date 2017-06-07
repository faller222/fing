package Controladores;

import Conceptos.Conversacion;
import Manejadores.MConversacion;
import java.util.ArrayList;
import java.util.List;

public class CConversacion {

    private MConversacion mCon;

    public CConversacion() {
        mCon = MConversacion.getInstance();
    }

    public void agregarMensaje(String Cliente, String Proveedor, String Mensaje) {
        mCon.nuevoMensaje(Cliente, Proveedor, Mensaje);
    }

    public List<String> mensajesEntre(String Cliente, String Proveedor) {
        String clave = Cliente + "-" + Proveedor;
        Conversacion Conv = mCon.getConversacion(clave);
        List<String> Ret;
        if (Conv == null) {
            Ret = new ArrayList<>();
        } else {
            Ret = Conv.getMensajes();
        }
        return Ret;
    }

    public int contarMensajes(String Cliente, String Proveedor) {
        return mensajesEntre(Cliente, Proveedor).size();
    }

    public List<String> conQuienHable(String Yo) {
        return mCon.getLista(Yo);
    }
}
