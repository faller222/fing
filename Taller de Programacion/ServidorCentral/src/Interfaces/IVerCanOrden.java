package Interfaces;

import DataTypes.DataOrdenCompra;
import java.util.Map;

public interface IVerCanOrden {

    public Map<Integer, DataOrdenCompra> listarOrdenesRecibidas();

    public DataOrdenCompra seleccionarOrden(Integer num) throws Exception;//no existe orden

    public void cancelarOrden() throws Exception;//debe seleccionar orden

    public void prepararOrden() throws Exception;//debe seleccionar orden

    public void confirmarOrden(String nick) throws Exception;//debe seleccionar orden
}
