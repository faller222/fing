package Logica;

import Controladores.CAgregarProducto;
import DataTypes.DataCategoria;
import DataTypes.DataProducto;
import Extras.Utils;
import java.awt.image.BufferedImage;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CAgregarProductoTest {

    public CAgregarProductoTest() {
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
     * Test of listarProveedores method, of class CAgregarProducto.
     */
    @Test
    public void testListarProveedores() {
        System.out.println("listarProveedores");
        CAgregarProducto instance = new CAgregarProducto();
        Map result = instance.listarProveedores();
    }

    /**
     * Test of ingresarDataProducto method, of class CAgregarProducto.
     *
     * @Test public void testIngresarDataProducto() throws Exception {
     * System.out.println("ingresarDataProducto"); DataProducto dataP = null;
     * CAgregarProducto instance = new CAgregarProducto();
     * instance.ingresarDataProducto(dataP); // TODO review the generated test
     * code and remove the default call to fail. fail("The test case is a
     * prototype."); }
     */
    /**
     * Test of listarCategorias method, of class CAgregarProducto.
     */
    @Test
    public void testListarCategorias() {
        System.out.println("listarCategorias");
        CAgregarProducto instance = new CAgregarProducto();
        DataCategoria result = instance.listarCategorias();
    }

    /**
     * Test of agregarCategoriaAProducto method, of class CAgregarProducto.
     *
     * @Test public void testAgregarCategoriaAProducto() throws Exception {
     * System.out.println("agregarCategoriaAProducto"); String nomCat = "";
     * CAgregarProducto instance = new CAgregarProducto();
     * instance.agregarCategoriaAProducto(nomCat); // TODO review the generated
     * test code and remove the default call to fail. fail("The test case is a
     * prototype."); }
     */
    /**
     * Test of agregarImagenAProducto method, of class CAgregarProducto.
     */
    @Test
    public void testAgregarImagenAProducto() throws Exception {
        System.out.println("agregarImagenAProducto");
        int w = 100;
        int h = 100;
        int type = BufferedImage.TYPE_INT_RGB;  // other options
        int Pos = 1;
        CAgregarProducto CAP = new CAgregarProducto();
        CAP.ingresarDataProducto(new DataProducto("iPhone 55", 55, (float) 1999, "Tim1"));
        CAP.agregarCategoriaAProducto("iPhone");
        CAP.agregarCategoriaAProducto("iOS");
        CAP.agregarCategoriaAProducto("Apple");
        CAP.altaProducto();
        CAP.agregarImagenAProducto(null, Pos);
    }

    /**
     * Test of altaProducto method, of class CAgregarProducto.
     *
     * @Test public void testAltaProducto() throws Exception {
     * System.out.println("altaProducto"); CAgregarProducto instance = new
     * CAgregarProducto(); instance.altaProducto(); // TODO review the generated
     * test code and remove the default call to fail. fail("The test case is a
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
     * Test Ingresar Data Producto.
     */
    @Test(expected = Exception.class)
    public void testIngresarDataProd1() throws Exception {
        System.out.println("testIngresarDataProd no Existe usuario");
        CAgregarProducto instance = new CAgregarProducto();
        DataProducto dp = new DataProducto("iPhone 55", 55, (float) 1999, "Falucho");
        instance.ingresarDataProducto(dp);
    }

    @Test(expected = Exception.class)
    public void testIngresarDataProd2() throws Exception {
        System.out.println("testIngresarDataProd usuario es Cliente");
        CAgregarProducto instance = new CAgregarProducto();
        DataProducto dp = new DataProducto("iPhone 55", 55, (float) 1999, "Dan");
        instance.ingresarDataProducto(dp);
    }

    @Test(expected = Exception.class)
    public void testIngresarDataProd3() throws Exception {
        System.out.println("testIngresarDataProd usuario es Cliente");
        CAgregarProducto instance = new CAgregarProducto();
        DataProducto dp = new DataProducto("iPhone 5", 55, (float) 1999, "Tim1");
        instance.ingresarDataProducto(dp);
    }

    @Test(expected = Exception.class)
    public void testIngresarDataProd4() throws Exception {
        System.out.println("testIngresarDataProd usuario es Cliente");
        CAgregarProducto instance = new CAgregarProducto();
        DataProducto dp = new DataProducto("iPhone 55", 1, (float) 1999, "Tim1");
        instance.ingresarDataProducto(dp);
    }

    /**
     * Test Agregar Categoria a Producto.
     */
    @Test(expected = Exception.class)
    public void testIngresarCateAProd1() throws Exception {
        System.out.println("testIngresarCateAProd Memoria no seteada");
        CAgregarProducto instance = new CAgregarProducto();
        DataProducto dp = new DataProducto("iPhone 55", 177, (float) 1999, "Tim1");
        instance.agregarCategoriaAProducto("Apple");
    }

    @Test(expected = Exception.class)
    public void testIngresarCateAProd2() throws Exception {
        System.out.println("testIngresarCateAProd No existe Categoria");
        CAgregarProducto instance = new CAgregarProducto();
        DataProducto dp = new DataProducto("iPhone 55", 177, (float) 1999, "Tim1");
        instance.ingresarDataProducto(dp);
        instance.agregarCategoriaAProducto("Cosas");
    }

    @Test(expected = Exception.class)
    public void testIngresarCateAProd3() throws Exception {
        System.out.println("testIngresarCateAProd Categoria Compuesta");
        CAgregarProducto instance = new CAgregarProducto();
        DataProducto dp = new DataProducto("iPhone 55", 177, (float) 1999, "Tim1");
        instance.ingresarDataProducto(dp);
        instance.agregarCategoriaAProducto("Equipos");
    }

    /**
     * Test Alta Producto.
     */
    @Test(expected = Exception.class)
    public void testAltaProducto1() throws Exception {
        System.out.println("testAltaProducto Memoria no seteada");
        CAgregarProducto instance = new CAgregarProducto();
        instance.altaProducto();
    }

    @Test(expected = Exception.class)
    public void testAltaProducto2() throws Exception {
        System.out.println("testAltaProducto Sin Categorias");
        CAgregarProducto instance = new CAgregarProducto();
        DataProducto dp = new DataProducto("iPhone 55", 177, (float) 1999, "Tim1");
        instance.ingresarDataProducto(dp);
        instance.altaProducto();
    }

    /**
     * Test Agregar Imagen.
     */
    @Test(expected = Exception.class)
    public void testAgregarImagen() throws Exception {
        System.out.println("testAgregarImagen memoria no seteada");
        CAgregarProducto instance = new CAgregarProducto();
        instance.agregarImagenAProducto(null, 1);
    }
}
