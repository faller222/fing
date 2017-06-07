package Logica;

import Conceptos.Usuario;
import Controladores.CSesion;
import DataTypes.DataOrdenCompra;
import DataTypes.DataProveedor;
import DataTypes.DataUsuario;
import Extras.Utils;
import java.util.Date;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CSesionTest {

    public CSesionTest() {
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
     * Test of inicioSesion method, of class CSesion.
     */
    @Test
    public void testInicioSesion_String_String() throws Exception {
        System.out.println("inicioSesion");
        String nm = "Dan";
        String contra = "danr";
        CSesion instance = new CSesion();
        DataUsuario result = instance.inicioSesion(nm, contra);
        Usuario user = instance.getUsuario();
        DataUsuario infoP = instance.verInfoPerfil();

        Integer cant = 2;
        Integer num = 2;
        instance.agregaLinea(cant, num);

        DataOrdenCompra carrito = instance.verCarrito();
        instance.generarOrden();

        boolean puedeComent = instance.puedeComentar(2); // lo compro asi que puede
        instance.comentar(2, "Yo lo compré");

        //instance.responder(2, "Si", 1);
    }

    /**
     * Test de Constructor.
     */
    @Test
    public void testCSesionConstructor() {
        System.out.println("testCSesionConstructor");
        CSesion instance = new CSesion();
    }

    /**
     * Test de Inicio Sesion X Nick.
     */
    @Test
    public void testCSesionInicioxNick() throws Exception {
        System.out.println("testCSesionInicioxNick");
        CSesion instance = new CSesion();
        DataUsuario dU = instance.inicioSesion("Tim1", "tim123");
    }

    /**
     * Test de Inicio Sesion X Mail.
     */
    @Test
    public void testCSesionInicioxMail() throws Exception {
        System.out.println("testCSesionInicioxMail");
        CSesion instance = new CSesion();
        DataUsuario dU = instance.inicioSesion("tim.cook@apple.com", "tim123");
    }

    /**
     * Test de Inicio Sesion X DataUsuario.
     */
    @Test
    public void testCSesionInicioxData() throws Exception {
        System.out.println("testCSesionInicioxData");
        CSesion instance = new CSesion();
        DataUsuario dU = instance.inicioSesion("tim.cook@apple.com", "tim123");
        CSesion otra = new CSesion();
        otra.inicioSesion(dU);
    }

    /**
     * Test de GetUsuario.
     */
    @Test
    public void testCSesionGetUsuario() throws Exception {
        System.out.println("testCSesionGetUsuario");
        CSesion instance = new CSesion();
        instance.inicioSesion("tim.cook@apple.com", "tim123");
        Usuario U = instance.getUsuario();
    }

    /**
     * Test de VerInfo Usuario.
     */
    @Test
    public void testCSesionVerInfoUsuario() throws Exception {
        System.out.println("testCSesionVerInfoUsuario");
        CSesion instance = new CSesion();
        instance.inicioSesion("tim.cook@apple.com", "tim123");
        DataUsuario dU = instance.verInfoPerfil();
    }

    /**
     * Test de VerCarrito.
     */
    @Test(expected = Exception.class)
    public void testCSesionVerCarrito1() throws Exception {
        System.out.println("testCSesionVerCarrito No Login");
        CSesion instance = new CSesion();
        instance.verCarrito();
    }

    /**
     * Test de VerCarrito.
     */
    @Test(expected = Exception.class)
    public void testCSesionVerCarrito2() throws Exception {
        System.out.println("testCSesionVerCarrito Log Proveedor");
        CSesion instance = new CSesion();
        instance.inicioSesion("tim.cook@apple.com", "tim123");
        instance.verCarrito();
    }

    /**
     * Test de VerCarrito.
     */
    @Test
    public void testCSesionVerCarrito3() throws Exception {
        System.out.println("testCSesionVerInfoUsuario");
        CSesion instance = new CSesion();
        instance.inicioSesion("Dan", "danr");
        instance.verCarrito();
    }

    /**
     * Test de Agregar Linea.
     */
    @Test(expected = Exception.class)
    public void testCSesionAgregarLiena1() throws Exception {
        System.out.println("testCSesionAgregarLiena No Login");
        CSesion instance = new CSesion();
        instance.agregaLinea(1, 2);
    }

    /**
     * Test de Agregar Linea.
     */
    @Test(expected = Exception.class)
    public void testCSesionAgregarLiena2() throws Exception {
        System.out.println("testCSesionAgregarLiena Es Proveedor");
        CSesion instance = new CSesion();
        instance.inicioSesion("tim.cook@apple.com", "tim123");
        instance.agregaLinea(1, 2);
    }

    /**
     * Test de Agregar Linea.
     */
    @Test(expected = Exception.class)
    public void testCSesionAgregarLiena3() throws Exception {
        System.out.println("testCSesionAgregarLiena cantidad Negativa");
        CSesion instance = new CSesion();
        instance.inicioSesion("Dan", "danr");
        instance.agregaLinea(-1, 2);
    }

    /**
     * Test de Agregar Linea.
     */
    @Test
    public void testCSesionAgregarLiena4() throws Exception {
        System.out.println("testCSesionAgregarLiena normal");
        CSesion instance = new CSesion();
        instance.inicioSesion("Dan", "danr");
        instance.agregaLinea(1, 2);
    }

    /**
     * Test de Agregar Linea.
     */
    @Test(expected = Exception.class)
    public void testCSesionAgregarLiena5() throws Exception {
        System.out.println("testCSesionAgregarLiena cantidad Negativa(2)");
        CSesion instance = new CSesion();
        instance.inicioSesion("Dan", "danr");
        instance.agregaLinea(1, 2);
        instance.agregaLinea(-2, 2);
    }

    /**
     * Test de Agregar Linea.
     */
    @Test
    public void testCSesionAgregarLiena6() throws Exception {
        System.out.println("testCSesionAgregarLiena 2 lineas iguales");
        CSesion instance = new CSesion();
        instance.inicioSesion("Dan", "danr");
        instance.agregaLinea(1, 2);
        instance.agregaLinea(1, 2);
    }

    /**
     * Test de Generar Orden.
     */
    @Test(expected = Exception.class)
    public void testCSesionGenerarOrden1() throws Exception {
        System.out.println("testCSesionGenerarOrden Provee");
        CSesion instance = new CSesion();
        instance.generarOrden();
    }

    /**
     * Test de Generar Orden.
     */
    @Test(expected = Exception.class)
    public void testCSesionGenerarOrden2() throws Exception {
        System.out.println("testCSesionGenerarOrden Carro Vacio");
        CSesion instance = new CSesion();
        instance.inicioSesion("Dan", "danr");
        instance.generarOrden();
    }

    /**
     * Test de Generar Orden.
     */
    @Test
    public void testCSesionGenerarOrden3() throws Exception {
        System.out.println("testCSesionGenerarOrden");
        CSesion instance = new CSesion();
        instance.inicioSesion("Dan", "danr");
        instance.agregaLinea(1, 2);
        instance.generarOrden();
    }

    /**
     * Test de PuedeComentar.
     */
    @Test
    public void testCSesionPuedeComentar1() throws Exception {
        System.out.println("testCSesionPuedeComentar");
        CSesion instance = new CSesion();
        instance.inicioSesion("Dan", "danr");
        instance.puedeComentar(1);
    }

    /**
     * Test de PuedeComentar.
     */
    @Test
    public void testCSesionPuedeComentar2() {
        System.out.println("testCSesionPuedeComentar no logueo");
        CSesion instance = new CSesion();
        instance.puedeComentar(1);
    }

    /**
     * Test de PuedeComentar.
     */
    @Test
    public void testCSesionPuedeComentar3() throws Exception {
        System.out.println("testCSesionPuedeComentar Es Proveedor");
        CSesion instance = new CSesion();
        instance.inicioSesion("Tim1", "tim123");
        instance.puedeComentar(1);
    }

    /**
     * Test de PuedeComentar.
     */
    @Test
    public void testCSesionPuedeComentar4() {
        System.out.println("testCSesionPuedeComentar no compro");
        CSesion instance = new CSesion();
        instance.puedeComentar(13);
    }

    /**
     * Test de Comentar.
     */
    @Test(expected = Exception.class)
    public void testCSesionComentar1() throws Exception {
        System.out.println("testCSesionComentar No logue");
        CSesion instance = new CSesion();
        instance.comentar(1, "hola");
    }

    /**
     * Test de Comentar.
     */
    @Test(expected = Exception.class)
    public void testCSesionComentar2() throws Exception {
        System.out.println("testCSesionComentar Es Provee");
        CSesion instance = new CSesion();
        instance.inicioSesion("tim.cook@apple.com", "tim123");
        instance.comentar(1, "hola");
    }

    /**
     * Test de Comentar.
     */
    @Test
    public void testCSesionComentar3() throws Exception {
        System.out.println("testCSesionComentar Normal");
        CSesion instance = new CSesion();
        instance.inicioSesion("Dan", "danr");
        instance.comentar(1, "hola");
    }

    /**
     * Test de Responder.
     */
    @Test(expected = Exception.class)
    public void testCSesionResponder1() throws Exception {
        System.out.println("testCSesionResponder No logue");
        CSesion instance = new CSesion();
        instance.responder(1, "hola", 1);
    }

    /**
     * Test de Responder.
     */
    @Test(expected = Exception.class)
    public void testCSesionResponder2() throws Exception {
        System.out.println("testCSesionResponder es proveedor");
        CSesion instance = new CSesion();
        instance.inicioSesion("tim.cook@apple.com", "tim123");
        instance.responder(1, "hola", 1);
    }

    /**
     * Test de Responder.
     */
    @Test(expected = Exception.class)
    public void testCSesionResponder3() throws Exception {
        System.out.println("testCSesionResponder comentario no pertenece a producto");
        CSesion instance = new CSesion();
        instance.inicioSesion("Dan", "danr");
        instance.responder(1, "hola", 13);
    }

    /**
     * Test de Responder.
     */
    @Test
    public void testCSesionResponder4() throws Exception {
        System.out.println("testCSesionResponder Correcto");
        CSesion instance = new CSesion();
        instance.inicioSesion("Dan", "danr");
        instance.responder(1, "hola", 1);
    }

    /**
     * Test de Responder.
     */
    @Test(expected = Exception.class)
    public void testCSesionResponder5() throws Exception {
        System.out.println("testCSesionResponder Producto No existe");
        CSesion instance = new CSesion();
        instance.inicioSesion("Dan", "danr");
        instance.responder(0, "hola", 1);
    }

    //Fito segunda parte
    /**
     * Test de Comentar.
     */
    @Test(expected = Exception.class)
    public void testCSesionComentarCF1() throws Exception {
        System.out.println("testCSesionComentarCF No logue");
        CSesion instance = new CSesion();
        instance.ComentarioConFecha(1, "hola", new Date());
    }

    /**
     * Test de Comentar.
     */
    @Test(expected = Exception.class)
    public void testCSesionComentarCF2() throws Exception {
        System.out.println("testCSesionComentarCF Es Provee");
        CSesion instance = new CSesion();
        instance.inicioSesion("tim.cook@apple.com", "tim123");
        instance.ComentarioConFecha(1, "hola", new Date());
    }

    /**
     * Test de Comentar.
     */
    @Test
    public void testCSesionComentarCF3() throws Exception {
        System.out.println("testCSesionComentarCF Normal");
        CSesion instance = new CSesion();
        instance.inicioSesion("Dan", "danr");
        instance.ComentarioConFecha(1, "hola", new Date());
    }

    /**
     * Test de Responder.
     */
    @Test(expected = Exception.class)
    public void testCSesionResponderCF1() throws Exception {
        System.out.println("testCSesionResponder No logue");
        CSesion instance = new CSesion();
        instance.responderConFecha(1, "hola", 1, new Date());
    }

    /**
     * Test de Responder.
     */
    @Test(expected = Exception.class)
    public void testCSesionResponderCF2() throws Exception {
        System.out.println("testCSesionResponder es proveedor");
        CSesion instance = new CSesion();
        instance.inicioSesion("tim.cook@apple.com", "tim123");
        instance.responderConFecha(1, "hola", 1, new Date());
    }

    /**
     * Test de Responder.
     */
    @Test(expected = Exception.class)
    public void testCSesionResponderCF3() throws Exception {
        System.out.println("testCSesionResponder comentario no pertenece a producto");
        CSesion instance = new CSesion();
        instance.inicioSesion("Dan", "danr");
        instance.responderConFecha(1, "hola", 13, new Date());
    }

    /**
     * Test de Responder.
     */
    @Test
    public void testCSesionResponderCF4() throws Exception {
        System.out.println("testCSesionResponder Correcto");
        CSesion instance = new CSesion();
        instance.inicioSesion("Dan", "danr");
        instance.responderConFecha(1, "hola", 1, new Date());
    }

    /**
     * Test de Responder.
     */
    @Test(expected = Exception.class)
    public void testCSesionResponderCF5() throws Exception {
        System.out.println("testCSesionResponder Producto No existe");
        CSesion instance = new CSesion();
        instance.inicioSesion("Dan", "danr");
        instance.responderConFecha(0, "hola", 1, new Date());
    }
    //inicio x data 2

    @Test
    public void testCSesionInicioxData2() {
        System.out.println("testCSesionInicioxData");
        CSesion instance = new CSesion();
        DataProveedor TC = new DataProveedor("153", "tim123", "tim.cook@apple.com", "Tim", "Cook", new Date(60, 10, 1));
        instance.inicioSesion(TC);
    }
}
