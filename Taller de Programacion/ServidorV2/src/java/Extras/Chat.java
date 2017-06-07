package Extras;

import Adapter.ISesion;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Chat extends HttpServlet {

    private int cantMes = 0;
    private String nick = "";

    private boolean hayMensajes(ISesion IS, String Otro) throws Exception {
        int aoura = IS.contarMensajes(Otro);
        boolean ret = !nick.equals(Otro);
        ret = ret || aoura > cantMes;
        return ret;
    }

    private void mandar(ISesion IS, String Otro, String Mensaje) throws Exception {
        IS.mandarMensaje(Otro, Mensaje);
    }

    private List dame(ISesion IS, String Otro) throws Exception {
        cantMes = IS.contarMensajes(Otro);
        nick = Otro;
        return IS.conversacionCon(Otro);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        TipoLogIn tipito = (TipoLogIn) request.getSession().getAttribute("TipoLog");
        try {
            if (tipito == TipoLogIn.VISITANTE) {
                throw new Exception();
            }
            ISesion IS = (ISesion) request.getSession().getAttribute("Sesion");
            request.setAttribute("Lista", IS.conQuienHable());
            String req = "?page=pages/ChatUp.jsp";
            request.getRequestDispatcher("DirectMarket" + req).forward(request, response);
            return;
        } catch (Exception e) {
            response.sendRedirect("/");
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        ISesion IS = (ISesion) request.getSession().getAttribute("Sesion");

        try {
            String Tipo = request.getParameter("Tipo");
            String Otro = request.getParameter("Otro");
            if (Tipo.equals("Contar")) {
                response.setContentType("text/html;charset=UTF-8");
                if (hayMensajes(IS, Otro)) {
                    out.write("<true>");
                } else {
                    out.write("<false>");
                }
            }
            if (Tipo.equals("Nuevos")) {
                response.setContentType("text/html;charset=UTF-8");
                if (IS.tengoMsj()) {
                    out.write("<true>");
                } else {
                    out.write("<false>");
                }
            }
            if (Tipo.equals("Mandar")) {
                String Mensaje = request.getParameter("Mensaje");
                mandar(IS, Otro, Mensaje);
            }
            if (Tipo.equals("Leer")) {
                request.setAttribute("Lista", dame(IS, Otro));
                request.getRequestDispatcher("/WEB-INF/pages/ChatMsj.jsp").forward(request, response);
            }
            if (Tipo.equals("Chat")) {
                request.setAttribute("Receptor", Otro);
                request.getRequestDispatcher("/WEB-INF/pages/Chat.jsp").forward(request, response);
            }
            if (Tipo.equals("Todo")) {
                request.setAttribute("Receptor", Otro);
                request.getRequestDispatcher("/WEB-INF/pages/ChatUp.jsp").forward(request, response);
            }
        } catch (Exception e) {
            response.sendRedirect("/");
        } finally {
            out.close();
        }
    }
// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

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
