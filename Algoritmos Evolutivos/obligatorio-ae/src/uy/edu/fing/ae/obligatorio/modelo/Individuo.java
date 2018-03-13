package uy.edu.fing.ae.obligatorio.modelo;

import java.util.List;
import uy.edu.fing.ae.obligatorio.controlador.ProblemaControlador;
import uy.edu.fing.ae.obligatorio.controlador.PropiedadesControlador;
import uy.edu.fing.ae.obligatorio.util.Logger;
import uy.edu.fing.ae.obligatorio.util.PropiedadesEnum;
import uy.edu.fing.ae.obligatorio.util.Util;

public final class Individuo {

    private Integer fitness = null;
    private String id = null;
    private Gen[] genes;
    private int[][] cubrimiento;

    public Individuo(Individuo copy) {
        cubrimiento = copy.cubrimiento;
        fitness = copy.fitness;
        Integer cantidadCamaras = PropiedadesControlador.getIntProperty(PropiedadesEnum.CAMARA_CANTIDAD);
        genes = new Gen[cantidadCamaras];
        for (int i = 0; i < cantidadCamaras; i++) {
            genes[i] = new Gen(copy.genes[i]);
        }
    }

    public Individuo(Gen[] genes) {
        this.genes = genes;
    }

    public Individuo() {

        int maxX = ProblemaControlador.getInstance().getWidth();
        int maxY = ProblemaControlador.getInstance().getHeight();
        cubrimiento = new int[maxY][maxX];

        Integer cantidadCamaras = PropiedadesControlador.getIntProperty(PropiedadesEnum.CAMARA_CANTIDAD);
        genes = new Gen[cantidadCamaras];
        for (int j = 0; j < genes.length; j++) {
            genes[j] = new Gen();
        }
    }

    public Integer getFitness() {
        if (fitness == null) {
            long start = System.currentTimeMillis();
            calcularFitness();
            long end = System.currentTimeMillis();
            Logger.debug("Demora FITNESS: " + (end - start) + " valor: " + fitness);
        }
        return fitness;
    }

    public int[][] getCubrimiento() {
        if (fitness == null) {
            calcularFitness();
        }
        return cubrimiento;
    }

    public void invalidarFitness() {
        fitness = null;
        cubrimiento = null;
    }
    
    private void calcularFitness() {
        
        Integer intNormal = PropiedadesControlador.getIntProperty(PropiedadesEnum.INTERES_NORMAL);
        Integer intMayor = PropiedadesControlador.getIntProperty(PropiedadesEnum.INTERES_MAYOR);

        ProblemaControlador problema = ProblemaControlador.getInstance();

        final int maxX = problema.getWidth();
        final int maxY = problema.getHeight();

        cubrimiento = new int[maxY][maxX];
        fitness = 0;

        for (int i = 0; i < genes.length; i++) {
            Gen gen = genes[i];
            boolean[][] cubrimientoParcial = gen.getCubrimiento();

            for (int x = 0; x < maxX; x++) {
                for (int y = 0; y < maxY; y++) {
                    if (cubrimientoParcial[y][x]) {
                        cubrimiento[y][x]++;
                    }
                }
            }
        }

        for (int x = 0; x < maxX; x++) {
            for (int y = 0; y < maxY; y++) {
                int val = cubrimiento[y][x];
                if (val>0) {
                    switch (problema.getSala(x, y)) {
                        case MAYOR:
                            fitness += intMayor;
                            break;
                        case NORMAL:
                            fitness += intNormal/val;
                            break;
                        default:
                            fitness += 0;
                    }
                }
            }
        }
    }
    
    public Gen[] getGenes() {
        return genes;
    }

    public boolean isValido() {
        for (Gen gen : genes) {
            if (!gen.isValido()) {
                return false;
            }
        }
        return true;
    }

    private void genId() {
        id = "";
    }

    public String getId() {
        if (id == null) {
            genId();
        }
        return id;
    }

    public boolean igualA(Individuo other) {
        boolean encontre;
        for (Gen g1 : genes) {
            encontre = false;
            for (Gen g2 : other.genes) {
                if (g1.igualA(g2)) {
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
