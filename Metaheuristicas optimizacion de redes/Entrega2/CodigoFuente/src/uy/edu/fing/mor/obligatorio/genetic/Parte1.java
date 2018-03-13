package uy.edu.fing.mor.obligatorio.genetic;

import java.util.Arrays;
import java.util.List;
import uy.edu.fing.mor.obligatorio.controlador.ProblemaControlador;
import uy.edu.fing.mor.obligatorio.controlador.PropiedadesControlador;
import uy.edu.fing.mor.obligatorio.modelo.Arista;
import uy.edu.fing.mor.obligatorio.util.GlpkUtil;
import uy.edu.fing.mor.obligatorio.util.PropiedadesEnum;

public final class Parte1 {

    //Clase metodos estaticos
    private Parte1() {
    }

    public static List<Arista> run() {

        ProblemaControlador problema = ProblemaControlador.getInstance();

        List<Arista> aristas = problema.getAristas();
        String[] especialesStr = PropiedadesControlador.getProperty(PropiedadesEnum.ESPECIALES).split(";");
        Integer[] especiales = Arrays.asList(especialesStr).stream().map(Integer::valueOf).toArray(size -> new Integer[size]);

        return GlpkUtil.run(aristas, especiales);
    }
}
