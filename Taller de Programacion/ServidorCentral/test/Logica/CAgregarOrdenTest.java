package Logica;

import Controladores.CAgregarOrden;
import DataTypes.DataCategoria;
import DataTypes.DataOrdenCompra;
import DataTypes.DataProducto;
import DataTypes.DataUsuario;
import Extras.Utils;
import java.util.Date;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CAgregarOrdenTest {

    public CAgregarOrdenTest() {
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
     * Test of listarClientes method, of class CAgregarOrden.
     */
    @Test
    public void testListarClientes() {
        System.out.println("listarClientes");
        CAgregarOrden instance = new CAgregarOrden();
        Map result = instance.listarClientes();
    }

    /**
     * Test of seleccionarCliente method, of class CAgregarOrden.
     */
    @Test
    public void testSeleccionarCliente() throws Exception {
        System.out.println("seleccionarCliente");
        String nick = "Dan";
        CAgregarOrden instance = new CAgregarOrden();
        DataUsuario result = instance.seleccionarCliente(nick);
    }

    /**
     * Test of listarCategorias method, of class CAgregarOrden.
     */
    @Test
    public void testListarCategorias() {
        System.out.println("listarCategorias");
        CAgregarOrden instance = new CAgregarOrden();
        DataCategoria result = instance.listarCategorias();
    }

    /**
     * Test of buscarCategoria method, of class CAgregarOrden.
     */
    @Test
    public void testBuscarCategoria() {
        System.out.println("buscarCategoria");
        String nCat = "Celulares";
        CAgregarOrden instance = new CAgregarOrden();
        DataCategoria result = instance.buscarCategoria(nCat);
    }

    /**
     * Test of listarProductosCategoria method, of class CAgregarOrden.
     */
    @Test
    public void testListarProductosCategoria() throws Exception {
        System.out.println("listarProductosCategoria");
        CAgregarOrden instance = new CAgregarOrden();
        Map result = instance.listarProductosCategoria("Apple");
    }

    /**
     * Test of agregarProductoAOrden method, of class CAgregarOrden.
     *
     * @Test public void testAgregarProductoAOrden() {
     * System.out.println("agregarProductoAOrden"); DataProducto dp = null;
     * Integer cant = null; CAgregarOrden instance = new CAgregarOrden();
     * instance.agregarProductoAOrden(dp, cant); }
     */
    /**
     * Test of altaOrden method, of class CAgregarOrden.
     *
     * @Test public void testAltaOrden() throws Exception {
     * System.out.println("altaOrden"); CAgregarOrden instance = new
     * CAgregarOrden(); DataOrdenCompra expResult = null; DataOrdenCompra result
     * = instance.altaOrden(); assertEquals(expResult, result); // TODO review
     * the generated test code and remove the default call to fail. fail("The
     * test case is a prototype."); }
     */
    /**
     * Test of OrdenConFecha method, of class CAgregarOrden.
     *
     * @Test public void testOrdenConFecha() {
     * System.out.println("OrdenConFecha"); Date F = null; CAgregarOrden
     * instance = new CAgregarOrden(); instance.OrdenConFecha(F); // TODO review
     * the generated test code and remove the default call to fail. fail("The
     * test case is a prototype."); }
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
     * Test Listar Productos Categoria
     */
    @Test(expected = Exception.class)
    public void testListarProdCat1() throws Exception {
        System.out.println("listarProductosCategoria No existe Categoria");
        CAgregarOrden instance = new CAgregarOrden();
        instance.listarProductosCategoria("Cosas");
    }

    @Test(expected = Exception.class)
    public void testListarProdCat2() throws Exception {
        System.out.println("listarProductosCategoria Categoria Compuesta");
        CAgregarOrden instance = new CAgregarOrden();
        instance.listarProductosCategoria("Equipos");
    }

    /**
     * Test Seleccionar Cliente
     */
    @Test(expected = Exception.class)
    public void testSeleccionarCliente1() throws Exception {
        System.out.println("testSeleccionarCliente No existe Cliente");
        CAgregarOrden instance = new CAgregarOrden();
        instance.seleccionarCliente("Falucho");
    }

    @Test(expected = Exception.class)
    public void testSeleccionarCliente2() throws Exception {
        System.out.println("testSeleccionarCliente Es Proveedor");
        CAgregarOrden instance = new CAgregarOrden();
        instance.seleccionarCliente("Tim1");
    }

    /**
     * Test Alta Orden
     */
    @Test(expected = Exception.class)
    public void testAltaOrden() throws Exception {
        System.out.println("testAltaOrden Sin Lineas");
        CAgregarOrden instance = new CAgregarOrden();
        instance.altaOrden();
    }
}
