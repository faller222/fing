package Publisher;

import DataTypes.DataUsuario;
import Interfaces.Fabrica;
import Interfaces.IAltaUsuario;
import java.util.HashMap;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public class PAltaUsuario extends PControlador {

    private IAltaUsuario Mia = null;
    private HashMap<Integer, IAltaUsuario> Instancias;

    public PAltaUsuario() {
        Mia = Fabrica.getIAU();
        Instancias = new HashMap<Integer, IAltaUsuario>();
    }

    @WebMethod
    public boolean verificarMail(@WebParam(name = "mail") String mail) {//True si esta disponible
        return Mia.verificarMail(mail);
    }

    @WebMethod
    public boolean verificarNickname(@WebParam(name = "Nickname") String Nickname) {//True si esta disponible
        return Mia.verificarNickname(Nickname);
    }

    @WebMethod
    public int ingresarDataUsuario(@WebParam(name = "dataU") DataUsuario dataU) throws Exception {
        IAltaUsuario Nueva = Fabrica.getIAU();
        int Numero = Nueva.hashCode();
        Instancias.put(Numero, Nueva);
        Nueva.ingresarDataUsuario(dataU);
        return Numero;
    }

    @WebMethod
    public void altaUsuario(@WebParam(name = "NumeroInstancia") int Instancia) throws Exception {
        IAltaUsuario Aux = Instancias.remove(Instancia);
        Aux.altaUsuario();
    }
}
