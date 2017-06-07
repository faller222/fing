package DataTypes;

import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType
public class DataLinea {

    private Integer Cantidad;
    private Float PrecioProd;
    private Float PrecioLinea;
    private DataProducto Prod;

    public DataLinea(Integer Cantidad, Float PrecioCompra, DataProducto Prod) {
        this.Cantidad = Cantidad;
        this.PrecioProd = PrecioCompra;
        this.Prod = Prod;
        PrecioLinea = PrecioProd * Cantidad;
    }

    public DataLinea(Integer Cantidad, DataProducto Prod) {
        this.Cantidad = Cantidad;
        this.PrecioProd = Prod.getPrecio();
        this.Prod = Prod;
        PrecioLinea = PrecioProd * Cantidad;
    }

    public DataLinea() {
    }

    public Integer getCantidad() {
        return Cantidad;
    }

    public void setCantidad(Integer Cantidad) {
        this.Cantidad = Cantidad;
    }

    public Float getPrecioProd() {
        return PrecioProd;
    }

    public void setPrecioProd(Float PrecioProd) {
        this.PrecioProd = PrecioProd;
    }

    public Float getPrecioLinea() {
        return PrecioLinea;
    }

    public void setPrecioLinea(Float PrecioLinea) {
        this.PrecioLinea = PrecioLinea;
    }

    public DataProducto getProd() {
        return Prod;
    }

    public void setProd(DataProducto Prod) {
        this.Prod = Prod;
    }
}
