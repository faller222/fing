package uy.edu.fing.ae.obligatorio.genetic;

import uy.edu.fing.ae.obligatorio.modelo.Individuo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import uy.edu.fing.ae.obligatorio.controlador.PropiedadesControlador;
import uy.edu.fing.ae.obligatorio.util.PropiedadesEnum;
import uy.edu.fing.ae.obligatorio.util.Random;

public final class Selector {
    //Aleatorio, torneo, rueda de ruleta, ranking, best, worst
    
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
                throw new RuntimeException("No se ha definido una accion " + algoritmo + " para la propiedad " + PropiedadesEnum.SELECCION.getNombre());
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
            //De Mayor a Menor
            return (-1) * o1.getFitness().compareTo(o2.getFitness());
        });

        return result.subList(0, tamanioPoblacion);
    }

    //Algoritmo ruleta, en función de las probabilidades realiza el truncamiento en la poblacion
    private static List<Individuo> algoritmo2(List<Individuo> candidatos) {
        Integer tamanioPoblacion = PropiedadesControlador.getIntProperty(PropiedadesEnum.INICIALIZACION_TAMANIO_POBLACION);

        Double[] probSeleccion = new Double[candidatos.size()];

        //Rankear a todos los candidatos de mayor a menor costo
        Collections.sort(candidatos, (o1, o2) -> {
            //De Mayor a Menor
            return (-1) * o1.getFitness().compareTo(o2.getFitness());
        });

        Integer acumuladoFitnes = 0;
        for (int j = 0; j < probSeleccion.length; j++) {
            Individuo candidato = candidatos.get(j);
            acumuladoFitnes += candidato.getFitness();
            probSeleccion[j] = Double.valueOf(candidato.getFitness());
        }
        
        //Guardo la probabilidad de seleccion de cada individuo
        for (int j = 0; j < probSeleccion.length; j++) {
            probSeleccion[j] = (probSeleccion[j] * 100) / acumuladoFitnes;
        }

        List<Individuo> resultado = new ArrayList<>();
        int j = 0;
        while (resultado.size() < tamanioPoblacion) {
            int index = ++j % probSeleccion.length;
            if (Random.prob(probSeleccion[index])) {
                Individuo chosenOne = candidatos.get(index);
                resultado.add(chosenOne);
            }
        }

        return resultado;
    }

    //Algoritmo hibrido, elitista con ruleta
    private static List<Individuo> algoritmo3(List<Individuo> candidatos) {
        Integer tamanioPoblacion = PropiedadesControlador.getIntProperty(PropiedadesEnum.INICIALIZACION_TAMANIO_POBLACION);
        Integer proporcionElite = PropiedadesControlador.getIntProperty(PropiedadesEnum.SELECCION_ELITE);
        Integer primerosMejores = proporcionElite * tamanioPoblacion / 100;

        double[] probSeleccion = new double[candidatos.size()];

        //Rankear a todos los candidatos de mayor a menor costo
        Collections.sort(candidatos, (o1, o2) -> {
            //De Mayor a Menor
            return (-1) * o1.getFitness().compareTo(o2.getFitness());
        });

        List<Individuo> resultado = candidatos.subList(0, primerosMejores);

        //Calculo de la probabilidad de seleccion
        Integer acumuladoFitnes = 0;
        for (int j = primerosMejores; j < candidatos.size(); j++) {
            Individuo candidato = candidatos.get(j);
            acumuladoFitnes += candidato.getFitness();
            probSeleccion[j] = Double.valueOf(candidato.getFitness());
        }

        for (int j = primerosMejores; j < candidatos.size(); j++) {
            probSeleccion[j] = (probSeleccion[j] * 100) / acumuladoFitnes;
        }

        int j = 0;
        while (resultado.size() < tamanioPoblacion) {
            int index = ++j % probSeleccion.length;
            if (Random.prob(probSeleccion[index])) {
                resultado.add(candidatos.get(index));
            }
        }
        
        return resultado;
    }
    private static List<Individuo> algoritmo4(List<Individuo> candidatos) {
        
        Integer tamanioPoblacion = PropiedadesControlador.getIntProperty(PropiedadesEnum.INICIALIZACION_TAMANIO_POBLACION);
        Integer torneo = PropiedadesControlador.getIntProperty(PropiedadesEnum.SELECCION_TORNEO);

        List<Individuo> resultado = new ArrayList<>();
        
        List<Individuo> competidores;
        
        while (resultado.size() < tamanioPoblacion) {
            competidores = new ArrayList<>();
            for (int i = 0; i < torneo; i++) {
                int index = Random.get(candidatos.size());
                Individuo candidato = candidatos.get(index);
                competidores.add(candidato);
            }
            Individuo ganador = competidores.get(0);
            for (Individuo competidor : competidores) {
                if (competidor.getFitness() > ganador.getFitness()) {
                    ganador = competidor;
                }
            }
            resultado.add(ganador);
        }
        return resultado;
    }
}