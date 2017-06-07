package ServletConsulta;

import Adapter.IPuntaje;
import Adapter.ISesion;
import Adapter.IVerProducto;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import publisher.DataCliente;
import publisher.DataProducto;
import publisher.DataUsuario;

public class Producto extends HttpServlet {

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
            Integer nR = Integer.parseInt(request.getParameter("nRef"));
            IVerProducto IVMP = new IVerProducto();
            HttpSession sesion = request.getSession();
            ISesion IS = (ISesion) sesion.getAttribute("Sesion");
            List<Integer> aPuntuar = new ArrayList();
            if (IS != null) {
                DataUsuario user = IS.verInfoPerfil();
                if (user instanceof DataCliente) {
                    IPuntaje IP = new IPuntaje();
                    IP.seleccionarCliente(user.getNickname());
                    aPuntuar = IP.noPuntuados();
                }
            }

            DataProducto dP = IVMP.seleccionarProducto(nR);
            List LCates = IVMP.listarCategoriaProductos();
            request.setAttribute("Producto", dP);
            request.setAttribute("Cates", LCates);
            request.setAttribute("aPuntuar", aPuntuar);


            String req = "?page=pages/verProducto.jsp";
            request.getRequestDispatcher("DirectMarket" + req).forward(request, response);
            return;
        } catch (Exception e) {
            response.sendRedirect("DirectMarket");
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
