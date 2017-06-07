
package publisher;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for dataOrdenCompra complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="dataOrdenCompra">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cliente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="estados" type="{http://Publisher/}dataEstado" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="lineas" type="{http://Publisher/}dataLinea" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="numero" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="total" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dataOrdenCompra", propOrder = {
    "cliente",
    "estados",
    "lineas",
    "numero",
    "total"
})
public class DataOrdenCompra {

    protected String cliente;
    @XmlElement(nillable = true)
    protected List<DataEstado> estados;
    @XmlElement(nillable = true)
    protected List<DataLinea> lineas;
    protected Integer numero;
    protected Float total;

    /**
     * Gets the value of the cliente property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCliente() {
        return cliente;
    }

    /**
     * Sets the value of the cliente property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCliente(String value) {
        this.cliente = value;
    }

    /**
     * Gets the value of the estados property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the estados property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEstados().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DataEstado }
     * 
     * 
     */
    public List<DataEstado> getEstados() {
        if (estados == null) {
            estados = new ArrayList<DataEstado>();
        }
        return this.estados;
    }

    /**
     * Gets the value of the lineas property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the lineas property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLineas().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DataLinea }
     * 
     * 
     */
    public List<DataLinea> getLineas() {
        if (lineas == null) {
            lineas = new ArrayList<DataLinea>();
        }
        return this.lineas;
    }

    /**
     * Gets the value of the numero property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumero() {
        return numero;
    }

    /**
     * Sets the value of the numero property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumero(Integer value) {
        this.numero = value;
    }

    /**
     * Gets the value of the total property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getTotal() {
        return total;
    }

    /**
     * Sets the value of the total property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setTotal(Float value) {
        this.total = value;
    }

}
