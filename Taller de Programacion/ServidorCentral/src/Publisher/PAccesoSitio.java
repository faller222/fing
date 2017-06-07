package Publisher;

import Controladores.CAcceso;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public class PAccesoSitio extends PControlador {

    public PAccesoSitio() {
    }

    @WebMethod
    public void regitrarAcceso(@WebParam(name = "Ip") String ip, @WebParam(name = "User") String user, @WebParam(name = "URL") String url, @WebParam(name = "Browser") String bro, @WebParam(name = "OS") String sis) {
        (new CAcceso()).nuevoAcceso(user, ip, url, bro, sis);
    }
}
