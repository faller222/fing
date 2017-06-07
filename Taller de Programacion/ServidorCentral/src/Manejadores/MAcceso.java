package Manejadores;

import Conceptos.Acceso;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class MAcceso {

    private static int conter = 1;
    private static MAcceso instancia = null;
    private LinkedList<Acceso> Accesos = new LinkedList<>();

    private MAcceso() {
    }

    public static MAcceso getInstance() {
        if (instancia == null) {
            instancia = new MAcceso();
        }
        return instancia;
    }

    public static Date resDays(Date d, int days) {
        return new Date(d.getTime() - days * 1000 * 60 * 60 * 24);
    }

    private void actualizar() {
        LinkedList<Acceso> nueva = new LinkedList<>();
        for (Acceso acceso : Accesos) {
            Date P = new Date();
            P = resDays(P, 15);
            P = resDays(P, 15);
            if (acceso.getFAcceso().after(P)) {
                nueva.addLast(acceso);
            }
        }
        Accesos = nueva;
    }

    public synchronized void agregarAcceso(Acceso ac) {
        ac.setNumero(conter++);
        if (Accesos.size() >= 10000) {
            Accesos.removeLast();
        }
        Accesos.addFirst(ac);
    }

    public List<Acceso> getAccesos() {
        return Accesos;
    }
}
