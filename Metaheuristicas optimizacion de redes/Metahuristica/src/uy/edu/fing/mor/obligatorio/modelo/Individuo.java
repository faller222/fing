package uy.edu.fing.mor.obligatorio.modelo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Individuo {

    Integer costo = null;
    String id = null;
    Concentrador[] concentradores;

    public Individuo(Individuo copy) {
        this.concentradores = new Concentrador[copy.concentradores.length];
        for (int i = 0; i < concentradores.length; i++) {
            this.concentradores[i] = new Concentrador(copy.concentradores[i]);
        }
    }

    public Individuo(Concentrador[] concentradores) {
        this.concentradores = concentradores;
    }

    public Integer getCosto() {
        if (costo == null) {
            calcularCosto();
        }
        return costo;
    }

    private void calcularCosto() {
        costo = 0;
        costo = getAristas().stream().map((arista) -> arista.getCosto()).reduce(costo, Integer::sum);
    }

    public boolean isValido() {
        for (Concentrador concentrador : concentradores) {
            if (!concentrador.isValido()) {
                return false;
            }
        }
        return true;
    }

    public List<Arista> getAristas() {
        Map<Integer, Arista> tmp = new HashMap<>();
        for (Concentrador concentrador : concentradores) {
            for (Linea linea : concentrador.getLineas()) {
                for (Arista arista : linea.getTramos()) {
                    tmp.put(arista.getId(), arista);
                }

            }
        }
        return new ArrayList(tmp.values());
    }

    public Concentrador[] getConcentradores() {
        return concentradores;
    }

    private void genId() {
        List<Concentrador> lista = Arrays.asList(concentradores);
        Collections.sort(lista, (o1, o2) -> {
            return o1.getId().compareTo(o2.getId());
        });
        id = "";
        for (Concentrador concentrador : lista) {
            id += concentrador.getId() + ":";
        }
    }

    public String getId() {
        if (id == null) {
            genId();
        }
        return id;
    }

    public boolean igualA(Individuo other) {
        boolean encontre;
        for (Concentrador c1 : concentradores) {
            encontre = false;
            for (Concentrador c2 : other.getConcentradores()) {
                if (c1.igualA(c2)) {
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
