package Adapter;

import Extras.Coneccion;
import com.sun.xml.ws.client.BindingProviderProperties;
import java.net.URL;
import java.util.Map;
import javax.xml.ws.BindingProvider;
import publisher.Coleccion;
import publisher.DataCategoria;
import publisher.DataProducto;
import publisher.PAgregarProducto;
import publisher.PAgregarProductoService;

public class IAgregarProducto {

    private int numero;
    private PAgregarProductoService PAPservice;
    private PAgregarProducto PAP;

    public IAgregarProducto() {
        try {
            URL url = new URL("http://" + Coneccion.IP + ":" + Coneccion.Puerto + "/PAP?wsdl");
            PAPservice = new publisher.PAgregarProductoService(url);
            PAP = PAPservice.getPAgregarProductoPort();
            numero = PAP.reservarSesion();
            Map<String, Object> requestContext = ((BindingProvider) PAP).getRequestContext();
            requestContext.put(BindingProviderProperties.REQUEST_TIMEOUT, 10000); // Timeout in millis
            requestContext.put(BindingProviderProperties.CONNECT_TIMEOUT, 10000); // Timeout in millis
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Coleccion listarProveedores() {
        return PAP.listarProveedores();
    }

    public void ingresarDataProducto(DataProducto dataP) throws Exception {
        PAP.ingresarDataProducto(dataP, numero);
    }

    public DataCategoria listarCategorias() {
        return PAP.listarCategorias();
    }

    public void agregarCategoriaAProducto(String nomCat) throws Exception {
        PAP.agregarCategoriaAProducto(nomCat, numero);
    }

    public void agregarImagenAProducto(byte[] Img, int Pos) throws Exception {
        PAP.agregarImagenAProducto(Img, Pos, numero);
    }

    public void altaProducto() throws Exception {//no agrego Categoria
        PAP.altaProducto(numero);
    }
}
