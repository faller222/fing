<%@page import="publisher.*"%>
<%@page import="Adapter.*"%>
<%
    //las variables estan asi para que no se repitan
    ISesion ffhfgh = (ISesion) session.getAttribute("Sesion");
    DataCliente dUaa = (DataCliente) ffhfgh.verInfoPerfil();
    String nickaa = dUaa.getNickname();
    String Nombreaa = dUaa.getNombre() + " " + dUaa.getApellido();
%>


<ul class="nav pull-right">
    <li class="dropdown">
        <a href="#" class="dropdown-toggle" data-toggle="dropdown"><img src="Image?id=<%=nickaa%>&tp=C" class="span3 img-polaroid img-rounded" > <%=Nombreaa%><b class="caret"></b></a>
        <ul class="dropdown-menu">
            <li ><a href=VerPerfil><i class="icon-user"></i> Perfil</a></li>
            <li ><a onclick="verCarrito();"><i class="icon-shopping-cart"></i> Carrito</a></li>
            <li ><a href="/Chat"><i class="icon-envelope"></i> Mensajes</a></li>
            <li ><a onclick="verPaPuntuar();"><i class="icon-star"></i> Puntuar productos</a></li>
            <li ><a onclick="Cerrar();"><i class="icon-eject"></i> Logout</a></li>
        </ul>
    </li>
</ul>
<a class="btn btn-danger btn-mini" id="chat-btn-barra" href="/Chat">
    <i class="icon-envelope icon-white"></i>
</a>
<script type="text/javascript" src="/js/loged.js"></script>