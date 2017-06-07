package Controladores;

import DataTypes.DataOrdenCompra;
import Interfaces.IVerCanOrden;
import Conceptos.Cliente;
import Manejadores.MOrdenCompra;
import Manejadores.MUsuario;
import Conceptos.OrdenCompra;
import Conceptos.Proveedor;
import Conceptos.Usuario;
import Conceptos.Linea;
import DataTypes.DataVenta;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CVerCanOrden implements IVerCanOrden {

    MOrdenCompra mOrdenCompra;
    MUsuario mUsuario;
    OrdenCompra Orden;

    public CVerCanOrden() {
        mOrdenCompra = MOrdenCompra.getInstance();
        mUsuario = MUsuario.getInstance();
        Orden = null;
    }

    @Override
    public Map<Integer, DataOrdenCompra> listarOrdenesRecibidas() {
        Map<Integer, OrdenCompra> OrdenesTotal = mOrdenCompra.getOrdenesRecibidas();

        Map<Integer, DataOrdenCompra> DataOrdenesTotal = new HashMap<Integer, DataOrdenCompra>();
        for (Map.Entry<Integer, OrdenCompra> entry : OrdenesTotal.entrySet()) {
            DataOrdenesTotal.put(entry.getKey(), entry.getValue().getDataOrdenCompra());
        }
        return DataOrdenesTotal;

    }

    @Override
    public DataOrdenCompra seleccionarOrden(Integer num) throws Exception {//no existe orden
        Orden = mOrdenCompra.getOrdenCompra(num);
        if (Orden == null) {
            throw new Exception("No existe Orden No" + num + " en el Sistema");
        }
        return Orden.getDataOrdenCompra();
    }

    @Override
    public void cancelarOrden() throws Exception {//Seleccione orden
        cancelarOrden(new Date());
    }

    @Override
    public void prepararOrden() throws Exception {//Seleccione orden
        prepararOrden(new Date());
    }

    @Override
    public void confirmarOrden(String nick) throws Exception {//Seleccione orden
        confirmarOrden(nick, new Date());
    }

    //solo para cargar
    public void cancelarOrden(Date F) throws Exception {//Seleccione orden
        if (Orden == null) {
            throw new Exception("Debe Seleccionar una orden");
        }
        mOrdenCompra.cancelarOrden(Orden.getNumero(), F);
    }

    public void prepararOrden(Date F) throws Exception {//Seleccione orden
        if (Orden == null) {
            throw new Exception("Debe Seleccionar una orden");
        }
        mOrdenCompra.prepararOrden(Orden.getNumero(), F);
    }

    public void confirmarOrden(String nick, Date F) throws Exception {//Seleccione orden
        Cliente clie;
        Usuario user = mUsuario.getUsuario(nick);
        if (user instanceof Proveedor) {
            throw new Exception("El usuario" + nick + "no es un Cliente");
        } else {
            clie = (Cliente) user;
        }
        if (Orden == null) {
            throw new Exception("Debe Seleccionar una orden");
        }

        for (Linea linea : Orden.getLineas()) {
            DataVenta dv = new DataVenta();
            dv.setCantidad(linea.getCantidad());
            dv.setFecha(F);
            dv.setPrecio(linea.getProd().getPrecio());
            linea.getProd().getEstadistic().agregarVenta(dv);
        }
        mOrdenCompra.confirmarOrden(Orden.getNumero(), F);
        clie.confirmoOrden(Orden);
    }
}
