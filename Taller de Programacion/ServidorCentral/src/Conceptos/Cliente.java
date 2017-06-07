package Conceptos;

import DataTypes.DataCliente;
import DataTypes.DataUsuario;
import Publisher.Publicador;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Cliente extends Usuario implements Observer {
    
    private boolean notiOrden = true;
    private boolean notiProve = true;
    private boolean notiProd = true;
    private boolean notiRec = true;
    private Map<Integer, OrdenCompra> Ordenes = new HashMap<Integer, OrdenCompra>();
    private Map<String, Proveedor> Proveedores = new HashMap<String, Proveedor>(); //Los proveedores a los que le compré
    private Map<Integer, Producto> Productos = new HashMap<Integer, Producto>(); //Los productos que he comprado
    private List<Integer> prodAPuntuar = new ArrayList<Integer>(); //NRef de los productos que no he puntuado
    private List<Reclamo> recs = new ArrayList<Reclamo>();
    
    public Cliente(DataUsuario dataU) throws Exception {
        super(dataU);
    }
    
    public Map<Integer, OrdenCompra> obtenerOrdenes() {
        return Ordenes;
    }
    
    public Map<String, Proveedor> obtenerProveedores() {
        return Proveedores;
    }
    
    public Map<Integer, Producto> obtenerProductos() {
        return Productos;
    }
    
    public List<Reclamo> obtenerReclamos() {
        return recs;
    }
    
    public void agregarReclamo(Reclamo r) {
        if (notiRec) {
            r.addObserver(this);
        }
        recs.add(r);
    }
    
    public void agregarOrden(OrdenCompra oc) {
        if (notiOrden) {
            oc.addObserver(this);
        }
        Ordenes.put(oc.getNumero(), oc);
    }
    
    public void borrarOrden(Integer oc) {
        Ordenes.remove(oc);
    }
    
    public boolean comproProducto(int nRef) {
        boolean ret = false;
        for (Map.Entry<Integer, Producto> entry : Productos.entrySet()) {
            Producto P = entry.getValue();
            ret = ret || (P.getNRef() == nRef);
        }
        return ret;
    }
    
    @Override
    public DataUsuario getDataUsuario() {
        DataCliente Ret = new DataCliente(this.getNickname(),
                this.getPass(),
                this.getMail(),
                this.getNombre(),
                this.getApellido(),
                this.getFechaNac(),
                this.getImagen());
        
        ArrayList<Integer> l = new ArrayList<Integer>();
        for (Integer I : Ordenes.keySet()) {
            l.add(I);
        }
        Ret.setOrdenes(l);
        Ret.setNotiOrden(notiOrden);
        Ret.setNotiProd(notiProd);
        Ret.setNotiProve(notiProve);
        Ret.setOnline(Online);
        return Ret;
    }
    
    public void confirmoOrden(OrdenCompra OC) {
        Map<Integer, Producto> prods = OC.getProductosOrden();
        for (Map.Entry<Integer, Producto> entry : prods.entrySet()) {
            Integer Nref = entry.getKey();
            Producto producto = entry.getValue();
            if (!Productos.containsKey(Nref)) {
                prodAPuntuar.add(Nref);
                Productos.put(Nref, producto);
            }
            Proveedor prov = producto.getProve();
            Proveedores.put(prov.getNickname(), prov);
            if (notiProd) {
                producto.addObserver(this);
            }
            if (notiProve) {
                prov.addObserver(this);
            }
        }
    }
    
    @Override
    public void Notificar(Observable o) {
        String to = this.mail;
        String from = "admin@directmarket.com";
        String host = "localhost";
        Properties properties = System.getProperties();
        properties.put("mail.smtp.port", Publicador.mailPort);
        properties.setProperty("mail.smtp.host", host);
        Session session = Session.getDefaultInstance(properties);
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(to));
            if (o instanceof Proveedor) {
                Proveedor Pro = (Proveedor) o;
                message.setSubject("Direct Market: " + Pro.getNombre() + " ha hecho una publicacion.");
                message.setContent("<h3>Direct Market</h3>\n"
                        + "<br>\n"
                        + "Estimado " + this.getNombre() + ", El proveedor " + Pro.getNombre() + " ha publicado un nuevo producto"
                        + ". Para mas informacion dirijase a este "
                        + "<a href=\"http://" + Publicador.Maquina + "/ServidorWeb/bProducto?Pag=0&Tp=0&Clave=" + Pro.getNickname() + "\">enlace</a>.\n"
                        + "<br>\n"
                        + "-------------------------------------------------------------------------------------------------------\n"
                        + "<br>\n"
                        + "Si desea dejar de recibir notificaciones de DirectMarket, <a href=\"http://" + Publicador.Maquina + "/ServidorWeb/VerInfoPerfil\" >desactive </a> las notifiaciones.", "text/html");
            } else if (o instanceof Producto) {
                Producto Pro = (Producto) o;
                message.setSubject("Direct Market: nuevo comentario en " + Pro.getNombre() + ".");
                message.setContent("<h3>Direct Market</h3>\n"
                        + "<br>\n"
                        + "Estimado " + this.getNombre() + ", se ha comentado el producto " + Pro.getNombre() + ".<br>\n"
                        + "Para mas informacion dirijase a este "
                        + "<a href=\"http://" + Publicador.Maquina + "/ServidorWeb/Producto?nRef=" + Pro.getNRef() + "\">enlace</a>.\n"
                        + "<br>\n"
                        + "-------------------------------------------------------------------------------------------------------\n"
                        + "<br>\n"
                        + "Si desea dejar de recibir notificaciones de DirectMarket, <a href=\"http://" + Publicador.Maquina + "/ServidorWeb/VerInfoPerfil\" >desactive </a> las notifiaciones.", "text/html");
            } else if (o instanceof OrdenCompra) {
                OrdenCompra Ord = (OrdenCompra) o;
                message.setSubject("Notificacion orden de compra");
                message.setContent("<h3>Direct Market</h3>\n"
                        + "<br>\n"
                        + "Estimado " + this.getNombre() + ", la orden de compra numero " + Ord.getNumero() + " ha cambiado su estado a "
                        + Ord.getEstado().getEst().name() + ". Para mas informacion dirijase a este "
                        + "<a href=\"http://" + Publicador.Maquina + "/ServidorWeb/SoloOrden?nOrd=" + Ord.getNumero() + "\">enlace</a>.\n"
                        + "<br>\n"
                        + "-------------------------------------------------------------------------------------------------------\n"
                        + "<br>\n"
                        + "Si desea dejar de recibir notificaciones de DirectMarket, <a href=\"http://" + Publicador.Maquina + "/ServidorWeb/VerInfoPerfil\" >desactive </a> las notifiaciones.", "text/html");
            } else if (o instanceof Reclamo) {
                Reclamo rec = (Reclamo) o;
                message.setSubject("Notificacion Reclamo");
                message.setContent("<h3>Direct Market</h3>\n"
                        + "<br>\n"
                        + "Estimado " + this.getNombre() + ", su reclamo al producto " + rec.getDataReclamo().getNomProd() + " ha sido respondido"
                        + ". Para mas informacion dirijase a este "
                        + "<a href=\"http://" + Publicador.Maquina + "/ServidorWeb/Producto?nRef=" + rec.getDataReclamo().getnProd() + "\">enlace</a>.\n"
                        + "<br>\n"
                        + "-------------------------------------------------------------------------------------------------------\n"
                        + "<br>\n"
                        + "Si desea dejar de recibir notificaciones de DirectMarket, <a href=\"http://" + Publicador.Maquina + "/ServidorWeb/VerInfoPerfil\" >desactive </a> las notifiaciones.", "text/html");
            }
            
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
    
    public boolean isNotiRec() {
        return notiRec;
    }
    
    public boolean isNotiOrden() {
        return notiOrden;
    }
    
    public boolean isNotiProve() {
        return notiProve;
    }
    
    public boolean isNotiProd() {
        return notiProd;
    }
    
    public void setNotiRec(boolean notiRec) {
        this.notiRec = notiRec;
    }
    
    public void setNotiOrden(boolean notiOrden) {
        this.notiOrden = notiOrden;
    }
    
    public void setNotiProve(boolean notiProve) {
        this.notiProve = notiProve;
    }
    
    public void setNotiProd(boolean notiProd) {
        this.notiProd = notiProd;
    }
    
    public List<Integer> getProdAPuntuar() {
        return prodAPuntuar;
    }
    
    public void yaPuntuo(Integer nref) {
        prodAPuntuar.remove(nref);
    }
}
