package Extras;

import DataTypes.DataCliente;
import DataTypes.DataProducto;
import DataTypes.DataProveedor;
import Controladores.CAgregarCategoria;
import Controladores.CAgregarOrden;
import Controladores.CAgregarProducto;
import Controladores.CAltaUsuario;
import Controladores.CPuntaje;
import Controladores.CReclamo;
import Controladores.CSesion;
import Controladores.CVerCanOrden;
import Manejadores.MCategoria;
import Manejadores.MOrdenCompra;
import Manejadores.MProducto;
import Manejadores.MUsuario;
import java.util.Date;
import org.jasypt.util.password.BasicPasswordEncryptor;

public class Utils {

    public static void Cargar() {
        Cargar(true);
    }

    public static void Cargar(boolean Img) {
        try {
            UtilImage UI = new UtilImage();
            String PathImg = UI.Path() + "Imagenes/";
            //String PathImg = "C:\\Users\\Faller\\Documents\\NetBeansProjects\\Tarea2\\Estacion\\src\\Imagenes\\";

            System.out.println("\n\n\n\n\n\n\n\n\n\n\n");
            System.out.println("Comienza Cargar");
            System.out.println("\tCreando Usuarios...");
            System.out.println("\t\tTim1");
            DataProveedor TC = new DataProveedor("Tim1", "tim123", "tim.cook@apple.com", "Tim", "Cook", new Date(60, 10, 1));
            System.out.println("\t\tEddy");
            DataProveedor EC = new DataProveedor("Eddy", "edd", "eddy.cue@samsung.com", "Eduardo", "Cue", new Date(65, 8, 2));
            System.out.println("\t\tCraigX");
            DataProveedor CF = new DataProveedor("CraigX", "craig@", "craig.feder@sony.com", "Craig", "Federighi", new Date(70, 4, 4));
            System.out.println("\t\tJohnny");
            DataProveedor JI = new DataProveedor("Johnny", "john", "johnny.ive@outlook.com", "Jonathan", "Ive", new Date(67, 1, 12));
            System.out.println("\t\tOpenPeter");
            DataProveedor PO = new DataProveedor("OpenPeter", "peter42", "peter.open@htc.com", "Peter", "Oppenhemier", new Date(63, 7, 5));
            System.out.println("\t\tDan");
            DataCliente DR = new DataCliente("Dan", "danr", "dan.riccio@gmail.com", "Daniel", "Riccio", new Date(63, 6, 5));
            System.out.println("\t\tPhil");
            DataCliente PS = new DataCliente("Phil", "philip61", "phil.schiller@gmail.com", "Philip", "Schiller", new Date(61, 9, 7));
            System.out.println("\t\tBruceS");
            DataCliente BS = new DataCliente("BruceS", "bruces", "bruce.sewell@gmail.com", "Bruce", "Sewell", new Date(59, 11, 3));
            System.out.println("\t\tJeffW");
            DataCliente JW = new DataCliente("JeffW", "jeffw", "jeff.williams@gmail.com", "Jeff", "Wiliams", new Date(64, 10, 27));
            System.out.println("\t\tRicky");
            DataCliente RR = new DataCliente("Ricky", "rickyr", "ricky.r@gmail.com", "Ricky", "Ricón", new Date(80, 7, 26));

            if (Img) {
                //Sube imagenes
                TC.setImagen(PathImg + "01.jpg");
                EC.setImagen(PathImg + "02.jpg");
                CF.setImagen(PathImg + "03.jpg");
                JI.setImagen(PathImg + "04.jpg");
                PS.setImagen(PathImg + "05.jpg");
            }

            //Info extra Proveedores
            TC.setSitioWeb("http://www.apple.com");
            TC.setCompania("Apple");
            EC.setSitioWeb("http://www.samsung.com");
            EC.setCompania("Samsung");
            CF.setSitioWeb("http://us.playstation.com");
            CF.setCompania("Sony");
            JI.setSitioWeb("http://www.xbox.com");
            JI.setCompania("Microsoft");
            PO.setSitioWeb("http://www.htc.com");
            PO.setCompania("HTC");


            System.out.println("\tAgregando Usuarios...");
            //Agrego Usuarios
            CAltaUsuario CAU;
            System.out.println("\t\tTC");
            CAU = new CAltaUsuario();
            CAU.ingresarDataUsuario(TC);
            CAU.altaUsuario();

            System.out.println("\t\tEC");
            CAU = new CAltaUsuario();
            CAU.ingresarDataUsuario(EC);
            CAU.altaUsuario();
            System.out.println("\t\tCF");
            CAU = new CAltaUsuario();
            CAU.ingresarDataUsuario(CF);
            CAU.altaUsuario();
            System.out.println("\t\tJI");
            CAU = new CAltaUsuario();
            CAU.ingresarDataUsuario(JI);
            CAU.altaUsuario();
            System.out.println("\t\tPO");
            CAU = new CAltaUsuario();
            CAU.ingresarDataUsuario(PO);
            CAU.altaUsuario();
            System.out.println("\t\tDR");
            CAU = new CAltaUsuario();
            CAU.ingresarDataUsuario(DR);
            CAU.altaUsuario();
            System.out.println("\t\tPS");
            CAU = new CAltaUsuario();
            CAU.ingresarDataUsuario(PS);
            CAU.altaUsuario();
            System.out.println("\t\tBS");
            CAU = new CAltaUsuario();
            CAU.ingresarDataUsuario(BS);
            CAU.altaUsuario();
            System.out.println("\t\tJW");
            CAU = new CAltaUsuario();
            CAU.ingresarDataUsuario(JW);
            CAU.altaUsuario();
            System.out.println("\t\tRR");
            CAU = new CAltaUsuario();
            CAU.ingresarDataUsuario(RR);
            CAU.altaUsuario();



            //Agregar Cates
            System.out.println("\tCargando Categorias...");
            CAgregarCategoria CAC = new CAgregarCategoria();
            //Primer Nivel
            System.out.println("\t\t Celulares");
            CAC.altaCategoria("Celulares", false);
            System.out.println("\t\t Apple");
            CAC.altaCategoria("Apple", true);
            System.out.println("\t\t Videojuegos");
            CAC.altaCategoria("Videojuegos", false);

            //Segundo Nivel
            System.out.println("\t\t Sistemas Operativos");
            CAC.altaCategoria("Sistemas Operativos", false, "Celulares");
            System.out.println("\t\t Equipos");
            CAC.altaCategoria("Equipos", false, "Celulares");
            System.out.println("\t\t Accesorios");
            CAC.altaCategoria("Accesorios", false, "Celulares");

            System.out.println("\t\t Playstation");
            CAC.altaCategoria("Playstation", true, "Videojuegos");
            System.out.println("\t\t Xbox");
            CAC.altaCategoria("Xbox", true, "Videojuegos");

            //Tercer Nivel
            System.out.println("\t\t iOS");
            CAC.altaCategoria("iOS", true, "Sistemas Operativos");
            System.out.println("\t\t Android");
            CAC.altaCategoria("Android", true, "Sistemas Operativos");
            System.out.println("\t\t Windows Phone");
            CAC.altaCategoria("Windows Phone", true, "Sistemas Operativos");
            System.out.println("\t\t Symbian");
            CAC.altaCategoria("Symbian", true, "Sistemas Operativos");
            System.out.println("\t\t Blackberry OS");
            CAC.altaCategoria("Blackberry OS", true, "Sistemas Operativos");
            System.out.println("\t\t Samsung");
            CAC.altaCategoria("Samsung", false, "Equipos");
            System.out.println("\t\t iPhone");
            CAC.altaCategoria("iPhone", true, "Equipos");
            System.out.println("\t\t Nexus");
            CAC.altaCategoria("Nexus", true, "Equipos");
            System.out.println("\t\t Blackberry");
            CAC.altaCategoria("Blackberry", true, "Equipos");
            System.out.println("\t\t Nokia");
            CAC.altaCategoria("Nokia", true, "Equipos");

            System.out.println("\t\t Protectores");
            CAC.altaCategoria("Protectores", true, "Accesorios");
            System.out.println("\t\t Baterías");
            CAC.altaCategoria("Baterías", true, "Accesorios");

            //Cuarto Nivel
            System.out.println("\t\t Galaxy S3");
            CAC.altaCategoria("Galaxy S3", true, "Samsung");
            System.out.println("\t\t Galaxy S4");
            CAC.altaCategoria("Galaxy S4", true, "Samsung");
            System.out.println("\t\t Galaxy Ace");
            CAC.altaCategoria("Galaxy Ace", true, "Samsung");



            System.out.println("\tCreando Productos...");
            System.out.println("\t\t iPhone 5");
            DataProducto IPH5 = new DataProducto("iPhone 5", 1, (float) 199, "Tim1");
            System.out.println("\t\t iPhone 4S");
            DataProducto IPH4 = new DataProducto("iPhone 4S", 2, (float) 99, "Tim1");
            System.out.println("\t\t Nexus4");
            DataProducto NEX4 = new DataProducto("Nexus4", 3, (float) 299, "Eddy");
            System.out.println("\t\t Samsung Galaxy S3");
            DataProducto GA3 = new DataProducto("Samsung Galaxy S3", 4, (float) 415, "Eddy");
            System.out.println("\t\t Samsung Galaxy S4");
            DataProducto GA4 = new DataProducto("Samsung Galaxy S4", 5, (float) 839.99, "Eddy");
            System.out.println("\t\t Galaxy Ace S5830");
            DataProducto AS5 = new DataProducto("Galaxy Ace S5830", 6, (float) 237, "Eddy");
            System.out.println("\t\t Protector de cuero para Galaxy");
            DataProducto PCG = new DataProducto("Protector de cuero para Galaxy", 7, (float) 3.5, "Eddy");
            System.out.println("\t\t Protector de aluminio para HTC");
            DataProducto PMH = new DataProducto("Protector de aluminio para HTC", 8, (float) 3.4, "OpenPeter");
            System.out.println("\t\t iPad Retina Display");
            DataProducto IRD = new DataProducto("iPad Retina Display", 9, (float) 499, "Tim1");
            System.out.println("\t\t iPad Mini");
            DataProducto IM = new DataProducto("iPad Mini", 10, (float) 329, "Tim1");
            System.out.println("\t\t Receptor inalambrico para Xbox");
            DataProducto RIX = new DataProducto("Receptor inalámbrico para Xbox", 11, (float) 10.99, "Johnny");
            System.out.println("\t\t Control inalámbrico para Xbox");
            DataProducto CIX = new DataProducto("Control inalambrico para Xbox", 12, (float) 27.27, "Johnny");
            System.out.println("\t\t Cable HDMI para PS3");
            DataProducto CHP = new DataProducto("Cable HDMI para PS3 ", 13, (float) 7.99, "CraigX");
            System.out.println("\t\t Control para PS3");
            DataProducto CP3 = new DataProducto("Control para PS3", 14, (float) 30.80, "CraigX");
            System.out.println("\t\t iPhone 5S");
            DataProducto IPH5S = new DataProducto("iPhone 5S", 15, (float) 199.0, "Tim1");
            System.out.println("\t\t iPhone 5C");
            DataProducto IPH5C = new DataProducto("iPhone 5C", 16, (float) 99.0, "Tim1");
            System.out.println("\t\t iPad Air");
            DataProducto IPAA = new DataProducto("iPad Air", 17, (float) 499.0, "Tim1");
            System.out.println("\t\t iPad Mini Retina Display");
            DataProducto IPAM = new DataProducto("iPad Mini Retina Display", 18, (float) 399.0, "Tim1");

            if (Img) {
                //Seteo imagenes a Productos
                IPH5.agregarImgs(PathImg + "p1.jpg", 0);
                IPH4.agregarImgs(PathImg + "p2.jpg", 0);
                NEX4.agregarImgs(PathImg + "p3.jpg", 0);
                NEX4.agregarImgs(PathImg + "p4.jpg", 1);
                GA4.agregarImgs(PathImg + "p5.jpg", 0);
                AS5.agregarImgs(PathImg + "p6.jpg", 0);
                IRD.agregarImgs(PathImg + "p7.png", 0);
                IM.agregarImgs(PathImg + "p8.jpg", 0);
            }

            //Mas Datos
            IPH5.setDescripcion("El último celular de Apple");
            IPH4.setDescripcion("El siguiente celular al iPhone 4");
            NEX4.setDescripcion("El celular de Google");
            GA3.setDescripcion("La versión S3 de la línea Samsung Galaxy");
            GA4.setDescripcion("La versión S4 de la línea Samsung Galaxy");
            AS5.setDescripcion("La versión Ace de la línea Samsung Galaxy");
            PCG.setDescripcion("Asombroso protector de cuero para Samsung Galaxy I900");
            PMH.setDescripcion("El mejor protector de aluminio para HTC Desire HD");
            IRD.setDescripcion("La última tableta de Apple con pantalla Retina");
            IM.setDescripcion("La primera tableta chica de Apple");
            RIX.setDescripcion("Receptor inalámbrico de color negro para controles de Xbox 360");
            CIX.setDescripcion("Control inalámbrico de 2.4 GHz para Xbox 360");
            CHP.setDescripcion("Es un cable HDMI para PS3");
            CP3.setDescripcion("Control inalámbrico Dualshock 3 de color azul para Playstation 3");
            IPH5S.setDescripcion("La evolución del iPhone 5, con Touch ID y A7");
            IPH5C.setDescripcion("Dale color a tu vida con esta nueva gama de iPhone");
            IPAA.setDescripcion("Más ligero, más delgado y con mejor procesador que el anterior iPad");
            IPAM.setDescripcion("Igual que el modelo anterior, sólo que con pantalla Retina");

            IPH5.setEspecificacion("Capacidad: 16 GB\nPeso: 112 g\nPantalla: 4”Versiones de Wifi: a/b/g/n");
            IPH4.setEspecificacion("Capacidad: 16 GB\nPeso: 140 g\nPantalla: 3.5”\nVersiones de Wifi: b/g/n");
            NEX4.setEspecificacion("Capacidad: 8 GB\nPeso: 139 g\nPantalla: 4.7”\nVersión de Android: 4.3");
            GA3.setEspecificacion("Dimensiones: 136.6 x 70.6 x 8.6 mm\nPeso: 133 g\nPantalla: 4.8”\nVersión de Android: 4.0.4");
            GA4.setEspecificacion("Dimensiones: 136.6 x 69.8 x 7.9 mm\nPeso: 130 g\nPantalla: 4.99”\nVersión de Android: 4.2.2");
            AS5.setEspecificacion("Dimensiones: 112.4 x 59.9 x 11.5 mm\nPeso: 113 g\nPantalla: 3.5”\nVersión de Android: 2.3");
            PCG.setEspecificacion("Dimensiones: 12.5 cm x 6.7 cm x 2.0 cm\nPeso: 44 g");
            PMH.setEspecificacion("Dimensiones: 12.4 cm x 7.0 cm x 1.3 cm\nPeso: 26 g");
            IRD.setEspecificacion("Capacidad: 16 GB\nPeso: 652 g\nPantalla: 9.7”\nProcesador: A6X");
            IM.setEspecificacion("Capacidad: 16 GB\nPeso: 308 g\nPantalla: 7.9”\nProcesador: A5");
            RIX.setEspecificacion("Dimensiones: 7.5 cm x 4.2 cm x 1.8 cm\nPeso: 111 g");
            CIX.setEspecificacion("Garantía: 3 meses\nDimensiones: 5.91 in x 4.33 in x 1.77 in\nPeso: 7.83 oz");
            CHP.setEspecificacion("Dimensiones: 0 in x 0 in x 0 in\nPeso: 7.83 oz");
            CP3.setEspecificacion("Dimensiones: 16.0 cm x 9.5 cm x 5.0 cm\nPeso: 184 g");
            IPH5S.setEspecificacion("Capacidad: 16 GB\nPeso: 112 g\nPantalla: 4”\nVersiones de Wifi: a/b/g/n");
            IPH5C.setEspecificacion("Capacidad: 16 GB\nPeso: 132 g\nPantalla: 4”\nVersiones de Wifi: a/b/g/n");
            IPAA.setEspecificacion("Capacidad: 16 GB\nPeso: 469 g\nPantalla: 9.7”\nVersiones de Wifi: a/b/g/n con MIMO");
            IPAM.setEspecificacion("Capacidad: 16 GB\nPeso: 331 g\nPantalla: 7.9”\nVersiones de Wifi: a/b/g/n con MIMO");

            System.out.println("\tAgregando Productos...");
            CAgregarProducto CAP;

            System.out.println("\t\t IPH5");
            CAP = new CAgregarProducto();
            CAP.ingresarDataProducto(IPH5);
            CAP.agregarCategoriaAProducto("iPhone");
            CAP.agregarCategoriaAProducto("iOS");
            CAP.agregarCategoriaAProducto("Apple");
            CAP.altaProducto();

            System.out.println("\t\t IPH4");
            CAP = new CAgregarProducto();
            CAP.ingresarDataProducto(IPH4);
            CAP.agregarCategoriaAProducto("iPhone");
            CAP.agregarCategoriaAProducto("iOS");
            CAP.agregarCategoriaAProducto("Apple");
            CAP.altaProducto();

            System.out.println("\t\t NEX4");
            CAP = new CAgregarProducto();
            CAP.ingresarDataProducto(NEX4);
            CAP.agregarCategoriaAProducto("Android");
            CAP.agregarCategoriaAProducto("Nexus");
            CAP.altaProducto();

            System.out.println("\t\t GA3");
            CAP = new CAgregarProducto();
            CAP.ingresarDataProducto(GA3);
            CAP.agregarCategoriaAProducto("Android");
            CAP.agregarCategoriaAProducto("Galaxy S3");
            CAP.altaProducto();

            System.out.println("\t\t GA4");
            CAP = new CAgregarProducto();
            CAP.ingresarDataProducto(GA4);
            CAP.agregarCategoriaAProducto("Android");
            CAP.agregarCategoriaAProducto("Galaxy S4");
            CAP.altaProducto();

            System.out.println("\t\t AS5");
            CAP = new CAgregarProducto();
            CAP.ingresarDataProducto(AS5);
            CAP.agregarCategoriaAProducto("Android");
            CAP.agregarCategoriaAProducto("Galaxy Ace");
            CAP.altaProducto();

            System.out.println("\t\t PCG");
            CAP = new CAgregarProducto();
            CAP.ingresarDataProducto(PCG);
            CAP.agregarCategoriaAProducto("Protectores");
            CAP.altaProducto();

            System.out.println("\t\t PMH");
            CAP = new CAgregarProducto();
            CAP.ingresarDataProducto(PMH);
            CAP.agregarCategoriaAProducto("Protectores");
            CAP.altaProducto();

            System.out.println("\t\t IRD");
            CAP = new CAgregarProducto();
            CAP.ingresarDataProducto(IRD);
            CAP.agregarCategoriaAProducto("iOS");
            CAP.agregarCategoriaAProducto("Apple");
            CAP.altaProducto();

            System.out.println("\t\t IM");
            CAP = new CAgregarProducto();
            CAP.ingresarDataProducto(IM);
            CAP.agregarCategoriaAProducto("iOS");
            CAP.agregarCategoriaAProducto("Apple");
            CAP.altaProducto();

            System.out.println("\t\t RIX");
            CAP = new CAgregarProducto();
            CAP.ingresarDataProducto(RIX);
            CAP.agregarCategoriaAProducto("Xbox");
            CAP.altaProducto();

            System.out.println("\t\t CIX");
            CAP = new CAgregarProducto();
            CAP.ingresarDataProducto(CIX);
            CAP.agregarCategoriaAProducto("Xbox");
            CAP.altaProducto();

            System.out.println("\t\t CHP");
            CAP = new CAgregarProducto();
            CAP.ingresarDataProducto(CHP);
            CAP.agregarCategoriaAProducto("Playstation");
            CAP.altaProducto();

            System.out.println("\t\t CP3");
            CAP = new CAgregarProducto();
            CAP.ingresarDataProducto(CP3);
            CAP.agregarCategoriaAProducto("Playstation");
            CAP.altaProducto();

            System.out.println("\t\t IPH5S");
            CAP = new CAgregarProducto();
            CAP.ingresarDataProducto(IPH5S);
            CAP.agregarCategoriaAProducto("iPhone");
            CAP.agregarCategoriaAProducto("iOS");
            CAP.agregarCategoriaAProducto("Apple");
            CAP.altaProducto();

            System.out.println("\t\t IPH5C");
            CAP = new CAgregarProducto();
            CAP.ingresarDataProducto(IPH5C);
            CAP.agregarCategoriaAProducto("iPhone");
            CAP.agregarCategoriaAProducto("iOS");
            CAP.agregarCategoriaAProducto("Apple");
            CAP.altaProducto();

            System.out.println("\t\t IPAA");
            CAP = new CAgregarProducto();
            CAP.ingresarDataProducto(IPAA);
            CAP.agregarCategoriaAProducto("iOS");
            CAP.agregarCategoriaAProducto("Apple");
            CAP.altaProducto();

            System.out.println("\t\t IPAM");
            CAP = new CAgregarProducto();
            CAP.ingresarDataProducto(IPAM);
            CAP.agregarCategoriaAProducto("iOS");
            CAP.agregarCategoriaAProducto("Apple");
            CAP.altaProducto();


            //Oredenes de compra
            System.out.println("\tCargando OredenesCompra...");
            CAgregarOrden CAO;

            System.out.println("\t\t O1");
            CAO = new CAgregarOrden();
            CAO.OrdenConFecha(new Date(113, 7, 12));
            CAO.seleccionarCliente(DR.getNickname());
            CAO.agregarProductoAOrden(IPH5, 1);
            CAO.agregarProductoAOrden(IRD, 2);
            CAO.agregarProductoAOrden(IM, 2);
            CAO.altaOrden();

            System.out.println("\t\t O2");
            CAO = new CAgregarOrden();
            CAO.OrdenConFecha(new Date(113, 7, 12));
            CAO.seleccionarCliente(DR.getNickname());
            CAO.agregarProductoAOrden(NEX4, 3);
            CAO.altaOrden();

            System.out.println("\t\t O3");
            CAO = new CAgregarOrden();
            CAO.OrdenConFecha(new Date(113, 7, 12));
            CAO.seleccionarCliente(PS.getNickname());
            CAO.agregarProductoAOrden(CHP, 2);
            CAO.agregarProductoAOrden(CP3, 3);
            CAO.altaOrden();

            System.out.println("\t\t O4");
            CAO = new CAgregarOrden();
            CAO.OrdenConFecha(new Date(113, 8, 19));
            CAO.seleccionarCliente(BS.getNickname());
            CAO.agregarProductoAOrden(CIX, 4);
            CAO.altaOrden();

            System.out.println("\t\t O5");
            CAO = new CAgregarOrden();
            CAO.OrdenConFecha(new Date(113, 8, 17));
            CAO.seleccionarCliente(JW.getNickname());
            CAO.agregarProductoAOrden(PCG, 1);
            CAO.altaOrden();

            System.out.println("\t\t O6");
            CAO = new CAgregarOrden();
            CAO.OrdenConFecha(new Date(113, 7, 9));
            CAO.seleccionarCliente(PS.getNickname());
            CAO.agregarProductoAOrden(IPH5, 1);
            CAO.altaOrden();

            System.out.println("\t\t O7");
            CAO = new CAgregarOrden();
            CAO.OrdenConFecha(new Date(113, 7, 12));
            CAO.seleccionarCliente(RR.getNickname());
            CAO.agregarProductoAOrden(IPH5, 1);
            CAO.agregarProductoAOrden(IPH5C, 1);
            CAO.agregarProductoAOrden(IPH5S, 1);
            CAO.altaOrden();

            System.out.println("\t\t O8");
            CAO = new CAgregarOrden();
            CAO.OrdenConFecha(new Date(113, 8, 13));
            CAO.seleccionarCliente(RR.getNickname());
            CAO.agregarProductoAOrden(IPH4, 1);
            CAO.agregarProductoAOrden(IPAA, 1);
            CAO.agregarProductoAOrden(IM, 1);
            CAO.agregarProductoAOrden(IRD, 1);
            CAO.altaOrden();

            System.out.println("\t\t O9");
            CAO = new CAgregarOrden();
            CAO.OrdenConFecha(new Date(113, 8, 14));
            CAO.seleccionarCliente(RR.getNickname());
            CAO.agregarProductoAOrden(IPAM, 1);
            CAO.agregarProductoAOrden(IPH5C, 1);
            CAO.agregarProductoAOrden(IPH4, 1);
            CAO.agregarProductoAOrden(IPH5S, 1);
            CAO.altaOrden();

            System.out.println("\t\t O10");
            CAO = new CAgregarOrden();
            CAO.OrdenConFecha(new Date(113, 9, 10));
            CAO.seleccionarCliente(RR.getNickname());
            CAO.agregarProductoAOrden(IPAA, 1);
            CAO.agregarProductoAOrden(IM, 1);
            CAO.agregarProductoAOrden(IRD, 1);
            CAO.altaOrden();

            System.out.println("\t\t O11");
            CAO = new CAgregarOrden();
            CAO.OrdenConFecha(new Date(113, 9, 25));
            CAO.seleccionarCliente(RR.getNickname());
            CAO.agregarProductoAOrden(IPH5, 1);
            CAO.agregarProductoAOrden(IRD, 1);
            CAO.agregarProductoAOrden(IM, 1);
            CAO.agregarProductoAOrden(IPH5C, 1);
            CAO.altaOrden();

            System.out.println("\t\t O12");
            CAO = new CAgregarOrden();
            CAO.OrdenConFecha(new Date(113, 9, 24));
            CAO.seleccionarCliente(RR.getNickname());
            CAO.agregarProductoAOrden(IPH5, 1);
            CAO.agregarProductoAOrden(IRD, 1);
            CAO.agregarProductoAOrden(IM, 1);
            CAO.agregarProductoAOrden(IPH5C, 1);
            CAO.altaOrden();

            CVerCanOrden CVCO;
            System.out.println("\tCambiando estados...");
            System.out.println("\t\t O1");
            CVCO = new CVerCanOrden();
            CVCO.seleccionarOrden(1);
            CVCO.prepararOrden(new Date(113, 7, 13));
            CVCO.confirmarOrden(DR.getNickname(), new Date(113, 7, 20));

            System.out.println("\t\t O3");
            CVCO = new CVerCanOrden();
            CVCO.seleccionarOrden(3);
            CVCO.prepararOrden(new Date(113, 7, 30));

            System.out.println("\t\t O4");
            CVCO = new CVerCanOrden();
            CVCO.seleccionarOrden(4);
            CVCO.prepararOrden(new Date(113, 8, 20));
            CVCO.confirmarOrden(BS.getNickname(), new Date(113, 8, 25));

            System.out.println("\t\t O5");
            CVCO = new CVerCanOrden();
            CVCO.seleccionarOrden(5);
            CVCO.prepararOrden(new Date(113, 8, 20));
            CVCO.confirmarOrden(JW.getNickname(), new Date(113, 8, 25));

            System.out.println("\t\t O6");
            CVCO = new CVerCanOrden();
            CVCO.seleccionarOrden(6);
            CVCO.prepararOrden(new Date(113, 8, 12));
            CVCO.confirmarOrden(PS.getNickname(), new Date(113, 8, 19));

            System.out.println("\t\t O7");
            CVCO = new CVerCanOrden();
            CVCO.seleccionarOrden(7);
            CVCO.prepararOrden(new Date(113, 7, 13));
            CVCO.confirmarOrden(RR.getNickname(), new Date(113, 7, 20));

            System.out.println("\t\t O8");
            CVCO = new CVerCanOrden();
            CVCO.seleccionarOrden(8);
            CVCO.prepararOrden(new Date(113, 8, 14));
            CVCO.confirmarOrden(RR.getNickname(), new Date(113, 8, 21));

            System.out.println("\t\t O9");
            CVCO = new CVerCanOrden();
            CVCO.seleccionarOrden(9);
            CVCO.prepararOrden(new Date(113, 8, 15));
            CVCO.confirmarOrden(RR.getNickname(), new Date(113, 8, 20));

            System.out.println("\t\t O10");
            CVCO = new CVerCanOrden();
            CVCO.seleccionarOrden(10);
            CVCO.prepararOrden(new Date(113, 9, 11));
            CVCO.confirmarOrden(RR.getNickname(), new Date(113, 9, 20));

            System.out.println("\t\t O11");
            CVCO = new CVerCanOrden();
            CVCO.seleccionarOrden(11);
            CVCO.cancelarOrden(new Date(113, 9, 26));

            System.out.println("\t\t O12");
            CVCO = new CVerCanOrden();
            CVCO.seleccionarOrden(12);
            CVCO.prepararOrden(new Date(113, 9, 25));
            CVCO.confirmarOrden(RR.getNickname(), new Date(113, 10, 1));

            System.out.println("\tCargando Comentarios...");
            String C1 = "El mejor iPhone hasta el momento. Es la mejor compra que he hecho en años. Le pasa el trapo a todos los teléfonos Android.";
            String C2 = "Me parece que tu comentario es un poco desubicado. Hay muy buenos teléfonos que creo que mejoran las prestaciones de este, como el Samsung Galaxy S4.";
            String C3 = "No creo, supe tener un Galaxy S2 y lo tenía que reiniciar todos los días. Nunca más vuelvo a Android.";
            String C4 = "Se ha mejorado mucho desde entonces, pero me parece que estás muy cerrado con tu opinión. Saludos.";
            String C5 = "¡Excelente control! Puedo disfrutar de mi GTA V sin la molestia de cables.";
            String C6 = "Retracto lo que escribí antes....se me rompió a los 3 dias. Me han estafado.";
            String C7 = "Cumple su cometido. No he notado ninguna rayita nueva en mi Samsung.";

            CSesion CS;
            System.out.println("\t\t C1");
            CS = new CSesion();
            CS.inicioSesion(DR);
            CS.ComentarioConFecha(IPH5.getNRef(), C1, new Date(113, 8, 19, 12, 39));

            System.out.println("\t\t C2");
            CS = new CSesion();
            CS.inicioSesion(PS);
            CS.responderConFecha(IPH5.getNRef(), C2, 1, new Date(113, 8, 19, 14, 23));

            System.out.println("\t\t C3");
            CS = new CSesion();
            CS.inicioSesion(DR);
            CS.responderConFecha(IPH5.getNRef(), C3, 2, new Date(113, 8, 20, 10, 46));

            System.out.println("\t\t C4");
            CS = new CSesion();
            CS.inicioSesion(PS);
            CS.responderConFecha(IPH5.getNRef(), C4, 3, new Date(113, 8, 20, 17, 51));

            System.out.println("\t\t C5");
            CS = new CSesion();
            CS.inicioSesion(BS);
            CS.ComentarioConFecha(CIX.getNRef(), C5, new Date(113, 8, 25, 13, 23));

            System.out.println("\t\t C6");
            CS = new CSesion();
            CS.inicioSesion(BS);
            CS.ComentarioConFecha(CIX.getNRef(), C6, new Date(113, 8, 28, 14, 32));

            System.out.println("\t\t C7");
            CS = new CSesion();
            CS.inicioSesion(JW);
            CS.ComentarioConFecha(PCG.getNRef(), C7, new Date(113, 8, 25, 8, 39));


            CReclamo CR = new CReclamo();
            String Reclamo;
            String Respuesta;
            System.out.println("\tCargando Reclamos...");
            System.out.println("\t\t R1");
            CR = new CReclamo();
            CR.seleccionarCliente(PS.getNickname());
            CR.seleccionarProducto(IPH5.getNRef());
            Reclamo = "Me demoró más de un mes en llegar el teléfono, estaría bueno que mejoraran los tiempos de envío.";
            Respuesta = "Estimado, le pido disculpas por la demora.";
            CR.altaReclamo(Reclamo, Respuesta, new Date(113, 8, 19));
            System.out.println("\t\t R2");
            CR = new CReclamo();
            CR.seleccionarCliente(BS.getNickname());
            CR.seleccionarProducto(CIX.getNRef());
            Reclamo = "Lo puse en los comentarios y quiero escribirlo acá. Su producto me vino defectuoso, espero un reembolso de dinero.";
            Respuesta = "Me comunicaré con la dirección de Direct Market, pero no creo que sea posible el reembolso.";
            CR.altaReclamo(Reclamo, Respuesta, new Date(113, 8, 28));
            System.out.println("\t\t R3");
            CR = new CReclamo();
            CR.seleccionarCliente(BS.getNickname());
            CR.seleccionarProducto(CIX.getNRef());
            Reclamo = "No he tenido respuesta de parte suya. Estaría bueno que mejoraran su servicio de atención al cliente, porque me parece que el actual es pésimo.";
            Respuesta = "Le contesté en el reclamo anterior.";
            CR.altaReclamo(Reclamo, Respuesta, new Date(113, 9, 15));
            System.out.println("\t\t R4");
            CR = new CReclamo();
            CR.seleccionarCliente(RR.getNickname());
            CR.seleccionarProducto(IPH5.getNRef());
            Reclamo = "Me han enviado un iPhone 4 en lugar del iPhone 5 que encargué... ";
            Respuesta = "Eso es muy extraño, averiguaré que pasó, mientras tanto le voy a pedir que tenga un poco de paciencia. ";
            CR.altaReclamo(Reclamo, Respuesta, new Date(113, 10, 1));
            System.out.println("\t\t R5");
            CR = new CReclamo();
            CR.seleccionarCliente(RR.getNickname());
            CR.seleccionarProducto(IRD.getNRef());
            Reclamo = "Vino con algunas rayitas. Estaría bueno que controlaran que estas cosas no pasaran en el traslado del producto.";
            Respuesta = "Muchas gracias por su sugerencia, la trataremos de tener en cuenta.";
            CR.altaReclamo(Reclamo, Respuesta, new Date(113, 10, 1));
            System.out.println("\t\t R6");
            CR = new CReclamo();
            CR.seleccionarCliente(RR.getNickname());
            CR.seleccionarProducto(IM.getNRef());
            Reclamo = "Este dispositivo vino fallado de fábrica, me gustaría que me lo reembolsaran.";
            Respuesta = "No es posible el reembolso de dinero en su caso.";
            CR.altaReclamo(Reclamo, Respuesta, new Date(113, 10, 1));
            System.out.println("\t\t R7");
            CR = new CReclamo();
            CR.seleccionarCliente(RR.getNickname());
            CR.seleccionarProducto(IPH5C.getNRef());
            Reclamo = "No vinieron los auriculares incluidos en la caja.";
            Respuesta = "Lo lamento mucho, trataremos de enviarle un par sin recargo a la brevedad.";
            CR.altaReclamo(Reclamo, Respuesta, new Date(113, 10, 1));


            CPuntaje CP = new CPuntaje();
            System.out.println("\tCargando Puntajes...");
            System.out.println("\t\t P1");
            CP.seleccionarCliente(DR.getNickname());
            CP.seleccionarProducto(IPH5.getNRef());
            CP.puntuarProducto(5);
            System.out.println("\t\t P2");
            CP.seleccionarCliente(DR.getNickname());
            CP.seleccionarProducto(IM.getNRef());
            CP.puntuarProducto(5);
            System.out.println("\t\t P3");
            CP.seleccionarCliente(DR.getNickname());
            CP.seleccionarProducto(IRD.getNRef());
            CP.puntuarProducto(5);
            System.out.println("\t\t P4");
            CP.seleccionarCliente(PS.getNickname());
            CP.seleccionarProducto(IPH5.getNRef());
            CP.puntuarProducto(3);
            System.out.println("\t\t P5");
            CP.seleccionarCliente(RR.getNickname());
            CP.seleccionarProducto(IPH5.getNRef());
            CP.puntuarProducto(3);
            System.out.println("\t\t P6");
            CP.seleccionarCliente(RR.getNickname());
            CP.seleccionarProducto(IM.getNRef());
            CP.puntuarProducto(4);
            System.out.println("\t\t P7");
            CP.seleccionarCliente(RR.getNickname());
            CP.seleccionarProducto(IRD.getNRef());
            CP.puntuarProducto(3);
            System.out.println("Fin Cargar");


        } catch (Exception e) {
            System.err.println(e.getMessage());
            // throw new RuntimeException(e);
        }
    }
    private static BasicPasswordEncryptor textEncryptor = new BasicPasswordEncryptor();

    public static String enc(String aenc) {

        return textEncryptor.encryptPassword(aenc);
    }

    public static boolean matchPass(String pass, String enc) {
        return textEncryptor.checkPassword(pass, enc);
    }

    public static void Limpiar() {
        MUsuario mU = MUsuario.getInstance();
        MProducto mP = MProducto.getInstance();
        MOrdenCompra mOC = MOrdenCompra.getInstance();
        MCategoria mC = MCategoria.getInstance();
        mU.clearUsers();
        mP.clearProductos();
        mOC.clearOrdenes();
        mC.clearCategorias();
    }
}
