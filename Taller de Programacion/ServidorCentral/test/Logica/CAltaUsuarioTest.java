package Logica;

import Controladores.CAltaUsuario;
import DataTypes.DataProveedor;
import DataTypes.DataUsuario;
import Extras.Utils;
import java.util.Date;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CAltaUsuarioTest {

    public CAltaUsuarioTest() {
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
     * Test of verificarMail method, of class CAltaUsuario.
     */
    @Test
    public void testVerificarMail() {
        System.out.println("verificarMail");
        String mail = "tim.cook@apple.com";
        CAltaUsuario instance = new CAltaUsuario();
        boolean expResult = false;
        boolean result = instance.verificarMail(mail);
        assertEquals(expResult, result);
    }

    @Test
    public void testVerificarMail2() {
        System.out.println("verificarMail");
        String mail = null;
        CAltaUsuario instance = new CAltaUsuario();
        boolean expResult = true;
        boolean result = instance.verificarMail(mail);
        assertEquals(expResult, result);
    }

    /**
     * Test of verificarNickname method, of class CAltaUsuario.
     */
    @Test
    public void testVerificarNickname() {
        System.out.println("verificarNickname");
        String Nickname = "Tim1";
        CAltaUsuario instance = new CAltaUsuario();
        boolean expResult = false;
        boolean result = instance.verificarNickname(Nickname);
    }

    /**
     * Test of ingresarDataUsuario method, of class CAltaUsuario.
     *
     * @Test public void testIngresarDataUsuario() throws Exception {
     * System.out.println("ingresarDataUsuario"); DataUsuario dataU = null;
     * CAltaUsuario instance = new CAltaUsuario();
     * instance.ingresarDataUsuario(dataU); // TODO review the generated test
     * code and remove the default call to fail. fail("The test case is a
     * prototype."); }
     */
    /**
     * Test of altaUsuario method, of class CAltaUsuario.
     *
     * @Test public void testAltaUsuario() throws Exception {
     * System.out.println("altaUsuario"); CAltaUsuario instance = new
     * CAltaUsuario(); instance.altaUsuario(); // TODO review the generated test
     * code and remove the default call to fail. fail("The test case is a
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
    //Ingresar Datos
    @Test(expected = Exception.class)
    public void testIngresarDU1() throws Exception {
        System.out.println("testIngresarDU Ya existe Nick");
        DataProveedor TC = new DataProveedor("Tim1", "tim123", "tim@apple.com", "Tim", "Cook", new Date(60, 10, 1));
        CAltaUsuario instance = new CAltaUsuario();
        instance.ingresarDataUsuario(TC);
    }

    @Test(expected = Exception.class)
    public void testIngresarDU2() throws Exception {
        System.out.println("testIngresarDU Ya existe Mail");
        DataProveedor TC = new DataProveedor("Juan", "tim123", "tim.cook@apple.com", "Tim", "Cook", new Date(60, 10, 1));
        CAltaUsuario instance = new CAltaUsuario();
        instance.ingresarDataUsuario(TC);
    }
    //Alta Usuario

    @Test(expected = Exception.class)
    public void testAltaUsuario() throws Exception {
        System.out.println("testAltaUsuario memoria no seteada");
        CAltaUsuario instance = new CAltaUsuario();
        instance.altaUsuario();
    }
}
