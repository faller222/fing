package uy.edu.fing.ae.obligatorio.grasp;

import java.util.ArrayList;
import java.util.List;
import uy.edu.fing.ae.obligatorio.controlador.ProblemaControlador;
import uy.edu.fing.ae.obligatorio.modelo.Gen;
import uy.edu.fing.ae.obligatorio.modelo.Individuo;
import uy.edu.fing.ae.obligatorio.util.Random;

public final class Rcl {

    //Clase con metodos estaticos
    private Rcl() {
    }

    public static List<Individuo> bestImprovement(List<Individuo> poblacion) {

        List<Individuo> result = new ArrayList<>();
        Individuo best;
        for (Individuo individuo : poblacion) {
            long start = System.currentTimeMillis();
            best = individuo;
            List<Individuo> rcl = rcl(individuo);
            for (Individuo vecino : rcl) {
                if (vecino.getFitness() > best.getFitness()) {
                    best = vecino;
                }
            }
            result.add(best);

            long end = System.currentTimeMillis();
            ///System.out.println("best "+(end - start));
        }

        return result;
    }

    public static List<Individuo> firstImprovement(List<Individuo> poblacion) {

        List<Individuo> result = new ArrayList<>();
        Individuo first;
        for (Individuo individuo : poblacion) {
            long start = System.currentTimeMillis();
            first = individuo;
            List<Individuo> rcl = rcl(individuo);
            for (Individuo vecino : rcl) {
                if (vecino.getFitness() > first.getFitness()) {
                    first = vecino;
                    break;
                }
            }
            result.add(first);

            long end = System.currentTimeMillis();
            //System.out.println("first "+(end - start));
        }
        return result;
    }

    private static List<Individuo> rcl(Individuo individuo) {

        final int maxVecinos = 10;
        List<Individuo> result = new ArrayList<>();
        Individuo vecino;
        int mod = individuo.getGenes().length;
        while (result.size() < maxVecinos) {
            int index = Random.get(mod * 50) % mod;
            vecino = vecino(individuo, index, true);
            if (vecino.isValido()) {
                result.add(vecino);
            }
            vecino = vecino(individuo, index, false);
            if (vecino.isValido()) {
                result.add(vecino);
            }
        }

        return result;
    }

    private static Individuo vecino(Individuo candidato, int genId, boolean angulo) {
        double maxY = ProblemaControlador.getInstance().getHeight();
        double maxX = ProblemaControlador.getInstance().getWidth();
        Individuo result = new Individuo(candidato);

        Gen[] genes = result.getGenes();

        Gen gen = genes[genId];
        if (angulo) {
            //desvio entre -45 y 45
            int desvioAngulo = Random.get(90) - 45;
            gen.desvioAngulo(desvioAngulo);
        } else {
            double offsetX = Random.get(maxX / 10) + maxX / 20;
            double offsetY = Random.get(maxY / 10) + maxY / 20;

            Double newX = gen.getCoord().getCoordX() + offsetX;
            Double newY = gen.getCoord().getCoordY() + offsetY;

            gen.getCoord().setCoordX(newX.intValue());
            gen.getCoord().setCoordY(newY.intValue());

        }
        genes[genId] = gen;

        result.invalidarFitness();

        long end = System.currentTimeMillis();
        return result;
    }
}
