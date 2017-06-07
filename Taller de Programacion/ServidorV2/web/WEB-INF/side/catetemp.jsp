


<%@page import="Adapter.IBuscarCate"%>
<%@page import="publisher.DataCategoria"%>
<%
    IBuscarCate IBCo = new IBuscarCate();
    DataCategoria dC = IBCo.buscarCategoria(request.getParameter("Cate"));
%>

<ul class="bs-docs-sidenav" style="display:none;">
    <%
        for (publisher.DataCategoria Dc : dC.getCateHijas()) {
            String Nombre = Dc.getNombre();
            if (Dc.isEsSimple()) {
    %>

    <li><a href="#" class="categuria"><%=Nombre%></a></li>

    <%} else {%>
    <li ><a href="#" class="parent"><%=Nombre%></a>
        <jsp:include page="catetemp.jsp">
            <jsp:param name="Cate" value="<%=Nombre%>"/>
        </jsp:include>
    </li>
    <%}
        }%>
</ul>
