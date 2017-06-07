package adapter;

import com.sun.xml.ws.client.BindingProviderProperties;
import extras.Configuracion;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.WebServiceException;
import publisher.DataCliente;
import publisher.DataProducto;
import publisher.DataProveedor;

public class AReclamo {

    private publisher.PReclamoService PRPservice;
    private publisher.PReclamo PRP;
    private int Numero;

    public AReclamo() throws WebServiceException {
        URL url;
        try {
            url = new URL(Configuracion.getDir() + "/PRP?wsdl");
        } catch (MalformedURLException ex) {
            throw new WebServiceException(ex);
        }
        PRPservice = new publisher.PReclamoService(url);
        PRP = PRPservice.getPReclamoPort();
        Numero = PRP.reservarSesion();
        Map<String, Object> requestContext = ((BindingProvider) PRP).getRequestContext();
        requestContext.put(BindingProviderProperties.REQUEST_TIMEOUT, 10000); // Timeout in millis
        requestContext.put(BindingProviderProperties.CONNECT_TIMEOUT, 10000); // Timeout in millis

    }

    public DataCliente seleccionarCliente(String nick) throws Exception {
        return PRP.seleccionarCliente(nick, Numero);
    }

    public DataProducto seleccionarProducto(Integer NRef) throws Exception {
        return PRP.seleccionarProducto(NRef, Numero);
    }

    public void altaReclamo(String texto) throws WebServiceException {
        PRP.altaReclamo(texto, Numero);
    }

    public DataProveedor seleccionarProveedor(String nick) throws Exception {
        return PRP.seleccionarProveedor(nick, Numero);
    }

    public List listarReclamos() throws WebServiceException {
        return PRP.listarReclamos(Numero).getList();
    }
}
