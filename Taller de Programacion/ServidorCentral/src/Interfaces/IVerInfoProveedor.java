package Interfaces;

import DataTypes.DataProducto;
import DataTypes.DataProveedor;
import java.util.List;
import java.util.Map;

public interface IVerInfoProveedor {

    public Map<String, DataProveedor> listarProveedores();

    public DataProveedor seleccionarProveedor(String nick) throws Exception;//Usuario no existe,No es Proveedor

    public List<DataProducto> listarProductosProveedor() throws Exception;

    public boolean isOnline(String Nick);
}
