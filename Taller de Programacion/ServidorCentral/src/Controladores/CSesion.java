package Controladores;

import DataTypes.DataCliente;
import DataTypes.DataOrdenCompra;
import DataTypes.DataUsuario;
import Interfaces.ISesion;
import Conceptos.Cliente;
import Conceptos.Comentario;
import Conceptos.Linea;
import Manejadores.MCategoria;
import Manejadores.MOrdenCompra;
import Manejadores.MProducto;
import Manejadores.MUsuario;
import Conceptos.OrdenCompra;
import Conceptos.Producto;
import Conceptos.Usuario;
import Extras.Utils;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class CSesion implements ISesion {

    private Integer i = 0;
    private boolean esCliente;
    private Usuario user;
    private OrdenCompra carrito;
    private static MUsuario mUsuario;
    private static MOrdenCompra mOrdenCompra;
    private static MCategoria mCategoria;
    private static MProducto mProducto;
    //deltimmer
    private Timer timer = new Timer();
    private boolean isTiming = false;

    public CSesion() {

        mUsuario = MUsuario.getInstance();
        mOrdenCompra = MOrdenCompra.getInstance();
        mCategoria = MCategoria.getInstance();
        mProducto = MProducto.getInstance();

        user = null;
        esCliente = false;
        carrito = null;
        //del timmer
        timer = new Timer();
        isTiming = false;
    }

    public Usuario getUsuario() {
        return user;
    }

    @Override
    public DataUsuario inicioSesion(String nm, String contra) throws Exception {
        Usuario aux = null;

        if (!mUsuario.disponibleNick(nm)) {
            aux = mUsuario.getUsuario(nm);
        }
        if (!mUsuario.disponibleMail(nm)) {
            aux = mUsuario.getUsuarioMail(nm);
        }
        if (aux != null) {
            if (Utils.matchPass(contra, aux.getPass())) {
                user = aux;
                if (user instanceof Cliente) {
                    esCliente = true;
                    carrito = new OrdenCompra();
                }
            }
        }
        if (user == null) {
            throw new Exception("Usuario o contraseña Incorrecta");
        }
        user.setOnline(true);
        return user.getDataUsuario();
    }

    @Override
    public DataUsuario verInfoPerfil() {
        return user.getDataUsuario();
    }

    @Override
    public void agregaLinea(Integer cant, Integer num) throws Exception {

        if (user == null) {
            throw new Exception("No ha iniciado Sesion");
        }

        if (!esCliente) {
            throw new Exception(user.getNickname() + " No es Cliente");
        }

        Linea lain = carrito.getLineaPorProducto(num);//busco la linea
        if (lain == null) {//si no esta
            if (cant < 1) {
                throw new Exception("No se acepta cantidad negativa en Linea de Venta");
            }
            carrito.addLinea(new Linea(cant, mProducto.getProducto(num))); //la agrego
        } else {//si esta en el carrito
            if ((-cant) >= lain.getCantidad()) {
                throw new Exception("No se acepta cantidad negativa en Linea de Venta");
            } else {//no puedo dejarlo en 0 pero si restarle
                lain.addCantidad(cant);
            }
        }
    }

    @Override
    public DataOrdenCompra verCarrito() throws Exception {
        if (user == null) {
            throw new Exception("No ha iniciado Sesion");
        }
        if (!esCliente) {
            throw new Exception(user.getNickname() + " No es Cliente");
        }
        return carrito.getDataOrdenCompra();

    }

    @Override
    public void generarOrden() throws Exception {
        if (carrito == null) {
            throw new Exception("Debe contener al menos una linea");
        }
        if (carrito.getDataOrdenCompra().getLineas().isEmpty()) {
            throw new Exception("Debe contener al menos una linea");
        }
        mOrdenCompra.addOrdenCompraRecibida(carrito); //Aqui adentro le definen el numero!!!
        ((Cliente) user).agregarOrden(carrito);
        carrito = new OrdenCompra();
    }

    @Override
    public boolean puedeComentar(Integer nRef) {
        boolean ret;
        ret = (user != null);
        ret = ret && esCliente;
        ret = ret && ((Cliente) user).comproProducto(nRef);
        return ret;
    }

    @Override
    public void comentar(Integer nRef, String Comen) throws Exception {
        if (user == null) {
            throw new Exception("No ha iniciado Sesion");
        }
        if (!esCliente) {
            throw new Exception(user.getNickname() + " No es Cliente");
        }
        Comentario C = new Comentario(Comen, (Cliente) user);

        Producto P = MProducto.getInstance().getProducto(nRef);
        P.Comentar(C);

    }

    @Override
    public void responder(Integer nRef, String Comen, Integer Padre) throws Exception {
        if (user == null) {
            throw new Exception("No ha iniciado Sesion");
        }
        if (!esCliente) {
            throw new Exception(user.getNickname() + " No es Cliente");
        }
        Comentario C = new Comentario(Comen, (Cliente) user);
        Producto P = MProducto.getInstance().getProducto(nRef);
        if (P == null) {
            throw new Exception("No existe Producto con Num " + nRef);
        }
        P.Responder(C, Padre);

    }

    @Override
    public void cerrarSesion() {
        System.err.println((i++) + ") " + user.getNickname() + " esta Cerro sesion ");
        user.setOnline(false);
    }

    @Override
    public void estoyOnline() {
        if (user != null) {
            user.setOnline(true);
            System.err.println((i++) + ") " + user.getNickname() + " esta Online");
            onceUponATime();
        }

    }

    private void onceUponATime() {
        if (isTiming) {
            isTiming = false;
            timer.cancel();
        }
        TimerTask timerTask = new TimerTask() {
            public void run() {
                System.err.println((i++) + ") " + user.getNickname() + " esta Desconectado por inactividad");
                user.setOnline(false);
            }
        };
        isTiming = true;
        timer = new Timer();
        timer.schedule(timerTask, 3 * 1000);
    }

    //Para Chat
    @Override
    public void mandarMensaje(String Receptor, String Mensaje) throws Exception {//agregarMensaje
        if (user == null) {
            throw new Exception("Debes Logearte.");
        }
        String Proveedor;
        String Cliente;
        if (user instanceof Cliente) {
            Cliente = user.getNickname();
            Proveedor = Receptor;
        } else {
            Proveedor = user.getNickname();
            Cliente = Receptor;
        }
        CConversacion CC = new CConversacion();
        Mensaje = user.getNickname() + ": " + Mensaje;

        CC.agregarMensaje(Cliente, Proveedor, Mensaje);
    }

    @Override
    public List<String> conversacionCon(String Otro) throws Exception {//mensajesEntre
        if (user == null) {
            throw new Exception("Debes Logearte.");
        }
        String Proveedor;
        String Cliente;
        if (user instanceof Cliente) {
            Cliente = user.getNickname();
            Proveedor = Otro;
        } else {
            Proveedor = user.getNickname();
            Cliente = Otro;
        }
        CConversacion CC = new CConversacion();

        user.setMensajeNuevo(false);
        return CC.mensajesEntre(Cliente, Proveedor);

    }

    @Override
    public int contarMensajes(String Otro) throws Exception {//contarMensajes
        if (user == null) {
            throw new Exception("Debes Logearte.");
        }
        String Proveedor;
        String Cliente;
        if (user instanceof Cliente) {
            Cliente = user.getNickname();
            Proveedor = Otro;
        } else {
            Proveedor = user.getNickname();
            Cliente = Otro;
        }
        CConversacion CC = new CConversacion();
        return CC.contarMensajes(Cliente, Proveedor);

    }

    @Override
    public List<String> conQuienHable() throws Exception {//contarMensajes
        if (user == null) {
            throw new Exception("Debes Logearte.");
        }
        CConversacion CC = new CConversacion();
        return CC.conQuienHable(user.getNickname());

    }

    @Override
    public boolean tengoMsj() {
        if (user == null) {
            return false;
        } else {
            return user.isMensajeNuevo();
        }
    }

//SOLO PARA CARGAR
    public void ComentarioConFecha(Integer nRef, String Comen, Date F) throws Exception {
        if (user == null) {
            throw new Exception("No ha iniciado Sesion");
        }
        if (!esCliente) {
            throw new Exception(user.getNickname() + " No es Cliente");
        }
        Comentario C = new Comentario(Comen, (Cliente) user, F);
        Producto P = MProducto.getInstance().getProducto(nRef);
        P.Comentar(C);
    }

    public void responderConFecha(Integer nRef, String Comen, int Padre, Date F) throws Exception {
        if (user == null) {
            throw new Exception("No ha iniciado Sesion");
        }
        if (!esCliente) {
            throw new Exception(user.getNickname() + " No es Cliente");
        }
        Comentario C = new Comentario(Comen, (Cliente) user, F);
        Producto P = MProducto.getInstance().getProducto(nRef);
        P.Responder(C, Padre);

    }

    public DataUsuario inicioSesion(DataUsuario dU) {

        DataUsuario ret = null;

        if (!mUsuario.disponibleNick(dU.getNickname())) {
            user = mUsuario.getUsuario(dU.getNickname());
        }
        if (!mUsuario.disponibleMail(dU.getMail())) {
            user = mUsuario.getUsuarioMail(dU.getMail());
        }
        if (user != null) {
            if (user.getPass().equals(dU.getPass())) {
                ret = user.getDataUsuario();
                if (ret instanceof DataCliente) {
                    esCliente = true;
                    carrito = new OrdenCompra();
                }
            }
        }
        return ret;
    }
}
