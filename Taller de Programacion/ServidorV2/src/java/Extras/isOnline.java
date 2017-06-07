package Extras;

import Adapter.IVerInfoProveedor;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class isOnline extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("/");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {

            response.setContentType("text/html;charset=UTF-8");
            String Nick = request.getParameter("prove");
            IVerInfoProveedor IVIP = new IVerInfoProveedor();
            if (IVIP.isOnline(Nick)) {

                response.getWriter().print("<true>");
            } else {
                response.getWriter().print("<false>");
            }
        } catch (Exception e) {
            response.getWriter().print("<false>");
        } finally {
            response.getWriter().close();
        }
    }

    // <editor-fold>
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
