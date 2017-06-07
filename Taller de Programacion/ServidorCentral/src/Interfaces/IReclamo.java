package Interfaces;

import DataTypes.DataCliente;
import DataTypes.DataProducto;
import DataTypes.DataProveedor;
import DataTypes.DataReclamo;
import java.util.List;

public interface IReclamo {

    public DataCliente seleccionarCliente(String nick) throws Exception;

    public DataProducto seleccionarProducto(Integer NRef) throws Exception;

    public void altaReclamo(String texto);

    public DataProveedor seleccionarProveedor(String nick) throws Exception;

    public List<DataReclamo> listarReclamos();

    public void respRec(String txt, Integer nRec, Integer nRef);
}
