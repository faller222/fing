package DataTypes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType
public class DataCliente extends DataUsuario {

    private boolean notiOrden = true;
    private boolean notiProve = true;
    private boolean notiProd = true;
    private boolean notiRec = true;
    ArrayList<Integer> Ordenes;

    public DataCliente() {
        super();
        Ordenes = new ArrayList<Integer>();
    }

    public boolean isNotiOrden() {
        return notiOrden;
    }

    public void setNotiOrden(boolean notiOrden) {
        this.notiOrden = notiOrden;
    }

    public boolean isNotiRec() {
        return notiRec;
    }

    public void setNotiRec(boolean notiRec) {
        this.notiRec = notiRec;
    }

    public boolean isNotiProve() {
        return notiProve;
    }

    public void setNotiProve(boolean notiProve) {
        this.notiProve = notiProve;
    }

    public boolean isNotiProd() {
        return notiProd;
    }

    public void setNotiProd(boolean notiProd) {
        this.notiProd = notiProd;
    }

    public DataCliente(String NickName,
            String pass,
            String Mail,
            String Nombre,
            String Apellido,
            Date FechaNac) throws IOException {
        super(NickName, pass, Mail, Nombre, Apellido, FechaNac);
        Ordenes = new ArrayList<Integer>();
    }

    public DataCliente(String NickName,
            String pass,
            String Mail,
            String Nombre,
            String Apellido,
            Date FechaNac,
            byte[] imagen) {
        super(NickName, pass, Mail, Nombre, Apellido, FechaNac, imagen);
        Ordenes = new ArrayList<Integer>();
    }

    public ArrayList<Integer> getOrdenes() {
        return Ordenes;
    }

    public void setOrdenes(ArrayList<Integer> Ordenes) {
        this.Ordenes = Ordenes;
    }
}
