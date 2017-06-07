package extras;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Configuracion {

    public static String IP = "localhost";
    public static String Port = "20016";

    public static void load() {
        try {
            String path = System.getProperty("user.home") + "/.DirectMarket/Movil.prop";
            FileInputStream in = new FileInputStream(path);
            Properties ap = new Properties();
            ap.load(in);
            IP = ap.getProperty("IP");
            Port = ap.getProperty("PORT");
        } catch (IOException e) {
            defaultSet();
            try {
                save();
            } catch (Exception e2) {
            }
        }
    }

    public static void defaultSet() {
        IP = "localhost";
        Port = "20016";
    }

    public static void save() {
        try {
            String path = System.getProperty("user.home") + "/.DirectMarket/Movil.prop";
            FileOutputStream out = new FileOutputStream(path);
            Properties ap = new Properties();
            ap.setProperty("IP", IP);
            ap.setProperty("PORT", Port);
            ap.store(out, "Propiedades");
        } catch (Exception e) {
        }
    }

    public static String getDir() {
        String ret = "";
        ret += "http://";
        ret += IP;
        ret += ":";
        ret += Port;
        return ret;
    }
}
