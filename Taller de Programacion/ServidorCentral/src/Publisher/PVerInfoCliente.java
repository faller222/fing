package Publisher;

import Extras.Coleccion;
import DataTypes.DataCliente;
import DataTypes.DataOrdenCompra;
import Interfaces.Fabrica;
import Interfaces.IVerCanOrden;
import Interfaces.IVerInfoCliente;
import java.util.HashMap;
import java.util.Map;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlSeeAlso({DataOrdenCompra.class})
@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public class PVerInfoCliente extends PControlador {

    private HashMap<Integer, IVerInfoCliente> Instancias;

    public PVerInfoCliente() {

        Instancias = new HashMap<Integer, IVerInfoCliente>();
    }

    @WebMethod
    public int reservarSesion() {
        IVerInfoCliente Nueva = Fabrica.getIVC();
        int Numero = Nueva.hashCode();
        Instancias.put(Numero, Nueva);
        return Numero;
    }

    @WebMethod
    public Coleccion listarClientes() {
        IVerInfoCliente Aux = Fabrica.getIVC();
        Coleccion col = new Coleccion();
        for (Map.Entry<String, DataCliente> e : Aux.listarClientes().entrySet()) {
            col.addElem(e.getValue());
        }
        return col;
    }

    @WebMethod
    public DataCliente seleccionarCliente(@WebParam(name = "nick") String nick, @WebParam(name = "NumeroSesion") int NumeroSesion) throws Exception {
        IVerInfoCliente Aux = Instancias.get(NumeroSesion);
        return Aux.seleccionarCliente(nick);
    }

    @WebMethod
    public DataOrdenCompra buscarOrden(@WebParam(name = "NumeroOrden") int NumeroOrden) throws Exception {
        IVerCanOrden Aux = Fabrica.getIVCO();
        return Aux.seleccionarOrden(NumeroOrden);
    }

    @WebMethod
    public Coleccion listarOrdenesCliente(@WebParam(name = "NumeroSesion") int NumeroSesion) throws Exception {//retorno lista de Numeros nada mas
        IVerInfoCliente Aux = Instancias.get(NumeroSesion);
        Coleccion col = new Coleccion();
        for (Map.Entry<Integer, DataOrdenCompra> e : Aux.listarOrdenesCliente().entrySet()) {
            col.addElem(e.getValue().getNumero());
        }
        return col;
    }


}
