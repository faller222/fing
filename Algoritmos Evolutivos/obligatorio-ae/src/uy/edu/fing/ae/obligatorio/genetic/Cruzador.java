package uy.edu.fing.ae.obligatorio.genetic;

import uy.edu.fing.ae.obligatorio.modelo.Individuo;
import java.util.ArrayList;
import java.util.List;
import uy.edu.fing.ae.obligatorio.controlador.PropiedadesControlador;
import uy.edu.fing.ae.obligatorio.modelo.Gen;
import uy.edu.fing.ae.obligatorio.util.PropiedadesEnum;
import uy.edu.fing.ae.obligatorio.util.Random;

public final class Cruzador {

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
            case 2:
                return algoritmo2(candidatos);
            case 3:
                return algoritmo3(candidatos);
            default:
                throw new RuntimeException("No se ha definido una accion " + algoritmo + " para la propiedad " + PropiedadesEnum.CRUZAMIENTO.getNombre());
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

            Individuo[] hijos = cruzarUPX(padre1, padre2);

            resultado.add(hijos[0]);
            resultado.add(hijos[1]);
        }
        return resultado;
    }

    private static List<Individuo> algoritmo2(List<Individuo> candidatos) {
        Integer cantidadCruzamientos = PropiedadesControlador.getIntProperty(PropiedadesEnum.CRUZAMIENTO_CANTIDAD);

        List<Individuo> resultado = new ArrayList<>(candidatos);

        for (int j = 0; j < cantidadCruzamientos; j++) {
            int p1 = Random.get(candidatos.size());
            int p2 = Random.get(candidatos.size());

            Individuo padre1 = candidatos.get(p1);
            Individuo padre2 = candidatos.get(p2);

            Individuo[] hijos = cruzarSPX(padre1, padre2);

            resultado.add(hijos[0]);
            resultado.add(hijos[1]);
        }
        return resultado;
    }

    private static List<Individuo> algoritmo3(List<Individuo> candidatos) {
        Integer cantidadCruzamientos = PropiedadesControlador.getIntProperty(PropiedadesEnum.CRUZAMIENTO_CANTIDAD);

        List<Individuo> resultado = new ArrayList<>(candidatos);

        for (int j = 0; j < cantidadCruzamientos; j++) {
            int p1 = Random.get(candidatos.size());
            int p2 = Random.get(candidatos.size());

            Individuo padre1 = candidatos.get(p1);
            Individuo padre2 = candidatos.get(p2);

            Individuo[] hijos = cruzarBLX_alpha(padre1, padre2);

            if (hijos[0].isValido()) {
                resultado.add(hijos[0]);
            }
            if (hijos[1].isValido()) {
                resultado.add(hijos[1]);
            }
        }
        return resultado;
    }

    private static Individuo[] cruzarUPX(Individuo padre1, Individuo padre2) {
        Integer probCruz = PropiedadesControlador.getIntProperty(PropiedadesEnum.CRUZAMIENTO_PROBABILIDAD);

        Gen[] genP1 = padre1.getGenes();
        Gen[] genP2 = padre2.getGenes();

        Gen[] genH1 = new Gen[genP1.length];
        Gen[] genH2 = new Gen[genP2.length];

        for (int i = 0; i < genP1.length; i++) {
            if (Random.prob(probCruz)) {
                genH1[i] = genP1[i];
                genH2[i] = genP2[i];
            } else {
                genH1[i] = genP2[i];
                genH2[i] = genP1[i];
            }
        }

        Individuo hijo1 = new Individuo(genH1);
        Individuo hijo2 = new Individuo(genH2);

        return new Individuo[]{hijo1, hijo2};
    }

    private static Individuo[] cruzarSPX(Individuo padre1, Individuo padre2) {

        Gen[] genP1 = padre1.getGenes();
        Gen[] genP2 = padre2.getGenes();

        Gen[] genH1 = new Gen[genP1.length];
        Gen[] genH2 = new Gen[genP2.length];

        int punto = Random.beetwen(1, genP1.length);

        for (int i = 0; i < genP1.length; i++) {
            if (i < punto) {
                genH1[i] = genP1[i];
                genH2[i] = genP2[i];
            } else {
                genH1[i] = genP2[i];
                genH2[i] = genP1[i];
            }
        }

        Individuo hijo1 = new Individuo(genH1);
        Individuo hijo2 = new Individuo(genH2);

        return new Individuo[]{hijo1, hijo2};
    }

    private static Individuo[] cruzarBLX_alpha(Individuo padre1, Individuo padre2) {

        Gen[] genP1 = padre1.getGenes();
        Gen[] genP2 = padre2.getGenes();

        Gen[] genH1 = new Gen[genP1.length];
        Gen[] genH2 = new Gen[genP2.length];

        for (int i = 0; i < genP1.length; i++) {

            int blxA_h1 = blxCoord(genP1[i].getAngulo(), genP2[i].getAngulo());
            int blxX_h1 = blxCoord(genP1[i].getCoord().getCoordX(), genP2[i].getCoord().getCoordX());
            int blxY_h1 = blxCoord(genP1[i].getCoord().getCoordY(), genP2[i].getCoord().getCoordY());
            genH1[i] = new Gen(blxX_h1, blxY_h1, blxA_h1);

            int blxA_h2 = blxCoord(genP1[i].getAngulo(), genP2[i].getAngulo());
            int blxX_h2 = blxCoord(genP1[i].getCoord().getCoordX(), genP2[i].getCoord().getCoordX());
            int blxY_h2 = blxCoord(genP1[i].getCoord().getCoordY(), genP2[i].getCoord().getCoordY());
            genH2[i] = new Gen(blxX_h2, blxY_h2, blxA_h2);

        }

        Individuo hijo1 = new Individuo(genH1);
        Individuo hijo2 = new Individuo(genH2);

        return new Individuo[]{hijo1, hijo2};
    }

    private static int blxCoord(int a, int b) {
        if (a == b) {
            return a;
        }

        final Double ALPHA = PropiedadesControlador.getDoubleProperty(PropiedadesEnum.CRUZAMIENTO_ALPHA);

        int diff = Math.abs(a - b);
        Double range = diff + 2 * ALPHA * diff;
        Double result = Random.get(range) - ALPHA * diff;

        if (a < b) {
            return result.intValue() + a;
        }
        return result.intValue() + b;
    }
}
