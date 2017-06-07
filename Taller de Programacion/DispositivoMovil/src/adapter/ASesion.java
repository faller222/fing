package adapter;

import com.sun.xml.ws.client.BindingProviderProperties;
import extras.Configuracion;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.WebServiceException;
import publisher.DataOrdenCompra;
import publisher.DataUsuario;
import publisher.PInicioSesion;
import publisher.PInicioSesionService;

public class ASesion {

    private PInicioSesionService Servis;
    private PInicioSesion PIS;
    private String nick;

    public ASesion() throws WebServiceException {
        URL url;
        try {
            url = new URL(Configuracion.getDir() + "/PIS?wsdl");
        } catch (MalformedURLException ex) {
            throw new WebServiceException(ex);
        }
        Servis = new PInicioSesionService(url);
        PIS = Servis.getPInicioSesionPort();
        Map<String, Object> requestContext = ((BindingProvider) PIS).getRequestContext();
        requestContext.put(BindingProviderProperties.REQUEST_TIMEOUT, 5000); // Timeout in millis
        requestContext.put(BindingProviderProperties.CONNECT_TIMEOUT, 5000); // Timeout in millis
    }

    public DataUsuario iniciarSesion(String NU, String Pass) throws WebServiceException {
        DataUsuario ret;
        try {
            ret = PIS.inicioSesion(NU, Pass);
            nick = ret.getNickname();
        } catch (Exception E) {
            ret = null;
        }
        return ret;
    }

    public DataUsuario verInfoPerfil() throws WebServiceException {
        DataUsuario r;
        try {
            r = PIS.verInfoPerfil(nick);
        } catch (WebServiceException e) {
            r = null;
        }
        return r;
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

    public boolean puedeComentar(Integer nRef) throws WebServiceException {
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

    public void estoyOnline() throws Exception {
        PIS.estoyOnline(nick);
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
