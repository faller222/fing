package uy.edu.fing.ae.obligatorio.genetic;

import java.util.ArrayList;
import uy.edu.fing.ae.obligatorio.modelo.Individuo;
import java.util.List;
import uy.edu.fing.ae.obligatorio.controlador.ProblemaControlador;
import uy.edu.fing.ae.obligatorio.controlador.PropiedadesControlador;
import uy.edu.fing.ae.obligatorio.modelo.Gen;
import uy.edu.fing.ae.obligatorio.util.Logger;
import uy.edu.fing.ae.obligatorio.util.PropiedadesEnum;
import uy.edu.fing.ae.obligatorio.util.Random;

public class Mutador {

    private static Integer individuosFallidos = 0;
    private static Integer mutaciones = 0;

    //Clase con metodos estaticos
    private Mutador() {
    }

    public static void init() {
        individuosFallidos = 0;
        mutaciones = 0;
    }

    public static void show() {
        Logger.info("mutaciones: " + mutaciones);
        Logger.info("mutacionesMalas: " + individuosFallidos);
    }

    public static List<Individuo> run(List<Individuo> candidatos) {
        Integer algoritmo = PropiedadesControlador.getIntProperty(PropiedadesEnum.MUTACION);
        switch (algoritmo) {
            case 0:
                return candidatos;
            case 1:
                return algoritmo1(candidatos);
            default:
                throw new RuntimeException("No se ha definido una acion " + algoritmo + " para la propiedad " + PropiedadesEnum.MUTACION.getNombre());
        }
    }

    private static List<Individuo> algoritmo1(List<Individuo> candidatos) {
        List<Individuo> result = new ArrayList<>();
        for (Individuo candidato : candidatos) {
            Individuo mutado = mutar1(candidato);
            if (mutado.isValido()) {
                result.add(mutado);
            } else {
                individuosFallidos++;
            }
        }
        return result;
    }

    private static Individuo mutar1(Individuo candidato) {
        Integer probMutacion = PropiedadesControlador.getIntProperty(PropiedadesEnum.MUTACION_PROPABILIDAD);
        double maxY = ProblemaControlador.getInstance().getHeight();
        double maxX = ProblemaControlador.getInstance().getWidth();
        Individuo result = new Individuo(candidato);
        boolean muto = false;
        Gen[] genes = result.getGenes();

        for (int i = 0; i < genes.length; i++) {
            Gen gen = genes[i];
            if (Random.prob(probMutacion)) {
                //desvio entre -45 y 45
                int desvioAngulo = Random.get(90) - 45;
                gen.desvioAngulo(desvioAngulo);
                muto = true;
            }
            if (Random.prob(probMutacion)) {
                double offsetX = Random.get(maxX / 10) + maxX / 20;
                double offsetY = Random.get(maxY / 10) + maxY / 20;

                Double newX = gen.getCoord().getCoordX() + offsetX;
                Double newY = gen.getCoord().getCoordY() + offsetY;

                gen.getCoord().setCoordX(newX.intValue());
                gen.getCoord().setCoordY(newY.intValue());

                muto = true;
            }
            if (muto) {
                genes[i] = gen;
            }
        }
        if (muto) {
            mutaciones++;
            if (result.isValido()) {
                result.invalidarFitness();
            } else {
                individuosFallidos++;
                return new Individuo(candidato);
            }
        }
        return result;
    }
}
