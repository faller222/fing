package Adapter;

import Extras.Coneccion;
import com.sun.xml.ws.client.BindingProviderProperties;
import java.net.URL;
import java.util.List;
import java.util.Map;
import javax.xml.ws.BindingProvider;
import publisher.Coleccion;
import publisher.DataProveedor;

public class IVerInfoProveedor {

    private int numero;
    private publisher.PVerInfoProveedorService PVIPservice;
    private publisher.PVerInfoProveedor PVIP;

    public IVerInfoProveedor() {
        try {
            URL url = new URL("http://" + Coneccion.IP + ":" + Coneccion.Puerto + "/PIP?wsdl");
            PVIPservice = new publisher.PVerInfoProveedorService(url);
            PVIP = PVIPservice.getPVerInfoProveedorPort();
            numero = PVIP.reservarSesion();
            Map<String, Object> requestContext = ((BindingProvider) PVIP).getRequestContext();
            requestContext.put(BindingProviderProperties.REQUEST_TIMEOUT, 10000); // Timeout in millis
            requestContext.put(BindingProviderProperties.CONNECT_TIMEOUT, 10000); // Timeout in millis
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List listarProveedores() {
        return PVIP.listarProveedores().getList();
    }

    public DataProveedor seleccionarProveedor(String nick) throws Exception {
        return PVIP.seleccionarProveedor(nick, numero);
    }

    public List listarProductosProveedor() throws Exception {
        Coleccion col = PVIP.listarProductosProveedor(numero);
        return col.getList();
    }

    public boolean isOnline(String nick) {
        return PVIP.isOnline(nick);
    }
}
