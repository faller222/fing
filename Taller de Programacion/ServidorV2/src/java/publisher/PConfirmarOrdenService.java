
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
@WebServiceClient(name = "PConfirmarOrdenService", targetNamespace = "http://Publisher/", wsdlLocation = "http://localhost:20016/PCO?wsdl")
public class PConfirmarOrdenService
    extends Service
{

    private final static URL PCONFIRMARORDENSERVICE_WSDL_LOCATION;
    private final static WebServiceException PCONFIRMARORDENSERVICE_EXCEPTION;
    private final static QName PCONFIRMARORDENSERVICE_QNAME = new QName("http://Publisher/", "PConfirmarOrdenService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://localhost:20016/PCO?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        PCONFIRMARORDENSERVICE_WSDL_LOCATION = url;
        PCONFIRMARORDENSERVICE_EXCEPTION = e;
    }

    public PConfirmarOrdenService() {
        super(__getWsdlLocation(), PCONFIRMARORDENSERVICE_QNAME);
    }

    public PConfirmarOrdenService(WebServiceFeature... features) {
        super(__getWsdlLocation(), PCONFIRMARORDENSERVICE_QNAME, features);
    }

    public PConfirmarOrdenService(URL wsdlLocation) {
        super(wsdlLocation, PCONFIRMARORDENSERVICE_QNAME);
    }

    public PConfirmarOrdenService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, PCONFIRMARORDENSERVICE_QNAME, features);
    }

    public PConfirmarOrdenService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public PConfirmarOrdenService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns PConfirmarOrden
     */
    @WebEndpoint(name = "PConfirmarOrdenPort")
    public PConfirmarOrden getPConfirmarOrdenPort() {
        return super.getPort(new QName("http://Publisher/", "PConfirmarOrdenPort"), PConfirmarOrden.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns PConfirmarOrden
     */
    @WebEndpoint(name = "PConfirmarOrdenPort")
    public PConfirmarOrden getPConfirmarOrdenPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://Publisher/", "PConfirmarOrdenPort"), PConfirmarOrden.class, features);
    }

    private static URL __getWsdlLocation() {
        if (PCONFIRMARORDENSERVICE_EXCEPTION!= null) {
            throw PCONFIRMARORDENSERVICE_EXCEPTION;
        }
        return PCONFIRMARORDENSERVICE_WSDL_LOCATION;
    }

}
