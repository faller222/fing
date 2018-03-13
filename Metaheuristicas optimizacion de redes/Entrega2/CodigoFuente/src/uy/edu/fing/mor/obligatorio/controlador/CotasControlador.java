package uy.edu.fing.mor.obligatorio.controlador;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import uy.edu.fing.mor.obligatorio.genetic.Inicializador;
import uy.edu.fing.mor.obligatorio.genetic.Parte2;
import uy.edu.fing.mor.obligatorio.modelo.Arista;
import uy.edu.fing.mor.obligatorio.modelo.Concentrador;
import uy.edu.fing.mor.obligatorio.modelo.Individuo;
import uy.edu.fing.mor.obligatorio.modelo.Linea;
import uy.edu.fing.mor.obligatorio.util.PropiedadesEnum;

public class CotasControlador {

    private static CotasControlador INSTANCIA;

    private Individuo individuo = null;
    private Integer iteracion = 0;
    private Boolean forceStop = false;

    private Long startTime = null;
    private Long endTime = null;

    private List<Linea> prohibidas = new ArrayList<>();

    //Singleton
    private CotasControlador() {
    }

    public static synchronized CotasControlador getInstance() {
        if (INSTANCIA == null) {
            INSTANCIA = new CotasControlador();
        }
        return INSTANCIA;
    }

    public void run() {
        ProblemaControlador problema = ProblemaControlador.getInstance();

        List<Arista> aristas = problema.getAristas();
        String[] especialesStr = PropiedadesControlador.getProperty(PropiedadesEnum.ESPECIALES).split(";");
        Integer[] especiales = Arrays.asList(especialesStr).stream().map(Integer::valueOf).toArray(size -> new Integer[size]);

        forceStop = false;
        startTime = System.currentTimeMillis();
        endTime = null;

        try {
            while (keepRunning()) {
                List<Arista> cota = Parte2.run(aristas, especiales, prohibidas);
                forceStop = cota.isEmpty();
                if (forceStop) {
                    break;
                }
                individuo = formarIndividuo(cota);
                for (Concentrador concentrador : individuo.getConcentradores()) {
                    for (Linea linea : concentrador.getLineas()) {
                        if (linea.getDelay() > concentrador.getMaxDelayAllowed()) {
                            //Asumimos que no hay repetidas
                            prohibidas.add(linea);
                        }
                    }
                }
            }
            endTime = System.currentTimeMillis();

        } catch (Exception e) {
            throw e;
        } finally {
        }
    }

    private boolean keepRunning() {
        long current = System.currentTimeMillis();
        final long diff = current - startTime;

        iteracion++;
        return !forceStop && iteracion < 100 && diff < 300000 && (individuo == null || !individuo.isValido());
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

    //Metodo similar al algoritmo1 de la inicializacion, es mas, hace uso de parte de el
    private static Individuo formarIndividuo(List<Arista> aristas) {
        Integer cantCentros = PropiedadesControlador.getIntProperty(PropiedadesEnum.CANT_SUMIDEROS);
        Integer cantFuentes = PropiedadesControlador.getIntProperty(PropiedadesEnum.CANT_FUENTES);
        String[] especialesStr = PropiedadesControlador.getProperty(PropiedadesEnum.ESPECIALES).split(";");
        Integer[] lineasPorFuentes = Arrays.asList(especialesStr).stream().map(Integer::valueOf).toArray(size -> new Integer[size]);

        Concentrador[] concentradores = new Concentrador[cantFuentes];
        //Por cada fuente
        for (int i = cantCentros; i < (cantCentros + cantFuentes); i++) {
            Concentrador tmp = Inicializador.glpk1(i, aristas, lineasPorFuentes, true);
            concentradores[i - cantCentros] = tmp;
        }

        return new Individuo(concentradores);
    }

    public boolean isRunning() {
        return endTime == null;
    }

    public void forceStop() {
        forceStop = true;
    }

    public Individuo getIndividuo() {
        return individuo;
    }

    public Integer getIteracion() {
        return iteracion;
    }

}
