package ServletConsulta;

import Adapter.ISesion;
import Adapter.IVerInfoProveedor;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import publisher.DataCliente;
import publisher.DataUsuario;

public class ProductosProveedor extends HttpServlet {

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
        try {
            HttpSession sesion = request.getSession();

            ISesion IS = (ISesion) sesion.getAttribute("Sesion");
            DataUsuario prov = IS.verInfoPerfil();
            if (prov == null) {
                throw new Exception("Debes estar Logeado");
            }
            if (prov instanceof DataCliente) {
                throw new Exception("Debes ser Proveedor");
            }

            IVerInfoProveedor IVP = new IVerInfoProveedor();
            IVP.seleccionarProveedor(prov.getNickname());
            List<Object> productos = IVP.listarProductosProveedor();

            request.setAttribute("Lista", productos);
            request.getRequestDispatcher("/WEB-INF/pages/listarproductos.jsp").forward(request, response);
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
