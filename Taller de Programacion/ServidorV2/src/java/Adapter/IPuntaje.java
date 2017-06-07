/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Adapter;

import Extras.Coneccion;
import com.sun.xml.ws.client.BindingProviderProperties;
import java.net.URL;
import java.util.List;
import java.util.Map;
import javax.xml.ws.BindingProvider;
import publisher.DataCliente;
import publisher.PPuntaje;
import publisher.PPuntajeService;

/**
 *
 * @author Samuels
 */
public class IPuntaje {

    private int numero;
    private PPuntajeService PPPservice;
    private PPuntaje PPP;

    public IPuntaje() {
        try {
            URL url = new URL("http://" + Coneccion.IP + ":" + Coneccion.Puerto + "/PPP?wsdl");
            PPPservice = new PPuntajeService(url);
            PPP = PPPservice.getPPuntajePort();
            numero = PPP.reservarSesion();
            Map<String, Object> requestContext = ((BindingProvider) PPP).getRequestContext();
            requestContext.put(BindingProviderProperties.REQUEST_TIMEOUT, 10000); // Timeout in millis
            requestContext.put(BindingProviderProperties.CONNECT_TIMEOUT, 10000); // Timeout in millis
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public DataCliente seleccionarCliente(String nick) throws Exception {
        return PPP.seleccionarCliente(nick, numero);
    }

    public List listarProductosComprados() throws Exception {
        return PPP.listarProductosComprados(numero).getList();
    }

    public void seleccionarProducto(Integer nref) {
        PPP.seleccionarProducto(numero, nref);
    }

    public void puntuarProducto(Integer puntos) throws Exception {
        PPP.puntuarProducto(numero, puntos);
    }

    public List noPuntuados() {
        return PPP.noPuntuados(numero).getList();
    }
}
