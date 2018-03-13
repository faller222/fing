package uy.edu.fing.mor.obligatorio.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Concentrador {

    Integer maxDelayAllowed;
    Integer cantLineas;
    List<Linea> lineas;
    String id = null;

    public Concentrador(Concentrador copy) {
        maxDelayAllowed = copy.maxDelayAllowed;
        cantLineas = copy.cantLineas;
        id = null;
        lineas = new ArrayList<>();
        for (Linea linea : copy.lineas) {
            lineas.add(new Linea(linea));
        }
    }

    public Concentrador(Integer maxDelayAllowed, Integer cantLineas, List<Linea> lineas) {
        //TODO se podria validar que la entrada en cant coincida con el tamanio de lineas
        this.maxDelayAllowed = maxDelayAllowed;
        this.cantLineas = cantLineas;
        this.lineas = lineas;
    }

    private Integer[] getDelays() {
        Integer[] delays = new Integer[lineas.size()];
        for (int i = 0; i < lineas.size(); i++) {
            delays[i] = lineas.get(i).getDelay();
        }
        return delays;
    }

    public boolean isValido() {
        for (Integer delay : getDelays()) {
            if (delay > maxDelayAllowed) {
                return false;
            }
        }
        return true;
    }

    public Integer getMaxDelayAllowed() {
        return maxDelayAllowed;
    }

    public Integer getCantLineas() {
        return cantLineas;
    }

    public List<Linea> getLineas() {
        return lineas;
    }

    private void genId() {
        Collections.sort(lineas, (o1, o2) -> {
            return o1.getId().compareTo(o2.getId());
        });
        id = "";
        for (Linea linea : lineas) {
            id += linea.getId() + ":";
        }
    }

    public String getId() {
        if (id == null) {
            genId();
        }
        return id;
    }

    public boolean igualA(Concentrador other) {
        boolean encontre;
        for (Linea linea1 : lineas) {
            encontre = false;
            for (Linea linea2 : other.getLineas()) {
                if (linea1.igualA(linea2)) {
                    encontre = true;
                    break;
                }
            }
            if (!encontre) {
                return false;
            }
        }
        return true;
    }
}
