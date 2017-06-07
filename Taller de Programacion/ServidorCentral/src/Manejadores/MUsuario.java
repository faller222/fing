package Manejadores;

import Conceptos.Usuario;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.jasypt.util.text.BasicTextEncryptor;

public class MUsuario { //Singleton

    private static MUsuario instance = null;
    private Map<String, Usuario> usuarios; // Nickname, Usuario
    private Map<String, Usuario> mails; //key=Mail
    private Properties Users;

    private MUsuario() {
        this.usuarios = new HashMap<String, Usuario>();
        this.mails = new HashMap<String, Usuario>();
        Users = new Properties();

    }

    public boolean disponibleMail(String mail) {
        return !(mails.containsKey(mail));
    }

    public boolean disponibleNick(String Nick) {
        return !(usuarios.containsKey(Nick));
    }

    public static MUsuario getInstance() {
        if (instance == null) {
            instance = new MUsuario();
        }
        return instance;
    }

    public Usuario getUsuario(String nickname) {
        return usuarios.get(nickname);
    }

    public Usuario getUsuarioMail(String mail) {
        //retorna null si no encuentra el usuario con mail == mail
        Usuario res = mails.get(mail);
        return res;
    }

    public synchronized void addUsuario(Usuario u) {
        usuarios.put(u.getNickname(), u);
        mails.put(u.getMail(), u);
        Users.setProperty(u.getNickname(), u.getPass());
        save();
    }

    public Map<String, Usuario> getUsuarios() {
        return usuarios;
    }

    public boolean matchPass(String nick, String pass) {
        String passEnc = Users.getProperty(nick);
        return pass.equals(passEnc);
    }

    public void clearUsers() {
        this.usuarios.clear();
        this.mails.clear();
        Users = new Properties();
    }

    private void save() {
        try {
            FileOutputStream out = new FileOutputStream("Usuarios.enc");
            Users.store(out, "Usu");
        } catch (Exception e) {
        }
    }
}
