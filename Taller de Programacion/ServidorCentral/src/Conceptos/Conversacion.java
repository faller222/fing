package Conceptos;

import java.util.LinkedList;
import java.util.List;

public class Conversacion {

    private String cliente;
    private String proveedor;
    private LinkedList<String> Mensajes;

    public Conversacion(String Cliente, String Proveedor) {
        this.cliente = Cliente;
        this.proveedor = Proveedor;
        Mensajes = new LinkedList();
    }

    public void nuevoMensaje(String mensaje) {
        Mensajes.add(mensaje);

    }

    public List<String> getMensajes() {
        return Mensajes;
    }

    public String getCliente() {
        return cliente;
    }

    public String getProveedor() {
        return proveedor;
    }

    public String getClave() {
        return cliente + "-" + proveedor;
    }
}
