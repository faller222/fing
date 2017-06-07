package Adapter;

import Extras.Coneccion;
import com.sun.xml.ws.client.BindingProviderProperties;
import java.net.URL;
import java.util.Map;
import javax.xml.ws.BindingProvider;
import publisher.DataCategoria;
import publisher.PBuscarCategoria;
import publisher.PBuscarCategoriaService;

public class IBuscarCate {

    private PBuscarCategoriaService PBCservice;
    private PBuscarCategoria PBC;

    public IBuscarCate() {
        try {
            URL url = new URL("http://" + Coneccion.IP + ":" + Coneccion.Puerto + "/PBC?wsdl");
            PBCservice = new PBuscarCategoriaService(url);
            PBC = PBCservice.getPBuscarCategoriaPort();
            Map<String, Object> requestContext = ((BindingProvider) PBC).getRequestContext();
            requestContext.put(BindingProviderProperties.REQUEST_TIMEOUT, 10000); // Timeout in millis
            requestContext.put(BindingProviderProperties.CONNECT_TIMEOUT, 10000); // Timeout in millis
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public DataCategoria listarCategorias() {
        return PBC.listarCategorias();
    }

    public DataCategoria buscarCategoria(String nCat) {
        return PBC.buscarCategoria(nCat);
    }
}
