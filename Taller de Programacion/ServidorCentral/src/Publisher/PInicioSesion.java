package Publisher;

import DataTypes.DataOrdenCompra;
import DataTypes.DataUsuario;
import Extras.Coleccion;
import Interfaces.Fabrica;
import Interfaces.ISesion;
import java.util.HashMap;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public class PInicioSesion extends PControlador {

    private HashMap<String, ISesion> Instancias;//se guardan por nickname

    public PInicioSesion() {
        Instancias = new HashMap<>();
    }

    @WebMethod
    public DataUsuario inicioSesion(@WebParam(name = "nm") String nm, @WebParam(name = "contra") String contra) throws Exception {
        ISesion Nueva = Fabrica.getIS();
        DataUsuario dU;
        try {
            dU = Nueva.inicioSesion(nm, contra);
        } catch (Exception e) {
            throw e;
        }
        Instancias.put(dU.getNickname(), Nueva);
        return dU;
    }

    @WebMethod
    public void cerrarSesion(@WebParam(name = "NickName") String nick) throws Exception {
        ISesion Aux = Instancias.get(nick);
        Aux.cerrarSesion();
        Instancias.remove(nick);
    }

    @WebMethod
    public void estoyOnline(@WebParam(name = "NickName") String nick) throws Exception {
        ISesion Aux = Instancias.get(nick);
        Aux.estoyOnline();
    }

    @WebMethod
    public DataUsuario verInfoPerfil(@WebParam(name = "NickName") String nick) {
        ISesion Aux = Instancias.get(nick);
        return Aux.verInfoPerfil();
    }

    @WebMethod
    public void agregaLinea(@WebParam(name = "cant") Integer cant, @WebParam(name = "num") Integer num, @WebParam(name = "NickName") String nick) throws Exception {
        ISesion Aux = Instancias.get(nick);
        Aux.agregaLinea(cant, num);
    }

    @WebMethod
    public DataOrdenCompra verCarrito(@WebParam(name = "NickName") String nick) throws Exception {
        ISesion Aux = Instancias.get(nick);
        return Aux.verCarrito();
    }

    @WebMethod
    public void generarOrden(@WebParam(name = "NickName") String nick) throws Exception {
        ISesion Aux = Instancias.get(nick);
        Aux.generarOrden();
    }

    @WebMethod
    public boolean puedeComentar(@WebParam(name = "nRef") Integer nRef, @WebParam(name = "NickName") String nick) {
        ISesion Aux = Instancias.get(nick);
        return Aux.puedeComentar(nRef);
    }

    @WebMethod
    public void comentar(@WebParam(name = "nRef") Integer nRef, @WebParam(name = "Comen") String Comen, @WebParam(name = "NickName") String nick) throws Exception {
        ISesion Aux = Instancias.get(nick);
        Aux.comentar(nRef, Comen);
    }

    @WebMethod
    public void responder(@WebParam(name = "nRef") Integer nRef, @WebParam(name = "Comen") String Comen, @WebParam(name = "Padre") Integer Padre, @WebParam(name = "NickName") String nick) throws Exception {
        ISesion Aux = Instancias.get(nick);
        Aux.responder(nRef, Comen, Padre);
    }

    //Para Chat
    @WebMethod
    public void mandarMensaje(@WebParam(name = "Receptor") String Receptor, @WebParam(name = "Mensaje") String Mensaje, @WebParam(name = "NickName") String nick) throws Exception {
        ISesion Aux = Instancias.get(nick);
        Aux.mandarMensaje(Receptor, Mensaje);
    }

    @WebMethod
    public Coleccion conversacionCon(@WebParam(name = "Otro") String Otro, @WebParam(name = "NickName") String nick) throws Exception {
        ISesion Aux = Instancias.get(nick);
        Coleccion nueva = new Coleccion();
        for (String mes : Aux.conversacionCon(Otro)) {
            nueva.addElem(mes);
        }
        return nueva;

    }

    @WebMethod
    public int contarMensajes(@WebParam(name = "Otro") String Otro, @WebParam(name = "NickName") String nick) throws Exception {
        ISesion Aux = Instancias.get(nick);
        return Aux.contarMensajes(Otro);
    }

    @WebMethod
    public Coleccion conQuienHable(@WebParam(name = "NickName") String nick) throws Exception {
        ISesion Aux = Instancias.get(nick);
        Coleccion nueva = new Coleccion();
        for (String ppl : Aux.conQuienHable()) {
            nueva.addElem(ppl);
        }
        return nueva;
    }

    @WebMethod
    public boolean tengoMsj(@WebParam(name = "NickName") String nick) throws Exception {
        ISesion Aux = Instancias.get(nick);
        return Aux.tengoMsj();
    }
}
