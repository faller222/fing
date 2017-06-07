package Interfaces;

import DataTypes.DataCategoria;
import DataTypes.DataCliente;
import DataTypes.DataOrdenCompra;
import DataTypes.DataProducto;
import DataTypes.DataUsuario;
import java.util.Map;

public interface IAgregarOrden {

    public Map<String, DataCliente> listarClientes();

    public DataUsuario seleccionarCliente(String nick) throws Exception;//no existe usuario

    public DataCategoria listarCategorias();

    public DataCategoria buscarCategoria(String nCat);

    public Map<Integer, DataProducto> listarProductosCategoria(String nombre) throws Exception;//no existe Cate o no es del tipo

    public void agregarProductoAOrden(DataProducto dp, Integer cant);

    public DataOrdenCompra altaOrden() throws Exception;//Debe tener al menos una linea
}
