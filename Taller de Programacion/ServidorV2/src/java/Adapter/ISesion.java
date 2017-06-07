package Adapter;

import Extras.Coneccion;
import com.sun.xml.ws.client.BindingProviderProperties;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import javax.xml.ws.BindingProvider;
import publisher.DataOrdenCompra;
import publisher.DataUsuario;
import publisher.PInicioSesionService;

public class ISesion {

    private PInicioSesionService Servis;
    private publisher.PInicioSesion PIS;
    private String nick;

    public ISesion() {
        try {
            URL url = new URL("http://" + Coneccion.IP + ":" + Coneccion.Puerto + "/PIS?wsdl");
            Servis = new PInicioSesionService(url);
            PIS = Servis.getPInicioSesionPort();
            Map<String, Object> requestContext = ((BindingProvider) PIS).getRequestContext();
            requestContext.put(BindingProviderProperties.REQUEST_TIMEOUT, 10000); // Timeout in millis
            requestContext.put(BindingProviderProperties.CONNECT_TIMEOUT, 10000); // Timeout in millis
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Vincular();
    }

    public DataUsuario inicioSesion(String nm, String contra) {
        DataUsuario du = null;
        try {
            du = PIS.inicioSesion(nm, contra);
            nick = du.getNickname();
        } catch (Exception e) {
        }
        return du;
    }

    public DataUsuario verInfoPerfil() {
        DataUsuario du = null;
        try {
            du = PIS.verInfoPerfil(nick);
        } catch (Exception e) {
        }
        return du;
    }

    public void agregaLinea(Integer cant, Integer num) throws Exception {
        PIS.agregaLinea(cant, num, nick);
    }

    public DataOrdenCompra verCarrito() throws Exception {
        return PIS.verCarrito(nick);
    }

    public void generarOrden() throws Exception {
        PIS.generarOrden(nick);
    }

    public boolean puedeComentar(Integer nRef) {
        return PIS.puedeComentar(nRef, nick);
    }

    public void comentar(Integer nRef, String Comen) throws Exception {
        PIS.comentar(nRef, Comen, nick);
    }

    public void responder(Integer nRef, String Comen, Integer Padre) throws Exception {
        PIS.responder(nRef, Comen, Padre, nick);
    }

    public void cerrarSesion() throws Exception {
        PIS.cerrarSesion(nick);
    }

    private void Vincular() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    PIS.estoyOnline(nick);
                } catch (Exception e) {
                }
            }
        };
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(timerTask, 0, 1000);
    }

    //Para Chat
    public void mandarMensaje(String Receptor, String Mensaje) throws Exception {
        PIS.mandarMensaje(Receptor, Mensaje, nick);
    }

    public List conversacionCon(String Otro) throws Exception {
        return PIS.conversacionCon(Otro, nick).getList();
    }

    public List conQuienHable() throws Exception {
        return PIS.conQuienHable(nick).getList();
    }

    public int contarMensajes(String Otro) throws Exception {
        return PIS.contarMensajes(Otro, nick);
    }

    public boolean tengoMsj() throws Exception {
        return PIS.tengoMsj(nick);
    }
}
