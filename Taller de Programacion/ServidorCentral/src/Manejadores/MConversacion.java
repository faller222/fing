package Manejadores;

import Conceptos.Conversacion;
import Conceptos.Usuario;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MConversacion {

    private Map<String, Conversacion> Todas;
    private Map<String, LinkedList<String>> Listas;
    private static MConversacion instancia = null;
    private static MUsuario mu = null;

    private MConversacion() {
        Todas = new HashMap<>();
        Listas = new HashMap<>();
    }

    public static MConversacion getInstance() {
        if (instancia == null) {
            instancia = new MConversacion();
        }
        mu = MUsuario.getInstance();
        return instancia;
    }

    public void addConversacion(Conversacion conv) {
        Todas.put(conv.getClave(), conv);
    }

    public Conversacion getConversacion(String Clave) {
        return Todas.get(Clave);
    }

    public List<String> getLista(String Yo) {
        List<String> Aux = Listas.get(Yo);
        if (Aux == null) {
            Aux = new LinkedList<>();
        }
        return Aux;
    }

    public void nuevoMensaje(String Cliente, String Proveedor, String Mensaje) {
        String clave = Cliente + "-" + Proveedor;
        Conversacion Conv = Todas.get(clave);
        if (Conv == null) {
            Conv = new Conversacion(Cliente, Proveedor);
            Todas.put(Conv.getClave(), Conv);
            addConversacion(Conv);
        }
        mantenerLista(Conv);
        Conv.nuevoMensaje(Mensaje);
    }

    private void mantenerLista(Conversacion conv) {
        String Cliente = conv.getCliente();
        String Proveedor = conv.getProveedor();
        Usuario Clie = mu.getUsuario(Cliente);
        Usuario Prove = mu.getUsuario(Proveedor);
        try {
            Clie.setMensajeNuevo(true);
        } catch (Exception e) {
        }
        try {
            Prove.setMensajeNuevo(true);
        } catch (Exception e) {
        }
        LinkedList<String> aux;
        aux = Listas.get(Cliente);
        if (aux == null) {
            aux = new LinkedList<>();
            Listas.put(Cliente, aux);
        } else {
            aux.remove(Proveedor);
        }
        aux.addFirst(Proveedor);

        aux = Listas.get(Proveedor);
        if (aux == null) {
            aux = new LinkedList<>();
            Listas.put(Proveedor, aux);
        } else {
            aux.remove(Cliente);
        }
        aux.addFirst(Cliente);
    }
}
