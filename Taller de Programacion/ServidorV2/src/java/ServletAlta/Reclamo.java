/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ServletAlta;

import Adapter.IReclamo;
import Adapter.ISesion;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import publisher.DataProveedor;
import publisher.DataUsuario;

/**
 *
 * @author tprog077
 */
public class Reclamo extends HttpServlet {

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
        PrintWriter out = response.getWriter();
        try {
            HttpSession sesion = request.getSession();
            ISesion IS = (ISesion) sesion.getAttribute("Sesion");
            DataUsuario clie = IS.verInfoPerfil();
            if (clie == null) {
                throw new Exception("Debes estar Logeado");
            }
            if (clie instanceof DataProveedor) {
                throw new Exception("Debes ser Proveedor");
            }

            String P = request.getParameter("Prod");
            Integer nP = Integer.parseInt(P);
            String R = request.getParameter("Reclamo");

            String Cliente = clie.getNickname();


            IReclamo IR = new IReclamo();
            IR.seleccionarCliente(Cliente);
            IR.seleccionarProducto(nP);
            IR.altaReclamo(R);

            out.print("<true>");

        } catch (Exception e) {
            out.print("<false>");
        } finally {
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
