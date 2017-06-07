package Publisher;

import DataTypes.DataCliente;
import DataTypes.DataProducto;
import Extras.Coleccion;
import Interfaces.Fabrica;
import Interfaces.IPuntaje;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlSeeAlso({DataProducto.class})
@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public class PPuntaje extends PControlador {

    private HashMap<Integer, IPuntaje> Instancias;

    public PPuntaje() {

        Instancias = new HashMap();
    }

    @WebMethod
    public int reservarSesion() {
        IPuntaje Nueva = Fabrica.getIP();
        int Numero = Nueva.hashCode();
        Instancias.put(Numero, Nueva);
        return Numero;
    }
    
    @WebMethod
    public DataCliente seleccionarCliente(@WebParam(name = "nick") String nick, @WebParam(name = "NumeroSesion") int NumeroSesion) throws Exception {
        IPuntaje Aux = Instancias.get(NumeroSesion);
        return Aux.seleccionarCliente(nick);
    }
    
    @WebMethod
    public Coleccion listarProductosComprados(@WebParam(name = "NumeroSesion") int NumeroSesion) {
        IPuntaje Aux = Instancias.get(NumeroSesion);
        Coleccion col = new Coleccion();
        for (Map.Entry<Integer, DataProducto> e : Aux.listarProductosComprados().entrySet()) {
            col.addElem(e.getValue());
        }
        return col;
    }
    
    @WebMethod
    public void seleccionarProducto(@WebParam(name = "NumeroSesion") int NumeroSesion, @WebParam(name = "nref") int nref){
        IPuntaje Aux = Instancias.get(NumeroSesion);
        Aux.seleccionarProducto(nref);
    }
    
    @WebMethod
    public void puntuarProducto(@WebParam(name = "NumeroSesion") int NumeroSesion, @WebParam(name = "puntaje") int puntaje)  throws Exception{
        IPuntaje Aux = Instancias.get(NumeroSesion);
        Aux.puntuarProducto(puntaje);
    }

    @WebMethod
    public Coleccion noPuntuados(@WebParam(name = "NumeroSesion") int NumeroSesion) {
        IPuntaje Aux = Instancias.get(NumeroSesion);
        Coleccion col = new Coleccion();
        List<Integer> lista = Aux.noPuntuados();
        for (Integer nref : lista) {
            col.addElem(nref);
        }
        return col;
    }
}