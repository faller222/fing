package Publisher;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.Endpoint;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public abstract class PControlador {

    private Endpoint eP = null;

    public PControlador() {
    }

    @WebMethod(exclude = true)
    public void Publicar(String Dominio, String Puerto, String NService) throws Exception {
        String Dire = Dominio + ":" + Puerto + "/" + NService;
        eP = Endpoint.publish(Dire, this);
    }

    public void stop() {
        if (eP != null) {
            eP.stop();
        }
    }

    @WebMethod(exclude = true)
    public Endpoint getEndpoint() {
        return eP;
    }
}
