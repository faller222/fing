package conexion;

import adapter.ASesion;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.xml.ws.WebServiceException;
import persistencia.PUsuario;
import publisher.DataUsuario;

public class CSesion {

    private static DataUsuario UserConected = null;
    private static ASesion Sesion = null;
    private static CSesion Instancia = null;
    private static String Pass = null;
    private static String Nick = null;

    private CSesion() {
        try {
            UserConected = PUsuario.getInstance().getDataUsuario();
            Sesion = new ASesion();
            Pass = UserConected.getPass();
            Nick = UserConected.getNickname();
        } catch (Exception e) {
        }
        Vincular();
    }

    public static CSesion getInstance() {
        if (Instancia == null) {
            try {
                Sesion = new ASesion();
            } catch (Exception e) {
            }
            Instancia = new CSesion();
        }
        return Instancia;
    }

    public DataUsuario iniciarSesion(String User, String Pass) throws Exception {
        try {
            Sesion = new ASesion();
            UserConected = Sesion.iniciarSesion(User, Pass);
            if (UserConected == null) {
                throw new Exception("Usuario o Contraseña Incorrecta");
            }

            this.Nick = User;
            this.Pass = Pass;

            UserConected.setPass(Pass);
            PUsuario.getInstance().setUsuario(UserConected);
        } catch (WebServiceException e) {
            throw new Exception("Imposible Conectar con el servidor");
        }
        return UserConected;
    }

    public DataUsuario verInfoPerfil() {
        DataUsuario dU = null;
        try {
            dU = Sesion.verInfoPerfil();
            if (dU != null) {
                UserConected = dU;
                UserConected.setPass(Pass);
            }
        } catch (Exception e) {
        }
        return UserConected;
    }

    private static void Vincular() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    Sesion.estoyOnline();
                } catch (Exception e) {
                    try {
                        Sesion = new ASesion();
                        Sesion.iniciarSesion(Nick, Pass);
                    } catch (Exception e2) {
                    }
                }
            }
        };
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(timerTask, 0, 1000);
    }

    public boolean puedeComentar(Integer nRef) {
        return Sesion.puedeComentar(nRef);
    }

    public void cerrarSesion() throws Exception {
        try {
            Sesion.cerrarSesion();
            UserConected = null;
            Sesion = null;
            Nick = "";
            Pass = "";
            Instancia = null;
        } catch (Exception e) {
            throw new Exception("Imposible cerrar Sesion.");
        }
    }

    //Para Chat
    public void mandarMensaje(String Receptor, String Mensaje) throws Exception {
        try {
            Sesion.mandarMensaje(Receptor, Mensaje);
        } catch (Exception e) {
            throw new Exception("Imposible cerrar Sesion.");
        }
    }

    public List conversacionCon(String Otro) throws Exception {
        try {
            return Sesion.conversacionCon(Otro);
        } catch (Exception e) {
            throw new Exception("Imposible cerrar Sesion.");
        }
    }

    public List conQuienHable() throws Exception {

        try {
            return Sesion.conQuienHable();
        } catch (Exception e) {
            throw new Exception("Imposible cerrar Sesion.");
        }
    }

    public int contarMensajes(String Otro) throws Exception {
        try {
            return Sesion.contarMensajes(Otro);
        } catch (Exception e) {
            throw new Exception("Imposible cerrar Sesion.");
        }
    }

    public boolean tengoMsj() throws Exception {
        try {
            return Sesion.tengoMsj();
        } catch (Exception e) {
            throw new Exception("Imposible cerrar Sesion.");
        }
    }
}
