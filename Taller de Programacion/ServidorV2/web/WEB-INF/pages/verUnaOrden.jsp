

<%@page import="java.util.List" %>
<%@page import="publisher.*"%>
<%@page import="Adapter.ISesion"%>
<%@page import="Extras.Utilidad"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    ISesion IS = (ISesion) session.getAttribute("Sesion");
    DataUsuario user = IS.verInfoPerfil();
    String naame = user.getNombre();
    DataOrdenCompra DOC = (DataOrdenCompra) request.getAttribute("Oden");

%>

<Legend> <Strong> Contenido </Strong></legend>
<br>
<div class="row-fluid">
    <div class="span2"><strong>Cant</strong></div>
    <div class="span5"><strong>Producto</strong></a></div>
    <div class="span2"><strong>Precio</strong></div>
    <div class="span2"><strong>Sub total</strong></div>
</div>
<%for (DataLinea dL : DOC.getLineas()) {
%>
<div class="row-fluid">
    <div class="span2"><strong><%= dL.getCantidad()%></strong></div>
    <div class="span5"><a href=VerProducto?nRef=<%= dL.getProd().getNRef()%>><%= dL.getProd().getNombre()%></a></div>
    <div class="span2"><%= dL.getPrecioProd()%></div>
    <div class="span2"><%= dL.getPrecioLinea()%></div>
</div>
<%}%>
<blockquote class="pull-right">
    <p>Total <strong> U$S <%= DOC.getTotal()%></strong>   </p>
</blockquote>
<br>
<div class="row-fluid">
    <div class="span5">
        <Legend> <Strong> Evolucion </Strong></legend>
        <div class="row-fluid">
            <div class="span6"><strong>Estado</strong></div>
            <div class="span6"><strong>Fecha</strong></div>
        </div>
        <% for (DataEstado dE : DOC.getEstados()) {
        %>
        <div class="row-fluid">
            <div class="span6"><%= dE.getEst().toString()%> </div>
            <div class="span6"><%= Utilidad.formatS(dE.getFecha())%> </div>
            <br>
        </div>
        <%}%>
    </div>
    <div class="span5">
        <%  List<DataEstado> LDE = DOC.getEstados();
            switch (LDE.get(LDE.size() - 1).getEst()) {
                case CANCELADA:%>
        <img  src="/img/Ord/EstadoCancelada.png">
        <%;
                break;
            case CONFIRMADA:%>
        <img src="/img/Ord/EstadoConfirmada.png">
        <%break;
            case PREPARADA:%>
        <img  src="/img/Ord/EstadoPreparada.png">
        <a data-toggle="modal" class="btn btn-info btn-mini" href="#confordenModal">
            <i class="icon-shopping-cart icon-white"></i>Confirmar
        </a>
        <%;
                break;
            case RECIBIDA:%>
        <img  src="/img/Ord/EstadoRecibida.png">
        <% break;
            }%>
        <a class="btn btn-info btn-mini"href="pdfCreator?N=<%= DOC.getNumero()%>" target="_blank">
            <i class="icon-download icon-white"></i>Imprimir
        </a>
    </div>
</div>
<jsp:include page="/WEB-INF/modals/ConfirmarOrden.jsp" flush="true">
    <jsp:param name="nombre" value="<%= naame%>" />
    <jsp:param name="numero" value="<%= DOC.getNumero()%>" />
</jsp:include>