package ServletAlta;

import Adapter.IAgregarProducto;
import Adapter.ISesion;
import Adapter.IVerProducto;
import Extras.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;
import publisher.DataUsuario;

@WebServlet(name = "RegistrarProducto", urlPatterns = {"/RegistrarProducto"})
public class Producto extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        IVerProducto IVP = new IVerProducto();
        boolean catcheo = false;
        String num = request.getParameter("nref");
        response.setContentType("text/xml");
        response.setHeader("Cache-Control", "no-cache");
        try {
            IVP.seleccionarProducto(Integer.parseInt(num));
        } catch (Exception e) {
            response.getWriter().write("<valid>true</valid>");
            catcheo = true;
        }
        if (!catcheo) {
            response.getWriter().write("<valid>false</valid>");

        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        //Parametros
        String pname = null;
        String desc = null;
        String esp = null;
        Float precio = new Float(0);
        int nref = 0;
        List categorias = new ArrayList();
        String proveedor = null;
        int i = 0;//iterador Imagenes
        BufferedImage Img = null;
        byte[][] im = new byte[3][];
        im[0] = null;
        im[1] = null;
        im[2] = null;

        List<FileItem> items;
        try {
            items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(new ServletRequestContext(request));
            for (FileItem item : items) {
                if (item.isFormField()) {
                    String parametro = item.getFieldName();
                    String Valor = item.getString();
                    if (parametro.equals("pname")) {
                        pname = Valor;
                    } else if (parametro.equals("nref")) {
                        nref = Integer.parseInt(Valor);
                    } else if (parametro.equals("desc")) {
                        desc = Valor;
                    } else if (parametro.equals("espe")) {
                        esp = Valor;
                    } else if (parametro.equals("precio")) {
                        precio = Float.parseFloat(Valor);
                    } else if (parametro.equals("categoria")) {
                        categorias.add(Valor);
                    }
                } else {//si es un archivo
                    Img = ImageIO.read(item.getInputStream());
                    im[i] = Image.toByteArray(Img);
                    i++;
                }
            }
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            RequestDispatcher rd = request.getRequestDispatcher("error.jsp");
            rd.forward(request, response);
        }

        HttpSession sesion = request.getSession();
        ISesion IS = (ISesion) sesion.getAttribute("Sesion");
        DataUsuario prov = IS.verInfoPerfil();
        proveedor = prov.getNickname();
        publisher.DataProducto dp = new publisher.DataProducto();
        dp.setNombre(pname);
        dp.setDescripcion(desc);
        dp.setEspecificacion(esp);
        dp.setNRef(nref);
        dp.setPrecio(precio);
        dp.setProveedor(proveedor);
        //Acomodo
        if (im[1] == null) {
            im[1] = im[2];
            im[2] = null;
        }
        if (im[0] == null) {
            im[0] = im[1];
            im[1] = im[2];
            im[2] = null;
        }
        //Fin acomodo

        if (im[0] != null) {
            dp.setImg0(im[0]);
        }
        if (im[1] != null) {
            dp.setImg1(im[1]);
        }
        if (im[2] != null) {
            dp.setImg2(im[2]);
        }

        IAgregarProducto IAP = new IAgregarProducto();
        try {

            IAP.ingresarDataProducto(dp);
            for (Iterator<Object> it = categorias.iterator(); it.hasNext();) {
                Object categoria = it.next();
                IAP.agregarCategoriaAProducto(categoria.toString());
            }
            IAP.altaProducto();

            response.sendRedirect("VerProducto?nRef=" + nref);
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
    } // </editor-fold>
}
