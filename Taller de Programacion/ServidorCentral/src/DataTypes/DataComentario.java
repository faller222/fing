package DataTypes;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType
public class DataComentario {

    private ArrayList<DataComentario> respuestas;
    private Date fecha;
    private String texto;
    private Integer id;
    private String clie;

    public DataComentario(Integer Num, Date F, String te, String cl) {
        respuestas = new ArrayList<DataComentario>();
        fecha = F;
        id = Num;
        texto = te;
        clie = cl;
    }

    public DataComentario getComentario(Integer id) {
        return respuestas.get(id);
    }

    public void addRespuesta(DataComentario respuesta) {
        this.respuestas.add(respuesta);
    }

    //para el xml
    public DataComentario() {
    }

    public Date getFecha() {
        return fecha;
    }

    public String getTexto() {
        return texto;
    }

    public Integer getId() {
        return id;
    }

    public String getClie() {
        return clie;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setClie(String clie) {
        this.clie = clie;
    }

    public ArrayList<DataComentario> getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(ArrayList<DataComentario> respuestas) {
        this.respuestas = respuestas;
    }
}
