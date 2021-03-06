
package publisher;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.FaultAction;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebService(name = "PAgregarProducto", targetNamespace = "http://Publisher/")
@SOAPBinding(style = SOAPBinding.Style.RPC)
@XmlSeeAlso({
    ObjectFactory.class
})
public interface PAgregarProducto {


    /**
     * 
     * @return
     *     returns publisher.DataCategoria
     */
    @WebMethod
    @WebResult(partName = "return")
    @Action(input = "http://Publisher/PAgregarProducto/listarCategoriasRequest", output = "http://Publisher/PAgregarProducto/listarCategoriasResponse")
    public DataCategoria listarCategorias();

    /**
     * 
     * @return
     *     returns publisher.Coleccion
     */
    @WebMethod
    @WebResult(partName = "return")
    @Action(input = "http://Publisher/PAgregarProducto/listarProveedoresRequest", output = "http://Publisher/PAgregarProducto/listarProveedoresResponse")
    public Coleccion listarProveedores();

    /**
     * 
     * @param numeroSesion
     * @param dataP
     * @throws Exception_Exception
     */
    @WebMethod
    @Action(input = "http://Publisher/PAgregarProducto/ingresarDataProductoRequest", output = "http://Publisher/PAgregarProducto/ingresarDataProductoResponse", fault = {
        @FaultAction(className = Exception_Exception.class, value = "http://Publisher/PAgregarProducto/ingresarDataProducto/Fault/Exception")
    })
    public void ingresarDataProducto(
        @WebParam(name = "dataP", partName = "dataP")
        DataProducto dataP,
        @WebParam(name = "NumeroSesion", partName = "NumeroSesion")
        int numeroSesion)
        throws Exception_Exception
    ;

    /**
     * 
     * @param numeroSesion
     * @param nomCat
     * @throws Exception_Exception
     */
    @WebMethod
    @Action(input = "http://Publisher/PAgregarProducto/agregarCategoriaAProductoRequest", output = "http://Publisher/PAgregarProducto/agregarCategoriaAProductoResponse", fault = {
        @FaultAction(className = Exception_Exception.class, value = "http://Publisher/PAgregarProducto/agregarCategoriaAProducto/Fault/Exception")
    })
    public void agregarCategoriaAProducto(
        @WebParam(name = "nomCat", partName = "nomCat")
        String nomCat,
        @WebParam(name = "NumeroSesion", partName = "NumeroSesion")
        int numeroSesion)
        throws Exception_Exception
    ;

    /**
     * 
     * @param numeroSesion
     * @param img
     * @param pos
     * @throws Exception_Exception
     */
    @WebMethod
    @Action(input = "http://Publisher/PAgregarProducto/agregarImagenAProductoRequest", output = "http://Publisher/PAgregarProducto/agregarImagenAProductoResponse", fault = {
        @FaultAction(className = Exception_Exception.class, value = "http://Publisher/PAgregarProducto/agregarImagenAProducto/Fault/Exception")
    })
    public void agregarImagenAProducto(
        @WebParam(name = "Img", partName = "Img")
        byte[] img,
        @WebParam(name = "Pos", partName = "Pos")
        int pos,
        @WebParam(name = "NumeroSesion", partName = "NumeroSesion")
        int numeroSesion)
        throws Exception_Exception
    ;

    /**
     * 
     * @param numeroSesion
     * @throws Exception_Exception
     */
    @WebMethod
    @Action(input = "http://Publisher/PAgregarProducto/altaProductoRequest", output = "http://Publisher/PAgregarProducto/altaProductoResponse", fault = {
        @FaultAction(className = Exception_Exception.class, value = "http://Publisher/PAgregarProducto/altaProducto/Fault/Exception")
    })
    public void altaProducto(
        @WebParam(name = "NumeroSesion", partName = "NumeroSesion")
        int numeroSesion)
        throws Exception_Exception
    ;

    /**
     * 
     * @return
     *     returns int
     */
    @WebMethod
    @WebResult(partName = "return")
    @Action(input = "http://Publisher/PAgregarProducto/reservarSesionRequest", output = "http://Publisher/PAgregarProducto/reservarSesionResponse")
    public int reservarSesion();

    /**
     * 
     */
    @WebMethod
    @Action(input = "http://Publisher/PAgregarProducto/stopRequest", output = "http://Publisher/PAgregarProducto/stopResponse")
    public void stop();

}
