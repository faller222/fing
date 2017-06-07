package Extras;

import Adapter.ISesion;
import Adapter.IVerInfoCliente;
import Adapter.IVerInfoProveedor;
import Adapter.IVerProducto;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import publisher.DataProducto;
import publisher.DataUsuario;

public class Image extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public static byte[] toByteArray(BufferedImage BI) throws Exception {
        if (BI == null) {
            return null;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(BI, "jpg", baos);
        baos.flush();
        byte[] ret = baos.toByteArray();
        baos.close();
        return ret;
    }

    public static BufferedImage resize(BufferedImage img, int newH) {
        if (img == null) {
            return null;
        } else {
            int w = img.getWidth();
            int h = img.getHeight();
            int D = w / h;

            BufferedImage dimg = new BufferedImage(newH * D, newH, img.getType());
            Graphics2D g = dimg.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawImage(img, 0, 0, newH * D, newH, 0, 0, w, h, null);
            g.dispose();
            return dimg;
        }
    }

    public static BufferedImage creator(int Size) {
        BufferedImage image = new BufferedImage(Size, Size, BufferedImage.TYPE_BYTE_INDEXED);

        Graphics2D graphics = image.createGraphics();

        // Set back ground of the generated image to white
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, Size, Size);

        // release resources used by graphics context
        graphics.dispose();

        return image;

    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("image/jpeg");
        String Tp = request.getParameter("tp");//si es chica o grande, prove Prod o clie
        String Id = request.getParameter("id");//nref de prod o nick de useer
        String No = request.getParameter("no");//id de img, si es null busco en user

        BufferedImage BI = null;
        byte[] Traida = null;
        HttpSession Sesion = request.getSession();
        ISesion IS = (ISesion) Sesion.getAttribute("Sesion");

        try {
            File Fi = null;
            if (No == null) {//id de img, si es null busco en user
                DataUsuario dU;
                BI = creator(125);
                if (Tp != null) {
                    if (Tp.equals("P")) {//Quiero un Provee
                        IVerInfoProveedor IVIP = new IVerInfoProveedor();
                        dU = IVIP.seleccionarProveedor(Id);
                    } else {//la quiero Grande Clie
                        IVerInfoCliente IVIC = new IVerInfoCliente();
                        dU = IVIC.seleccionarCliente(Id);
                    }
                    Traida = dU.getBimagen();
                }
            } else {
                BI = creator(1);
                Integer num = Integer.parseInt(Id);
                Integer I = Integer.parseInt(No);
                IVerProducto IVP = new IVerProducto();
                DataProducto dP = IVP.seleccionarProducto(num);
                switch (I) {
                    case 0:
                        Traida = dP.getImg0();
                        break;
                    case 1:
                        Traida = dP.getImg1();
                        break;
                    case 2:
                        Traida = dP.getImg2();
                        break;
                }
            }

            if (Traida == null) {
                Traida = toByteArray(creator(1));
            }
        } catch (Exception e) {
        } finally {

            response.setContentType("image/jpeg");
            response.setContentLength(Traida.length);
            response.getOutputStream().write(Traida);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

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
