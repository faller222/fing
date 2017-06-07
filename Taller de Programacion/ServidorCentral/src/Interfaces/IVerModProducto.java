package Interfaces;

import DataTypes.DataCategoria;
import DataTypes.DataProducto;
import java.util.List;
import java.util.Map;

public interface IVerModProducto {

    public DataCategoria listarCategorias();

    public DataCategoria seleccionarCategoria(String nCat) throws Exception;//no exsite

    public Map<Integer, DataProducto> listarProductosCategoria() throws Exception;// no selecciono categoria o es Compuesta

    public DataProducto seleccionarProducto(Integer NRef) throws Exception;// no Existe Producto

    public Map<String, DataCategoria> listarCategoriaProductos() throws Exception;// no selecciono Producto

    public void modificarProducto(DataProducto dp) throws Exception; // no selecciono Producto

    public List<DataProducto> buscarProd(String Clave, Integer Pag, int Tipo);//Tipo: 0 como esta, 1 por Nombre, 2 por Precio
}
