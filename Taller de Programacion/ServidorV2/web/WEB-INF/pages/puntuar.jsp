
<%@page import="java.util.Iterator"%>
<%@page import="publisher.*"%>
<%@page import="Adapter.*"%>
<%@page import="java.util.List"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<div class="span10 ">
    <div class="well color1">
        <legend><h2 class="color1"> Productos comprados </h2></legend>
    </div>
    <div class="row-fluid">
        <legend>
            <div class="span1">
                Nº
            </div>
            <div class="span2">
                Producto
            </div>
            <div class="span2">
                Imagen
            </div>
            <div class="span2">
                Precio
            </div>
            <div class="span2">
                Descripcion
            </div>
            <div class="span3">
                Puntuar
            </div>
        </legend>
    </div>
    <%
        List<DataProducto> prodsComprados = (List<DataProducto>) request.getAttribute("Comprados");
        List<Integer> aPuntuar = (List<Integer>) request.getAttribute("aPuntuar");
        ISesion Ses = (ISesion) session.getAttribute("Sesion");
        DataUsuario user = Ses.verInfoPerfil();
        String Clie = user.getNombre();
        Iterator it = prodsComprados.iterator();
        Integer nRef;
        String nombre;
        Float precio;
        String descripcion;
        Integer cant;
        boolean nopuntuado = false;
        for (it = prodsComprados.iterator(); it.hasNext();) {
            nopuntuado = false;
            DataProducto dp = (DataProducto) it.next();
            nRef = dp.getNRef();
            nombre = dp.getNombre();
            precio = dp.getPrecio();
            descripcion = dp.getDescripcion();
            if (aPuntuar.contains(nRef)) {
                nopuntuado = true;
            }
    %>
    <div class="row-fluid">
        <div class="span1">
            <%= nRef%>
        </div>
        <div class="span2">
            <a href="VerProducto?nRef=<%=nRef%>"><%= nombre%></a>
        </div>
        <div class="span2">
            <img src="Image?id=<%=nRef%>&tp=P&no=0" class="img-polaroid img-rounded ">
        </div>
        <div class="span2">
            <%="U$S " + precio%>
        </div>
        <div class="span2">
            <%= descripcion%>
        </div>
        <div class="span3">
            <% if (nopuntuado == true) {%>
            <a href="VerProducto?nRef=<%=nRef%>" class="btn btn-info btn-mini">
                <i class="icon-star icon-white"></i> Puntuar
            </a>
            <% } else {%>
            <a  class="btn btn-info btn-mini disabled">
                <i class="icon-star icon-white"></i> Puntuar
            </a>
            <%}%>
        </div>
    </div>
    <%}%>
</div>
