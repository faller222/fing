package Extras;

import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType
public class Par<Key, Value> {

    private Key Clave;
    private Value Valor;

    public Par(Key Clave, Value Valor) {
        this.Clave = Clave;
        this.Valor = Valor;
    }

    public Par() {
    }

    public Key getKey() {
        return Clave;
    }

    public Value getValue() {
        return Valor;
    }

    public void setClave(Key Clave) {
        this.Clave = Clave;
    }

    public void setValor(Value Valor) {
        this.Valor = Valor;
    }
}
