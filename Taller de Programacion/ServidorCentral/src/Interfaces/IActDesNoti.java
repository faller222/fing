/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import DataTypes.DataCliente;
import DataTypes.DataOrdenCompra;

/**
 *
 * @author Samuels
 */
public interface IActDesNoti {

    public DataCliente seleccionarCliente(String nick) throws Exception;

    public DataOrdenCompra seleccionarOrdenCompra(Integer Noc) throws Exception;

    public void ActivarNotiOrdenes();

    public void ActivarNotiProveedor();

    public void ActivarNotiProducto();

    public void ActivarNotiReclamo();

    public void DesactivarNotiReclamo();

    public void DesactivarNotiOrdenes();

    public void DesactivarNotiProveedor();

    public void DesactivarNotiProducto();
}
