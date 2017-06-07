package dataType;

import java.util.Date;

public class DataReclamo {

    private String cliente;
    private Date fecha;
    private String nomProd;
    private String texto;
    private Integer nRef;

    public DataReclamo() {
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

    public Integer getnRef() {
        return nRef;
    }

    public void setnRef(Integer nRef) {
        this.nRef = nRef;
    }
}
