
<%@page import="Extras.TipoLogIn"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="head.html" %>
    </head>
    <body background="../img/fondo.png" style="background-attachment: fixed">
        <script type="text/javascript" src="/js/jquery.js"></script>
        <script type="text/javascript" src="/js/bootstrap.js"></script>
        <script type="text/javascript" src="/js/buscar.js"></script>

        <script type="text/javascript" src="https://www.google.com/jsapi"></script>

        <%@include file="/WEB-INF/modals/AltaUsuario.jsp" %>
        <%@include file="/WEB-INF/modals/Login.jsp" %>
        <%@include file="/WEB-INF/modals/AltaProducto.jsp" %>



        <div class="navbar navbar-fixed-top">
            <div class="navbar-inner ">
                <div class="well-small">
                    <div class="row-fluid">
                        <div class="span2">
                            <a href="/">
                                <img src="/img/directmarket.gif">
                            </a>
                        </div>
                        <div class="span7 well-small ">
                            <div class="pagination-centered" >

                                <input type="hidden" value="0" name=Pag id="Pag">
                                <input type="hidden" value="0" name=Tp id="Tp">

                                <div class="input-append span15">
                                    <input placeholder="Buscar Producto" name=Clave class="span9" id="bPro" type="text" onkeyup="busProds(0);" onkeydown="enterSearch(event);">
                                    <div class="btn-group">
                                        <script src="/js/buscar.js">

                                        </script>
                                        <button class="btn dropdown-toggle" data-toggle="dropdown" id="selOp" >
                                            Por Defecto
                                            <span class="caret"></span>
                                        </button>
                                        <ul class="dropdown-menu">
                                            <li>
                                                <a href="#" id="0" class="op pull-left">Por Defecto</a>
                                            </li>
                                            <li>
                                                <a href="#" id="1" class="op pull-left">Por Nombre</a>
                                            </li>
                                            <li>
                                                <a href="#" id="2" class="op pull-left">Por Precio</a>
                                            </li>
                                            <li>
                                                <a href="#" id="3" class="op pull-left">Por Ventas</a>
                                            </li>
                                        </ul>
                                    </div>
                                    <a class="btn" onclick="busProds(0);">
                                        <i class="icon-search"></i>
                                    </a>
                                </div>

                            </div>
                        </div>

                        <div class="span3 ">
                            <div class="pull-right ">
                                <%
                                    TipoLogIn TL = (TipoLogIn) session.getAttribute("TipoLog");
                                    System.out.println(TL);
                                    if (TL != null) {
                                        if (TL == TipoLogIn.PROVEEDOR) {%>
                                <%@include file="/WEB-INF/login/loginProve.jsp" %>
                                <% }
                                    if (TL == TipoLogIn.CLIENTE) {%>
                                <%@include file="/WEB-INF/login/loginClie.jsp" %>
                                <%  }
                                    if (TL == TipoLogIn.VISITANTE) {%>
                                <%@include file="/WEB-INF/login/loginVisit.jsp" %>
                                <% }
                                } else {%>
                                <%@include file="/WEB-INF/login/loginVisit.jsp" %>
                                <% }%>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row-fluid">

            <div class="span3">
                <%@include file="/WEB-INF/side/categorias.jsp" %>
            </div>
            <div class="span9">
                <div id="main">
                    <%String Page = (String) request.getAttribute("page");%>
                    <jsp:include page="<%=Page%>" flush="true" />
                </div>
            </div>

        </div >
    </body>
</html>
