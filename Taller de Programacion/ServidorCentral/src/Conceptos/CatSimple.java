package Conceptos;

import DataTypes.DataCategoria;
import java.util.HashMap;
import java.util.Map;

public class CatSimple extends Categoria {

    private Map<Integer, Producto> Productos = new HashMap<Integer, Producto>();

    public CatSimple(String Nombre) {
        this.Nombre = Nombre;
    }

    public void addProd(Producto Nuevo) {
        Productos.put(Nuevo.getNRef(), Nuevo);
    }

    public void delProd(Producto Viejo) {
        Productos.remove(Viejo.getNRef());
    }

    @Override
    public DataCategoria getDataCategoria() {
        return new DataCategoria(Nombre, true);
    }

    public Map<Integer, Producto> getProductos() {
        return Productos;
    }
}