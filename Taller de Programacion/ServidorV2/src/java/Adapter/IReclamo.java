package Adapter;

import Extras.Coneccion;
import com.sun.xml.ws.client.BindingProviderProperties;
import java.net.URL;
import java.util.List;
import java.util.Map;
import javax.xml.ws.BindingProvider;
import publisher.DataCliente;
import publisher.DataProducto;
import publisher.DataProveedor;

public class IReclamo {

    private publisher.PReclamoService PRPservice;
    private publisher.PReclamo PRP;
    private int Numero;

    public IReclamo() {
        try {
            URL url = new URL("http://" + Coneccion.IP + ":" + Coneccion.Puerto + "/PRP?wsdl");
            PRPservice = new publisher.PReclamoService(url);
            PRP = PRPservice.getPReclamoPort();
            Numero = PRP.reservarSesion();
            Map<String, Object> requestContext = ((BindingProvider) PRP).getRequestContext();
            requestContext.put(BindingProviderProperties.REQUEST_TIMEOUT, 10000); // Timeout in millis
            requestContext.put(BindingProviderProperties.CONNECT_TIMEOUT, 10000); // Timeout in millis
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public DataCliente seleccionarCliente(String nick) throws Exception {
        return PRP.seleccionarCliente(nick, Numero);
    }

    public DataProducto seleccionarProducto(Integer NRef) throws Exception {
        return PRP.seleccionarProducto(NRef, Numero);
    }

    public void altaReclamo(String texto) {
        PRP.altaReclamo(texto, Numero);
    }

    public DataProveedor seleccionarProveedor(String nick) throws Exception {
        return PRP.seleccionarProveedor(nick, Numero);
    }

    public List listarReclamos() {
        return PRP.listarReclamos(Numero).getList();
    }

    public void respRec(String txt, Integer nRec, Integer idProd) {
        PRP.respRec(Numero, txt, nRec, idProd);
    }
}
