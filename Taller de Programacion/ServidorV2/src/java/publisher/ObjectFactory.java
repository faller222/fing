
package publisher;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the publisher package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Exception_QNAME = new QName("http://Publisher/", "Exception");
    private final static QName _DataOrdenCompra_QNAME = new QName("http://Publisher/", "dataOrdenCompra");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: publisher
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Exception }
     * 
     */
    public Exception createException() {
        return new Exception();
    }

    /**
     * Create an instance of {@link DataOrdenCompra }
     * 
     */
    public DataOrdenCompra createDataOrdenCompra() {
        return new DataOrdenCompra();
    }

    /**
     * Create an instance of {@link DataLinea }
     * 
     */
    public DataLinea createDataLinea() {
        return new DataLinea();
    }

    /**
     * Create an instance of {@link DataEstadistica }
     * 
     */
    public DataEstadistica createDataEstadistica() {
        return new DataEstadistica();
    }

    /**
     * Create an instance of {@link DataPuntaje }
     * 
     */
    public DataPuntaje createDataPuntaje() {
        return new DataPuntaje();
    }

    /**
     * Create an instance of {@link DataProveedor }
     * 
     */
    public DataProveedor createDataProveedor() {
        return new DataProveedor();
    }

    /**
     * Create an instance of {@link DataReclamo }
     * 
     */
    public DataReclamo createDataReclamo() {
        return new DataReclamo();
    }

    /**
     * Create an instance of {@link DataEstado }
     * 
     */
    public DataEstado createDataEstado() {
        return new DataEstado();
    }

    /**
     * Create an instance of {@link DataCliente }
     * 
     */
    public DataCliente createDataCliente() {
        return new DataCliente();
    }

    /**
     * Create an instance of {@link DataProducto }
     * 
     */
    public DataProducto createDataProducto() {
        return new DataProducto();
    }

    /**
     * Create an instance of {@link DataCategoria }
     * 
     */
    public DataCategoria createDataCategoria() {
        return new DataCategoria();
    }

    /**
     * Create an instance of {@link DataComentario }
     * 
     */
    public DataComentario createDataComentario() {
        return new DataComentario();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Exception }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Publisher/", name = "Exception")
    public JAXBElement<Exception> createException(Exception value) {
        return new JAXBElement<Exception>(_Exception_QNAME, Exception.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DataOrdenCompra }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Publisher/", name = "dataOrdenCompra")
    public JAXBElement<DataOrdenCompra> createDataOrdenCompra(DataOrdenCompra value) {
        return new JAXBElement<DataOrdenCompra>(_DataOrdenCompra_QNAME, DataOrdenCompra.class, null, value);
    }

}
