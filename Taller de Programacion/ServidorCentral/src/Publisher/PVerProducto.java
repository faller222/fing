package Publisher;

import Extras.Coleccion;
import DataTypes.DataCategoria;
import DataTypes.DataProducto;
import Interfaces.Fabrica;
import Interfaces.IVerModProducto;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.Endpoint;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public class PVerProducto extends PControlador {

    private IVerModProducto Mia = null;
    private HashMap<Integer, IVerModProducto> Instancias;

    public PVerProducto() {
        Mia = Fabrica.getIVMP();
        Instancias = new HashMap<Integer, IVerModProducto>();
    }

    @WebMethod
    public int reservarSesion() {
        IVerModProducto Nueva = Fabrica.getIVMP();
        int Numero = Nueva.hashCode();
        Instancias.put(Numero, Nueva);
        return Numero;
    }

    @WebMethod
    public Coleccion buscarProd(@WebParam(name = "Clave") String Clave, @WebParam(name = "Pag") Integer Pag, @WebParam(name = "Tipo") int Tipo) {//no importa son 8
        List<DataProducto> lista = Mia.buscarProd(Clave, Pag, Tipo);
        Coleccion Col = new Coleccion();
        for (DataProducto dP : lista) {
            Col.addElem(dP);
        }
        return Col;
    }

    @WebMethod
    public DataCategoria listarCategorias() {
        return Mia.listarCategorias();
    }

    @WebMethod
    public DataCategoria seleccionarCategoria(@WebParam(name = "nCat") String nCat, @WebParam(name = "NumeroInstancia") int NumeroInstancia) throws Exception {//no exsite
        IVerModProducto Aux = Instancias.get(NumeroInstancia);
        return Aux.seleccionarCategoria(nCat);
    }

    @WebMethod
    public Coleccion listarProductosCategoria(@WebParam(name = "NumeroInstancia") int NumeroInstancia) throws Exception {// no selecciono categoria o es Compuesta
        IVerModProducto Aux = Instancias.get(NumeroInstancia);
        Coleccion col = new Coleccion();
        for (Map.Entry<Integer, DataProducto> e : Aux.listarProductosCategoria().entrySet()) {
            col.addElem(e.getValue());
        }
        return col;
    }

    @WebMethod
    public DataProducto seleccionarProducto(@WebParam(name = "NRef") Integer NRef, @WebParam(name = "NumeroInstancia") int NumeroInstancia) throws Exception {// no Existe Producto
        IVerModProducto Aux = Instancias.get(NumeroInstancia);
        return Aux.seleccionarProducto(NRef);
    }

    @WebMethod
    public Coleccion listarCategoriaProductos(@WebParam(name = "NumeroInstancia") int NumeroInstancia) throws Exception {// no selecciono Producto
        IVerModProducto Aux = Instancias.get(NumeroInstancia);
        Coleccion col = new Coleccion();
        for (Map.Entry<String, DataCategoria> e : Aux.listarCategoriaProductos().entrySet()) {
            col.addElem(e.getValue());
        }
        return col;
    }
}
