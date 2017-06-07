package Controladores;

import DataTypes.DataCategoria;
import DataTypes.DataProducto;
import DataTypes.DataProveedor;
import Interfaces.IAgregarProducto;
import Conceptos.CatCompuesta;
import Conceptos.CatSimple;
import Conceptos.Categoria;
import Conceptos.Cliente;
import Manejadores.MCategoria;
import Manejadores.MProducto;
import Manejadores.MUsuario;
import Conceptos.Producto;
import Conceptos.Proveedor;
import Conceptos.Usuario;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class CAgregarProducto implements IAgregarProducto {

    MProducto mProducto;
    MUsuario mUsuario;
    MCategoria mCategoria;
    Producto Prod;

    public CAgregarProducto() {
        mProducto = MProducto.getInstance();
        mUsuario = MUsuario.getInstance();
        mCategoria = MCategoria.getInstance();
        Prod = null;

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
    public void ingresarDataProducto(DataProducto dataP) throws Exception {

        Usuario Proveedor = mUsuario.getUsuario(dataP.getProveedor());

        if (Proveedor == null) {
            throw new Exception("No existe el Usuario " + dataP.getProveedor());
        }
        if (Proveedor instanceof Cliente) {
            throw new Exception("El Usuario " + dataP.getProveedor() + " es un Cliente");
        }

        if (mProducto.getProducto(dataP.getNombre()) != null) {
            throw new Exception("El nombre " + dataP.getNombre() + " ya esta en el sistema");
        }

        if (mProducto.getProducto(dataP.getNRef()) != null) {
            throw new Exception("Ya existe un producto con No" + dataP.getNRef() + " en el sistema");
        }

        Prod = new Producto(dataP, (Proveedor) Proveedor);
    }

    @Override
    public DataCategoria listarCategorias() {
        return mCategoria.getCategoriasAnidadas().getDataCategoria();
    }

    @Override
    public void agregarCategoriaAProducto(String nomCat) throws Exception {
        Categoria cat = mCategoria.getCategoria(nomCat);

        if (Prod == null) {
            throw new Exception("No se seteo la memoria");
        }

        if (cat == null) {
            throw new Exception("No existe Categoria");
        }

        if (cat instanceof CatCompuesta) {
            throw new Exception("No contiene productos");
        }

        Prod.setCategoria(cat);
    }

    @Override
    public void agregarImagenAProducto(byte[] Img, int Pos) throws Exception {
        if (Prod == null) {
            throw new Exception("No se seteo la memoria");
        }
        Prod.agregarImgs(Img, Pos);
    }

    @Override
    public void altaProducto() throws Exception {//no agrego Categoria
        if (Prod == null) {
            throw new Exception("No se seteo la memoria");
        }
        if (Prod.getCategorias().isEmpty()) {
            throw new Exception(Prod.getNombre() + " Debe Agregar almenos una Categoria");
        }

        for (Map.Entry<String, Categoria> entry : Prod.getCategorias().entrySet()) {
            ((CatSimple) entry.getValue()).addProd(Prod);//Categoria ahora ve Producto
        }
        Proveedor prove = Prod.getProve();
        prove.addProducto(Prod);
        mProducto.addProducto(Prod);
    }
}
