package conceptos;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Linea {

    private String Prod;
    private Integer nProd;
    private Integer Cant;
    private Float Precio;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer IdLinea;

    public Linea() {
    }

    public Linea(publisher.DataLinea dL) {
        Prod = dL.getProd().getNombre();
        nProd = dL.getProd().getNRef();
        Cant = dL.getCantidad();
        Precio = dL.getPrecioProd();
    }

    public String getProd() {
        return Prod;
    }

    public void setProd(String Prod) {
        this.Prod = Prod;
    }

    public Integer getCant() {
        return Cant;
    }

    public void setCant(Integer Cant) {
        this.Cant = Cant;
    }

    public Float getPrecio() {
        return Precio;
    }

    public void setPrecio(Float Precio) {
        this.Precio = Precio;
    }

    public Integer getnProd() {
        return nProd;
    }

    public void setnProd(Integer nProd) {
        this.nProd = nProd;
    }

    public dataType.DataLinea getDataLinea() {
        return new dataType.DataLinea(nProd, Prod, Cant, Precio);
    }
}
