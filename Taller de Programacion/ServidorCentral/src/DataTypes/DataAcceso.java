package DataTypes;

import java.util.Date;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType
public class DataAcceso {

    private Integer Numero;
    private String User;
    private String IP;
    private String URL;
    private String Browser;
    private String SistOper;
    private Date FAcceso;

    public DataAcceso(Integer num, String user, String ip, String url, String bro, String sis, Date fac) {
        Numero = num;
        User = user;
        IP = ip;
        URL = url;
        Browser = bro;
        SistOper = sis;
        FAcceso = fac;
    }

    public Date getFAcceso() {
        return FAcceso;
    }

    public Integer getNumero() {
        return Numero;
    }

    public String getIP() {
        return IP;
    }

    public String getURL() {
        return URL;
    }

    public String getBrowser() {
        return Browser;
    }

    public String getSistOper() {
        return SistOper;
    }

    public String getUser() {
        return User;
    }

    public void setUser(String User) {
        this.User = User;
    }

    public void setNumero(Integer Numero) {
        this.Numero = Numero;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public void setBrowser(String Browser) {
        this.Browser = Browser;
    }

    public void setSistOper(String SistOper) {
        this.SistOper = SistOper;
    }

    public void setFAcceso(Date FAcceso) {
        this.FAcceso = FAcceso;
    }
}
