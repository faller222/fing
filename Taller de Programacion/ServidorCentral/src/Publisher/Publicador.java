package Publisher;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.xml.ws.Endpoint;

public class Publicador {

    public static Integer mailPort = 3016;
    public static String Maquina = "localhost:10016";
    private static PAltaUsuario PAU;
    private static PInicioSesion PIS;
    private static PBuscarCategoria PBC;
    private static PVerProducto PVP;
    private static PAgregarProducto PAP;
    private static PVerInfoProveedor PIP;
    private static PVerInfoCliente PIC;
    private static PAccesoSitio PAS;
    private static PConfirmarOrden PCO;
    private static PReclamo PRP;
    private static PPuntaje PPP;
    private static PActDesNoti PAD;
    private static Properties aP = new Properties();
    private static boolean Publicados = false;
    private static List<Endpoint> EnPons = new ArrayList<Endpoint>();

    public static boolean isPublicados() {
        return Publicados;
    }

    private static void init() {
        if (PAU == null) {
            PAU = new PAltaUsuario();
        }
        if (PIS == null) {
            PIS = new PInicioSesion();
        }
        if (PBC == null) {
            PBC = new PBuscarCategoria();
        }
        if (PVP == null) {
            PVP = new PVerProducto();
        }
        if (PAP == null) {
            PAP = new PAgregarProducto();
        }
        if (PIP == null) {
            PIP = new PVerInfoProveedor();
        }
        if (PIC == null) {
            PIC = new PVerInfoCliente();
        }
        if (PAS == null) {
            PAS = new PAccesoSitio();
        }
        if (PCO == null) {
            PCO = new PConfirmarOrden();
        }
        if (PRP == null) {
            PRP = new PReclamo();
        }
        if (PPP == null) {
            PPP = new PPuntaje();
        }
        if (PAD == null) {
            PAD = new PActDesNoti();
        }
    }

    public static void setPuerto(String Puerto) {
        aP.setProperty("Puerto", Puerto);
    }

    public static void setLocal() {
        aP.setProperty("Local", "http://localhost");
    }

    public static void setAddress(String Address) {
        aP.setProperty("Address", Address);
    }

    //Nombres de los WebServices
    public static void setPIS(String Num) {
        aP.setProperty("PIS", Num);
    }

    public static void setPAU(String Num) {
        aP.setProperty("PAU", Num);
    }

    public static void setPBC(String Num) {
        aP.setProperty("PBC", Num);
    }

    public static void setPVP(String Num) {
        aP.setProperty("PVP", Num);
    }

    public static void setPAP(String Num) {
        aP.setProperty("PAP", Num);
    }

    public static void setPIP(String Num) {
        aP.setProperty("PIP", Num);
    }

    public static void setPIC(String Num) {
        aP.setProperty("PIC", Num);
    }

    public static void setPAS(String Num) {
        aP.setProperty("PAS", Num);
    }

    public static void setPCO(String Num) {
        aP.setProperty("PCO", Num);
    }

    public static void setPRP(String Num) {
        aP.setProperty("PRP", Num);
    }

    public static void setPPP(String Num) {
        aP.setProperty("PPP", Num);
    }

    public static void setPAD(String Num) {
        aP.setProperty("PAD", Num);
    }

    public static void defaultSet() {
        mailPort = 3016;
        Maquina = "localhost:10016";
        setPAS("PAS");
        setPAP("PAP");
        setPAU("PAU");
        setPBC("PBC");
        setPIC("PIC");
        setPIP("PIP");
        setPIS("PIS");
        setPVP("PVP");
        setPCO("PCO");
        setPRP("PRP");
        setPPP("PPP");
        setPAD("PAD");
        setPuerto("20016");
        setLocal();
        String Dominio = "http://localhost";
        try {
            Dominio = "http://" + InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
        }
        setAddress(Dominio);

    }

    public static void load() {
        try {
            String path = System.getProperty("user.home") + "/.DirectMarket/Servidor.prop";
            FileInputStream in = new FileInputStream(path);
            aP.load(in);
        } catch (IOException e) {
            defaultSet();
            save();
        }
    }

