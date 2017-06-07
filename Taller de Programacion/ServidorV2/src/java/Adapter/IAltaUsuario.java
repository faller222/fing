package Adapter;

import Extras.Coneccion;
import com.sun.xml.ws.client.BindingProviderProperties;
import java.net.URL;
import java.util.Map;
import javax.xml.ws.BindingProvider;

public class IAltaUsuario {

    private int numero;
    private publisher.PAltaUsuarioService PAUservice;
    private publisher.PAltaUsuario PAU;

    public IAltaUsuario() {
        try {
            URL url = new URL("http://" + Coneccion.IP + ":" + Coneccion.Puerto + "/PAU?wsdl");
            PAUservice = new publisher.PAltaUsuarioService(url);
            PAU = PAUservice.getPAltaUsuarioPort();
            Map<String, Object> requestContext = ((BindingProvider) PAU).getRequestContext();
            requestContext.put(BindingProviderProperties.REQUEST_TIMEOUT, 10000); // Timeout in millis
            requestContext.put(BindingProviderProperties.CONNECT_TIMEOUT, 10000); // Timeout in millis
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean verificarNickname(String Nick) {
        return PAU.verificarNickname(Nick);
    }

    public boolean verificarMail(String Mail) {
        return PAU.verificarMail(Mail);
    }

    public void ingresarDataUsuario(publisher.DataUsuario dU) throws Exception {
        numero = PAU.ingresarDataUsuario(dU);
    }

    public void altaUsuario() throws Exception {
        PAU.altaUsuario(numero);
    }
}
