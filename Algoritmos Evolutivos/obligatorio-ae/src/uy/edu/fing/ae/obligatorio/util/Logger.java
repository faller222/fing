package uy.edu.fing.ae.obligatorio.util;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import uy.edu.fing.ae.obligatorio.controlador.PropiedadesControlador;

public final class Logger {

    private static final String hostName = getHostName();
    private static final String homeDir = System.getProperty("user.home");
    private static final String fileSeparator = System.getProperty("file.separator");
    public static String NAME_LOGGER = "evolutivos.log";

    private static long initTIme = System.currentTimeMillis();

    public static String getHostName() {
        String tmp;
        try {
             tmp = InetAddress.getLocalHost().getHostName();
             if(tmp!=null){
                 return tmp;
             }
        } catch (Exception ignore) {
        }
        return "";
    }

    public static void initLogger() {

        final String filePath = homeDir + fileSeparator + NAME_LOGGER +"."+ hostName;
        try {
            initTIme = System.currentTimeMillis();

            File file = new File(filePath);
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static synchronized void write(String text) {
        final String filePath = homeDir + fileSeparator + NAME_LOGGER +"."+ hostName;
        try {
            final String now = (System.currentTimeMillis() - initTIme) + "\t";
            final String msg = now + text + "\n";
            File file = new File(filePath);
            if (file.exists()) {
                Files.write(Paths.get(filePath), msg.getBytes(), StandardOpenOption.APPEND);
            } else {
                Files.write(Paths.get(filePath), msg.getBytes(), StandardOpenOption.CREATE_NEW);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void info(String msg) {
        //logger.log(Level.INFO, msg);
        System.out.println("INFO : " + msg);
        write("INFO\t" + msg);
    }

    public static void debug(String msg) {
        //logger.log(Level.INFO, msg);
        Boolean debugEneabled = PropiedadesControlador.getBoolProperty(PropiedadesEnum.DEBUG);
        if (debugEneabled) {
            System.out.println("DEBUG: " + msg);
            write("DEBUG\t" + msg);
        }
    }

    public static void error(String msg) {
        //logger.log(Level.SEVERE, msg);
        System.err.println("ERROR: " + msg);
        write("ERROR\t" + msg);
    }

    public static void error(String msg, Throwable t) {
        //logger.log(Level.SEVERE, msg, t);
        System.err.println("ERROR: " + msg + " : " + t.getMessage());

        write("ERROR\t" + msg + "\t" + t.getMessage());
        t.printStackTrace();
    }

}
