package Publisher;

import Extras.Coleccion;
import DataTypes.DataProducto;
import DataTypes.DataProveedor;
import Interfaces.Fabrica;
import Interfaces.IVerInfoProveedor;
import java.util.HashMap;
import java.util.Map;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlSeeAlso({DataProducto.class})
@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public class PVerInfoProveedor extends PControlador {

    private HashMap<Integer, IVerInfoProveedor> Instancias;
    private IVerInfoProveedor mia;

    public PVerInfoProveedor() {
        mia = Fabrica.getIVP();
        Instancias = new HashMap<Integer, IVerInfoProveedor>();
    }

    @WebMethod
    public int reservarSesion() {
        IVerInfoProveedor Nueva = Fabrica.getIVP();
        int Numero = Nueva.hashCode();
        Instancias.put(Numero, Nueva);
        return Numero;
    }

    @WebMethod
    public Coleccion listarProveedores() {
        IVerInfoProveedor Aux = Fabrica.getIVP();
        Coleccion col = new Coleccion();
        for (Map.Entry<String, DataProveedor> e : Aux.listarProveedores().entrySet()) {
            col.addElem(e.getValue());
        }
        return col;
    }

    @WebMethod
    public DataProveedor seleccionarProveedor(@WebParam(name = "nick") String nick, @WebParam(name = "NumeroSesion") int NumeroSesion) throws Exception {
        IVerInfoProveedor Aux = Instancias.get(NumeroSesion);
        return Aux.seleccionarProveedor(nick);
    }

    @WebMethod
    public Coleccion listarProductosProveedor(@WebParam(name = "NumeroSesion") int NumeroSesion) throws Exception {
        IVerInfoProveedor Aux = Instancias.get(NumeroSesion);
        Coleccion col = new Coleccion();
        for (DataProducto dP : Aux.listarProductosProveedor()) {
            col.addElem(dP);
        }
        return col;
    }

    @WebMethod
    public boolean isOnline(@WebParam(name = "Proveedor") String nick) {
        return mia.isOnline(nick);
    }
}
