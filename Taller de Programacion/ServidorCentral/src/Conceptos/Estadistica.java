package Conceptos;

import DataTypes.DataEstadistica;
import DataTypes.DataVenta;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class Estadistica {

    private List<DataVenta> ventas;
    private Float ganancia;

    public Estadistica() {
        ventas = new ArrayList<DataVenta>();
        ganancia = new Float(0);
    }

    public void agregarVenta(DataVenta dv) {
        revisar();
        ventas.add(dv);
        ganancia += dv.getGanancia();

    }

    private void revisar() {
        Date hace_un_año = new Date();
        hace_un_año.setYear(hace_un_año.getYear() - 1);
        List<DataVenta> ventAux = new ArrayList<DataVenta>();
        for (DataVenta dp : ventas) {
            if (dp.getFecha().after(hace_un_año)) {
                ventAux.add(dp);
            }
        }
        ventas = ventAux;
    }

    public Float getGananciasTotal() {
        return ganancia;
    }

    public Float getGananciasPorMes(Integer mes) {
        revisar();
        Float ganMes = new Float(0);
        for (Iterator<DataVenta> it = ventas.iterator(); it.hasNext();) {
            DataVenta dataVenta = it.next();
            if (dataVenta.getFecha().getMonth() == mes) {
                ganMes += dataVenta.getGanancia();
            }
        }
        return ganMes;
    }

    public DataEstadistica getDataEstadistica() {
        DataEstadistica de = new DataEstadistica();
        if (this != null) {
            de.setGanancia(ganancia);
            Float[] ganMes = new Float[12];
            for (int i = 0; i < 12; i++) {
                ganMes[i] = this.getGananciasPorMes(i);
            }
            de.setMeses(ganMes);

        }

        return de;
    }
}
