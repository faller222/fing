

<%@page import="publisher.DataProducto"%>
<%@page import="java.util.List"%>

<div class="span10">
    <table class="table table-striped">
        <tr>
            <th>Nombre</th>
            <th>Descripción</th>
            <th>Precio (U$S)</th>
        </tr>
        <%
            List<Object> L = (List) request.getAttribute("Lista");
            for (Object O : L) {
                DataProducto dP = (DataProducto) O;
                String Nom = dP.getNombre();
                String nRef = dP.getNRef().toString();
                String Des = dP.getDescripcion();
                Float Pre = dP.getPrecio();
        %>
        <tr>
            <td><a href=VerProducto?nRef=<%=nRef%>><%= Nom%></td>
            <td><%= Des%></td>
            <td><%= Pre%></td>
        </tr>
        <% }%>
    </table>
