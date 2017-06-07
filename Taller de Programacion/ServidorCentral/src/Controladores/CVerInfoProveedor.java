package Controladores;

import DataTypes.DataProducto;
import DataTypes.DataProveedor;
import Interfaces.IVerInfoProveedor;
import Conceptos.Cliente;
import Manejadores.MUsuario;
import Conceptos.Producto;
import Conceptos.Proveedor;
import Conceptos.Usuario;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CVerInfoProveedor implements IVerInfoProveedor {

    private MUsuario mUsuario;
    private DataProveedor dataP;
    private Proveedor Temp;

    public CVerInfoProveedor() {
        dataP = null;
        mUsuario = MUsuario.getInstance();
    }

    @Override
    public Map<String, DataProveedor> listarProveedores() {
        Map<String, DataProveedor> proveedores = new HashMap<String, DataProveedor>();
        DataProveedor dp;
        for (Map.Entry<String, Usuario> entry : mUsuario.getUsuarios().entrySet()) {
            String string = entry.getKey();
            Usuario usuario = entry.getValue();
            if (usuario instanceof Proveedor) {
                dp = (DataProveedor) usuario.getDataUsuario();
                proveedores.put(string, dp);
            }
        }
        return proveedores;
    }

    @Override
    public DataProveedor seleccionarProveedor(String nick) throws Exception {//Usuario no existe,No es Proveedor
        Usuario usu = mUsuario.getUsuario(nick);
        if (usu == null) {
            throw new Exception("No existe Usuario " + nick + " En el Sistema");
        }
        if (usu instanceof Cliente) {
            throw new Exception(nick + " Es un Cliente");
        }
        Temp = (Proveedor) usu;
        dataP = (DataProveedor) Temp.getDataUsuario();
        return dataP;
    }

    @Override
    public List<DataProducto> listarProductosProveedor() throws Exception {
        List<DataProducto> dps = new ArrayList();
        if (Temp == null) {
            throw new Exception("Memoria no seteada");
        }
        Map<String, Producto> prods = Temp.getProductos();
        for (Map.Entry<String, Producto> entry : prods.entrySet()) {
            Producto producto = entry.getValue();
            dps.add(producto.getDataProducto());
        }
        return dps;
    }

    public boolean isOnline(String Nick) {
        Usuario prove = mUsuario.getUsuario(Nick);
        if (prove == null) {
            return false;
        } else {
            return prove.isOnline();
        }
    }
}
