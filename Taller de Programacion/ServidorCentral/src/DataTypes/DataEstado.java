package DataTypes;

import Extras.TipoEstado;
import java.util.Date;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType
public class DataEstado {

    private TipoEstado est;
    private Date fecha;

    public DataEstado() {
    }

    public TipoEstado getEst() {
        return est;
    }

    public void setEst(TipoEstado est) {
        this.est = est;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
