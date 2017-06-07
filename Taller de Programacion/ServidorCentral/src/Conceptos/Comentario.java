package Conceptos;

import DataTypes.DataComentario;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Comentario {

    private Map<Integer, Comentario> respuestas;
    private Date fecha;
    private String texto;
    private static Integer AutoGen = 1;
    private Integer id;
    private Cliente clie;

    public Comentario(String te, Cliente cl) throws Exception {
        if (cl == null) {
            throw new Exception("Cliente no valido");
        }
        if (te == null) {
            throw new Exception("Comentario no valido");
        }
        if (te.equals("")) {
            throw new Exception("Comentario Vacio no aceptado");
        }
        respuestas = new HashMap<Integer, Comentario>();
        fecha = new Date();
        texto = te;
        id = AutoGen++;
        clie = cl;
    }

    public Comentario(String te, Cliente cl, Date F) throws Exception {
        if (cl == null) {
            throw new Exception("Cliente no valido");
        }
        if (te == null) {
            throw new Exception("Comentario no valido");
        }
        if (te.equals("")) {
            throw new Exception("Comentario Vacio no aceptado");
        }
        respuestas = new HashMap<Integer, Comentario>();
        fecha = F;
        texto = te;
        id = AutoGen++;
        clie = cl;
    }

    public DataComentario getDataComentario() {
        DataComentario Resp = new DataComentario(id, fecha, texto, clie.getNickname());
        for (Map.Entry<Integer, Comentario> entry : respuestas.entrySet()) {//Recursion
            Resp.addRespuesta(entry.getValue().getDataComentario());
        }
        return Resp;
    }

    public Date getFecha() {
        return fecha;
    }

    public Integer getId() {
        return id;
    }

    public void addRespuesta(Comentario respuesta) {
        this.respuestas.put(respuesta.getId(), respuesta);
    }
    
    public void comentsACero() {
        AutoGen = 1;
        this.respuestas.clear();
    }
            
}
