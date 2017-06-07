package adapter;

import com.sun.xml.ws.client.BindingProviderProperties;
import extras.Configuracion;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.WebServiceException;
import publisher.DataCliente;

public class AVerOrden {

    private int numero;
    private publisher.PVerInfoClienteService PVICservice;
    private publisher.PVerInfoCliente PVIC;

    public AVerOrden() throws WebServiceException {
        URL url;
        try {
            url = new URL(Configuracion.getDir() + "/PIC?wsdl");
        } catch (MalformedURLException ex) {
            throw new WebServiceException(ex);
        }
        PVICservice = new publisher.PVerInfoClienteService(url);
        PVIC = PVICservice.getPVerInfoClientePort();
        numero = PVIC.reservarSesion();
        Map<String, Object> requestContext = ((BindingProvider) PVIC).getRequestContext();
        requestContext.put(BindingProviderProperties.REQUEST_TIMEOUT, 10000); // Timeout in millis
        requestContext.put(BindingProviderProperties.CONNECT_TIMEOUT, 10000); // Timeout in millis
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
