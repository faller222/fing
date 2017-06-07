
<%@page import="Extras.Utilidad"%>
<%@page import="publisher.DataReclamo"%>
<%@page import="java.util.List"%>

<Div class="span10">
    <div class="well color1" >
        <legend><h3 class="color1"> Reclamos a tus productos </h3></legend>
    </div>
    <table class="table table-hover">
        <tr>
        <th>Fecha</th>
        <th>Cliente</th>
        <th>Producto</th>
        <th>Reclamo</th>
        </tr>
        <%
            List LP = (List) request.getAttribute("Lista");

            for (Object O : LP) {
                DataReclamo dP = (DataReclamo) O;
                String Clie = dP.getClient();
                String Nom = dP.getNomProd();
                String Txt = dP.getTexto();
                String Fec = Utilidad.formatS(dP.getFecha());
        %>
        <tr>
        <INPUT type="hidden" value="<%=dP.getNProd()%>" id="P<%= dP.getId()%>">
        <td><%= Fec%></td>
        <td><%= Clie%></td>
        <td><%= Nom%></td>
        <td><%= Txt%></td>
        <% if (dP.getRespuesta() == null) {%>
        <td><button class="btn btn-info btn-mini respRec" id="<%= dP.getId()%>">Atender</button></td>
        </tr>
        <%}
            }%>
    </table>
    <div id="divResp" hidden>
        <form id="enviarRespuesta" action="AltaRespRec" method="GET">
            <input type="hidden" name="respRecId" value="" id="respRecId">
            <input type="hidden" name="IdProd" value="" id="IdProd">
            <input type="text" name="txtRespRec" id="txtRespRec" placeholder="responde">
            <br>
            <a id="RespRecBtn" class="btn btn-inverse">
                Responder <i class="icon-bell icon-white icon-mini"></i>
            </a>
        </form>
    </div>
</Div>

<script src="/js/reclamo.js" type="text/javascript"></script>
