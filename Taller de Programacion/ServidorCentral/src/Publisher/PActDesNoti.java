/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Publisher;

import DataTypes.DataCliente;
import DataTypes.DataOrdenCompra;
import Interfaces.Fabrica;
import Interfaces.IActDesNoti;
import java.util.HashMap;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public class PActDesNoti extends PControlador {

    private IActDesNoti Mia = null;
    private HashMap<Integer, IActDesNoti> Instancias;

    public PActDesNoti() {
        Mia = Fabrica.getIAD();
        Instancias = new HashMap<Integer, IActDesNoti>();
    }

    @WebMethod
    public int reservarSesion() {
        IActDesNoti Nueva = Fabrica.getIAD();
        int Numero = Nueva.hashCode();
        Instancias.put(Numero, Nueva);
        return Numero;
    }

    @WebMethod
    public DataCliente seleccionarCliente(@WebParam(name = "nick") String nick, @WebParam(name = "NumeroSesion") int NumeroSesion) throws Exception {
        IActDesNoti Aux = Instancias.get(NumeroSesion);
        return Aux.seleccionarCliente(nick);
    }

    @WebMethod
    public DataOrdenCompra seleccionarOrdenCompra(@WebParam(name = "Noc") Integer Noc, @WebParam(name = "NumeroSesion") int NumeroSesion) throws Exception {
        IActDesNoti Aux = Instancias.get(NumeroSesion);
        return Aux.seleccionarOrdenCompra(Noc);
    }

    @WebMethod
    public void ActivarNotiOrdenes(@WebParam(name = "NumeroSesion") int NumeroSesion) {
        IActDesNoti Aux = Instancias.get(NumeroSesion);
        Aux.ActivarNotiOrdenes();
    }

    @WebMethod
    public void ActivarNotiProveedor(@WebParam(name = "NumeroSesion") int NumeroSesion) {
        IActDesNoti Aux = Instancias.get(NumeroSesion);
        Aux.ActivarNotiProveedor();
    }

    @WebMethod
    public void ActivarNotiProducto(@WebParam(name = "NumeroSesion") int NumeroSesion) {
        IActDesNoti Aux = Instancias.get(NumeroSesion);
        Aux.ActivarNotiProducto();
    }

    @WebMethod
    public void ActivarNotiReclamo(@WebParam(name = "NumeroSesion") int NumeroSesion) {
        IActDesNoti Aux = Instancias.get(NumeroSesion);
        Aux.ActivarNotiReclamo();
    }

    @WebMethod
    public void DesactivarNotiReclamo(@WebParam(name = "NumeroSesion") int NumeroSesion) {
        IActDesNoti Aux = Instancias.get(NumeroSesion);
        Aux.DesactivarNotiReclamo();
    }

    @WebMethod
    public void DesactivarNotiOrdenes(@WebParam(name = "NumeroSesion") int NumeroSesion) {
        IActDesNoti Aux = Instancias.get(NumeroSesion);
        Aux.DesactivarNotiOrdenes();
    }

    @WebMethod
    public void DesactivarNotiProveedor(@WebParam(name = "NumeroSesion") int NumeroSesion) {
        IActDesNoti Aux = Instancias.get(NumeroSesion);
        Aux.DesactivarNotiProveedor();
    }

    @WebMethod
    public void DesactivarNotiProducto(@WebParam(name = "NumeroSesion") int NumeroSesion) {
        IActDesNoti Aux = Instancias.get(NumeroSesion);
        Aux.DesactivarNotiProducto();
    }
}
