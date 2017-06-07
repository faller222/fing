package Logica;

import Controladores.CVerInfoProveedor;
import DataTypes.DataProveedor;
import Extras.Utils;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CVerInfoProveedorTest {

    public CVerInfoProveedorTest() {
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
     * Test of listarProveedores method, of class CVerInfoProveedor.
     */
    @Test
    public void testListarProveedores() {
        System.out.println("listarProveedores");
        CVerInfoProveedor instance = new CVerInfoProveedor();
        Map result = instance.listarProveedores();
    }

    /**
     * Test of seleccionarProveedor method, of class CVerInfoProveedor.
     */
    @Test
    public void testSeleccionarProveedorListarProds() throws Exception {
        System.out.println("seleccionarProveedor");
        String nick = "Tim1";
        CVerInfoProveedor instance = new CVerInfoProveedor();
        DataProveedor result = instance.seleccionarProveedor(nick);
        List prods = instance.listarProductosProveedor();
    }

    /**
     * Test of listarProductosProveedor method, of class CVerInfoProveedor.
     *
     * @Test public void testListarProductosProveedor() throws Exception {
     * System.out.println("listarProductosProveedor"); CVerInfoProveedor
     * instance = new CVerInfoProveedor(); List expResult = null;
     *
     * assertEquals(expResult, result); // TODO review the generated test code
     * and remove the default call to fail. fail("The test case is a
     * prototype."); }
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
     * Test seleccionarProveedor
     */
    @Test(expected = Exception.class)
    public void testSeleccionarProveedor1() throws Exception {
        System.out.println("seleccionarProveedor no existe");
        CVerInfoProveedor instance = new CVerInfoProveedor();
        instance.seleccionarProveedor("Falucho");
    }

    @Test(expected = Exception.class)
    public void testSeleccionarProveedor2() throws Exception {
        System.out.println("seleccionarProveedor Es Cliente");
        CVerInfoProveedor instance = new CVerInfoProveedor();
        instance.seleccionarProveedor("Dan");
    }

    /**
     * Test Listar Productos Proveedor
     */
    @Test(expected = Exception.class)
    public void testListProdProvee() throws Exception {
        System.out.println("testListProdProvee Memoria NO seteada");
        CVerInfoProveedor instance = new CVerInfoProveedor();
        instance.listarProductosProveedor();
    }
}
