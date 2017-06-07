/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;


import DataTypes.DataCliente;
import DataTypes.DataProducto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author tprog076
 */
public interface IPuntaje {
    
    public DataCliente seleccionarCliente(String nick) throws Exception;
    
    public Map<Integer, DataProducto> listarProductosComprados();
    
    public void seleccionarProducto(Integer nRef);
    
    public void puntuarProducto(Integer puntos) throws Exception;
    
    public List<Integer> noPuntuados();
}
