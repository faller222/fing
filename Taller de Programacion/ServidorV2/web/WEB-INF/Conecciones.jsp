
<%@page import="Extras.TipoLogIn"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="head.html" %>
    </head>
    <body background="../img/fondo7.png" style="background-attachment: fixed">
        <script type="text/javascript" src="/js/jquery.js"></script>
        <script type="text/javascript" src="/js/bootstrap.js"></script>

        <%
            TipoLogIn TL = (TipoLogIn) session.getAttribute("TipoLog");
            if (TL != TipoLogIn.ADMIN) {
        %>
        <form  method="POST" action="Coneccion" name="connForm" accept-charset="UTF-8">
            User:<input type="text" name="User"><br>
            Pass:<input type="password" name="Pass"><br>
            <button type="submit" > Entrar</button>
        </form>
        <%} else {
            String User = (String) request.getAttribute("User");
            String Pass = (String) request.getAttribute("Pass");
            String IP = (String) request.getAttribute("IP");
            String PORT = (String) request.getAttribute("PORT");
        %>
        <form method="POST" action="Coneccion" name="connForm" accept-charset="UTF-8">
            User:<input type="text" name="User" value="<%=User%>"><br>
            Pass:<input type="password" name="Pass" value="<%=Pass%>"><br>
            IP:<input type="text" name="IP" value="<%=IP%>"><br>
            PORT:<input type="text" name="PORT" value="<%=PORT%>"><br>
            <button type="submit" > Guardar</button>
        </form>
        <%}%>

    </body>
</html>
