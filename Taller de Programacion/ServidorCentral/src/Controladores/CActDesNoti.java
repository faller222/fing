package Controladores;

import DataTypes.DataCliente;
import DataTypes.DataOrdenCompra;
import Interfaces.IActDesNoti;
import Conceptos.Cliente;
import Manejadores.MOrdenCompra;
import Manejadores.MUsuario;
import Conceptos.OrdenCompra;
import Conceptos.Producto;
import Conceptos.Proveedor;
import Conceptos.Reclamo;
import Conceptos.Usuario;
import java.util.List;
import java.util.Map;

public class CActDesNoti implements IActDesNoti {

    private Cliente Clie;
    private MUsuario mUsuario;
    private DataCliente dataC;
    private MOrdenCompra mOrden;
    private OrdenCompra Orden;

    @Override
    public DataCliente seleccionarCliente(String nick) throws Exception {//Usuario no existe,No es Proveedor
        Usuario Temp = mUsuario.getUsuario(nick);
        if (Temp == null) {
            throw new Exception("No existe Usuario " + nick + " En el Sistema");
        }
        if (Temp instanceof Proveedor) {
            throw new Exception(nick + " Es un Proveedor");
        }
        Clie = (Cliente) Temp;
        dataC = (DataCliente) Temp.getDataUsuario();
        return dataC;
    }

    @Override
    public DataOrdenCompra seleccionarOrdenCompra(Integer Noc) throws Exception {
        OrdenCompra oc = mOrden.getOrdenCompra(Noc);
        if (oc == null) {
            throw new Exception("La orden de compra no existe");
        }
        Orden = oc;
        return oc.getDataOrdenCompra();
    }

    public CActDesNoti() {
        mUsuario = MUsuario.getInstance();
    }

    @Override
    public void ActivarNotiOrdenes() {
        Clie.setNotiOrden(true);
        Map<Integer, OrdenCompra> ordenes = Clie.obtenerOrdenes();
        for (Map.Entry<Integer, OrdenCompra> entry : ordenes.entrySet()) {
            OrdenCompra ordenCompra = entry.getValue();
            ordenCompra.addObserver(Clie);
        }
    }

    @Override
    public void ActivarNotiProveedor() {
        Clie.setNotiProve(true);
        Map<String, Proveedor> provs = Clie.obtenerProveedores();
        for (Map.Entry<String, Proveedor> entry : provs.entrySet()) {
            String nick = entry.getKey();
            Proveedor proveedor = entry.getValue();
            proveedor.addObserver(Clie);
        }
    }

    @Override
    public void ActivarNotiProducto() {
        Clie.setNotiProd(true);
        Map<Integer, Producto> prods = Clie.obtenerProductos();
        for (Map.Entry<Integer, Producto> entry : prods.entrySet()) {
            Integer integer = entry.getKey();
            Producto producto = entry.getValue();
            producto.addObserver(Clie);
        }
    }

    @Override
    public void ActivarNotiReclamo() {
        Clie.setNotiRec(true);
        List<Reclamo> recs = Clie.obtenerReclamos();
        for (Reclamo rec : recs) {
            rec.addObserver(Clie);
        }
    }

    @Override
    public void DesactivarNotiReclamo() {
        Clie.setNotiRec(false);
        List<Reclamo> recs = Clie.obtenerReclamos();
        for (Reclamo rec : recs) {
            rec.delObserver(Clie);
        }
    }

    @Override
    public void DesactivarNotiOrdenes() {
        Clie.setNotiOrden(false);
        Map<Integer, OrdenCompra> ordenes = Clie.obtenerOrdenes();
        for (Map.Entry<Integer, OrdenCompra> entry : ordenes.entrySet()) {
            Integer integer = entry.getKey();
            OrdenCompra ordenCompra = entry.getValue();
            ordenCompra.delObserver(Clie);
        }
    }

    @Override
    public void DesactivarNotiProveedor() {
        Clie.setNotiProve(false);
        Map<String, Proveedor> provs = Clie.obtenerProveedores();
        for (Map.Entry<String, Proveedor> entry : provs.entrySet()) {
            String nick = entry.getKey();
            Proveedor proveedor = entry.getValue();
            proveedor.delObserver(Clie);
        }
    }

    @Override
    public void DesactivarNotiProducto() {
        Clie.setNotiProd(false);
        Map<Integer, Producto> prods = Clie.obtenerProductos();
        for (Map.Entry<Integer, Producto> entry : prods.entrySet()) {
            Integer integer = entry.getKey();
            Producto producto = entry.getValue();
            producto.delObserver(Clie);
        }
    }
}
