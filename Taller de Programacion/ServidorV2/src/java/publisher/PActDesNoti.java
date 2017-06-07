
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
@WebService(name = "PActDesNoti", targetNamespace = "http://Publisher/")
@SOAPBinding(style = SOAPBinding.Style.RPC)
@XmlSeeAlso({
    ObjectFactory.class
})
public interface PActDesNoti {


    /**
     * 
     * @param nick
     * @param numeroSesion
     * @return
     *     returns publisher.DataCliente
     * @throws Exception_Exception
     */
    @WebMethod
    @WebResult(partName = "return")
    @Action(input = "http://Publisher/PActDesNoti/seleccionarClienteRequest", output = "http://Publisher/PActDesNoti/seleccionarClienteResponse", fault = {
        @FaultAction(className = Exception_Exception.class, value = "http://Publisher/PActDesNoti/seleccionarCliente/Fault/Exception")
    })
    public DataCliente seleccionarCliente(
        @WebParam(name = "nick", partName = "nick")
        String nick,
        @WebParam(name = "NumeroSesion", partName = "NumeroSesion")
        int numeroSesion)
        throws Exception_Exception
    ;

    /**
     * 
     * @param noc
     * @param numeroSesion
     * @return
     *     returns publisher.DataOrdenCompra
     * @throws Exception_Exception
     */
    @WebMethod
    @WebResult(partName = "return")
    @Action(input = "http://Publisher/PActDesNoti/seleccionarOrdenCompraRequest", output = "http://Publisher/PActDesNoti/seleccionarOrdenCompraResponse", fault = {
        @FaultAction(className = Exception_Exception.class, value = "http://Publisher/PActDesNoti/seleccionarOrdenCompra/Fault/Exception")
    })
    public DataOrdenCompra seleccionarOrdenCompra(
        @WebParam(name = "Noc", partName = "Noc")
        int noc,
        @WebParam(name = "NumeroSesion", partName = "NumeroSesion")
        int numeroSesion)
        throws Exception_Exception
    ;

    /**
     * 
     * @param numeroSesion
     */
    @WebMethod(operationName = "ActivarNotiOrdenes")
    @Action(input = "http://Publisher/PActDesNoti/ActivarNotiOrdenesRequest", output = "http://Publisher/PActDesNoti/ActivarNotiOrdenesResponse")
    public void activarNotiOrdenes(
        @WebParam(name = "NumeroSesion", partName = "NumeroSesion")
        int numeroSesion);

    /**
     * 
     * @param numeroSesion
     */
    @WebMethod(operationName = "ActivarNotiProveedor")
    @Action(input = "http://Publisher/PActDesNoti/ActivarNotiProveedorRequest", output = "http://Publisher/PActDesNoti/ActivarNotiProveedorResponse")
    public void activarNotiProveedor(
        @WebParam(name = "NumeroSesion", partName = "NumeroSesion")
        int numeroSesion);

    /**
     * 
     * @param numeroSesion
     */
    @WebMethod(operationName = "ActivarNotiProducto")
    @Action(input = "http://Publisher/PActDesNoti/ActivarNotiProductoRequest", output = "http://Publisher/PActDesNoti/ActivarNotiProductoResponse")
    public void activarNotiProducto(
        @WebParam(name = "NumeroSesion", partName = "NumeroSesion")
        int numeroSesion);

    /**
     * 
     * @param numeroSesion
     */
    @WebMethod(operationName = "ActivarNotiReclamo")
    @Action(input = "http://Publisher/PActDesNoti/ActivarNotiReclamoRequest", output = "http://Publisher/PActDesNoti/ActivarNotiReclamoResponse")
    public void activarNotiReclamo(
        @WebParam(name = "NumeroSesion", partName = "NumeroSesion")
        int numeroSesion);

    /**
     * 
     * @param numeroSesion
     */
    @WebMethod(operationName = "DesactivarNotiReclamo")
    @Action(input = "http://Publisher/PActDesNoti/DesactivarNotiReclamoRequest", output = "http://Publisher/PActDesNoti/DesactivarNotiReclamoResponse")
    public void desactivarNotiReclamo(
        @WebParam(name = "NumeroSesion", partName = "NumeroSesion")
        int numeroSesion);

    /**
     * 
     * @param numeroSesion
     */
    @WebMethod(operationName = "DesactivarNotiOrdenes")
    @Action(input = "http://Publisher/PActDesNoti/DesactivarNotiOrdenesRequest", output = "http://Publisher/PActDesNoti/DesactivarNotiOrdenesResponse")
    public void desactivarNotiOrdenes(
        @WebParam(name = "NumeroSesion", partName = "NumeroSesion")
        int numeroSesion);

    /**
     * 
     * @param numeroSesion
     */
    @WebMethod(operationName = "DesactivarNotiProveedor")
    @Action(input = "http://Publisher/PActDesNoti/DesactivarNotiProveedorRequest", output = "http://Publisher/PActDesNoti/DesactivarNotiProveedorResponse")
    public void desactivarNotiProveedor(
        @WebParam(name = "NumeroSesion", partName = "NumeroSesion")
        int numeroSesion);

    /**
     * 
     * @param numeroSesion
     */
    @WebMethod(operationName = "DesactivarNotiProducto")
    @Action(input = "http://Publisher/PActDesNoti/DesactivarNotiProductoRequest", output = "http://Publisher/PActDesNoti/DesactivarNotiProductoResponse")
    public void desactivarNotiProducto(
        @WebParam(name = "NumeroSesion", partName = "NumeroSesion")
        int numeroSesion);

    /**
     * 
     * @return
     *     returns int
     */
    @WebMethod
    @WebResult(partName = "return")
    @Action(input = "http://Publisher/PActDesNoti/reservarSesionRequest", output = "http://Publisher/PActDesNoti/reservarSesionResponse")
    public int reservarSesion();

    /**
     * 
     */
    @WebMethod
    @Action(input = "http://Publisher/PActDesNoti/stopRequest", output = "http://Publisher/PActDesNoti/stopResponse")
    public void stop();

}