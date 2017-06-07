package DataTypes;

import java.util.Date;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType
public class DataVenta {

    private Date Fecha;
    private Integer Cantidad;
    private Float Precio;

    public DataVenta() {
    }

    public Date getFecha() {
        return Fecha;
    }

    public void setFecha(Date Fecha) {
        this.Fecha = Fecha;
    }

    public Integer getCantidad() {
        return Cantidad;
    }

    public void setCantidad(Integer Cantidad) {
        this.Cantidad = Cantidad;
    }

    public Float getPrecio() {
        return Precio;
    }

    public void setPrecio(Float Precio) {
        this.Precio = Precio;
    }

    public Float getGanancia() {
        return Precio * Cantidad;
    }
}
