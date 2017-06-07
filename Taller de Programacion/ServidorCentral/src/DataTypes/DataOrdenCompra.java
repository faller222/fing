package DataTypes;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement
@XmlAccessorType
@XmlSeeAlso({DataLinea.class})
public class DataOrdenCompra {

    private Integer Numero;
    private String Cliente;
    private ArrayList<DataLinea> Lineas = new ArrayList();
    private ArrayList<DataEstado> Estados = new ArrayList();
    private Float Total;

    public DataOrdenCompra(Integer num, ArrayList<DataLinea> line, ArrayList<DataEstado> est, Float Total) {
        Numero = num;
        Lineas = line;
        Estados = est;
        this.Total = Total;
    }

    public DataOrdenCompra() {
    }

    public Integer getNumero() {
        return Numero;
    }

    public String getCliente() {
        return Cliente;
    }

    public ArrayList<DataLinea> getLineas() {
        return Lineas;
    }

    public Float getTotal() {
        return Total;
    }

    public ArrayList<DataEstado> getEstados() {
        return Estados;
    }

    public DataEstado getEstado() {
        return Estados.get(Estados.size() - 1);
    }

    public void setEstados(ArrayList<DataEstado> Estados) {
        this.Estados = Estados;
    }

    public void addEstado(DataEstado est) {
        this.Estados.add(est);
    }

    public void setNumero(Integer Numero) {
        this.Numero = Numero;
    }

    public void setCliente(String Cliente) {
        this.Cliente = Cliente;
    }

    public void setLineas(ArrayList<DataLinea> Lineas) {
        this.Lineas = Lineas;
    }

    public void setTotal(Float Total) {
        this.Total = Total;
    }
}
