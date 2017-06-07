/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import DataTypes.DataCliente;
import DataTypes.DataProducto;
import Interfaces.IPuntaje;
import Conceptos.Cliente;
import Manejadores.MUsuario;
import Conceptos.Producto;
import Conceptos.Proveedor;
import Conceptos.Puntaje;
import Conceptos.Usuario;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author tprog076
 */
public class CPuntaje implements IPuntaje {
    private Cliente Clie;
    private MUsuario mUsuario;
    private Producto Prod;
    
    public CPuntaje(){
        mUsuario = MUsuario.getInstance();
    }
    
    @Override
    public DataCliente seleccionarCliente(String nick) throws Exception {//Usuario no existe,No es Proveedor
        Usuario Temp = mUsuario.getUsuario(nick);
        if (Temp == null) {
            throw new Exception("No existe Usuario " + nick + " En el Sistema");
        }
        if (Temp instanceof Proveedor) {
            throw new Exception(nick + " Es un Proveedor");
        }
        Clie = (Cliente) Temp;
        return (DataCliente) Temp.getDataUsuario();
    }
    
    @Override
    public Map<Integer, DataProducto> listarProductosComprados(){
        Map<Integer, DataProducto> dataProds = new HashMap();
        Map<Integer, Producto> Prods = Clie.obtenerProductos();
        for (Map.Entry<Integer, Producto> entry : Prods.entrySet()) {
            Integer nref = entry.getKey();
            Producto producto = entry.getValue();
            dataProds.put(nref, producto.getDataProducto());
        }
        return dataProds;
    }
    
    @Override
    public void seleccionarProducto(Integer nRef){
       Map<Integer, Producto> Prods = Clie.obtenerProductos();
       Prod = Prods.get(nRef);
    }
    
    @Override
    public void puntuarProducto(Integer puntos) throws Exception{
        Map<String, Puntaje> puntajes = Prod.getPuntajes();
        Puntaje p = puntajes.get(Clie.getNickname());
        if (p == null){    
            Puntaje punt = new Puntaje(Clie, puntos);
            Prod.agregarPuntaje(Clie.getNickname(), punt);
            Clie.yaPuntuo(Prod.getNRef());
        }else{
            throw new Exception("El cliente" + Clie.getNickname() + "ya ha puntuado el producto" + Prod.getNombre());
        }
    }
    
    @Override
    public List<Integer> noPuntuados(){
        return Clie.getProdAPuntuar();
    }
    
}
