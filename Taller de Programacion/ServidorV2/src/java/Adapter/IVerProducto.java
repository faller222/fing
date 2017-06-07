package Adapter;

import Extras.Coneccion;
import com.sun.xml.ws.client.BindingProviderProperties;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.xml.ws.BindingProvider;
import publisher.Coleccion;
import publisher.DataCategoria;
import publisher.DataProducto;

public class IVerProducto {

    private int numero;
    private publisher.PVerProductoService PVPservice;
    private publisher.PVerProducto PVP;

    public IVerProducto() {
        try {
            URL url = new URL("http://" + Coneccion.IP + ":" + Coneccion.Puerto + "/PVP?wsdl");
            PVPservice = new publisher.PVerProductoService(url);
            PVP = PVPservice.getPVerProductoPort();
            numero = PVP.reservarSesion();
            Map<String, Object> requestContext = ((BindingProvider) PVP).getRequestContext();
            requestContext.put(BindingProviderProperties.REQUEST_TIMEOUT, 10000); // Timeout in millis
            requestContext.put(BindingProviderProperties.CONNECT_TIMEOUT, 10000); // Timeout in millis
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List buscarProd(String Nick, int pag, int tipo) {
        Coleccion aux = PVP.buscarProd(Nick, pag, tipo);
        return aux.getList();
    }

    public DataCategoria listarCategorias() {
        return PVP.listarCategorias();
    }

    public DataCategoria seleccionarCategoria(String nCat) throws Exception {
        return PVP.seleccionarCategoria(nCat, numero);
    }

    public List listarProductosCategoria() throws Exception {
        return PVP.listarProductosCategoria(numero).getList();
    }

    public DataProducto seleccionarProducto(Integer NRef) throws Exception {
        return PVP.seleccionarProducto(NRef, numero);
    }

    public List getProductos(List<Integer> prods) throws Exception {
        ArrayList<DataProducto> lista = new ArrayList<DataProducto>();
        for (Integer prod : prods) {
            lista.add(this.seleccionarProducto(prod));
            System.out.println(prod);
        }
        return lista;
    }

    public List listarCategoriaProductos() throws Exception {
        Coleccion col = PVP.listarCategoriaProductos(numero);
        return col.getList();
    }
}