    public static void save() {
        try {
            String path = System.getProperty("user.home") + "/.DirectMarket/Servidor.prop";
            FileOutputStream out = new FileOutputStream(path);
            aP.store(out, "Propiedades");
        } catch (Exception e) {
        }
    }

    private static void PS(String Donde, String Puerto) {
        try {
            PAU.Publicar(Donde, Puerto, aP.getProperty("PAU"));
            EnPons.add(PAU.getEndpoint());
            PIS.Publicar(Donde, Puerto, aP.getProperty("PIS"));
            EnPons.add(PIS.getEndpoint());
            PBC.Publicar(Donde, Puerto, aP.getProperty("PBC"));
            EnPons.add(PBC.getEndpoint());
            PVP.Publicar(Donde, Puerto, aP.getProperty("PVP"));
            EnPons.add(PVP.getEndpoint());
            PAP.Publicar(Donde, Puerto, aP.getProperty("PAP"));
            EnPons.add(PAP.getEndpoint());
            PIP.Publicar(Donde, Puerto, aP.getProperty("PIP"));
            EnPons.add(PIP.getEndpoint());
            PIC.Publicar(Donde, Puerto, aP.getProperty("PIC"));
            EnPons.add(PIC.getEndpoint());
            PAS.Publicar(Donde, Puerto, aP.getProperty("PAS"));
            EnPons.add(PAS.getEndpoint());
            PCO.Publicar(Donde, Puerto, aP.getProperty("PCO"));
            EnPons.add(PCO.getEndpoint());
            PRP.Publicar(Donde, Puerto, aP.getProperty("PRP"));
            EnPons.add(PRP.getEndpoint());
            PPP.Publicar(Donde, Puerto, aP.getProperty("PPP"));
            EnPons.add(PPP.getEndpoint());
            PAD.Publicar(Donde, Puerto, aP.getProperty("PAD"));
            EnPons.add(PAD.getEndpoint());
            System.out.println("Publicando servicios en : " + Donde + ":" + Puerto);
        } catch (Exception e) {
            System.err.println("No se pudo Publicar en " + Donde + ":" + Puerto);
        }
    }

    public static void publicarServicios() {
        if (!Publicados) {
            Publicados = true;
            init();
            load();
            PS(aP.getProperty("Local"), aP.getProperty("Puerto"));
            PS(aP.getProperty("Address"), aP.getProperty("Puerto"));
        }
    }

    public static void refreshServicios() {
        if (Publicados) {
            stop();
            load();
            PS(aP.getProperty("Local"), aP.getProperty("Puerto"));
            PS(aP.getProperty("Address"), aP.getProperty("Puerto"));
        }
    }

    public static void stop() {
        Publicados = false;
        for (Endpoint E : EnPons) {
            E.stop();
        }
        EnPons.clear();
    }

    public static String getPuerto() {
        return aP.getProperty("Puerto");
    }

    public static String getLocal() {
        return aP.getProperty("Local");
    }

    public static String getAddress() {
        return aP.getProperty("Address");
    }

    //Nombres de los WebServices
    public static String getPIS() {
        return aP.getProperty("PIS");
    }

    public static String getPAU() {
        return aP.getProperty("PAU");
    }

    public static String getPBC() {
        return aP.getProperty("PBC");
    }

    public static String getPVP() {
        return aP.getProperty("PVP");
    }

    public static String getPAP() {
        return aP.getProperty("PAP");
    }

    public static String getPIP() {
        return aP.getProperty("PIP");
    }

    public static String getPIC() {
        return aP.getProperty("PIC");
    }

    public static String getPAS() {
        return aP.getProperty("PAS");
    }

    public static String getPCO() {
        return aP.getProperty("PCO");
    }

    public static String getPRP() {
        return aP.getProperty("PRP");
    }

    public static String getPPP() {
        return aP.getProperty("PPP");
    }

    public static String getPAD() {
        return aP.getProperty("PAD");
    }
}
