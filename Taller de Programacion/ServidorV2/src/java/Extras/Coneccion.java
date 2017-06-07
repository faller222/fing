/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Extras;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

/**
 *
 * @author Faller
 */
public class Coneccion extends HttpServlet {

    // <editor-fold defaultstate="collapsed" desc="Operaciones que manejan la Ip.">
    public static String IP = "localhost";
    public static String Puerto = "20016";
    public static String user = "admin";
    public static String pass = "pass";
    private static Properties Prop = new Properties();

    public static void defaultSet() {
        IP = "localhost";
        Puerto = "20016";
        user = "admin";
        pass = "pass";
        PtoF();
    }

    public static String encrypt(String cadena, String Pal) {
        StandardPBEStringEncryptor s = new StandardPBEStringEncryptor();
        s.setPassword(Pal);
        return s.encrypt(cadena);
    }

    public static String decrypt(String cadena, String Pal) {
        StandardPBEStringEncryptor s = new StandardPBEStringEncryptor();
        s.setPassword(Pal);
        String devuelve = "";
        try {
            devuelve = s.decrypt(cadena);
        } catch (Exception e) {
        }
        return devuelve;
    }

    private static void PtoF() {
        Prop.setProperty("ip", IP);
        Prop.setProperty("port", Puerto);
        String eUser = encrypt(user, "user");
        String ePass = encrypt(pass, "pass");
        Prop.setProperty("user", eUser);
        Prop.setProperty("pass", ePass);
    }

    private static void FtoP() {
        IP = Prop.getProperty("ip");
        Puerto = Prop.getProperty("port");
        String eUser = Prop.getProperty("user");
        String ePass = Prop.getProperty("pass");
        user = decrypt(eUser, "user");
        pass = decrypt(ePass, "pass");
    }

    public static void save() {
        try {
            FileOutputStream out = new FileOutputStream("Server.ini");
            PtoF();
            Prop.store(out, "Propiedades");
        } catch (Exception e) {
        }
    }

    public static void load() {
        try {
            FileInputStream in = new FileInputStream("Server.ini");
            Prop.load(in);
            FtoP();
        } catch (IOException e) {
            defaultSet();
            try {
                save();
            } catch (Exception e2) {
            }
        }
    }// </editor-fold>

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
        request.getRequestDispatcher("/WEB-INF/Conecciones.jsp").forward(request, response);
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

        String User = request.getParameter("User");
        String Pass = request.getParameter("Pass");
        String Ip = request.getParameter("IP");
        String Port = request.getParameter("PORT");
        HttpSession Session = request.getSession();
        if (Ip == null) {
            if ((User.equals(user)) && (Pass.equals(pass))) {
                Session.setAttribute("TipoLog", TipoLogIn.ADMIN);
                request.setAttribute("User", user);
                request.setAttribute("Pass", pass);
                request.setAttribute("IP", IP);
                request.setAttribute("PORT", Puerto);
            }
            request.getRequestDispatcher("/WEB-INF/Conecciones.jsp").forward(request, response);
            return;
        } else {
            user = User;
            pass = Pass;
            IP = Ip;
            Puerto = Port;
            save();
            Session.setAttribute("TipoLog", TipoLogIn.VISITANTE);
            response.sendRedirect("/");
        }
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
