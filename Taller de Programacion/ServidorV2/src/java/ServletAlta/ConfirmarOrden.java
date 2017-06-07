package ServletAlta;

import Adapter.IConfirmarOrden;
import Adapter.ISesion;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import publisher.DataUsuario;

@WebServlet(name = "ConfirmarOrden", urlPatterns = {"/ConfirmarOrden"})
public class ConfirmarOrden extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            String numOrd = request.getParameter("num");
            Integer num = Integer.parseInt(numOrd);
            IConfirmarOrden ICO = new IConfirmarOrden();
            HttpSession sesion = request.getSession();
            ISesion IS = (ISesion) sesion.getAttribute("Sesion");
            DataUsuario user = IS.verInfoPerfil();
            ICO.seleccionarOrden(num);
            ICO.confirmarOrden(user.getNickname());
            response.sendRedirect("VerPerfil#");
            return;
        } catch (Exception e) {
            PrintWriter out = response.getWriter();
            out.print("<false>");
            out.close();
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
