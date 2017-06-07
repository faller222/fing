package Conceptos;

import DataTypes.DataAcceso;
import java.util.Date;

public class Acceso {

    private Integer Numero;
    private String User;
    private String IP;
    private String URL;
    private String Browser;
    private String SistOper;
    private Date FAcceso;

    public Acceso(String ip, String user, String url, String bro, String sis, Date fac) {
        Numero = -1;
        User = user;
        IP = ip;
        URL = url;
        Browser = bro;
        SistOper = sis;
        FAcceso = fac;
    }

    public DataAcceso getDataAcceso() {
        return new DataAcceso(Numero, User, IP, URL, Browser, SistOper, FAcceso);
    }

    public Date getFAcceso() {
        return FAcceso;
    }

    public void setNumero(Integer Numero) {
        this.Numero = Numero;
    }
}
