package conceptos;

import dataType.DataOrdenCompra;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import publisher.DataEstado;
import publisher.DataLinea;
import static extras.utilDate.toDate;

@Entity
public class OrdenCompra {

    @Id
    private Integer nOrd;
    @OneToMany(cascade = CascadeType.PERSIST)
    private List<Linea> lineas;
    private Float Total;
    @OneToMany(cascade = CascadeType.PERSIST)
    private List<Estado> estados;

    public OrdenCompra() {
        estados = new ArrayList<>();
        lineas = new ArrayList<>();
    }

    public OrdenCompra(publisher.DataOrdenCompra doc) {
        estados = new ArrayList<>();
        lineas = new ArrayList<>();
        List<DataEstado> Estados = doc.getEstados();
        for (DataEstado est : Estados) {
            Estado e = new Estado();
            e.setFecha(toDate(est.getFecha()));
            e.setTipo(est.getEst());
            estados.add(e);
        }
        List<DataLinea> Lineas = doc.getLineas();
        for (DataLinea lin : Lineas) {
            Linea L = new Linea(lin);
            lineas.add(L);
        }
        Total = doc.getTotal();
        nOrd = doc.getNumero();
    }

    public Integer getnOrd() {
        return nOrd;
    }

    public void setnOrd(Integer nOrd) {
        this.nOrd = nOrd;
    }

    public List<Linea> getLineas() {
        return lineas;
    }

    public void addLinea(Linea linea) {
        this.lineas.add(linea);
    }

    public void setLineas(List<Linea> lineas) {
        this.lineas = lineas;
    }

    public Float getTotal() {
        return Total;
    }

    public void setTotal(Float Total) {
        this.Total = Total;
    }

    public List<Estado> getEstados() {
        return estados;
    }

    public void setEstados(List<Estado> estados) {
        this.estados = estados;
    }

    public void addEstado(Estado estado) {
        this.estados.add(estado);
    }

    public dataType.DataOrdenCompra getDataOrden() {
        ArrayList<dataType.DataEstado> Estados = new ArrayList<>();
        ArrayList<dataType.DataLinea> Lineas = new ArrayList<>();
        for (Estado est : estados) {
            Estados.add(est.getDataEstado());
        }
        for (Linea lin : lineas) {
            Lineas.add(lin.getDataLinea());
        }
        return new DataOrdenCompra(nOrd, Lineas, Total, Estados);
    }
}
