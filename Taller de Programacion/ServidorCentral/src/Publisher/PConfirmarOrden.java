package Publisher;

import DataTypes.DataOrdenCompra;
import Interfaces.Fabrica;
import Interfaces.IVerCanOrden;
import java.util.HashMap;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public class PConfirmarOrden extends PControlador {

    private IVerCanOrden Mia = null;
    private HashMap<Integer, IVerCanOrden> Instancias;

    public PConfirmarOrden() {
        Mia = Fabrica.getIVCO();
        Instancias = new HashMap<Integer, IVerCanOrden>();
    }

    @WebMethod
    public int reservarSesion() {
        IVerCanOrden Nueva = Fabrica.getIVCO();
        int Numero = Nueva.hashCode();
        Instancias.put(Numero, Nueva);
        return Numero;
    }

    @WebMethod
    public DataOrdenCompra seleccionarOrden(@WebParam(name = "num") Integer num, @WebParam(name = "NumeroSesion") int NumeroSesion) throws Exception {
        IVerCanOrden Aux = Instancias.get(NumeroSesion);
        return Aux.seleccionarOrden(num);
    }

    @WebMethod
    public void confirmarOrden(@WebParam(name = "nick") String nick, @WebParam(name = "NumeroSesion") int NumeroSesion) throws Exception {
        IVerCanOrden Aux = Instancias.get(NumeroSesion);
        Aux.confirmarOrden(nick);
    }
}
