/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Adapter;

import Extras.Coneccion;
import com.sun.xml.ws.client.BindingProviderProperties;
import java.net.URL;
import java.util.Map;
import javax.xml.ws.BindingProvider;
import publisher.DataCliente;
import publisher.DataOrdenCompra;
import publisher.PActDesNoti;
import publisher.PActDesNotiService;

public class IActDesNoti {

    private int numero;
    private PActDesNotiService PADservice;
    private PActDesNoti PAD;

    public IActDesNoti() {
        try {
            URL url = new URL("http://" + Coneccion.IP + ":" + Coneccion.Puerto + "/PAD?wsdl");
            PADservice = new publisher.PActDesNotiService(url);
            PAD = PADservice.getPActDesNotiPort();
            numero = PAD.reservarSesion();
            Map<String, Object> requestContext = ((BindingProvider) PAD).getRequestContext();
            requestContext.put(BindingProviderProperties.REQUEST_TIMEOUT, 10000); // Timeout in millis
            requestContext.put(BindingProviderProperties.CONNECT_TIMEOUT, 10000); // Timeout in millis
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public DataCliente seleccionarCliente(String nick) throws Exception {
        return PAD.seleccionarCliente(nick, numero);
    }

    public DataOrdenCompra seleccionarOrdenCompra(Integer Noc) throws Exception {
        return PAD.seleccionarOrdenCompra(Noc, numero);
    }

    public void ActivarNotiOrdenes() {
        PAD.activarNotiOrdenes(numero);
    }

    public void ActivarNotiProveedor() {
        PAD.activarNotiProveedor(numero);
    }

    public void ActivarNotiProducto() {
        PAD.activarNotiProducto(numero);
    }

    public void ActivarNotiReclamo() {
        PAD.activarNotiReclamo(numero);
    }

    public void DesactivarNotiReclamo() {
        PAD.desactivarNotiReclamo(numero);
    }

    public void DesactivarNotiOrdenes() {
        PAD.desactivarNotiOrdenes(numero);
    }

    public void DesactivarNotiProveedor() {
        PAD.desactivarNotiProveedor(numero);
    }

    public void DesactivarNotiProducto() {
        PAD.desactivarNotiProducto(numero);
    }
}
