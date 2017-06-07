
package publisher;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for dataCategoria complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="dataCategoria">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cateHijas" type="{http://Publisher/}dataCategoria" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="esSimple" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="nombre" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dataCategoria", propOrder = {
    "cateHijas",
    "esSimple",
    "nombre"
})
public class DataCategoria {

    @XmlElement(nillable = true)
    protected List<DataCategoria> cateHijas;
    protected Boolean esSimple;
    protected String nombre;

    /**
     * Gets the value of the cateHijas property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the cateHijas property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCateHijas().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DataCategoria }
     * 
     * 
     */
    public List<DataCategoria> getCateHijas() {
        if (cateHijas == null) {
            cateHijas = new ArrayList<DataCategoria>();
        }
        return this.cateHijas;
    }

    /**
     * Gets the value of the esSimple property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isEsSimple() {
        return esSimple;
    }

    /**
     * Sets the value of the esSimple property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setEsSimple(Boolean value) {
        this.esSimple = value;
    }

    /**
     * Gets the value of the nombre property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Sets the value of the nombre property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombre(String value) {
        this.nombre = value;
    }

}
