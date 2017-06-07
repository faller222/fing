package Interfaces;

import DataTypes.DataOrdenCompra;
import DataTypes.DataUsuario;
import java.util.List;

public interface ISesion {

    public DataUsuario inicioSesion(String nm, String contra) throws Exception;

    public void cerrarSesion();

    public void estoyOnline();

    public DataUsuario verInfoPerfil();

    public void agregaLinea(Integer cant, Integer num) throws Exception;

    public DataOrdenCompra verCarrito() throws Exception;

    public void generarOrden() throws Exception;

    public boolean puedeComentar(Integer nRef);

    public void comentar(Integer nRef, String Comen) throws Exception;

    public void responder(Integer nRef, String Comen, Integer Padre) throws Exception;

    //Para Chat
    public void mandarMensaje(String Receptor, String Mensaje) throws Exception;//agregarMensaje

    public List<String> conversacionCon(String Otro) throws Exception;//mensajesEntre

    public int contarMensajes(String Otro) throws Exception;//contarMensajes

    public List<String> conQuienHable() throws Exception;//contarMensajes

    public boolean tengoMsj();
}
