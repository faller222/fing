package DataTypes;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType
public class DataCategoria {

    private String Nombre;
    private Boolean esSimple;
    private ArrayList<DataCategoria> CateHijas = new ArrayList< DataCategoria>();

    public DataCategoria() {
    }

    public String getNombre() {
        return Nombre;
    }

    public DataCategoria(String Name, Boolean Simple) {
        Nombre = Name;
        esSimple = Simple;
    }

    public Boolean getEsSimple() {
        return esSimple;
    }

    public void addHija(DataCategoria Hija) {
        CateHijas.add(Hija);
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public void setEsSimple(Boolean esSimple) {
        this.esSimple = esSimple;
    }

    public ArrayList<DataCategoria> getCateHijas() {
        return CateHijas;
    }

    public void setCateHijas(ArrayList<DataCategoria> CateHijas) {
        this.CateHijas = CateHijas;
    }
}
