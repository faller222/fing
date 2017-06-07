/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import Controladores.CVerModProducto;
import DataTypes.DataCategoria;
import DataTypes.DataProducto;
import Extras.Utils;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Helen
 */
public class CVerModProductoTest {

    public CVerModProductoTest() {
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
     * Test of listarCategorias method, of class CVerModProducto.
     */
    @Test
    public void testListarCategorias() {
        System.out.println("listarCategorias");
        CVerModProducto instance = new CVerModProducto();
        DataCategoria result = instance.listarCategorias();
    }

    /**
     * Test of seleccionarCategoria method, of class CVerModProducto.
     */
    @Test
    public void testSeleccionarCategoriaListarProd() throws Exception {
        System.out.println("seleccionarCategoria");
        String nCat = "Apple";
        CVerModProducto instance = new CVerModProducto();
        DataCategoria result = instance.seleccionarCategoria(nCat);
        Map prods = instance.listarProductosCategoria();
    }

    /**
     * Test of listarProductosCategoria method, of class CVerModProducto.
     *
     * @Test public void testuctosCategoria() throws Exception {
     * System.out.println("listarProductosCategoria"); CVerModProducto instance
     * = new CVerModProducto(); Map expResult = null;
     *
     * assertEquals(expResult, result); // TODO review the generated test code
     * and remove the default call to fail. fail("The test case is a
     * prototype."); }
     */
    /**
     * Test of seleccionarProducto method, of class CVerModProducto.
     */
    @Test
    public void testSeleccionarProductoListarCate() throws Exception {
        System.out.println("seleccionarProducto");
        Integer NRef = 1;
        CVerModProducto instance = new CVerModProducto();
        DataProducto result = instance.seleccionarProducto(NRef);
        Map cates = instance.listarCategoriaProductos();
    }

    /**
     * Test of listarCategoriaProductos method, of class CVerModProducto.
     *
     * @Test public void testgoriaProductos() throws Exception {
     * System.out.println("listarCategoriaProductos"); CVerModProducto instance
     * = new CVerModProducto(); Map expResult = null;
     *
     * assertEquals(expResult, result); // TODO review the generated test code
     * and remove the default call to fail. fail("The test case is a
     * prototype."); }
     */
    /**
     * Test of modificarProducto method, of class CVerModProducto.
     */
    @Test(expected = Exception.class)
    public void testModificarProducto() throws Exception {
        System.out.println("modificarProducto");
        CVerModProducto instance = new CVerModProducto();
        DataProducto dp = new DataProducto("iPhone 5", 1, (float) 199, "Tim1");
        DataCategoria result = instance.seleccionarCategoria("Apple");
        instance.seleccionarProducto(1);
        instance.modificarProducto(dp);
    }

    /**
     * Test of buscarProd method, of class CVerModProducto.
     *
     * @Test public void testBuscarProd() { System.out.println("buscarProd");
     * String Clave = ""; Integer Pag = 1; int Tipo = 0; CVerModProducto
     * instance = new CVerModProducto(); List expResult = null; List result =
     * instance.buscarProd(Clave, Pag, Tipo); }
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
     * Test Listar Categorias Producto.
     */
    @Test(expected = Exception.class)
    public void testListarCateProd1() throws Exception {
        System.out.println("testListarCateProd1 no selecciono Producto");
        CVerModProducto instance = new CVerModProducto();
        instance.listarCategoriaProductos();
    }

    /**
     * Test Seleccionar Categoria.
     */
    @Test(expected = Exception.class)
    public void testSeleccionarCategoria1() throws Exception {
        System.out.println("testSeleccionarCategoria1 no existe Categoria");
        CVerModProducto instance = new CVerModProducto();
        instance.seleccionarCategoria("Hola");
    }

    /**
     * Test Seleccionar Producto.
     */
    @Test(expected = Exception.class)
    public void testSeleccionarProducto1() throws Exception {
        System.out.println("testSeleccionarProducto1 no existe Producto");
        CVerModProducto instance = new CVerModProducto();
        instance.seleccionarProducto(155);
    }

    /**
     * Test Listar Producto Categorias.
     */
    @Test(expected = Exception.class)
    public void testListarProdCate1() throws Exception {
        System.out.println("testListarProdCate1 no selecciono Categoria");
        CVerModProducto instance = new CVerModProducto();
        instance.listarProductosCategoria();
    }

    /**
     * Test Listar Producto Categorias.
     */
    @Test(expected = Exception.class)
    public void testListarProdCate2() throws Exception {
        System.out.println("testListarProdCate2 selecciono Categoria Comuesta");
        CVerModProducto instance = new CVerModProducto();
        instance.seleccionarCategoria("Equipos");
        instance.listarProductosCategoria();
    }

    /**
     * Test Modificar Producto.
     */
    @Test(expected = Exception.class)
    public void testModificarProducto1() throws Exception {
        System.out.println("testModificarProducto1 no selecciono Producto");
        CVerModProducto instance = new CVerModProducto();
        DataProducto dp = new DataProducto("iPhone 5s", 1, (float) 299, "Tim1");
        instance.modificarProducto(dp);
    }

    @Test(expected = Exception.class)
    public void testModificarProducto2() throws Exception {
        System.out.println("testModificarProducto2 No es el mismo Producto");
        CVerModProducto instance = new CVerModProducto();
        instance.seleccionarProducto(2);
        DataProducto dp = new DataProducto("iPhone 5s", 1, (float) 299, "Tim1");
        DataCategoria dC = instance.seleccionarCategoria("Apple");
        dp.agregarCategorias(dC);
        instance.modificarProducto(dp);
    }

    @Test
    public void testModificarProducto3() throws Exception {
        System.out.println("testModificarProducto3 Correcto");
        CVerModProducto instance = new CVerModProducto();
        instance.seleccionarProducto(1);
        DataProducto dp = new DataProducto("iPhone 5s", 1, (float) 299, "Tim1");
        DataCategoria dC = instance.seleccionarCategoria("Apple");
        dp.agregarCategorias(dC);
        instance.modificarProducto(dp);
    }

    /**
     * Test Buscar Producto.
     */
    @Test
    public void testBuscarProducto1() {
        System.out.println("testBuscarProducto1 por Defecto");
        CVerModProducto instance = new CVerModProducto();
        instance.buscarProd("", 0, 0);
    }

    @Test
    public void testBuscarProducto2() {
        System.out.println("testBuscarProducto1 por Nombre");
        CVerModProducto instance = new CVerModProducto();
        instance.buscarProd("", 1, 1);
    }

    @Test
    public void testBuscarProducto3() {
        System.out.println("testBuscarProducto1 por Precio");
        CVerModProducto instance = new CVerModProducto();
        instance.buscarProd("", 0, 2);
    }
}
