package DataTypes;

import Extras.UtilImage;
import java.awt.image.BufferedImage;
import java.util.Date;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlAccessorType
@XmlSeeAlso({DataCliente.class, DataProveedor.class})
public abstract class DataUsuario {

    public DataUsuario() {
    }
    private boolean Online;
    private String mail;
    private String nickname;
    private String pass;
    private String nombre;
    private String apellido;
    private Date fechaNac;
    private byte[] bimagen;

    public DataUsuario(String NickName,
            String pass,
            String Mail,
            String Nombre,
            String Apellido,
            Date FechaNac) {
        this.nickname = NickName;
        this.pass = pass;
        this.mail = Mail;
        this.nombre = Nombre;
        this.apellido = Apellido;
        this.fechaNac = FechaNac;
        this.bimagen = null;
    }

    public DataUsuario(String NickName,
            String pass,
            String Mail,
            String Nombre,
            String Apellido,
            Date FechaNac,
            byte[] imagen) {
        this.nickname = NickName;
        this.pass = pass;
        this.mail = Mail;
        this.nombre = Nombre;
        this.apellido = Apellido;
        this.fechaNac = FechaNac;
        this.bimagen = imagen;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String email) {
        this.mail = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Date getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(Date fechaN) {
        this.fechaNac = fechaN;
    }

    public void setImagen(String image) throws Exception {
        if (image != null && !image.equals("")) {
            this.bimagen = UtilImage.toByteArray(image);
        }
    }

    public byte[] getBimagen() {
        return bimagen;
    }

    public void setBimagen(byte[] bimagen) throws Exception {
        this.bimagen = bimagen;
    }

    public boolean isOnline() {
        return Online;
    }

    public void setOnline(boolean Online) {
        this.Online = Online;
    }
}
