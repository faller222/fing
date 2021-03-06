
package publisher;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "PAltaUsuarioService", targetNamespace = "http://Publisher/", wsdlLocation = "http://localhost:20016/PAU?wsdl")
public class PAltaUsuarioService
    extends Service
{

    private final static URL PALTAUSUARIOSERVICE_WSDL_LOCATION;
    private final static WebServiceException PALTAUSUARIOSERVICE_EXCEPTION;
    private final static QName PALTAUSUARIOSERVICE_QNAME = new QName("http://Publisher/", "PAltaUsuarioService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://localhost:20016/PAU?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        PALTAUSUARIOSERVICE_WSDL_LOCATION = url;
        PALTAUSUARIOSERVICE_EXCEPTION = e;
    }

    public PAltaUsuarioService() {
        super(__getWsdlLocation(), PALTAUSUARIOSERVICE_QNAME);
    }

    public PAltaUsuarioService(WebServiceFeature... features) {
        super(__getWsdlLocation(), PALTAUSUARIOSERVICE_QNAME, features);
    }

    public PAltaUsuarioService(URL wsdlLocation) {
        super(wsdlLocation, PALTAUSUARIOSERVICE_QNAME);
    }

    public PAltaUsuarioService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, PALTAUSUARIOSERVICE_QNAME, features);
    }

    public PAltaUsuarioService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public PAltaUsuarioService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns PAltaUsuario
     */
    @WebEndpoint(name = "PAltaUsuarioPort")
    public PAltaUsuario getPAltaUsuarioPort() {
        return super.getPort(new QName("http://Publisher/", "PAltaUsuarioPort"), PAltaUsuario.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns PAltaUsuario
     */
    @WebEndpoint(name = "PAltaUsuarioPort")
    public PAltaUsuario getPAltaUsuarioPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://Publisher/", "PAltaUsuarioPort"), PAltaUsuario.class, features);
    }

    private static URL __getWsdlLocation() {
        if (PALTAUSUARIOSERVICE_EXCEPTION!= null) {
            throw PALTAUSUARIOSERVICE_EXCEPTION;
        }
        return PALTAUSUARIOSERVICE_WSDL_LOCATION;
    }

}
