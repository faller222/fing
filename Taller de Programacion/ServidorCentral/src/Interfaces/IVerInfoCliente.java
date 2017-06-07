package Interfaces;

import DataTypes.DataCliente;
import DataTypes.DataOrdenCompra;
import DataTypes.DataProducto;
import java.util.List;
import java.util.Map;

public interface IVerInfoCliente {

    public Map<String, DataCliente> listarClientes();

    public DataCliente seleccionarCliente(String nick) throws Exception;//Usuario no existe,No es Cliente

    public Map<Integer, DataOrdenCompra> listarOrdenesCliente() throws Exception;//No selecciono susario
    
}
