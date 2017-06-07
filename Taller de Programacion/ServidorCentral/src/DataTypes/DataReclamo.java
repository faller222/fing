package DataTypes;

import java.util.Date;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType
public class DataReclamo {

    private Date Fecha;
    private String Texto;
    private String Client;
    private String nomProd;
    private Integer nRef;
    private String respuesta;
    private Integer id;
    private Integer nProd;

    public DataReclamo(Date fe, String tex, String cli, String res, Integer id, Integer nProd) {
        Fecha = fe;
        Texto = tex;
        Client = cli;
        respuesta = res;
        this.id = id;
        this.nProd = nProd;
    }

    //para el xml
    public DataReclamo() {
    }

    public Date getFecha() {
        return Fecha;
    }

    public void setFecha(Date Fecha) {
        this.Fecha = Fecha;
    }

    public String getTexto() {
        return Texto;
    }

    public Integer getnProd() {
        return nProd;
    }

    public void setnProd(Integer nProd) {
        this.nProd = nProd;
    }

    public void setTexto(String Texto) {
        this.Texto = Texto;
    }

    public String getClient() {
        return Client;
    }

    public void setClient(String Client) {
        this.Client = Client;
    }

    public String getNomProd() {
        return nomProd;
    }

    public Integer getnRef() {
        return nRef;
    }

    public void setNomProd(String nomProd) {
        this.nomProd = nomProd;
    }

    public void setnRef(Integer nRef) {
        this.nRef = nRef;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }
}
