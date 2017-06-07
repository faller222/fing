package dataType;

import java.util.List;

public class DataOrdenCompra {

    private Integer nOrd;
    private List<DataLinea> lineas;
    private Float Total;
    private List<DataEstado> estados;

    public DataOrdenCompra(Integer nOrd, List<DataLinea> lineas, Float Total, List<DataEstado> estados) {
        this.nOrd = nOrd;
        this.lineas = lineas;
        this.Total = Total;
        this.estados = estados;
    }

    public Integer getnOrd() {
        return nOrd;
    }

    public List<DataLinea> getLineas() {
        return lineas;
    }

    public Float getTotal() {
        return Total;
    }

    public List<DataEstado> getEstados() {
        return estados;
    }
}
