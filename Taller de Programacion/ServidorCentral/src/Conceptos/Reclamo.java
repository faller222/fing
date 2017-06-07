package Conceptos;

import DataTypes.DataReclamo;
import java.util.Date;

public class Reclamo implements Observable {

    private Date Fecha;
    private String Texto;
    private Cliente Client;
    private String respuesta;
    private Integer id;
    private static Integer Autogen = 0;
    private Integer nProd;
    private Observer Obs;

    @Override
    public void addObserver(Observer o) {
        Obs = o;
    }

    @Override
    public void delObserver(Observer o) {
        Obs = null;
    }

    @Override
    public void delObservers() {
        Obs = null;
    }

    @Override
    public void notifyObservers() {
        if (Obs != null) {
            Obs.Notificar((Observable) this);
        }
    }

    public Reclamo(Date fe, String tex, Cliente cli, Integer nProd) {
        Fecha = fe;
        Texto = tex;
        Client = cli;
        cli.agregarReclamo(this);
        respuesta = null;
        id = Autogen++;
        this.nProd = nProd;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
        this.notifyObservers();
    }

    public Integer getId() {
        return id;
    }

    public DataReclamo getDataReclamo() {
        return new DataReclamo(Fecha, Texto, Client.getNickname(), respuesta, id, nProd);
    }
}
