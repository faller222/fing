package Interfaces;

import DataTypes.DataCategoria;
import DataTypes.DataProducto;
import DataTypes.DataProveedor;
import java.util.Map;

public interface IAgregarProducto {

    public Map<String, DataProveedor> listarProveedores();

    public void ingresarDataProducto(DataProducto dataP) throws Exception;

    public DataCategoria listarCategorias();

    public void agregarCategoriaAProducto(String nomCat) throws Exception;

    public void agregarImagenAProducto(byte[] Img, int Pos) throws Exception;

    public void altaProducto() throws Exception;//no agrego Categoria
}
