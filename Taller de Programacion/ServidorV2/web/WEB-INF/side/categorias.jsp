
<%@page import="publisher.DataCategoria"%>
<%@page import="Adapter.IBuscarCate"%>
<%@page import="Extras.Coneccion"%>
<%@page import="java.net.URL"%>
<%
    IBuscarCate IBC = new IBuscarCate();
    DataCategoria dC = IBC.listarCategorias();
%>
<ul id="nav" class="nav nav-list bs-docs-sidenav asdf">
    <%
        for (DataCategoria Dc : dC.getCateHijas()) {
            String Nombre = Dc.getNombre();
            if (Dc.isEsSimple()) {%>
    <li><a href="#" class="categuria" ><%=Nombre%></a></li>
        <%} else {%>
    <li><a href="#" class="parent"><%=Nombre%></a>
        <jsp:include page="/WEB-INF/side/catetemp.jsp">
            <jsp:param name="Cate" value="<%=Nombre%>"/>
        </jsp:include>
    </li>
    <%   }
        }%>
</ul>
<script type="text/javascript" src="/js/cateScript.js"></script>
