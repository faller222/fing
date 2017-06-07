package ServletConsulta;

import Adapter.IConfirmarOrden;
import Adapter.ISesion;
import Extras.TipoLogIn;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import publisher.DataCliente;
import publisher.DataOrdenCompra;
import publisher.DataUsuario;

public class UnaOrden extends HttpServlet {

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

            String O = request.getParameter("Orden");
            Integer num = Integer.parseInt(O);

            HttpSession Sesion = request.getSession();
            ISesion IS = (ISesion) Sesion.getAttribute("Sesion");
            TipoLogIn TL = (TipoLogIn) Sesion.getAttribute("TipoLog");
            boolean Nueva = (null == request.getParameter("onClie"));

            boolean Correcto = false;
            if (TL == TipoLogIn.CLIENTE) {
                DataUsuario user = IS.verInfoPerfil();
                DataCliente clie = (DataCliente) user;
                for (Integer it : clie.getOrdenes()) {
                    Correcto = Correcto || (it == num);
                }
            } else {
                throw new Exception("Debes ser Cliente Logueado. ");
            }

            if (Correcto) {
                IConfirmarOrden ICO = new IConfirmarOrden();
                DataOrdenCompra DOC = ICO.seleccionarOrden(num);
                request.setAttribute("Oden", DOC);
                if (Nueva) {
                    request.setAttribute("page", "pages/verUnaOrden.jsp");
                    request.getRequestDispatcher("/WEB-INF/Template.jsp").forward(request, response);
                } else {
                    request.getRequestDispatcher("/WEB-INF/pages/verUnaOrden.jsp").forward(request, response);
                }
            } else {
                throw new Exception("No te corresponde esta orden.");
            }

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
