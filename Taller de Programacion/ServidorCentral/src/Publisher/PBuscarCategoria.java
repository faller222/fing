package Publisher;

import DataTypes.DataCategoria;
import Interfaces.Fabrica;
import Interfaces.IAgregarCategoria;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public class PBuscarCategoria extends PControlador {

    private IAgregarCategoria Mia = null;

    public PBuscarCategoria() {
        Mia = Fabrica.getIAC();
    }

    @WebMethod
    public DataCategoria listarCategorias() {
        return Mia.listarCategorias();
    }

    @WebMethod
    public DataCategoria buscarCategoria(@WebParam(name = "nCat") String nCat) {
        return Mia.buscarCategoria(nCat);
    }
}
