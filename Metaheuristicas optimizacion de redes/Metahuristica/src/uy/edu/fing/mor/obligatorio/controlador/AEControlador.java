package uy.edu.fing.mor.obligatorio.controlador;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import uy.edu.fing.mor.obligatorio.genetic.Cruzador;
import uy.edu.fing.mor.obligatorio.genetic.Inicializador;
import uy.edu.fing.mor.obligatorio.genetic.Mutador;
import uy.edu.fing.mor.obligatorio.genetic.Selector;
import uy.edu.fing.mor.obligatorio.modelo.Individuo;
import uy.edu.fing.mor.obligatorio.util.PropiedadesEnum;

public class AEControlador {

    private Integer maxGen = PropiedadesControlador.getIntProperty(PropiedadesEnum.GENERACIONES);
    private Integer generacionesInvariantesAllowed = PropiedadesControlador.getIntProperty(PropiedadesEnum.GENERACIONES_INVARIANTES);

    private static AEControlador INSTANCIA;

    private List<Individuo> poblacion;

    private Individuo best = null;

    private boolean forceStop = false;
    private int generacion = 0;
    private int generacionInvariante = 0;
    private Long startTime = null;
    private Long endTime = null;

    //Singleton
    private AEControlador() {
    }

    public static synchronized AEControlador getInstance() {
        if (INSTANCIA == null) {
            INSTANCIA = new AEControlador();
        }
        return INSTANCIA;
    }

    public Individuo run() {

        this.init();
        Mutador.init();
        List<Individuo> poblacionTemp = null;
        try {
            poblacion = Inicializador.run();
            mejorIndividuo();

            while (keepRunning()) {
                poblacionTemp = Cruzador.run(poblacion);
                poblacionTemp = Mutador.run(poblacionTemp);
                poblacion = Selector.run(poblacionTemp);
                mejorIndividuo();
            }

            return best;
        } catch (Exception e) {
            throw e;
        } finally {
            Mutador.show();
            endTime = System.currentTimeMillis();
            System.out.println("Demora " + (endTime - startTime));
        }
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
        endTime = null;
        startTime = System.currentTimeMillis();
        best = null;
        forceStop = false;
        maxGen = PropiedadesControlador.getIntProperty(PropiedadesEnum.GENERACIONES);
        generacionesInvariantesAllowed = PropiedadesControlador.getIntProperty(PropiedadesEnum.GENERACIONES_INVARIANTES);
        generacion = 0;
        generacionInvariante = 0;
    }

    private boolean keepRunning() {
        generacion++;
        generacionInvariante++;
        return !forceStop && generacion < maxGen && generacionInvariante <= generacionesInvariantesAllowed;
    }

    private void mejorIndividuo() {
        if (best == null) {
            best = poblacion.get(0);
        }
        for (Individuo individuo : poblacion) {
            if (individuo.getCosto() < best.getCosto()) {
                generacionInvariante = 0;
                best = individuo;
                Logger.getLogger(AEControlador.class.getName()).log(Level.INFO, "Costo mejorado: " + best.getCosto() + " Generacion: " + generacion);
            }
        }
    }
}
