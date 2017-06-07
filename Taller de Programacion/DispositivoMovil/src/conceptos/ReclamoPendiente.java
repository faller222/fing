package conceptos;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ReclamoPendiente {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idRecPen;
    private String texto;
    private String clie;
    private Integer nRef;

    public ReclamoPendiente() {
    }

    public Integer getIdRecPen() {
        return idRecPen;
    }

    public void setIdRecPen(Integer idRecPen) {
        this.idRecPen = idRecPen;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getClie() {
        return clie;
    }

    public void setClie(String clie) {
        this.clie = clie;
    }

    public Integer getnRef() {
        return nRef;
    }

    public void setnRef(Integer nRef) {
        this.nRef = nRef;
    }
}
