package Adapter;

import Extras.Coneccion;
import com.sun.xml.ws.client.BindingProviderProperties;
import java.net.URL;
import java.util.Map;
import javax.xml.ws.BindingProvider;
import publisher.DataOrdenCompra;

public class IConfirmarOrden {

    private int numero;
    private publisher.PConfirmarOrdenService PCOservice;
    private publisher.PConfirmarOrden PCO;

    public IConfirmarOrden() {
        try {
            URL url = new URL("http://" + Coneccion.IP + ":" + Coneccion.Puerto + "/PCO?wsdl");
            PCOservice = new publisher.PConfirmarOrdenService(url);
            PCO = PCOservice.getPConfirmarOrdenPort();
            numero = PCO.reservarSesion();
            Map<String, Object> requestContext = ((BindingProvider) PCO).getRequestContext();
            requestContext.put(BindingProviderProperties.REQUEST_TIMEOUT, 10000); // Timeout in millis
            requestContext.put(BindingProviderProperties.CONNECT_TIMEOUT, 10000); // Timeout in millis
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public DataOrdenCompra seleccionarOrden(Integer num) throws Exception {
        return PCO.seleccionarOrden(num, numero);
    }

    public void confirmarOrden(String nick) throws Exception {
        PCO.confirmarOrden(nick, numero);
    }
}
