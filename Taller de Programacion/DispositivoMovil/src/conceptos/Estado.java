package conceptos;

import dataType.DataEstado;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import publisher.TipoEstado;

@Entity
public class Estado {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer estadoId;
    private TipoEstado tipo;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date Fecha;

    public Estado() {
    }

    public TipoEstado getTipo() {
        return tipo;
    }

    public void setTipo(TipoEstado tipo) {
        this.tipo = tipo;
    }

    public Date getFecha() {
        return Fecha;
    }

    public void setFecha(Date Fecha) {
        this.Fecha = Fecha;
    }

    public Integer getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(Integer estadoId) {
        this.estadoId = estadoId;
    }

    public dataType.DataEstado getDataEstado() {
        return new dataType.DataEstado(tipo, Fecha);
    }
}
