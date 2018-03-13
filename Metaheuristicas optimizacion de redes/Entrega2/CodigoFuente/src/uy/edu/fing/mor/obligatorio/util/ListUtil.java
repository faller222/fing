package uy.edu.fing.mor.obligatorio.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import uy.edu.fing.mor.obligatorio.modelo.Arista;

public final class ListUtil {

    //Clase con metodos estaticos
    private ListUtil() {
    }

    public static <T> List<T> restaDeConjunto(List<T> grande, List<T> chico) {
        Map<Integer, T> mapa = new HashMap<>();
        grande.forEach((t) -> {
            mapa.put(t.hashCode(), t);
        });

        chico.forEach((t) -> {
            mapa.remove(t.hashCode());
        });
        return new ArrayList<>(mapa.values());
    }

    public static List<Arista> restaDeAristas(List<Arista> grande, List<Arista> chico) {
        HashMap<Integer, Arista> mapa = new HashMap<>();
        grande.forEach((t) -> {
            mapa.put(t.getId(), t);
        });

        chico.forEach((t) -> {
            mapa.remove(t.getId());
        });
        return new ArrayList<>(mapa.values());
    }
}
