package Logica;

import Controladores.CVerInfoCliente;
import DataTypes.DataCliente;
import Extras.Utils;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CVerInfoClienteTest {

    public CVerInfoClienteTest() {
    }

    @Before
    public void setUp() {
        System.out.println("setUp");
        Utils.Cargar(false);
    }

    @After
    public void tearDown() {
        System.out.println("tearDown");
        Utils.Limpiar();
    }

    /**
     * Test of listarClientes method, of class CVerInfoCliente.
     */
    @Test
    public void testListarClientes() {
        System.out.println("listarClientes");
        CVerInfoCliente instance = new CVerInfoCliente();
        Map result = instance.listarClientes();
    }

    /**
     * Test of seleccionarCliente method, of class CVerInfoCliente.
     */
    @Test
    public void testSeleccionarClienteListarOrdenes() throws Exception {
        System.out.println("seleccionarCliente");
        String nick = "BruceS";
        CVerInfoCliente instance = new CVerInfoCliente();
        DataCliente result = instance.seleccionarCliente(nick);
        Map ordenes = instance.listarOrdenesCliente();
    }

    /**
     * Test of listarOrdenesCliente method, of class CVerInfoCliente.
     *
     * @Test public void testListarOrdenesCliente() throws Exception {
     * System.out.println("listarOrdenesCliente"); CVerInfoCliente instance =
     * new CVerInfoCliente();
     *
     * }
     */
    /*Fito de aca para abajo
     **************************     *
     * *****  *  *****  ***** *     *
     * *           *    *   * *     *
     * ***    *    *    *   * *  *  *  *
     * *      *    *    *   * *   * * *
     * *      *    *    ***** *    * *
     *                        *     *
     **************************/
    /**
     * Test seleccionarCliente
     */
    @Test(expected = Exception.class)
    public void testSeleccionarCliente1() throws Exception {
        System.out.println("testSeleccionarCliente1 no existe");
        CVerInfoCliente instance = new CVerInfoCliente();
        instance.seleccionarCliente("Falucho");
    }

    @Test(expected = Exception.class)
    public void testSeleccionarCliente2() throws Exception {
        System.out.println("testSeleccionarCliente1 Es Proveedor");
        CVerInfoCliente instance = new CVerInfoCliente();
        instance.seleccionarCliente("Tim1");
    }
}
