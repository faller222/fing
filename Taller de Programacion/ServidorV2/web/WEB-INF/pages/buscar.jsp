
<%@page import="publisher.*"%>
<%@page import="Adapter.*"%>
<%@page import="java.util.List"%>


<div class="span10">
    <%
        String Cl = (String) request.getAttribute("Clave");
        Integer Pg = (Integer) request.getAttribute("Pag");
        Integer Tp = (Integer) request.getAttribute("Tp");
        Integer Next = Pg + 1;
        Integer Prev = Pg - 1;

        List LP = (List) request.getAttribute("Lista");

        int i = 0;
        for (Object O : LP) {
            if (i < 8) {
                i++;
                DataProducto dP = (DataProducto) O;
                String Nom = dP.getNombre();
                String nRef = dP.getNRef().toString();
                String Des = dP.getDescripcion();
                Float Pre = dP.getPrecio();
    %>
    <div class="row-fluid">
        <div class="span3">
            <a href="/VerProducto?nRef=<%=nRef%>"><%= Nom%>  </a>
        </div>
        <div class="span7">
            <%= Des%>
        </div>
        <div class="span2">
            U$S <%= Pre%>
        </div>
    </div>
    <%}
        }%>
    <div class="row-fluid">
        <div class="pull-left">
            <%  if (Pg > 0) {%>
            <a onclick="busProds(<%=Prev%>);" class="btn btn-inverse btn-mini">
                <i class="icon-arrow-left icon-white"></i>
            </a>
            <% }%>
        </div>
        <div class="pull-right">
            <% if (LP.size() == 9) {%>
            <a onclick="busProds(<%=Next%>);" class="btn btn-inverse btn-mini">
                <i class="icon-arrow-right icon-white"></i>
            </a>
            <% }%>
        </div>
    </div>
