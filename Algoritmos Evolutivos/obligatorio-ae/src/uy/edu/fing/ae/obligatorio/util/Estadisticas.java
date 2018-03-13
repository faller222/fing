package uy.edu.fing.ae.obligatorio.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import uy.edu.fing.ae.obligatorio.controlador.AEControlador;
import uy.edu.fing.ae.obligatorio.controlador.GraspControlador;
import uy.edu.fing.ae.obligatorio.controlador.ProblemaControlador;
import uy.edu.fing.ae.obligatorio.controlador.PropiedadesControlador;

public class Estadisticas {

    private static String HOME = System.getProperty("user.home");
    private static String escenarioA = "escenario 1.png";
    private static String escenarioB = "escenario 2.png";
    private static String escenarioC = "escenario 3.png";
    private static String escenarioD = "escenario 4.png";
    private static String escenarioE = "escenario 5.png";
    private static String escenarioF = "sala grande.png";

    public static void main(String[] args) throws UnknownHostException {
        plan01();
        plan02();
        plan03();
        plan04();
        plan05();
        plan06();
        plan07();
        plan08();
        plan09();
        plan10();
        plan11();
        plan12();
        plan13();
        plan14();
        plan15();

        planGrasp(21, escenarioD, "ae/planGrasp1-1.log");
        planGrasp(22, escenarioE, "ae/planGrasp1-2.log");
        planGrasp(23, escenarioF, "ae/planGrasp1-3.log");
        
        planGrasp(31, escenarioD, "ae/planGrasp2-1.log");
        planGrasp(32, escenarioE, "ae/planGrasp2-2.log");
        planGrasp(33, escenarioF, "ae/planGrasp2-3.log");
        
        planGrasp(41, escenarioD, "ae/planGrasp3-1.log");
        planGrasp(42, escenarioE, "ae/planGrasp3-2.log");
        planGrasp(43, escenarioF, "ae/planGrasp3-3.log");
    }

    public static void plan01() {
        //SPX es el 2
        //Torneo es el 4
        Map<PropiedadesEnum, String> props = new HashMap<>();
        props.put(PropiedadesEnum.SELECCION, "4");
        props.put(PropiedadesEnum.CRUZAMIENTO, "2");

        planGenerico(1, escenarioA, "ae/plan1.log", props);
    }

    public static void plan02() {
        Map<PropiedadesEnum, String> props = new HashMap<>();
        props.put(PropiedadesEnum.SELECCION, "4");
        props.put(PropiedadesEnum.CRUZAMIENTO, "2");
        planGenerico(2, escenarioB, "ae/plan2.log", props);
    }

    public static void plan03() {
        Map<PropiedadesEnum, String> props = new HashMap<>();
        props.put(PropiedadesEnum.SELECCION, "4");
        props.put(PropiedadesEnum.CRUZAMIENTO, "2");
        planGenerico(3, escenarioC, "ae/plan3.log", props);
    }

    public static void plan04() {
        Map<PropiedadesEnum, String> props = new HashMap<>();
        props.put(PropiedadesEnum.SELECCION, "3");
        props.put(PropiedadesEnum.CRUZAMIENTO, "2");

        planGenerico(4, escenarioA, "ae/plan4.log", props);
    }

    public static void plan05() {
        Map<PropiedadesEnum, String> props = new HashMap<>();
        props.put(PropiedadesEnum.SELECCION, "3");
        props.put(PropiedadesEnum.CRUZAMIENTO, "2");
        planGenerico(5, escenarioB, "ae/plan5.log", props);
    }

    public static void plan06() {
        Map<PropiedadesEnum, String> props = new HashMap<>();
        props.put(PropiedadesEnum.SELECCION, "3");
        props.put(PropiedadesEnum.CRUZAMIENTO, "2");
        planGenerico(6, escenarioC, "ae/plan6.log", props);
    }
//########################################################################################

    public static void plan07() {
        //UPX es el 1
        //SPX es el 2
        //BLX es el 3
        Map<PropiedadesEnum, String> props = new HashMap<>();
        props.put(PropiedadesEnum.SELECCION, "4");
        props.put(PropiedadesEnum.CRUZAMIENTO, "1");

        planGenerico(7, escenarioA, "ae/plan7.log", props);
    }

    public static void plan08() {
        Map<PropiedadesEnum, String> props = new HashMap<>();
        props.put(PropiedadesEnum.SELECCION, "4");
        props.put(PropiedadesEnum.CRUZAMIENTO, "1");
        planGenerico(8, escenarioB, "ae/plan8.log", props);
    }

    public static void plan09() {
        Map<PropiedadesEnum, String> props = new HashMap<>();
        props.put(PropiedadesEnum.SELECCION, "4");
        props.put(PropiedadesEnum.CRUZAMIENTO, "1");
        planGenerico(9, escenarioC, "ae/plan9.log", props);
    }

    public static void plan10() {
        Map<PropiedadesEnum, String> props = new HashMap<>();
        props.put(PropiedadesEnum.SELECCION, "4");
        props.put(PropiedadesEnum.CRUZAMIENTO, "3");

        planGenerico(10, escenarioA, "ae/plan10.log", props);
    }

    public static void plan11() {
        Map<PropiedadesEnum, String> props = new HashMap<>();
        props.put(PropiedadesEnum.SELECCION, "4");
        props.put(PropiedadesEnum.CRUZAMIENTO, "3");
        planGenerico(11, escenarioB, "ae/plan11.log", props);
    }

    public static void plan12() {
        Map<PropiedadesEnum, String> props = new HashMap<>();
        props.put(PropiedadesEnum.SELECCION, "4");
        props.put(PropiedadesEnum.CRUZAMIENTO, "3");
        planGenerico(12, escenarioC, "ae/plan12.log", props);
    }

