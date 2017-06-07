package dataType;

import java.util.Date;
import publisher.TipoEstado;

public class DataEstado {

    private TipoEstado tipo;
    private Date Fecha;

    public DataEstado(TipoEstado tipo, Date Fecha) {
        this.tipo = tipo;
        this.Fecha = Fecha;
    }

    public TipoEstado getTipo() {
        return tipo;
    }

    public Date getFecha() {
        return Fecha;
    }
}
