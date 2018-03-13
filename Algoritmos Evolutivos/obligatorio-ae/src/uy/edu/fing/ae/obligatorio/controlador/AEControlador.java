package uy.edu.fing.ae.obligatorio.controlador;

import java.util.ArrayList;
import java.util.List;
import uy.edu.fing.ae.obligatorio.util.Logger;
import uy.edu.fing.ae.obligatorio.genetic.Cruzador;
import uy.edu.fing.ae.obligatorio.genetic.Inicializador;
import uy.edu.fing.ae.obligatorio.genetic.Mutador;
import uy.edu.fing.ae.obligatorio.genetic.Selector;
import uy.edu.fing.ae.obligatorio.modelo.Individuo;
import uy.edu.fing.ae.obligatorio.util.PropiedadesEnum;

public class AEControlador {

    private Integer maxGen = null;
    private Integer generacionesInvariantesAllowed = null;

    private static AEControlador INSTANCIA;

    private List<Individuo> poblacion;

    private Individuo best = null;

    private boolean forceStop = false;
    private int generacion = 0;
    private int generacionBest = 0;
    private int generacionInvariante = 0;
    private Long startTime = null;
    private Long bestTime = null;
    private Long endTime = null;

    private Individuo acuInd = null;
    private Long acuTime = 0l;

    //Singleton
    private AEControlador() {
    }

    public static synchronized AEControlador getInstance() {
        if (INSTANCIA == null) {
            INSTANCIA = new AEControlador();
            INSTANCIA.init();
        }
        return INSTANCIA;
    }
    
    public Individuo run() {
        
        Integer corridas = PropiedadesControlador.getIntProperty(PropiedadesEnum.CORRIDAS);
        
        acuInd = null;
        acuTime = 0l;
        //necesito array de fitness

        List<Integer> fitness = new ArrayList<>();
        for (int i = 0; i < corridas; i++) {
            init();
            Logger.info("__________________________INIT" + i + "______________________________________");
            List<Individuo> poblacionTemp = null;
            try {
                poblacion = Inicializador.run();
                mejorIndividuo();

                while (keepRunning()) {
                    poblacionTemp = Cruzador.run(poblacion);
                    poblacionTemp = Mutador.run(poblacionTemp);
                    poblacion = Selector.run(poblacionTemp);
                    mejorIndividuo();
                    //System.gc();
                }

                //return best;
            } catch (java.lang.OutOfMemoryError e) {
                poblacion.clear();
                poblacion = null;
                System.gc();
                throw e;
            } catch (Exception e) {
                throw e;
            } finally {
                Mutador.show();
                endTime = System.currentTimeMillis();

                Logger.info("Mejor fitness: " + best.getFitness());
                Logger.info("Mejor tiempo: " + bestTime);
                Logger.info("Mejor Generacion: " + generacionBest);
                Logger.info("Generaciones Totales: " + generacion);
                Logger.info("Demora Total: " + (endTime - startTime));

                if (acuInd == null) {
                    acuInd = best;
                }
                if (best.getFitness() > acuInd.getFitness()) {
                    generacionInvariante = 0;
                    acuInd = best;
                }
                fitness.add(best.getFitness());
                acuTime += bestTime;
            }
        }

        Logger.info("__________________________FinisH______________________________________");
        Logger.info("Mejor fitness TOTAL: " + acuInd.getFitness());
        Logger.info("Tiempo Promedio: " + (acuTime / corridas));
        estadisticas(fitness);
        return acuInd;
    }

    private void estadisticas(List<Integer> fitness) {
        int cant = fitness.size();
        Integer acumulado = 0;
        Double sd = 0d;
        for (Integer fitnes : fitness) {
            acumulado += fitnes;
        }
        final Integer promedio = acumulado / cant;

        for (Integer fitnes : fitness) {
            sd += (fitnes - promedio) * (fitnes - promedio);
        }

        sd = Math.sqrt((sd) / cant);

        Logger.info("Fitness Promedio: " + promedio);
        Logger.info("Desviacion estandar : " + sd);
    }

    public String getTime() {
        long current;
        if (endTime == null) {
            current = System.currentTimeMillis();
        } else {
            current = endTime;
        }
        final long diff = current - startTime;
        final long sec = (diff / 1000) % 60;
        final long min = (diff / 60000) % 60;
        final long hour = (diff / 3600000);
        return (hour == 0 ? "" : hour + ":") + (min == 0 ? "" : (min < 10 ? "0" : "") + min + ":") + (sec < 10 ? "0" : "") + sec;
    }

    public Individuo getBest() {
        return best;
    }

    public int getGeneracion() {
        return generacion;
    }

    public void forceStop() {
        forceStop = true;
    }

    public boolean isRunning() {
        return endTime == null;
    }

    public void init() {
        Mutador.init();
        endTime = null;
        bestTime = null;
        poblacion = null;
        startTime = System.currentTimeMillis();
        best = null;
        forceStop = false;
        maxGen = PropiedadesControlador.getIntProperty(PropiedadesEnum.GENERACIONES);
        generacionesInvariantesAllowed = PropiedadesControlador.getIntProperty(PropiedadesEnum.GENERACIONES_INVARIANTES);
        generacion = 0;
        generacionBest = 0;
        generacionInvariante = 0;
    }

    private boolean keepRunning() {

        final long diff = System.currentTimeMillis() - startTime;
        Logger.debug("Generacion: " + generacion + " - Invariantes: " + generacionInvariante + " T:" + diff);
        if (forceStop) {
            Logger.info("ABORTADO!!");
        }
        generacion++;
        generacionInvariante++;
        return !forceStop && generacion < maxGen && generacionInvariante <= generacionesInvariantesAllowed;
    }

    private void mejorIndividuo() {
        boolean mejoro = false;
        if (best == null) {
            best = poblacion.get(0);
        }
        for (Individuo individuo : poblacion) {
            if (individuo.getFitness() > best.getFitness()) {
                generacionInvariante = 0;
                best = individuo;
                mejoro = true;
            }
        }
        if (mejoro) {
            final long current = System.currentTimeMillis();
            final long timeDiff = current - startTime;
            bestTime = timeDiff;
            generacionBest = generacion;

            Logger.info(timeDiff + "\t" + generacion + "\t" + best.getFitness());
        }
    }
}
