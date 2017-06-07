<%@page import="Adapter.*"%>
<%@page import="publisher.*"%>
<%
    //las variables estan asi para que no se repitan
    ISesion iSaa = (ISesion) session.getAttribute("Sesion");
    publisher.DataProveedor asyd = (DataProveedor) iSaa.verInfoPerfil();
    String huic = asyd.getNombre() + " " + asyd.getApellido();
    String adfgv = asyd.getNickname();
%>
<ul class="nav pull-right">
    <li class="dropdown">
        <a href="#" class="dropdown-toggle" data-toggle="dropdown"><img src="Image?id=<%=adfgv%>&tp=P" class="span3 img-polaroid img-rounded"><%=huic%><b class="caret"></b></a>
        <ul class="dropdown-menu">
            <li ><a href=VerPerfil><i class="icon-user"></i> Perfil</a></li>
            <li ><a href="#altaProducto" data-toggle="modal"><i class="icon-plus"></i> Vender</a></li>
            <li ><a href="/Chat"><i class="icon-envelope"></i> Mensajes</a></li>
            <li ><a onclick="verReclamos();"><i class="icon-bullhorn"></i> Reclamos</a></li>
            <li ><a onclick="Cerrar();"><i class="icon-eject"></i> Logout</a></li>
        </ul>
    </li>
</ul>
<a class="btn btn-danger btn-mini" id="chat-btn-barra" href="/Chat">
    <i class="icon-envelope icon-white"></i>
</a>
<script type="text/javascript" src="/js/loged.js"></script>