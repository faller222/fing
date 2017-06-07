
package publisher;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for dataCliente complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="dataCliente">
 *   &lt;complexContent>
 *     &lt;extension base="{http://Publisher/}dataUsuario">
 *       &lt;sequence>
 *         &lt;element name="notiOrden" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="notiProd" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="notiProve" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="notiRec" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="ordenes" type="{http://www.w3.org/2001/XMLSchema}int" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dataCliente", propOrder = {
    "notiOrden",
    "notiProd",
    "notiProve",
    "notiRec",
    "ordenes"
})
public class DataCliente
    extends DataUsuario
{

    protected boolean notiOrden;
    protected boolean notiProd;
    protected boolean notiProve;
    protected boolean notiRec;
    @XmlElement(nillable = true)
    protected List<Integer> ordenes;

    /**
     * Gets the value of the notiOrden property.
     * 
     */
    public boolean isNotiOrden() {
        return notiOrden;
    }

    /**
     * Sets the value of the notiOrden property.
     * 
     */
    public void setNotiOrden(boolean value) {
        this.notiOrden = value;
    }

    /**
     * Gets the value of the notiProd property.
     * 
     */
    public boolean isNotiProd() {
        return notiProd;
    }

    /**
     * Sets the value of the notiProd property.
     * 
     */
    public void setNotiProd(boolean value) {
        this.notiProd = value;
    }

    /**
     * Gets the value of the notiProve property.
     * 
     */
    public boolean isNotiProve() {
        return notiProve;
    }

    /**
     * Sets the value of the notiProve property.
     * 
     */
    public void setNotiProve(boolean value) {
        this.notiProve = value;
    }

    /**
     * Gets the value of the notiRec property.
     * 
     */
    public boolean isNotiRec() {
        return notiRec;
    }

    /**
     * Sets the value of the notiRec property.
     * 
     */
    public void setNotiRec(boolean value) {
        this.notiRec = value;
    }

    /**
     * Gets the value of the ordenes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ordenes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOrdenes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Integer }
     * 
     * 
     */
    public List<Integer> getOrdenes() {
        if (ordenes == null) {
            ordenes = new ArrayList<Integer>();
        }
        return this.ordenes;
    }

}
