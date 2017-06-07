package Conceptos;

import DataTypes.DataLinea;

public class Linea {

    private Integer Cantidad;
    private Float PrecioCompra;
    private Producto Prod;

    public Linea(Integer Cantidad, Producto Prod) {
        this.Cantidad = Cantidad;
        this.PrecioCompra = Prod.getPrecio();
        this.Prod = Prod;
    }

    public DataLinea getDataLinea() {
        return new DataLinea(Cantidad, PrecioCompra, Prod.getDataProducto());
    }

    public int getNumProd() {
        return Prod.getNRef();
    }

    public int getCantidad() {
        return Cantidad;
    }

    public void addCantidad(int cant) {
        Cantidad = Cantidad + cant;
    }

    public Producto getProd() {
        return Prod;
    }

    public Float getSubTotal() {
        return PrecioCompra * Cantidad;
    }
}
