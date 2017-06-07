package Manejadores;

import Conceptos.OrdenCompra;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MOrdenCompra {

    private static Integer AutoGen;
    private static MOrdenCompra instance = null;
    private Map<Integer, OrdenCompra> oRecibidas; //Numero de orden
    private Map<Integer, OrdenCompra> oPreparadas;
    private Map<Integer, OrdenCompra> oCanceladas;
    private Map<Integer, OrdenCompra> oConfirmadas;

    private MOrdenCompra() {
        AutoGen = 0;
        this.oRecibidas = new HashMap<Integer, OrdenCompra>();
        this.oPreparadas = new HashMap<Integer, OrdenCompra>();
        this.oCanceladas = new HashMap<Integer, OrdenCompra>();
        this.oConfirmadas = new HashMap<Integer, OrdenCompra>();
    }

    public static MOrdenCompra getInstance() {
        if (instance == null) {
            instance = new MOrdenCompra();
        }
        return instance;
    }

    public OrdenCompra getOrdenCompra(Integer nro) {
        OrdenCompra Ord = oRecibidas.get(nro);
        if (Ord == null) {
            Ord = oCanceladas.get(nro);
        }
        if (Ord == null) {
            Ord = oConfirmadas.get(nro);
        }
        if (Ord == null) {
            Ord = oPreparadas.get(nro);
        }
        return Ord;
    }

    public synchronized void addOrdenCompraRecibida(OrdenCompra oc) {
        AutoGen++;
        oc.setNumero(AutoGen);
        this.oRecibidas.put(AutoGen, oc);
    }

    public synchronized void confirmarOrden(int Orden, Date F) throws Exception {
        OrdenCompra OC = oPreparadas.remove(Orden);
        if (OC == null) {
            throw new Exception("No se encontro Preparada una orden con numero: " + Orden);
        }
        OC.confirmar(F);
        oConfirmadas.put(Orden, OC);
    }

    public synchronized void cancelarOrden(int Orden, Date F) throws Exception {
        OrdenCompra OC = oRecibidas.remove(Orden);
        if (OC == null) {
            throw new Exception("No se encontro Recibida una orden con numero: " + Orden);
        }
        OC.cancelar(F);
        oCanceladas.put(Orden, OC);
    }

    public synchronized void prepararOrden(int Orden, Date F) throws Exception {
        OrdenCompra OC = oRecibidas.remove(Orden);
        if (OC == null) {
            throw new Exception("No se encontro Recibida una orden con numero: " + Orden);
        }
        OC.preparar(F);
        oPreparadas.put(Orden, OC);

    }

    public Map<Integer, OrdenCompra> getOrdenesRecibidas() {
        return oRecibidas;
    }

    public Map<Integer, OrdenCompra> getOrdenesPreparadas() {
        return oPreparadas;
    }

    public Map<Integer, OrdenCompra> getOrdenesCanceladas() {
        return oCanceladas;
    }

    public Map<Integer, OrdenCompra> getOrdenesConfirmadas() {
        return oConfirmadas;
    }

    public void clearOrdenes() {
        AutoGen = 1;
        this.oRecibidas.clear();
        this.oCanceladas.clear();
        this.oPreparadas.clear();
        this.oConfirmadas.clear();
    }
}
