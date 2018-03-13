package uy.edu.fing.mor.obligatorio.genetic;

import java.util.ArrayList;
import java.util.Arrays;
import uy.edu.fing.mor.obligatorio.modelo.Individuo;
import java.util.List;
import uy.edu.fing.mor.obligatorio.controlador.ProblemaControlador;
import uy.edu.fing.mor.obligatorio.controlador.PropiedadesControlador;
import uy.edu.fing.mor.obligatorio.modelo.Arista;
import uy.edu.fing.mor.obligatorio.modelo.Concentrador;
import uy.edu.fing.mor.obligatorio.modelo.Linea;
import uy.edu.fing.mor.obligatorio.util.GlpkUtil;
import uy.edu.fing.mor.obligatorio.util.ListUtil;
import uy.edu.fing.mor.obligatorio.util.Logger;
import uy.edu.fing.mor.obligatorio.util.PropiedadesEnum;
import uy.edu.fing.mor.obligatorio.util.Random;

public class Mutador {

    private static List<Arista> aristas = null;
    private static Integer individuosRepetidos = 0;
    private static Integer individuosFallidos = 0;
    private static Integer mutaciones = 0;

    //Clase con metodos estaticos
    private Mutador() {
    }

    public static void init() {
        aristas = ProblemaControlador.getInstance().getAristas();
        individuosFallidos = 0;
        mutaciones = 0;
        individuosRepetidos = 0;
    }

    public static void show() {
        Logger.info("mutaciones: " + mutaciones);
        Logger.info("lineasRepetidos: " + individuosRepetidos);
        Logger.info("individuosFallidos: " + individuosFallidos);
    }

    public static List<Individuo> run(List<Individuo> candidatos) {
        Integer algoritmo = PropiedadesControlador.getIntProperty(PropiedadesEnum.MUTACION);
        switch (algoritmo) {
            case 0:
                return candidatos;
            case 1:
                return algoritmo1(candidatos);
            case 2:
                return algo2(candidatos);
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
        boolean muto = false;
        Individuo result = new Individuo(candidato);
        Concentrador[] concentradores = result.getConcentradores();
        for (int i = 0; i < concentradores.length; i++) {
            muto = mutar1(concentradores[i], i) || muto;
        }
        return result;
    }

    /* Algoritmo que realiza una mutacion producto de eliminar líneas y 
    reconstruirlas sobre grafos perturbados */
    private static boolean mutar1(Concentrador concentrador, int idConcentrador) {
        Integer prob = PropiedadesControlador.getIntProperty(PropiedadesEnum.MUTACION_PROPABILIDAD);

        List<Linea> lineas = concentrador.getLineas();
        boolean muto = false;
        for (int i = 0; i < lineas.size(); i++) {
            if (Random.prob(prob)) {
                mutaciones++;

                Linea lineaOriginal = lineas.get(i);

                //Calculo las aristas del concentrador
                List<Arista> listAristas = new ArrayList<>();
                for (int j = 0; j < lineas.size(); j++) {
                    if (j != i) {
                        listAristas.addAll(concentrador.getLineas().get(j).getTramos());
                    }
                }

                //Calculo las Aristas Usables
                List<Arista> aristasUsables = ListUtil.restaDeAristas(aristas, listAristas);

                Integer ruido = PropiedadesControlador.getIntProperty(PropiedadesEnum.MUTACION_RUIDO);
                Integer cantCentros = PropiedadesControlador.getIntProperty(PropiedadesEnum.CANT_SUMIDEROS);
                String[] especialesStr = PropiedadesControlador.getProperty(PropiedadesEnum.ESPECIALES).split(";");
                Integer[] lineasPorFuentes = new Integer[especialesStr.length];
                Arrays.fill(lineasPorFuentes, 0);
                lineasPorFuentes[cantCentros + idConcentrador] = 1;

                Linea lineaNueva = new Linea(GlpkUtil.delay(aristasUsables, lineasPorFuentes, ruido));
                if (lineaNueva.igualA(lineaOriginal)) {
                    individuosRepetidos++;
                } else {
                    muto = true;
                    concentrador.getLineas().set(i, lineaNueva);
                }
            }
        }
        return muto;
    }

    //Algoritmo no muta xD
    private static List<Individuo> algo2(List<Individuo> candidatos) {
        return candidatos;
    }
}
