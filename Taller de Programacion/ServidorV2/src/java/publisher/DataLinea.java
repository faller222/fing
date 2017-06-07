
package publisher;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for dataLinea complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="dataLinea">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cantidad" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="precioLinea" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/>
 *         &lt;element name="precioProd" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/>
 *         &lt;element name="prod" type="{http://Publisher/}dataProducto" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dataLinea", propOrder = {
    "cantidad",
    "precioLinea",
    "precioProd",
    "prod"
})
public class DataLinea {

    protected Integer cantidad;
    protected Float precioLinea;
    protected Float precioProd;
    protected DataProducto prod;

    /**
     * Gets the value of the cantidad property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCantidad() {
        return cantidad;
    }

    /**
     * Sets the value of the cantidad property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCantidad(Integer value) {
        this.cantidad = value;
    }

    /**
     * Gets the value of the precioLinea property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getPrecioLinea() {
        return precioLinea;
    }

    /**
     * Sets the value of the precioLinea property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setPrecioLinea(Float value) {
        this.precioLinea = value;
    }

    /**
     * Gets the value of the precioProd property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getPrecioProd() {
        return precioProd;
    }

    /**
     * Sets the value of the precioProd property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setPrecioProd(Float value) {
        this.precioProd = value;
    }

    /**
     * Gets the value of the prod property.
     * 
     * @return
     *     possible object is
     *     {@link DataProducto }
     *     
     */
    public DataProducto getProd() {
        return prod;
    }

    /**
     * Sets the value of the prod property.
     * 
     * @param value
     *     allowed object is
     *     {@link DataProducto }
     *     
     */
    public void setProd(DataProducto value) {
        this.prod = value;
    }

}
