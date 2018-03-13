package uy.edu.fing.ae.obligatorio.util;

import uy.edu.fing.ae.obligatorio.controlador.PropiedadesControlador;

public final class Random {

    private Random() {
    }

    public static java.util.Random init() {
        Integer seed = PropiedadesControlador.getIntProperty(PropiedadesEnum.SEED);
        return (seed == null ? new java.util.Random(System.nanoTime()) : new java.util.Random(seed));
    }

    public static int get(int max) {
        java.util.Random r = init();
        return max == 0 ? 0 : r.nextInt(max);
    }

    public static double get(double max) {
        java.util.Random r = init();
        return max == 0 ? 0 : r.nextDouble() * max;
    }

    public static double prob() {
        return Random.get(100.);
    }

    public static boolean prob(double prob) {
        return Random.prob() <= prob;
    }

    public static double beetwen(double min, double max) {
        return get(max - min) + min;
    }

    public static int beetwen(int min, int max) {
        return get(max - min) + min;
    }
}
