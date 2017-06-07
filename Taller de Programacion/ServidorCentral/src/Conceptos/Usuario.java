package Conceptos;

import DataTypes.DataUsuario;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Usuario {

    protected String nickname;
    protected String pass;
    protected String mail;
    protected String nombre;
    protected String apellido;
    protected Date fechaNac;
    protected byte[] imagen;
    protected boolean Online;
    protected boolean MensajeNuevo;

    public Usuario(DataUsuario dataU) throws Exception {
        if (!isEmail(dataU.getMail())) {
            throw new Exception("eMail Invalido");
        }
        this.mail = dataU.getMail().toLowerCase();
        this.pass = dataU.getPass();
        this.nickname = dataU.getNickname();
        this.nombre = dataU.getNombre().toLowerCase();
        this.apellido = dataU.getApellido().toLowerCase();
        this.fechaNac = dataU.getFechaNac();
        this.imagen = dataU.getBimagen();
        this.Online = false;
        this.MensajeNuevo = false;

        String Aux1 = nombre.substring(0, 1);
        String Aux2 = apellido.substring(0, 1);

        nombre = Aux1.toUpperCase() + nombre.substring(1);
        apellido = Aux2.toUpperCase() + apellido.substring(1);
    }

    private boolean isEmail(String correo) {
        //Pattern pat = Pattern.compile("^([0-9a-zA-Z]([_.w]*[0-9a-zA-Z])*@([0-9a-zA-Z][-w]*[0-9a-zA-Z].)+([a-zA-Z]{2,9}.)+[a-zA-Z]{2,3})$");
        Pattern pat = Pattern.compile("^[0-9a-zA-Z._%+-]+@[0-9a-zA-Z.-]+\\.[0-9a-zA-Z]{2,6}$");
        Matcher mat = pat.matcher(correo);
        return mat.find();
    }

    public String getNickname() {
        return nickname;
    }

    public String getPass() {
        return pass;
    }

    public String getMail() {
        return mail;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public Date getFechaNac() {
        return fechaNac;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] Image) {
        imagen = Image;
    }

    public void setOnline(boolean online) {
        Online = online;

    }

    public boolean isOnline() {
        return Online;
    }

    public boolean isMensajeNuevo() {
        return MensajeNuevo;
    }

    public void setMensajeNuevo(boolean MensajeNuevo) {
        this.MensajeNuevo = MensajeNuevo;
    }

    public abstract DataUsuario getDataUsuario();
}
