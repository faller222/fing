/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import Controladores.CVerCanOrden;
import DataTypes.DataOrdenCompra;
import Extras.Utils;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Helen
 */
public class CVerCanOrdenTest {

    public CVerCanOrdenTest() {
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
     * Test of listarOrdenesTotal method, of class CVerCanOrden.
     */
    @Test
    public void testListarOrdenesTotal() {
        System.out.println("listarOrdenesTotal");
        CVerCanOrden instance = new CVerCanOrden();
        Map result = instance.listarOrdenesRecibidas();
    }

    /**
     * Test of seleccionarOrden method, of class CVerCanOrden.
     *
     * @Test (expected = Exception.class) public void
     * testSeleccionarCancelarOrden() throws Exception {
     * System.out.println("seleccionarOrden"); Integer num = 1; CVerCanOrden
     * instance = new CVerCanOrden(); DataOrdenCompra result =
     * instance.seleccionarOrden(num); instance.cancelarOrden(); }
     */
    /**
     * Test of cancelarOrden method, of class CVerCanOrden.
     *
     * @Test public void testCancelarOrden() throws Exception {
     * System.out.println("cancelarOrden"); CVerCanOrden instance = new
     * CVerCanOrden();
     *
     * // TODO review the generated test code and remove the default call to
     * fail. fail("The test case is a prototype."); }
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
     * Test Seleccionar Orden.
     */
    @Test(expected = Exception.class)
    public void testSeleccionarOrden() throws Exception {
        System.out.println("testSeleccionarOrden no Existe");
        CVerCanOrden instance = new CVerCanOrden();
        instance.seleccionarOrden(15);
    }

    /**
     * Test Seleccionar Orden.
     */
    @Test(expected = Exception.class)
    public void testCancelarOrden1() throws Exception {
        System.out.println("testCancelarOrden no Selecciono");
        CVerCanOrden instance = new CVerCanOrden();
        instance.cancelarOrden();
    }
//    @Test
//    public void testCancelarOrden2() throws Exception {
//        System.out.println("testCancelarOrden Correcto");
//        CVerCanOrden instance = new CVerCanOrden();
//        instance.seleccionarOrden(2);
//        instance.cancelarOrden();
//    }
}
