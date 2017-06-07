/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Extras;

import Adapter.IActDesNoti;
import Adapter.ISesion;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import publisher.DataUsuario;

/**
 *
 * @author Usuario
 */
public class ActDesNoti extends HttpServlet {

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
            DataUsuario user = IS.verInfoPerfil();
            String tnoti = request.getParameter("tnoti");
            String fun = request.getParameter("fun");
            boolean Act = "Activar".equals(fun);
            boolean Des = "Desactivar".equals(fun);

            IActDesNoti IAD = new IActDesNoti();
            IAD.seleccionarCliente(user.getNickname());

            if ("Ord".equals(tnoti)) {
                if (Act) {
                    IAD.ActivarNotiOrdenes();
                } else if (Des) {
                    IAD.DesactivarNotiOrdenes();
                }
            } else if ("Prove".equals(tnoti)) {
                if (Act) {
                    IAD.ActivarNotiProveedor();
                } else if (Des) {
                    IAD.DesactivarNotiProveedor();
                }
            } else if ("Prod".equals(tnoti)) {
                if (Act) {
                    IAD.ActivarNotiProducto();
                } else if (Des) {
                    IAD.DesactivarNotiProducto();
                }
            } else if ("Rec".equals(tnoti)) {
                if (Act) {
                    IAD.ActivarNotiReclamo();
                } else if (Des) {
                    IAD.DesactivarNotiReclamo();
                }
            }
            if (Act) {
                out.print("Desactivar");
            } else if (Des) {
                out.print("Activar");
            }
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
