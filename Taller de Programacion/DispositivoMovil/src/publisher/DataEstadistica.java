
package publisher;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for dataEstadistica complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="dataEstadistica">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ganancia" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/>
 *         &lt;element name="meses" type="{http://www.w3.org/2001/XMLSchema}float" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dataEstadistica", propOrder = {
    "ganancia",
    "meses"
})
public class DataEstadistica {

    protected Float ganancia;
    @XmlElement(nillable = true)
    protected List<Float> meses;

    /**
     * Gets the value of the ganancia property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getGanancia() {
        return ganancia;
    }

    /**
     * Sets the value of the ganancia property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setGanancia(Float value) {
        this.ganancia = value;
    }

    /**
     * Gets the value of the meses property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the meses property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMeses().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Float }
     * 
     * 
     */
    public List<Float> getMeses() {
        if (meses == null) {
            meses = new ArrayList<Float>();
        }
        return this.meses;
    }

}
