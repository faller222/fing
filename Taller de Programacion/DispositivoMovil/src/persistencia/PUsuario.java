package persistencia;

import static extras.Configuracion.save;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.jasypt.util.text.BasicTextEncryptor;
import publisher.DataCliente;
import publisher.DataProveedor;
import publisher.DataUsuario;

public class PUsuario {

    private static PUsuario Instancia;
    private static BasicTextEncryptor textEncryptor;
    private String Nombre;
    private String Apellido;
    private String nickName;
    private String email;
    private String pass;
    private XMLGregorianCalendar fecha;
    private boolean esCliente;
    private final Properties Props;

    private PUsuario() {
        Props = new Properties();
        textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPassword("DirectMarket");
    }

    public static void dispose() {
        try {
            File Log = new File("Log.in");
            Log.setWritable(true);
            Log.delete();
        } catch (Exception e) {
        }
        try {
            PersistenseUnit.dispose();
            File db = new File("Clie.db");
            db.setWritable(true);
            boolean hola = db.delete();
        } catch (Exception e) {
        }
        Instancia = null;
    }

    public static PUsuario getInstance() {
        if (Instancia == null) {
            Instancia = new PUsuario();
        }
        return Instancia;
    }

    public DataUsuario getDataUsuario() throws Exception {
        load();
        DataUsuario ret;
        if (esCliente) {
            ret = new DataCliente();
        } else {
            ret = new DataProveedor();
        }
        ret.setApellido(Apellido);
        ret.setNombre(Nombre);
        ret.setMail(email);
        ret.setPass(pass);
        ret.setFechaNac(fecha);
        ret.setNickname(nickName);
        return ret;
    }

    public void setUsuario(DataUsuario du) {
        esCliente = du instanceof DataCliente;
        Nombre = du.getNombre();
        Apellido = du.getApellido();
        nickName = du.getNickname();
        email = du.getMail();
        pass = du.getPass();
        fecha = du.getFechaNac();
        save();
    }

    private void load() throws IOException, DatatypeConfigurationException, ParseException {
        FileInputStream in = new FileInputStream("Log.in");
        Props.load(in);
        PtoD();
    }

    private void save() {
        try {
            FileOutputStream out = new FileOutputStream("Log.in");
            DtoP();
            Props.store(out, "infoSesion");
        } catch (Exception e) {
        }
    }

    private void PtoD() throws DatatypeConfigurationException, ParseException {
        Nombre = Props.getProperty("Nombre");
        Apellido = Props.getProperty("Apellido");
        nickName = Props.getProperty("nickName");
        email = Props.getProperty("email");
        String enc = Props.getProperty("pass");
        pass = des(enc);
        String SFec = Props.getProperty("fecha");
        fecha = StrToGreg(SFec);
        String isClie = Props.getProperty("esCliente");
        esCliente = bts(isClie);
    }

    private void DtoP() {

        Props.setProperty("Nombre", Nombre);
        Props.setProperty("Apellido", Apellido);
        Props.setProperty("nickName", nickName);
        Props.setProperty("email", email);
        String enc = enc(pass);
        Props.setProperty("pass", enc);
        String SFec = GregToStr(fecha);
        Props.setProperty("fecha", SFec);
        String isClie = bts(esCliente);
        Props.setProperty("esCliente", isClie);
    }

    private XMLGregorianCalendar StrToGreg(String SFec) throws DatatypeConfigurationException, ParseException {
        SimpleDateFormat formFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date def;
        GregorianCalendar c = null;
        def = formFormat.parse(SFec);
        c = new GregorianCalendar();
        c.setTime(def);
        return DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
    }

    private String GregToStr(XMLGregorianCalendar SFec) {
        Date dt = SFec.toGregorianCalendar().getTime();
        SimpleDateFormat formFormat = new SimpleDateFormat("dd-MM-yyyy");
        return formFormat.format(dt);
    }

    private String bts(Boolean b) {
        return b.toString();
    }

    private Boolean bts(String b) {
        return Boolean.parseBoolean(b);
    }

    private String enc(String aenc) {
        return textEncryptor.encrypt(aenc);
    }

    private String des(String coso) {
        return textEncryptor.decrypt(coso);
    }
}
