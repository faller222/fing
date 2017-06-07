package ServletConsulta;

import Adapter.ISesion;
import Adapter.IVerProducto;
import Extras.TipoLogIn;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import publisher.DataProducto;
import publisher.DataProveedor;
import publisher.DataUsuario;

@WebServlet(name = "VerInfoPerfil", urlPatterns = {"/VerInfoPerfil"})
public class InfoPerfil extends HttpServlet {

    protected void Procesar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String Que = request.getParameter("Tipo");
        if (Que == null) {
            getInfo(request, response);
        } else {
            getJSon(request, response);
        }
    }

    protected void getInfo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession Sesion = request.getSession();
            ISesion IS = (ISesion) Sesion.getAttribute("Sesion");
            DataUsuario User = IS.verInfoPerfil();
            TipoLogIn tipo = (TipoLogIn) Sesion.getAttribute("TipoLog");

            if (tipo == TipoLogIn.PROVEEDOR) {
                List<Integer> lista = ((DataProveedor) User).getProductos();
                IVerProducto IVP = new IVerProducto();
                List<DataProducto> productos = IVP.getProductos(lista);
                String Torta = getTorta(productos);
                String Lineas = getLineas(productos);
                request.setAttribute("Torta", Torta);
                request.setAttribute("Lineas", Lineas);
            }

            request.setAttribute("Usuario", User);
            request.setAttribute("TipoUsuario", tipo);
            String req = "?page=pages/verPerfil.jsp";
            request.getRequestDispatcher("DirectMarket" + req).forward(request, response);
            return;
        } catch (Exception e) {
            response.sendRedirect("/");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Codigo para obtener los datos del grafico de lineas.">
    private String getLineas(List<DataProducto> productos) {
        String res = "";
        res += "[";//Inicio primer Corchete
        res += "['Mes'";//Esto y el for, la columna de la grafica
        for (DataProducto prod : productos) {
            res += ", '" + prod.getNombre() + "'";
        }
        res += "]";//Fin de la columna de la grafica
        int MesPrimero = (new Date()).getMonth() + 1;
        for (int i = 0; i < 12; i++) {//Reto de las columnas
            Float acu = new Float(0);
            String Colum = "";
            String mes = "";
            // <editor-fold defaultstate="collapsed" desc="Codigo imprimir los meses a partir del actual.">
            int aaa = (MesPrimero + i) % 12;
            switch (aaa) {
                case 0:
                    mes = "Enero";
                    break;
                case 1:
                    mes = "Febrero";
                    break;
                case 2:
                    mes = "Marzo";
                    break;
                case 3:
                    mes = "Abril";
                    break;
                case 4:
                    mes = "Mayo";
                    break;
                case 6:
                    mes = "Julio";
                    break;
                case 5:
                    mes = "Junio";
                    break;
                case 8:
                    mes = "Setiembre";
                    break;
                case 11:
                    mes = "Diciembre";
                    break;
                case 9:
                    mes = "Octubre";
                    break;
                case 7:
                    mes = "Agosto";
                    break;
                case 10:
                    mes = "Noviembre";
                    break;
            }// </editor-fold>
            Colum += ", ['" + mes + "'";//Inicio de la columna, en el piso el mes, luego datos
            for (DataProducto prod : productos) {
                Float N = prod.getEstadistic().getMeses().get((MesPrimero + i) % 12);
                acu += N;
                Colum += ", " + N;
            }
            Colum += "]";//Fin Columna
            if (acu > 0) {
                res += Colum;
            }
        }
        res += "]";//Fin resto de Columnas
        return res;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Codigo para obtener los datos del grafico de torta.">
    private String getTorta(List<DataProducto> productos) {
        String res = "";
        res += "[";
        for (DataProducto prod : productos) {
            res += "['" + prod.getNombre() + "'," + prod.getEstadistic().getGanancia() + "],";
        }
        res += "['Vacio', 0]";
        res += "]";
        return res;
    }// </editor-fold>

    protected void getJSon(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession sesion = request.getSession();
        ISesion IS = (ISesion) sesion.getAttribute("Sesion");
        DataUsuario User = IS.verInfoPerfil();
        String Que = request.getParameter("Tipo").toLowerCase();
        String devol = "<error>";
        if (User instanceof DataProveedor) {
            try {
                DataProveedor DP = (DataProveedor) User;
                List<Integer> lista = DP.getProductos();
                IVerProducto IVP = new IVerProducto();
                List<DataProducto> productos = IVP.getProductos(lista);
                if (Que.equals("torta")) {
                    devol = getTorta(productos);
                }
                if (Que.equals("lineas")) {
                    devol = getLineas(productos);
                }
            } catch (Exception ex) {
            }
        }
        PrintWriter Out = response.getWriter();
        Out.println(devol);
        Out.close();
    }
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Procesar(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Procesar(request, response);
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
