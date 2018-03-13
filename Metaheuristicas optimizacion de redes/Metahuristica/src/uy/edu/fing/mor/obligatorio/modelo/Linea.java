package uy.edu.fing.mor.obligatorio.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Linea {

    List<Arista> tramos;
    String id = null;
    Integer delay = null;

    public Linea(Linea copy) {
        this.id = copy.id;
        this.delay = copy.delay;
        this.tramos = new ArrayList<>(copy.tramos);
    }

    public Linea(List<Arista> tramos) {
        this.tramos = tramos;
    }

    public List<Arista> getTramos() {
        return tramos;
    }

    public String getId() {
        if (id == null) {
            genId();
        }
        return id;
    }

    public Integer getDelay() {
        if (delay == null) {
            calcularDelay();
        }
        return delay;
    }

    private void genId() {
        Collections.sort(tramos, (o1, o2) -> {
            return o1.getId().compareTo(o2.getId());
        });
        id = "";
        for (Arista tramo : tramos) {
            id += tramo.getId() + ".";
        }
    }

    private void calcularDelay() {
        delay = 0;
        for (Arista arista : tramos) {
            delay += arista.getDelay();
        }
    }

    public boolean igualA(Linea other) {
        return this.getId().equals(other.getId());
    }

}
