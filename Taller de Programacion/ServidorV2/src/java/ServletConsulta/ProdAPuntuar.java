/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ServletConsulta;

import Adapter.IPuntaje;
import Adapter.ISesion;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import publisher.DataProducto;
import publisher.DataUsuario;

/**
 *
 * @author Samuels
 */
public class ProdAPuntuar extends HttpServlet {

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
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {

            //Obtengo y declaro
            HttpSession sesion = request.getSession();
            ISesion Ses = (ISesion) sesion.getAttribute("Sesion");
            DataUsuario user = Ses.verInfoPerfil();
            IPuntaje IP = new IPuntaje();
            List<DataProducto> prodsComprados;
            List<Integer> aPuntuar;
            //Preparo y envio
            IP.seleccionarCliente(user.getNickname());
            prodsComprados = IP.listarProductosComprados();
            aPuntuar = IP.noPuntuados();
            request.setAttribute("Comprados", prodsComprados);
            request.setAttribute("aPuntuar", aPuntuar);
            request.getRequestDispatcher("/WEB-INF/pages/puntuar.jsp").forward(request, response);
            return;
        } catch (Exception e) {
            response.sendRedirect("/");
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
