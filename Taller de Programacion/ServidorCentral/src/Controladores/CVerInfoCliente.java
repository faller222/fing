package Controladores;

import DataTypes.DataCliente;
import DataTypes.DataOrdenCompra;
import DataTypes.DataProducto;
import Interfaces.IVerInfoCliente;
import Conceptos.Cliente;
import Manejadores.MUsuario;
import Conceptos.OrdenCompra;
import Conceptos.Producto;
import Conceptos.Proveedor;
import Conceptos.Usuario;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CVerInfoCliente implements IVerInfoCliente {

    private MUsuario mUsuario;
    private DataCliente dataC;
    private Cliente Clie;

    public CVerInfoCliente() {
        dataC = null;
        mUsuario = MUsuario.getInstance();
    }

    @Override
    public Map<String, DataCliente> listarClientes() {
        Map<String, DataCliente> proveedores = new HashMap<String, DataCliente>();
        DataCliente dp;
        for (Map.Entry<String, Usuario> entry : mUsuario.getUsuarios().entrySet()) {
            String string = entry.getKey();
            Usuario usuario = entry.getValue();
            if (usuario instanceof Cliente) {
                dp = (DataCliente) usuario.getDataUsuario();
                proveedores.put(string, dp);
            }
        }
        return proveedores;
    }

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
    public Map<Integer, DataOrdenCompra> listarOrdenesCliente() throws Exception {
        Map<Integer, DataOrdenCompra> ordenes = new HashMap<Integer, DataOrdenCompra>();
        for (Map.Entry<Integer, OrdenCompra> entry : Clie.obtenerOrdenes().entrySet()) {
            OrdenCompra Orden = entry.getValue();
            Integer key = entry.getKey();
            ordenes.put(Orden.getNumero(), Orden.getDataOrdenCompra());
        }
        return ordenes;
    }
    
}
