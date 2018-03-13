package uy.edu.fing.ae.obligatorio.controlador;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;
import uy.edu.fing.ae.obligatorio.util.Logger;
import uy.edu.fing.ae.obligatorio.util.PropiedadesEnum;

public class PropiedadesControlador {

    private static Properties p = new Properties();

    private PropiedadesControlador() {
    }

    static {
        try {
            load(new File("properties/instancia1.prop"));
        } catch (Exception ignore) {
        }
    }

    public static void setProperty(PropiedadesEnum key, String value) {
        p.put(key.getNombre(), value);
    }

    public static String getProperty(PropiedadesEnum prop) {
        getProps();
        String value = (String) p.get(prop.getNombre());
        return value == null ? prop.getValorDefecto() : value;
    }

    public static Boolean getBoolProperty(PropiedadesEnum prop) {
        getProps();
        String propI = getProperty(prop);
        if (propI == null || "".equals(propI)) {
            return Boolean.FALSE;
        }
        return Boolean.valueOf(propI);
    }

    public static Integer getIntProperty(PropiedadesEnum prop) {
        getProps();
        String propI = getProperty(prop);
        if (propI == null || "".equals(propI)) {
            return null;
        }
        return Integer.valueOf(propI);
    }

    public static Double getDoubleProperty(PropiedadesEnum prop) {
        getProps();
        String propI = getProperty(prop);
        if (propI == null || "".equals(propI)) {
            return null;
        }
        return Double.valueOf(propI);
    }

    public static void saveTest() {
        getProps();
        try (FileOutputStream fos = new FileOutputStream("mor.properties")) {
            p.store(fos, null);
        } catch (IOException ex) {
            Logger.error(null, ex);
        }
    }

    public static void load(File propFile) {
        try (FileInputStream is = new FileInputStream(propFile)) {
            p = new Properties();
            p.load(is);
        } catch (IOException ex) {
            throw new RuntimeException("Error leyendo el archivo de propiedades", ex);
        }
    }

    private static Properties getProps() {
        if (p == null) {
            p = new Properties();
            Arrays.asList(PropiedadesEnum.values()).forEach((value) -> {
                p.put(value.getNombre(), value.getValorDefecto());
            });
        }
        return p;
    }

    public static void printValues() {
        Logger.info("-----------------------   VALORES   -------------------------");
        for (PropiedadesEnum prop : PropiedadesEnum.values()) {
            Logger.info(prop.getNombre() + "\t" + getProperty(prop));
        }
    }
}
