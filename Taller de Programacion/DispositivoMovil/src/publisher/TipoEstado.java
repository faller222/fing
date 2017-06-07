
package publisher;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tipoEstado.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="tipoEstado">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Preparada"/>
 *     &lt;enumeration value="Cancelada"/>
 *     &lt;enumeration value="Confirmada"/>
 *     &lt;enumeration value="Recibida"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "tipoEstado")
@XmlEnum
public enum TipoEstado {

    @XmlEnumValue("Preparada")
    PREPARADA("Preparada"),
    @XmlEnumValue("Cancelada")
    CANCELADA("Cancelada"),
    @XmlEnumValue("Confirmada")
    CONFIRMADA("Confirmada"),
    @XmlEnumValue("Recibida")
    RECIBIDA("Recibida");
    private final String value;

    TipoEstado(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TipoEstado fromValue(String v) {
        for (TipoEstado c: TipoEstado.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
