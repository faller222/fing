package Adapter;

import Extras.Coneccion;
import com.sun.xml.ws.client.BindingProviderProperties;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.xml.ws.BindingProvider;
import publisher.DataCliente;

public class IVerInfoCliente {

    private int numero;
    private publisher.PVerInfoClienteService PVICservice;
    private publisher.PVerInfoCliente PVIC;

    public IVerInfoCliente() {
        try {
            URL url = new URL("http://" + Coneccion.IP + ":" + Coneccion.Puerto + "/PIC?wsdl");
            PVICservice = new publisher.PVerInfoClienteService(url);
            PVIC = PVICservice.getPVerInfoClientePort();
            numero = PVIC.reservarSesion();
            Map<String, Object> requestContext = ((BindingProvider) PVIC).getRequestContext();
            requestContext.put(BindingProviderProperties.REQUEST_TIMEOUT, 10000); // Timeout in millis
            requestContext.put(BindingProviderProperties.CONNECT_TIMEOUT, 10000); // Timeout in millis
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List listarClientes() {
        return PVIC.listarClientes().getList();
    }

    public DataCliente seleccionarCliente(String nick) throws Exception {
        return PVIC.seleccionarCliente(nick, numero);
    }

    public List listarOrdenesCliente() throws Exception {
        List ret = new ArrayList();
        for (Object O : PVIC.listarOrdenesCliente(numero).getList()) {
            Integer num = (Integer) O;
            ret.add(PVIC.buscarOrden(num));
        }
        return ret;
    }
}
