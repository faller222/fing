package uy.edu.fing.mor.obligatorio.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;

public class Logger {

    static java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Logger.class.getName());

    static {
        File file = new File("properties/logging.properties");
        try (FileInputStream is = new FileInputStream(file)) {
            //LogManager.getLogManager().readConfiguration(is);
            //logger.setLevel(Level.FINE);
            //logger.addHandler(new java.util.logging.ConsoleHandler());
            //logger.setUseParentHandlers(false);
        } catch (IOException ex) {
            throw new RuntimeException("Error leyendo el archivo de propiedades", ex);
        }
    }

    public static void info(String msg) {
        logger.log(Level.INFO, msg);
    }

    public static void error(String msg) {
        logger.log(Level.SEVERE, msg);
    }

    public static void error(String msg, Throwable t) {
        logger.log(Level.SEVERE, msg, t);
    }

}
