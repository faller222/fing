package conceptos;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import dataType.DataReclamo;

@Entity
public class Reclamo {

    private String cliente;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fecha;
    private String nomProd;
    private String texto;
    private Integer nRef;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idRec;

    public Reclamo() {
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getNomProd() {
        return nomProd;
    }

    public void setNomProd(String nomProd) {
        this.nomProd = nomProd;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Integer getIdRec() {
        return idRec;
    }

    public Integer getnRef() {
        return nRef;
    }

    public void setnRef(Integer nRef) {
        this.nRef = nRef;
    }

    public DataReclamo getDataReclamo() {
        DataReclamo res = new DataReclamo();
        res.setCliente(cliente);
        res.setFecha(fecha);
        res.setnRef(nRef);
        res.setNomProd(nomProd);
        res.setTexto(texto);
        return res;
    }
}
