package dataType;

public class DataLinea {

    private String Prod;
    private Integer nProd;
    private Integer Cant;
    private Float Precio;

    public DataLinea(Integer nProd, String Prod, Integer Cant, Float Precio) {
        this.nProd = nProd;
        this.Prod = Prod;
        this.Cant = Cant;
        this.Precio = Precio;
    }

    public String getProd() {
        return Prod;
    }

    public Integer getCant() {
        return Cant;
    }

    public Float getPrecio() {
        return Precio;
    }

    public Float getSubTotal() {
        return Precio * Cant;
    }

    public Integer getnProd() {
        return nProd;
    }

    public void setnProd(Integer nProd) {
        this.nProd = nProd;
    }
}
