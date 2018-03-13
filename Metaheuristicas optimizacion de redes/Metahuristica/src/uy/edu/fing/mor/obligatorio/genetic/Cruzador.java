package uy.edu.fing.mor.obligatorio.genetic;

import uy.edu.fing.mor.obligatorio.modelo.Concentrador;
import uy.edu.fing.mor.obligatorio.modelo.Individuo;
import java.util.ArrayList;
import java.util.List;
import uy.edu.fing.mor.obligatorio.controlador.PropiedadesControlador;
import uy.edu.fing.mor.obligatorio.util.PropiedadesEnum;
import uy.edu.fing.mor.obligatorio.util.Random;

public class Cruzador {

    //Clase con metodos estaticos
    private Cruzador() {
    }

    public static List<Individuo> run(List<Individuo> candidatos) {
        Integer algoritmo = PropiedadesControlador.getIntProperty(PropiedadesEnum.CRUZAMIENTO);
        switch (algoritmo) {
            case 0:
                return candidatos;
            case 1:
                return algoritmo1(candidatos);
            default:
                throw new RuntimeException("No se ha definido una acion " + algoritmo + " para la propiedad " + PropiedadesEnum.CRUZAMIENTO.getNombre());
        }
    }

    /* Algoritmo que realiza un cruzamiento seleccionando con una probabilidad x 
    sla cantdad de líneas que tome de un padre o de la madre en el cruzamiento*/
    private static List<Individuo> algoritmo1(List<Individuo> candidatos) {
        Integer cantidadCruzamientos = PropiedadesControlador.getIntProperty(PropiedadesEnum.CRUZAMIENTO_CANTIDAD);

        List<Individuo> resultado = new ArrayList<>(candidatos);

        for (int j = 0; j < cantidadCruzamientos; j++) {
            int p1 = Random.get(candidatos.size());
            int p2 = Random.get(candidatos.size());

            Individuo padre1 = candidatos.get(p1);
            Individuo padre2 = candidatos.get(p2);

            Individuo[] hijos = cruzar(padre1, padre2);

            resultado.add(hijos[0]);
            resultado.add(hijos[1]);
        }
        return resultado;
    }

    private static Individuo[] cruzar(Individuo padre1, Individuo padre2) {
        Integer cantFuentes = PropiedadesControlador.getIntProperty(PropiedadesEnum.CANT_FUENTES);
        Integer probCruz = PropiedadesControlador.getIntProperty(PropiedadesEnum.CRUZAMIENTO_PROBABILIDAD);

        Concentrador[] concentradoresP1 = padre1.getConcentradores();
        Concentrador[] concentradoresP2 = padre2.getConcentradores();

        Concentrador[] concentradoresH1 = new Concentrador[cantFuentes];
        Concentrador[] concentradoresH2 = new Concentrador[cantFuentes];

        for (int i = 0; i < cantFuentes; i++) {
            if (Random.prob(probCruz)) {
                concentradoresH1[i] = concentradoresP1[i];
                concentradoresH2[i] = concentradoresP2[i];
            } else {
                concentradoresH1[i] = concentradoresP2[i];
                concentradoresH2[i] = concentradoresP1[i];
            }
        }

        Individuo hijo1 = new Individuo(concentradoresH1);
        Individuo hijo2 = new Individuo(concentradoresH2);

        return new Individuo[]{hijo1, hijo2};
    }

}
