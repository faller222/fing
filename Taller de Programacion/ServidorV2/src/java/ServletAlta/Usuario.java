package ServletAlta;

import Adapter.IAltaUsuario;
import Adapter.ISesion;
import Extras.Image;
import Extras.TipoLogIn;
import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import java.awt.image.BufferedImage;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.imageio.ImageIO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.datatype.XMLGregorianCalendar;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileItemFactory;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;

public class Usuario extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        IAltaUsuario IAU = new IAltaUsuario();

        String verifTarget = request.getParameter("verif");
        boolean res;
        if (verifTarget.equals("nick")) {
            String targetNick = request.getParameter("nick");

            response.setContentType("text/html;charset=UTF-8");
            response.setHeader("Cache-Control", "no-cache");
            if (IAU.verificarNickname(targetNick)) { //!targetNick.equals("") &&
                res = true;
            } else {
                res = false;
            }
        } else {
            String targetMail = request.getParameter("email");
            if (IAU.verificarMail(targetMail)) { //!targetMail.equals("") &&
                res = true;
            } else {
                res = false;
            }
        }
        if (res) {
            response.getWriter().print("<true>");
        } else {
            response.getWriter().print("<false>");
        }
        response.getWriter().close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            IAltaUsuario IAU = new IAltaUsuario();

            //Parametros
            String nick = null;
            String email = null;
            String pass = null;
            String nombre = null;
            String apell = null;
            String fecha;
            Date def;
            GregorianCalendar newCalendar = new GregorianCalendar();
            XMLGregorianCalendar fNac;
            String tipo = null;
            String sitio = null;
            String compania = null;
            BufferedImage BI;
            byte[] Bi = null;

            SimpleDateFormat formFormat = new SimpleDateFormat("dd-MM-yyyy");
            List<FileItem> items;
            FileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);


            items = upload.parseRequest(new ServletRequestContext(request));
            for (FileItem item : items) {
                if (item.isFormField()) {
                    String parametro = item.getFieldName();
                    String Valor = item.getString();
                    if (parametro.equals("nick")) {
                        nick = Valor;
                    } else if (parametro.equals("email")) {
                        email = Valor;
                    } else if (parametro.equals("pass1")) {
                        pass = Valor;
                    } else if (parametro.equals("nombre")) {
                        nombre = Valor;
                    } else if (parametro.equals("apellido")) {
                        apell = Valor;
                    } else if (parametro.equals("DateSelect")) {
                        fecha = Valor;
                        try {
                            def = formFormat.parse(fecha);
                            newCalendar.setTime(def);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    } else if (parametro.equals("tipo")) {
                        tipo = Valor;
                    } else if (parametro.equals("sitio")) {
                        sitio = Valor;
                    } else if (parametro.equals("compania")) {
                        compania = Valor;
                    }
                } else {//si es un archivo
                    BI = ImageIO.read(item.getInputStream());
                    Bi = Image.toByteArray(BI);


//                    BI = ImageIO.read(item.getInputStream());
//                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                    ImageIO.write(BI, "jpg", baos);
//                    baos.flush();
//                    Bi = baos.toByteArray();
//                    baos.close();
                }
            }


            fNac = new XMLGregorianCalendarImpl(newCalendar);



            publisher.DataUsuario u;
            if (tipo.equals("proveedor")) {
                u = new publisher.DataProveedor();

                ((publisher.DataProveedor) u).setCompania(compania);
                ((publisher.DataProveedor) u).setSitioWeb(sitio);
            } else {
                u = new publisher.DataCliente();
            }
            u.setApellido(apell);
            u.setFechaNac(fNac);
            u.setMail(email);
            if (Bi != null) {
                u.setBimagen(Bi);
            }
            u.setNickname(nick);
            u.setNombre(nombre);
            u.setPass(pass);




            IAU.ingresarDataUsuario(u);
            IAU.altaUsuario();


            HttpSession Sesion = request.getSession();
            ISesion IS = new ISesion();
            u = IS.inicioSesion(nick, pass);
            if (u instanceof publisher.DataCliente) {//Si es Cliente
                Sesion.setAttribute("TipoLog", TipoLogIn.CLIENTE);
            } else {
                Sesion.setAttribute("TipoLog", TipoLogIn.PROVEEDOR);
            }
            Sesion.setAttribute("Sesion", IS);

            response.sendRedirect("/");
            return;
        } catch (Exception e) {
            PrintWriter out = response.getWriter();
            out.println("<false>");
            out.close();
        }

    }

    // <editor-fold defaultstate="collapsed" desc="getServletInfo.">
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
