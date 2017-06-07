package DataTypes;

import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType
public class DataPuntaje {
    private String Cli;
    private Integer Puntos;

    public DataPuntaje() {
        Cli=null;
        Puntos=0;
    }

    public String getCli() {
        return Cli;
    }

    public void setCli(String Cli) {
        this.Cli = Cli;
    }

    public Integer getPuntos() {
        return Puntos;
    }

    public void setPuntos(Integer Puntos) {
        this.Puntos = Puntos;
    }

}
