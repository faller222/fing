package Publisher;

import Extras.Coleccion;
import DataTypes.DataCategoria;
import DataTypes.DataProducto;
import DataTypes.DataProveedor;
import Interfaces.Fabrica;
import Interfaces.IAgregarProducto;
import java.util.HashMap;
import java.util.Map;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public class PAgregarProducto extends PControlador {

    private IAgregarProducto Mia = null;
    private HashMap<Integer, IAgregarProducto> Instancias;

    public PAgregarProducto() {
        Mia = Fabrica.getIAP();
        Instancias = new HashMap<Integer, IAgregarProducto>();
    }

    @WebMethod
    public int reservarSesion() {
        IAgregarProducto Nueva = Fabrica.getIAP();
        int Numero = Nueva.hashCode();
        Instancias.put(Numero, Nueva);
        return Numero;
    }

    @WebMethod
    public Coleccion listarProveedores() {
        Coleccion col = new Coleccion();
        for (Map.Entry<String, DataProveedor> e : Mia.listarProveedores().entrySet()) {
            col.addElem(e.getValue());
        }
        return col;
    }

    @WebMethod
    public void ingresarDataProducto(@WebParam(name = "dataP") DataProducto dataP, @WebParam(name = "NumeroSesion") int NumeroSesion) throws Exception {
        IAgregarProducto Aux = Instancias.get(NumeroSesion);
        Aux.ingresarDataProducto(dataP);
    }

    @WebMethod
    public DataCategoria listarCategorias() {
        return Mia.listarCategorias();
    }

    @WebMethod
    public void agregarCategoriaAProducto(@WebParam(name = "nomCat") String nomCat, @WebParam(name = "NumeroSesion") int NumeroSesion) throws Exception {
        IAgregarProducto Aux = Instancias.get(NumeroSesion);
        Aux.agregarCategoriaAProducto(nomCat);
    }

    @WebMethod
    public void agregarImagenAProducto(@WebParam(name = "Img") byte[] Img, @WebParam(name = "Pos") int Pos, @WebParam(name = "NumeroSesion") int NumeroSesion) throws Exception {
        IAgregarProducto Aux = Instancias.get(NumeroSesion);
        Aux.agregarImagenAProducto(Img, Pos);
    }

    @WebMethod
    public void altaProducto(@WebParam(name = "NumeroSesion") int NumeroSesion) throws Exception {//no agrego Categoria
        IAgregarProducto Aux = Instancias.get(NumeroSesion);
        Aux.altaProducto();
    }
}
