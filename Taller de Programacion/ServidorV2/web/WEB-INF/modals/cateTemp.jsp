<%@page import="java.util.Map"%>
<%@page import="publisher.*"%>
<%@page import="Adapter.*"%>


<%  IBuscarCate IBCo = new IBuscarCate();
    DataCategoria dC = IBCo.buscarCategoria(request.getParameter("Cate"));%>
<ul>
    <%    for (DataCategoria Dc : dC.getCateHijas()) {
            String Nombre = Dc.getNombre();
            if (Dc.isEsSimple()) {
    %>

    <li><label class="checkbox">
        <input type="checkbox" value="<%=Nombre%>" id="categoria" name="categoria">
        <%=Nombre%>
    </label>
</li>

<%} else {%>
<li ><a href="#" class="parent"><%=Nombre%></a>
    <jsp:include page="/WEB-INF/modals/cateTemp.jsp">
        <jsp:param name="Cate" value="<%=Nombre%>"/>
    </jsp:include>
</li>
<%}
        }%>
</ul>