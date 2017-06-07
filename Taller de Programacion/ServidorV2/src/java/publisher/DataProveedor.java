
package publisher;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for dataProveedor complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="dataProveedor">
 *   &lt;complexContent>
 *     &lt;extension base="{http://Publisher/}dataUsuario">
 *       &lt;sequence>
 *         &lt;element name="compania" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="productos" type="{http://www.w3.org/2001/XMLSchema}int" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="sitioWeb" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dataProveedor", propOrder = {
    "compania",
    "productos",
    "sitioWeb"
})
public class DataProveedor
    extends DataUsuario
{

    protected String compania;
    @XmlElement(nillable = true)
    protected List<Integer> productos;
    protected String sitioWeb;

    /**
     * Gets the value of the compania property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompania() {
        return compania;
    }

    /**
     * Sets the value of the compania property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompania(String value) {
        this.compania = value;
    }

    /**
     * Gets the value of the productos property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the productos property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProductos().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Integer }
     * 
     * 
     */
    public List<Integer> getProductos() {
        if (productos == null) {
            productos = new ArrayList<Integer>();
        }
        return this.productos;
    }

    /**
     * Gets the value of the sitioWeb property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSitioWeb() {
        return sitioWeb;
    }

    /**
     * Sets the value of the sitioWeb property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSitioWeb(String value) {
        this.sitioWeb = value;
    }

}
