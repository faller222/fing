package uy.edu.fing.ae.obligatorio.genetic;

import uy.edu.fing.ae.obligatorio.modelo.Individuo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import uy.edu.fing.ae.obligatorio.controlador.ProblemaControlador;
import uy.edu.fing.ae.obligatorio.controlador.PropiedadesControlador;
import uy.edu.fing.ae.obligatorio.modelo.Gen;
import uy.edu.fing.ae.obligatorio.modelo.Interes;
import uy.edu.fing.ae.obligatorio.util.PropiedadesEnum;
import uy.edu.fing.ae.obligatorio.util.Random;

public class Inicializador {

    //Clase con metodos estaticos
    private Inicializador() {
    }

    public static List<Individuo> run() {
        Integer algoritmo = PropiedadesControlador.getIntProperty(PropiedadesEnum.INICIALIZACION);
        switch (algoritmo) {
            case 1:
                return algoritmo1();
            case 2:
            case 3:
                return algoritmo2();
            default:
                throw new RuntimeException("No se ha definido una acion " + algoritmo + " para la propiedad " + PropiedadesEnum.INICIALIZACION.getNombre());
        }
    }

    //Algoritmo que genera los individuos de a uno random
    private static List<Individuo> algoritmo1() {

        List<Individuo> result = new ArrayList<>();

        Integer intentos = 0;
        Integer maxIntentos = PropiedadesControlador.getIntProperty(PropiedadesEnum.INICIALIZACION_INTENTOS);
        Integer poblacionSize = PropiedadesControlador.getIntProperty(PropiedadesEnum.INICIALIZACION_TAMANIO_POBLACION);
        while (result.size() < poblacionSize) {
            Individuo random = new Individuo();
            if (random.isValido()) {
                result.add(random);
            } else {
                intentos++;
                if (intentos > maxIntentos) {
                    throw new RuntimeException("Supero cantidad maxima de intentos");
                }
            }
        }
        return result;
    }

    //Algoritmo que genera los individuos de a uno random pero, siempre validos
    public static List<Individuo> algoritmo2() {

        List<Individuo> result = new ArrayList<>();
        Integer poblacionSize = PropiedadesControlador.getIntProperty(PropiedadesEnum.INICIALIZACION_TAMANIO_POBLACION);

        while (result.size() < poblacionSize) {
            result.add(makeSeudoRandom());
        }

        return result;
    }

    private static Individuo makeSeudoRandom() {

        Integer algoritmo = PropiedadesControlador.getIntProperty(PropiedadesEnum.INICIALIZACION);
        Integer cantCam = PropiedadesControlador.getIntProperty(PropiedadesEnum.CAMARA_CANTIDAD);
        ProblemaControlador problema = ProblemaControlador.getInstance();
        int height = problema.getHeight();
        int width = problema.getWidth();

        Gen[] camaras = new Gen[cantCam];
        List<Integer> elegidos;
        if (algoritmo == 2) {
            elegidos = getIndexOfChossen();
        } else {
            elegidos = getIndexOfChossen2();
        }
        
        int contador = 0;
        int pointer = 0;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {

                if (problema.getSala(i, j) != Interes.NUL0) {
                    if (elegidos.get(pointer) == contador) {
                        camaras[pointer] = new Gen(i, j, Random.get(360));
                        pointer++;
                        if (pointer == cantCam) {
                            return new Individuo(camaras);
                        }

                    }
                    contador++;
                }
            }
        }
        return new Individuo(camaras);
    }

    private static List<Integer> getIndexOfChossen() {
        Integer cantCam = PropiedadesControlador.getIntProperty(PropiedadesEnum.CAMARA_CANTIDAD);

        HashMap<Integer, Boolean> elegidos = new HashMap<>();

        while (elegidos.size() < cantCam) {
            elegidos.put(Random.get(ProblemaControlador.getInstance().getCantValidos()), null);
        }
        ArrayList<Integer> chosen = new ArrayList<>(elegidos.keySet());

        Collections.sort(chosen);
        return chosen;
    }

    private static List<Integer> getIndexOfChossen2() {
        final Integer cantCam = PropiedadesControlador.getIntProperty(PropiedadesEnum.CAMARA_CANTIDAD);
        final int cantValidos = ProblemaControlador.getInstance().getCantValidos();

        final int dist = cantValidos / cantCam;
        HashMap<Integer, Boolean> elegidos = new HashMap<>();
        Integer i = Random.get(dist);
        while (elegidos.size() < cantCam) {

            elegidos.put(i, null);
            i += dist;
        }
        ArrayList<Integer> chosen = new ArrayList<>(elegidos.keySet());

        Collections.sort(chosen);
        return chosen;
    }
}
