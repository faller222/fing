package Extras;

import Adapter.ISesion;
import java.net.URL;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import publisher.DataUsuario;
import publisher.PAccesoSitio;
import publisher.PAccesoSitioService;

public class gestorAcceso {

    private PAccesoSitioService PASSerice;
    private PAccesoSitio PAS;

    public gestorAcceso() {
        try {
            URL url = new URL("http://" + Coneccion.IP + ":" + Coneccion.Puerto + "/PAS?wsdl");
            PASSerice = new PAccesoSitioService(url);
            PAS = PASSerice.getPAccesoSitioPort();
        } catch (Exception e) {
        }
    }

    private void log(String ip, String User, String url, String Browser, String So) {
        if (PAS != null) {
            PAS.regitrarAcceso(ip, User, url, Browser, So);
        }
    }

    public void log(ServletRequest request) {
        String uaString = getUAgent(request);

        String ip = getIp(request);
        String User = getUser(request);
        String Url = getUrl(request);
        String Brw = getBrowser(uaString);
        String Os = getOS(uaString);

        log(ip, User, Url, Brw, Os);
    }

    private static String getUser(ServletRequest request) {
        String pla = "Visitante";
        if (request instanceof HttpServletRequest) {
            HttpSession sesion = ((HttpServletRequest) request).getSession();
            ISesion IS = (ISesion) sesion.getAttribute("Sesion");
            DataUsuario usr = null;
            if (IS != null) {
                usr = IS.verInfoPerfil();
            }

            if (usr != null) {
                pla = usr.getNickname();
            }
        }

        if (pla == null) {
            pla = "";
        }
        return pla;
    }

    private static String getIp(ServletRequest request) {
        String ret = request.getRemoteAddr();
        if (ret == null) {
            ret = "";
        }
        return ret;
    }

    private static String getUrl(ServletRequest request) {
        String url = null;
        String queryString = null;
        if (request instanceof HttpServletRequest) {
            url = ((HttpServletRequest) request).getRequestURL().toString();
            queryString = ((HttpServletRequest) request).getQueryString();
        }
        if (queryString != null) {
            url += "?" + queryString;
        }
        if (url == null) {
            url = "";
        }
        return url;

    }

    private static String getUAgent(ServletRequest request) {
        String pla = "";
        if (request instanceof HttpServletRequest) {
            pla = ((HttpServletRequest) request).getHeader("User-Agent");
        }
        return pla;
    }

    private static String getBrowser(String uAgent) {
        String Ret = "";
        try {
            String Aux = uAgent.substring(0, uAgent.lastIndexOf('/'));
            Ret = Aux.substring(Aux.lastIndexOf(' ') + 1);
        } catch (Exception e) {
            Ret = "";
        }
        return Ret;
    }

    private static String getOS(String uAgent) {
        String Ret = "";
        try {
            String Aux = uAgent.substring(uAgent.indexOf('('), uAgent.indexOf(')') + 1);
            Aux = Aux.substring(Aux.indexOf(' ') + 1, Aux.length() - 1);
            Ret = Aux.substring(0, Aux.indexOf(' '));
        } catch (Exception e) {
            Ret = "";
        }
        return Ret;
    }
}
