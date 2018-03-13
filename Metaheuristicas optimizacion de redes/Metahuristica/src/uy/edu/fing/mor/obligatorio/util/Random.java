package uy.edu.fing.mor.obligatorio.util;

import uy.edu.fing.mor.obligatorio.controlador.PropiedadesControlador;

public final class Random {

    private Random() {
    }

    public static java.util.Random init() {
        Integer seed = PropiedadesControlador.getIntProperty(PropiedadesEnum.SEED);
        java.util.Random r = (seed == null ? new java.util.Random() : new java.util.Random(seed));

        return r;
    }

    public static int get(int max) {
        java.util.Random r = init();
        return max == 0 ? 0 : r.nextInt(max);
    }

    public static int prob() {
        return Random.get(100);
    }

    public static boolean prob(int prob) {
        int numP = Random.prob();
        return numP <= prob;
    }

    public static int beetwen(int min, int max) {
        int diff = max - min;
        java.util.Random r = init();
        return diff == 0 ? min : r.nextInt(diff) + min;
    }

}
