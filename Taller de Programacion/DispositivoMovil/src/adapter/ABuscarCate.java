package adapter;

import com.sun.xml.ws.client.BindingProviderProperties;
import extras.Configuracion;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.WebServiceException;
import publisher.DataCategoria;
import publisher.PBuscarCategoria;
import publisher.PBuscarCategoriaService;

public class ABuscarCate {

    private PBuscarCategoriaService PBCservice;
    private PBuscarCategoria PBC;

    public ABuscarCate() throws WebServiceException {
        URL url;
        try {
            url = new URL(Configuracion.getDir() + "/PBC?wsdl");
        } catch (MalformedURLException ex) {
            throw new WebServiceException(ex);
        }

        PBCservice = new PBuscarCategoriaService(url);
        PBC = PBCservice.getPBuscarCategoriaPort();
        Map<String, Object> requestContext = ((BindingProvider) PBC).getRequestContext();
        requestContext.put(BindingProviderProperties.REQUEST_TIMEOUT, 10000); // Timeout in millis
        requestContext.put(BindingProviderProperties.CONNECT_TIMEOUT, 10000); // Timeout in millis

    }

    public DataCategoria listarCategorias() throws WebServiceException {
        return PBC.listarCategorias();
    }

    public DataCategoria buscarCategoria(String nCat) throws WebServiceException {
        return PBC.buscarCategoria(nCat);
    }
}