    //###################################
    public static void plan13() {
        //UPX es el 1
        //SPX es el 2
        //BLX es el 3
        Map<PropiedadesEnum, String> props = new HashMap<>();
        props.put(PropiedadesEnum.SELECCION, "4");
        props.put(PropiedadesEnum.CRUZAMIENTO, "3");

        planGenerico(13, escenarioD, "ae/plan13.log", props);
    }

    public static void plan14() {
        Map<PropiedadesEnum, String> props = new HashMap<>();
        props.put(PropiedadesEnum.SELECCION, "4");
        props.put(PropiedadesEnum.CRUZAMIENTO, "3");
        planGenerico(14, escenarioE, "ae/plan14.log", props);
    }

    public static void plan15() {
        Map<PropiedadesEnum, String> props = new HashMap<>();
        props.put(PropiedadesEnum.SELECCION, "4");
        props.put(PropiedadesEnum.CRUZAMIENTO, "3");
        planGenerico(15, escenarioF, "ae/plan15.log", props);
    }

    public static void planGenerico(Integer idRun, String mapa, String nombreLog, Map<PropiedadesEnum, String> props) {
        try {
            check(idRun);
        } catch (Exception ignore) {
            System.err.println("No se ejecuto");
            return;
        }

        Logger.NAME_LOGGER = nombreLog;// "ae/plan1.log";
        Logger.initLogger();
        Logger.info("############################################################################");
        defaultProperties();
        cargarMapa(mapa);
        for (Map.Entry<PropiedadesEnum, String> prop : props.entrySet()) {
            PropiedadesControlador.setProperty(prop.getKey(), prop.getValue());
        }
        PropiedadesControlador.printValues();

        AEControlador.getInstance().init();
        AEControlador.getInstance().run();
        Logger.info("############################################################################");
    }

    public static void planGrasp(Integer idRun, String mapa, String nombreLog) {

        try {
            check(idRun);
        } catch (Exception ignore) {
            System.err.println("No se ejecuto");
            return;
        }

        Logger.NAME_LOGGER = nombreLog;// "ae/plan1.log";
        Logger.initLogger();
        Logger.info("############################################################################");
        defaultProperties();    
        PropiedadesControlador.setProperty(PropiedadesEnum.CORRIDAS, "10");
        cargarMapa(mapa);

        PropiedadesControlador.printValues();

        GraspControlador.getInstance().init();
        GraspControlador.getInstance().run();
        Logger.info("############################################################################");
    }

    public static void check(Integer idRun) {
        final String filePath = HOME + System.getProperty("file.separator") + idRun + ".chk";

        File file = new File(filePath);
        if (file.exists()) {
            throw new RuntimeException("Alguien ya tomo la tarea");
        } else {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private static void defaultProperties() {

        PropiedadesControlador.setProperty(PropiedadesEnum.DEBUG, "false");
        PropiedadesControlador.setProperty(PropiedadesEnum.SEED, "");

        PropiedadesControlador.setProperty(PropiedadesEnum.GENERACIONES, "1000");
        PropiedadesControlador.setProperty(PropiedadesEnum.GENERACIONES_INVARIANTES, "500");

        PropiedadesControlador.setProperty(PropiedadesEnum.INICIALIZACION, "3");
        PropiedadesControlador.setProperty(PropiedadesEnum.INICIALIZACION_INTENTOS, "100");
        PropiedadesControlador.setProperty(PropiedadesEnum.INICIALIZACION_TAMANIO_POBLACION, "50");

        PropiedadesControlador.setProperty(PropiedadesEnum.SELECCION, "3");
        PropiedadesControlador.setProperty(PropiedadesEnum.SELECCION_ELITE, "10");

        PropiedadesControlador.setProperty(PropiedadesEnum.CRUZAMIENTO, "2");
        PropiedadesControlador.setProperty(PropiedadesEnum.CRUZAMIENTO_PROBABILIDAD, "50");
        PropiedadesControlador.setProperty(PropiedadesEnum.CRUZAMIENTO_CANTIDAD, "5");
        PropiedadesControlador.setProperty(PropiedadesEnum.CRUZAMIENTO_ALPHA, "0.5");

        PropiedadesControlador.setProperty(PropiedadesEnum.MUTACION, "1");
        PropiedadesControlador.setProperty(PropiedadesEnum.MUTACION_PROPABILIDAD, "10");
        PropiedadesControlador.setProperty(PropiedadesEnum.MUTACION_RUIDO, "10");

        PropiedadesControlador.setProperty(PropiedadesEnum.PIXEL_REPRESENTACION, "10");

        PropiedadesControlador.setProperty(PropiedadesEnum.CAMARA_CANTIDAD, "20");
        PropiedadesControlador.setProperty(PropiedadesEnum.CAMARA_RANGO, "150");
        PropiedadesControlador.setProperty(PropiedadesEnum.CAMARA_AMPLITUD, "35");

        PropiedadesControlador.setProperty(PropiedadesEnum.INTERES_MAYOR, "20");
        PropiedadesControlador.setProperty(PropiedadesEnum.INTERES_NORMAL, "1");

        PropiedadesControlador.setProperty(PropiedadesEnum.CORRIDAS, "30");
    }

    private static void cargarMapa(String path) {
        Logger.info("Instancia:  " + path);
        try (InputStream fis = new FileInputStream(HOME + "/instancias/" + path)) {

            BufferedImage mapaBI = ImageIO.read(fis);
            ProblemaControlador.getInstance().cargar(mapaBI);

        } catch (Exception ex) {
            Logger.error(null, ex);
        }
    }
}
