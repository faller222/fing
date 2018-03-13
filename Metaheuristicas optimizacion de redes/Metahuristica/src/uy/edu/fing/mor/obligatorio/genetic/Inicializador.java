package uy.edu.fing.mor.obligatorio.genetic;

import uy.edu.fing.mor.obligatorio.modelo.Concentrador;
import uy.edu.fing.mor.obligatorio.modelo.Individuo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import uy.edu.fing.mor.obligatorio.controlador.ProblemaControlador;
import uy.edu.fing.mor.obligatorio.controlador.PropiedadesControlador;
import uy.edu.fing.mor.obligatorio.modelo.Arista;
import uy.edu.fing.mor.obligatorio.modelo.Linea;
import uy.edu.fing.mor.obligatorio.util.GlpkUtil;
import uy.edu.fing.mor.obligatorio.util.ListUtil;
import uy.edu.fing.mor.obligatorio.util.PropiedadesEnum;
import uy.edu.fing.mor.obligatorio.util.Random;

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
                return algoritmo2();
            default:
                throw new RuntimeException("No se ha definido una acion " + algoritmo + " para la propiedad " + PropiedadesEnum.INICIALIZACION.getNombre());
        }
    }

    //Algoritmo que genera los individuos de a uno por partes y concentrador
    private static List<Individuo> algoritmo1() {
        List<Arista> aristas = ProblemaControlador.getInstance().getAristas();

        Integer cantCentros = PropiedadesControlador.getIntProperty(PropiedadesEnum.CANT_SUMIDEROS);
        Integer cantFuentes = PropiedadesControlador.getIntProperty(PropiedadesEnum.CANT_FUENTES);

        String[] especialesStr = PropiedadesControlador.getProperty(PropiedadesEnum.ESPECIALES).split(";");
        Integer[] lineasPorFuentes = Arrays.asList(especialesStr).stream().map(Integer::valueOf).toArray(size -> new Integer[size]);
        List<Individuo> result = new ArrayList<>();

        Integer maxIntentos = PropiedadesControlador.getIntProperty(PropiedadesEnum.INICIALIZACION_INTENTOS);
        Integer poblacionSize = PropiedadesControlador.getIntProperty(PropiedadesEnum.INICIALIZACION_TAMANIO_POBLACION);

        for (int p = 0; p < poblacionSize; p++) {
            Concentrador[] concentradores = new Concentrador[cantFuentes];
            //Por cada fuente
            for (int i = cantCentros; i < (cantCentros + cantFuentes); i++) {
                for (int trys = 0; trys < maxIntentos; trys++) {
                    Concentrador tmp = glpk1(i, aristas, lineasPorFuentes, trys % 2 == 0);
                    if (tmp.isValido()) {
                        concentradores[i - cantCentros] = tmp;
                        break;
                    }
                }
                if (concentradores[i - cantCentros] == null) {
                    throw new RuntimeException("Se han superado la cantidad maxima ("
                            + maxIntentos + ") de intentos para generar una solucion factible para el concentrador " + i + " - Poblacion " + p);
                }
            }
            result.add(new Individuo(concentradores));
        }

        return result;
    }

    //Algoritmo que genera los los individuos como productos cartesianos
    private static List<Individuo> algoritmo2() {
        Integer cantCentros = PropiedadesControlador.getIntProperty(PropiedadesEnum.CANT_SUMIDEROS);
        Integer cantFuentes = PropiedadesControlador.getIntProperty(PropiedadesEnum.CANT_FUENTES);
        Integer maxIntentos = PropiedadesControlador.getIntProperty(PropiedadesEnum.INICIALIZACION_INTENTOS);
        Integer poblacionSize = PropiedadesControlador.getIntProperty(PropiedadesEnum.INICIALIZACION_TAMANIO_POBLACION);
        String[] especialesStr = PropiedadesControlador.getProperty(PropiedadesEnum.ESPECIALES).split(";");
        Integer[] lineasPorFuentes = Arrays.asList(especialesStr).stream().map(Integer::valueOf).toArray(size -> new Integer[size]);

        List<Arista> aristas = ProblemaControlador.getInstance().getAristas();
        List<Individuo> result = new ArrayList<>();

        List<List<Concentrador>> subConjutoLineasEncontradas = new ArrayList<>();

        int cantPotencialesIndividuos = 1;
        int[] cantSubConjutoLineasEncontradas = new int[cantFuentes];

        for (int i = cantCentros; i < (cantCentros + cantFuentes); i++) {
            HashMap<String, Concentrador> concentradoresFuenteAux = new HashMap<>();
            List<Concentrador> concentradoresFuenteI = new ArrayList<>();

            int trys = 0;
            while (trys < maxIntentos) {
                Concentrador tmp = glpk1(i, aristas, lineasPorFuentes, trys % 2 == 0);
                if (concentradoresFuenteAux.containsKey(tmp.getId()) || !tmp.isValido()) {
                    trys++;
                } else {
                    concentradoresFuenteI.add(tmp);
                    concentradoresFuenteAux.put(tmp.getId(), tmp);
                }
            }
            if (concentradoresFuenteI.isEmpty()) {
                throw new RuntimeException("Se han superado la cantidad maxima ("
                        + maxIntentos + ") de intentos para generar soluciones factibles para el concentrador " + i);
            }
            cantPotencialesIndividuos = cantPotencialesIndividuos * concentradoresFuenteI.size();
            cantSubConjutoLineasEncontradas[i - cantCentros] = concentradoresFuenteI.size();
            subConjutoLineasEncontradas.add(concentradoresFuenteI);
        }
        int idIndividuoAux = -1;
        for (int p = 0; p < poblacionSize; p++) {
            Concentrador[] concentradoresNew = new Concentrador[cantFuentes];

            //Obtengo un elemento de la cantidad posible
            if (cantPotencialesIndividuos < poblacionSize) {
                idIndividuoAux++;
                if (idIndividuoAux == poblacionSize) {
                    idIndividuoAux = 0;
                }
            } else {
                idIndividuoAux = Random.get(cantPotencialesIndividuos);
            }
            //Estructura auxiliar para calcular el individuo a generar
            int[] indiceElementos = new int[cantFuentes];

            //Calculo el indice de la estructura anterior
            for (int i = 0; i < (cantFuentes - 1); i++) {
                indiceElementos[i] = idIndividuoAux % cantSubConjutoLineasEncontradas[i];
                idIndividuoAux = idIndividuoAux / cantSubConjutoLineasEncontradas[i];
            }
            indiceElementos[cantFuentes - 1] = idIndividuoAux;

            //Por cada fuente obtengo el subConjunto de lineas correspondientes al numero aleatorio generado
            for (int i = 0; i < (cantFuentes); i++) {
                concentradoresNew[i] = new Concentrador(subConjutoLineasEncontradas.get(i).get(indiceElementos[i]));
            }

            result.add(new Individuo(concentradoresNew));
        }
        return result;
    }

    public static Concentrador glpk1(int fuente, List<Arista> aristas, final Integer[] lineasPorFuentes, boolean intermitente) {
        Integer[] lineasPorFuentesCopy = Arrays.copyOf(lineasPorFuentes, lineasPorFuentes.length);
        //Me quedo con la fuente que me interesa
        for (int i = 0; i < lineasPorFuentesCopy.length; i++) {
            if (i != fuente) {
                lineasPorFuentesCopy[i] = 0;
            }
        }

        //Aplico ruido a las aristas
        Integer ruido = PropiedadesControlador.getIntProperty(PropiedadesEnum.RUIDO);

        //de esta manera genero diversidad en la inicializacion
        List<Arista> subIndividuo;
        if (intermitente) {
            subIndividuo = GlpkUtil.costo(aristas, lineasPorFuentesCopy, Random.get(ruido));
        } else {
            subIndividuo = GlpkUtil.delay(aristas, lineasPorFuentesCopy, Random.get(ruido));
        }

        return makeLineasFuente(fuente, subIndividuo, lineasPorFuentesCopy);
    }

    private static Concentrador makeLineasFuente(int fuente, List<Arista> subIndividuo, final Integer[] lineasPorFuentes) {

        Integer[] lineasPorFuentesCopy = Arrays.copyOf(lineasPorFuentes, lineasPorFuentes.length);

        String[] delays = PropiedadesControlador.getProperty(PropiedadesEnum.DELAYS).split(";");
        Integer lineas = lineasPorFuentesCopy[fuente];
        lineasPorFuentesCopy[fuente] = 1;
        List<Linea> lineasResult = new ArrayList<>();

        if (lineas > 0) {
            List<Arista> tmp = subIndividuo;
            for (int i = 1; i < lineas; i++) {
                List<Arista> linea = GlpkUtil.run(tmp, lineasPorFuentesCopy);
                lineasResult.add(new Linea(linea));
                tmp = ListUtil.restaDeAristas(tmp, linea);
            }
            lineasResult.add(new Linea(tmp));
        }

        if (lineasResult.size() != lineas) {
            throw new RuntimeException("Se esperaban " + lineas + " y se encontraron " + lineasResult.size());
        }

        return new Concentrador(Integer.valueOf(delays[fuente]), lineas, lineasResult);
    }

}
