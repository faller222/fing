package Controladores;

import DataTypes.DataCategoria;
import DataTypes.DataCliente;
import DataTypes.DataOrdenCompra;
import DataTypes.DataProducto;
import DataTypes.DataUsuario;
import Interfaces.IAgregarOrden;
import Conceptos.CatCompuesta;
import Conceptos.CatSimple;
import Conceptos.Categoria;
import Conceptos.Cliente;
import Conceptos.Linea;
import Manejadores.MCategoria;
import Manejadores.MOrdenCompra;
import Manejadores.MProducto;
import Manejadores.MUsuario;
import Conceptos.OrdenCompra;
import Conceptos.Producto;
import Conceptos.Proveedor;
import Conceptos.Usuario;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CAgregarOrden implements IAgregarOrden {

    MOrdenCompra mOrdenCompra;
    MUsuario mUsuario;
    MCategoria mCategoria;
    MProducto mProducto;
    Cliente Clie;
    CatSimple Cate;
    OrdenCompra Nueva;

    public CAgregarOrden() {
        Clie = null;
        Cate = null;
        Nueva = new OrdenCompra();
        mOrdenCompra = MOrdenCompra.getInstance();
        mUsuario = MUsuario.getInstance();
        mCategoria = MCategoria.getInstance();
        mProducto = MProducto.getInstance();

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
    public DataUsuario seleccionarCliente(String nick) throws Exception {//no existe usuario
        Usuario Temp = mUsuario.getUsuario(nick);
        if (Temp == null) {
            throw new Exception("No existe Usuario " + nick);
        }
        if (Temp instanceof Proveedor) {
            throw new Exception(nick + "Es un Proveedor");
        }
        Clie = (Cliente) Temp;
        return Clie.getDataUsuario();
    }

    @Override
    public DataCategoria listarCategorias() {
        return mCategoria.getCategoriasAnidadas().getDataCategoria();
    }

    @Override
    public DataCategoria buscarCategoria(String nCat) {
        return mCategoria.getCategoria(nCat).getDataCategoria();
    }

    @Override
    public Map<Integer, DataProducto> listarProductosCategoria(String nombre) throws Exception {//no existe Cate o no es del tipo
        Categoria temp = mCategoria.getCategoria(nombre);
        if (temp == null) {
            throw new Exception("No existe la Categoria " + nombre);
        }
        if (temp instanceof CatCompuesta) {
            throw new Exception(nombre + "Es Categoria Compuesta");
        }

        Cate = (CatSimple) temp;

        Map<Integer, DataProducto> Retorno = new HashMap<Integer, DataProducto>();
        for (Map.Entry<Integer, Producto> entry : Cate.getProductos().entrySet()) {
            Producto P = entry.getValue();
            Retorno.put(P.getNRef(), P.getDataProducto());
        }
        return Retorno;
    }

    @Override
    public void agregarProductoAOrden(DataProducto dp, Integer cant) {
        Nueva.addLinea(new Linea(cant, mProducto.getProducto(dp.getNRef())));
    }

    @Override
    public DataOrdenCompra altaOrden() throws Exception {//Debe tener al menos una linea
        if (Nueva.getDataOrdenCompra().getLineas().isEmpty()) {
            throw new Exception("Debe contener al menos una linea");
        }
        mOrdenCompra.addOrdenCompraRecibida(Nueva); //Aqui adentro le definen el numero!!!
        Clie.agregarOrden(Nueva);

        return Nueva.getDataOrdenCompra();
    }

    //SOLO PARA CARGAR
    public void OrdenConFecha(Date F) {
        Nueva = new OrdenCompra(F);
    }
}
