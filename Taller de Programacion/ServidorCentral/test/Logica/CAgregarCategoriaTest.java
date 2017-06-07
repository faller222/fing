/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import Controladores.CAgregarCategoria;
import DataTypes.DataCategoria;
import Extras.Utils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Helen
 */
public class CAgregarCategoriaTest {

    public CAgregarCategoriaTest() {
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
     * Test of listarCategorias method, of class CAgregarCategoria.
     */
    @Test
    public void testListarCategorias() {
        System.out.println("listarCategorias");
        CAgregarCategoria instance = new CAgregarCategoria();
        DataCategoria result = instance.listarCategorias();
    }

    /**
     * Test of buscarCategoria method, of class CAgregarCategoria.
     */
    @Test
    public void testBuscarCategoria() {
        System.out.println("buscarCategoria");
        String nCat = "Celulares";
        CAgregarCategoria instance = new CAgregarCategoria();
        DataCategoria result = instance.buscarCategoria(nCat);
    }

    /**
     * Test of altaCategoria method, of class CAgregarCategoria.
     */
    @Test
    public void testAltaCategoria_String_Boolean() throws Exception {
        System.out.println("altaCategoria_String_Boolean");
        String Nueva = "CategTest";
        CAgregarCategoria instance = new CAgregarCategoria();
        instance.altaCategoria(Nueva, true);
    }

    /**
     * Test of altaCategoria method, of class CAgregarCategoria.
     */
    @Test
    public void testAltaCategoria_3args() throws Exception {
        System.out.println("altaCategoria_3args");
        String Nueva = "HijaC";
        Boolean esSimple = false;
        String Padre = "Celulares";
        CAgregarCategoria instance = new CAgregarCategoria();
        instance.altaCategoria(Nueva, esSimple, Padre);
    }
}
