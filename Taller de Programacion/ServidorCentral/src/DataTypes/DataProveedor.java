package DataTypes;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType
public class DataProveedor extends DataUsuario {

    public DataProveedor() {
        super();
    }
    private String compania;
    private String sitioWeb;
    private ArrayList<Integer> productos = new ArrayList<Integer>();

    public DataProveedor(String NickName,
            String Pass,
            String Mail,
            String Nombre,
            String Apellido,
            Date FechaNac,
            String sitioWeb,
            String compania,
            byte[] imagen) {
        super(NickName, Pass, Mail, Nombre, Apellido, FechaNac, imagen);
        this.sitioWeb = sitioWeb;
        this.compania = compania;
    }

    public DataProveedor(String NickName,
            String Pass,
            String Mail,
            String Nombre,
            String Apellido,
            Date FechaNac,
            String sitioWeb,
            String compania) {
        super(NickName, Pass, Mail, Nombre, Apellido, FechaNac);
        this.sitioWeb = sitioWeb;
        this.compania = compania;
    }

    public DataProveedor(String NickName,
            String Pass,
            String Mail,
            String Nombre,
            String Apellido,
            Date FechaNac) {
        super(NickName, Pass, Mail, Nombre, Apellido, FechaNac);
    }

    public String getCompania() {
        return compania;
    }

    public void setCompania(String compania) {
        this.compania = compania;
    }

    public String getSitioWeb() {
        return sitioWeb;
    }

    public void setSitioWeb(String sitioWeb) {
        this.sitioWeb = sitioWeb;
    }

    public ArrayList<Integer> getProductos() {
        return productos;
    }

    public void setProductos(ArrayList<Integer> productos) {
        this.productos = productos;
    }
}
