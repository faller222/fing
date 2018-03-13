package uy.edu.fing.mor.obligatorio.genetic;

import uy.edu.fing.mor.obligatorio.modelo.Individuo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import uy.edu.fing.mor.obligatorio.controlador.PropiedadesControlador;
import uy.edu.fing.mor.obligatorio.util.PropiedadesEnum;
import uy.edu.fing.mor.obligatorio.util.Random;

public class Selector {

    //Clase con metodos estaticos
    private Selector() {
    }

    public static List<Individuo> run(List<Individuo> candidatos) {
        Integer algoritmo = PropiedadesControlador.getIntProperty(PropiedadesEnum.SELECCION);
        switch (algoritmo) {
            case 0:
                return algoritmo0(candidatos);
            case 1:
                return algoritmo1(candidatos);
            case 2:
                return algoritmo2(candidatos);
            case 3:
                return algoritmo3(candidatos);
            case 4:
                return algoritmo4(candidatos);
            default:
                throw new RuntimeException("No se ha definido una acion " + algoritmo + " para la propiedad " + PropiedadesEnum.SELECCION.getNombre());
        }
    }

    //Algoritmo identidad, solo trunca al tamanio de poblacion
    private static List<Individuo> algoritmo0(List<Individuo> candidatos) {
        Integer tamanioPoblacion = PropiedadesControlador.getIntProperty(PropiedadesEnum.INICIALIZACION_TAMANIO_POBLACION);
        return (new ArrayList<>(candidatos)).subList(0, tamanioPoblacion);
    }

    //Algoritmo elitista, trunca al tamaño de la poblacion sobre los mejores
    private static List<Individuo> algoritmo1(List<Individuo> candidatos) {
        Integer tamanioPoblacion = PropiedadesControlador.getIntProperty(PropiedadesEnum.INICIALIZACION_TAMANIO_POBLACION);

        List<Individuo> result = new ArrayList<>(candidatos);

        Collections.sort(result, (o1, o2) -> {
            return o1.getCosto().compareTo(o2.getCosto());
        });

        return result.subList(0, tamanioPoblacion);
    }

    //Algoritmo elitista ponderado, en función de las probabilidades realiza el truncamiento en la poblacion
    private static List<Individuo> algoritmo2(List<Individuo> candidatos) {
        Integer tamanioPoblacion = PropiedadesControlador.getIntProperty(PropiedadesEnum.INICIALIZACION_TAMANIO_POBLACION);
        Double factorSeleccion = PropiedadesControlador.getDoubleProperty(PropiedadesEnum.SELECCION_FACTOR);

        List<Integer> listaIndices = new ArrayList<>();
        //Rankear a todos los candidatos de mayor a menor costo
        Collections.sort(candidatos, (o1, o2) -> {
            //MAYOR A MENOR!!!
            return o2.getCosto().compareTo(o1.getCosto());
        });

        for (int j = 0; j < candidatos.size(); j++) {
            Individuo candidato = candidatos.get(j);
            Double peso = factorSeleccion / candidato.getCosto();
            if (peso < 1) {
                throw new RuntimeException("El valor del factor es demasiado bajo (" + factorSeleccion + "), para los costos que maneja esta instancia (" + candidato.getCosto() + ").");
            }
            for (int i = 0; i < peso.intValue(); i++) {
                listaIndices.add(j);
            }
        }

        List<Individuo> resultado = new ArrayList<>();

        for (int i = 0; i < tamanioPoblacion; i++) {
            int aux = Random.get(listaIndices.size());
            Integer chosenOneId = listaIndices.get(aux);
            Individuo chosenOne = candidatos.get(chosenOneId);
            resultado.add(chosenOne);
        }

        return resultado;
    }

    //Algoritmo hibrido, elitista con elitista ponderado
    private static List<Individuo> algoritmo3(List<Individuo> candidatos) {
        Integer tamanioPoblacion = PropiedadesControlador.getIntProperty(PropiedadesEnum.INICIALIZACION_TAMANIO_POBLACION);

        List<Individuo> result1 = new ArrayList<>(candidatos);

        Collections.sort(result1, (o1, o2) -> {
            return o1.getCosto().compareTo(o2.getCosto());
        });

        Integer primerosMejores = 10 * tamanioPoblacion / 100;
        List<Individuo> result2 = new ArrayList<>(result1.subList(primerosMejores + 1, tamanioPoblacion));
        //Armo una lista con los mejores candidatos (10%)
        result1.subList(0, primerosMejores);
        boolean chance;
        //Los restantes entran random y los agrego luego al otro 10%  
        for (int j = 0; j < result2.size(); j++) {
            chance = Random.prob(50);
            if (chance) {
                Individuo candidato = result2.get(j);
                result1.add(candidato);
            }
        }
        // Como es probabilistico tal vez no llegue a completar la cantidad de individuos de la población
        //completo random con los que sean necesarios hasta tener la población esperada.
        int cuantosFaltan = result2.size();
        int luckyOne;
        if (cuantosFaltan < tamanioPoblacion) {
            cuantosFaltan = tamanioPoblacion - cuantosFaltan;
            for (int j = 0; j < cuantosFaltan; j++) {
                luckyOne = Random.beetwen(0, tamanioPoblacion);
                result1.add(candidatos.get(luckyOne));
            }
        }
        return result1;
    }

    //Algoritmo hibrido, elitista con elitista ponderado
    private static List<Individuo> algoritmo4(List<Individuo> candidatos) {
        Integer tamanioPoblacion = PropiedadesControlador.getIntProperty(PropiedadesEnum.INICIALIZACION_TAMANIO_POBLACION);

        List<Individuo> result1 = new ArrayList<>(candidatos);

        Collections.sort(result1, (o1, o2) -> {
            return o1.getCosto().compareTo(o2.getCosto());
        });

        Integer primerosMejores = (5 * tamanioPoblacion) / 100;
        Integer ultimosPeores = (5 * tamanioPoblacion) / 100;
        List<Individuo> result2 = new ArrayList<>(result1.subList(primerosMejores + 1, tamanioPoblacion - ultimosPeores - 1));
        List<Individuo> result3 = new ArrayList<>(result1.subList(tamanioPoblacion - ultimosPeores, tamanioPoblacion));
        //Armo una lista con los mejores candidatos (10%)
        result1.subList(0, primerosMejores);
        for (int j = 0; j < result3.size(); j++) {
            result1.add(result3.get(j));
        }
        boolean chance;
        //Los restantes entran random y los agrego al 15% que ya tengo entre result2 y result3  
        for (int j = 0; j < result2.size(); j++) {
            chance = Random.prob(50);
            if (chance) {
                Individuo candidato = result2.get(j);
                result1.add(candidato);
            }
        }
        // Como es probabilistico tal vez no llegue a completar la cantidad de individuos de la población
        //completo random con los que sean necesarios hasta tener la población esperada.
        int cuantosFaltan = result2.size();
        int luckyOne;
        if (cuantosFaltan < tamanioPoblacion) {
            cuantosFaltan = tamanioPoblacion - cuantosFaltan;
            for (int j = 0; j < cuantosFaltan; j++) {
                luckyOne = Random.beetwen(0, tamanioPoblacion);
                result1.add(candidatos.get(luckyOne));
            }
        }
        return result1;
    }
}
