
package publisher;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for dataPuntaje complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="dataPuntaje">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cli" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="puntos" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dataPuntaje", propOrder = {
    "cli",
    "puntos"
})
public class DataPuntaje {

    protected String cli;
    protected Integer puntos;

    /**
     * Gets the value of the cli property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCli() {
        return cli;
    }

    /**
     * Sets the value of the cli property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCli(String value) {
        this.cli = value;
    }

    /**
     * Gets the value of the puntos property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPuntos() {
        return puntos;
    }

    /**
     * Sets the value of the puntos property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPuntos(Integer value) {
        this.puntos = value;
    }

}
