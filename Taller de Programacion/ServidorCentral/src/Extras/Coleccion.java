package Extras;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlAccessorType
@XmlSeeAlso({Par.class})
public class Coleccion {

    private ArrayList<Object> Lista;

    public Coleccion() {
        Lista = new ArrayList<Object>();
    }

    public ArrayList<Object> getList() {
        return Lista;
    }

    public void setList(ArrayList<Object> Lista) {
        this.Lista = Lista;
    }

    public void addElem(Object Elem) {
        Lista.add(Elem);
    }
}
