package Publisher;

import DataTypes.DataCliente;
import DataTypes.DataProducto;
import DataTypes.DataProveedor;
import DataTypes.DataReclamo;
import Extras.Coleccion;
import Interfaces.Fabrica;
import Interfaces.IReclamo;
import java.util.HashMap;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public class PReclamo extends PControlador {
    
    private HashMap<Integer, IReclamo> Instancias;
    
    public PReclamo() {
        Instancias = new HashMap<>();
    }
    
    @WebMethod
    public int reservarSesion() {
        IReclamo Nueva = Fabrica.getIR();
        int Numero = Nueva.hashCode();
        Instancias.put(Numero, Nueva);
        return Numero;
    }
    
    @WebMethod
    public DataCliente seleccionarCliente(@WebParam(name = "nick") String nick, @WebParam(name = "NumeroSesion") int NumeroSesion) throws Exception {
        IReclamo Aux = Instancias.get(NumeroSesion);
        return Aux.seleccionarCliente(nick);
    }
    
    @WebMethod
    public DataProducto seleccionarProducto(@WebParam(name = "NRef") Integer NRef, @WebParam(name = "NumeroSesion") int NumeroSesion) throws Exception {
        IReclamo Aux = Instancias.get(NumeroSesion);
        return Aux.seleccionarProducto(NRef);
    }
    
    @WebMethod
    public void altaReclamo(@WebParam(name = "texto") String texto, @WebParam(name = "NumeroSesion") int NumeroSesion) {
        IReclamo Aux = Instancias.get(NumeroSesion);
        Aux.altaReclamo(texto);
    }
    
    @WebMethod
    public DataProveedor seleccionarProveedor(@WebParam(name = "nick") String nick, @WebParam(name = "NumeroSesion") int NumeroSesion) throws Exception {
        IReclamo Aux = Instancias.get(NumeroSesion);
        return Aux.seleccionarProveedor(nick);
    }
    
    @WebMethod
    public Coleccion listarReclamos(@WebParam(name = "NumeroSesion") int NumeroSesion) {
        IReclamo Aux = Instancias.get(NumeroSesion);
        List<DataReclamo> list = Aux.listarReclamos();
        Coleccion col = new Coleccion();
        for (DataReclamo Dr : list) {
            col.addElem(Dr);
        }
        return col;
    }
    
    @WebMethod
    public void respRec(@WebParam(name = "NumeroSesion") int NumeroSesion, @WebParam(name = "texto") String texto, @WebParam(name = "NRec") Integer NRec, @WebParam(name = "Prod") Integer idProd) {
        IReclamo Aux = Instancias.get(NumeroSesion);
        Aux.respRec(texto, NRec, idProd);
    }
}
