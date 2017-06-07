package Manejadores;

import Conceptos.Producto;
import java.util.HashMap;
import java.util.Map;

public class MProducto { //Singleton

    private static MProducto instance = null;
    private Map<Integer, Producto> productos; //numRef,Producto

    private MProducto() {
        this.productos = new HashMap<Integer, Producto>();
    }

    public static MProducto getInstance() {
        if (instance == null) {
            instance = new MProducto();
        }
        return instance;
    }

    public Producto getProducto(Integer numRef) {
        //retorna null si no encuentra un producto por ese numRef
        return this.productos.get(numRef);
    }

    public Producto getProducto(String nombre) {
        //retorna null si no encuentra un producto por ese numRef
        Producto res = null;

        for (Map.Entry<Integer, Producto> entry : productos.entrySet()) {
            Producto producto = entry.getValue();
            if (producto.getNombre().equals(nombre)) {
                res = producto;
            }
        }
        return res;
    }

    public synchronized void addProducto(Producto p) {
        productos.put(p.getNRef(), p);
    }

    public void clearProductos() {
        for (Map.Entry<Integer, Producto> entry : productos.entrySet()) {
            Producto producto = entry.getValue();
            producto.clearComentarios();
        }
        this.productos.clear();
    }

    public Map<Integer, Producto> buscarProductos(CharSequence nom) {
        Map<Integer, Producto> result = new HashMap<Integer, Producto>();
        for (Map.Entry<Integer, Producto> entry : productos.entrySet()) {//recorro todos los productos
            Producto meto = null;
            Producto Pr = entry.getValue();
            if (Pr.getNombre().contains(nom)) {//si contiene el nombre lo meto
                meto = Pr;
            }
            if (Pr.getProve().getNickname().contains(nom)) {//si coincide nick proveedor, pa'dentro
                meto = Pr;
            }
            if (meto != null) {//si no lo meto
                if (!result.containsKey(meto.getNRef())) { //si no esta lo agrego
                    result.put(meto.getNRef(), meto);
                }
            }
        }
        return result;
    }
}
