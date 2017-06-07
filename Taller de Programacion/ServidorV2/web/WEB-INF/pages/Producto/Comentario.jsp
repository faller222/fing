
<%@page import="Extras.Utilidad"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.List"%>
<%@page import="publisher.*"%>
<%@page import="Adapter.*"%>


<%
    String Prod = request.getParameter("Prod");
    String Comen = request.getParameter("Comen");
    String punt = request.getParameter("Punt");
    Integer P = Integer.parseInt(Prod);
    Integer C = Integer.parseInt(Comen);
    Integer puntClie = Integer.parseInt(punt);

    IVerProducto IVP = new IVerProducto();
    DataProducto dP = IVP.seleccionarProducto(P);
    List<DataComentario> LCome = dP.getTodos();
    List<DataPuntaje> puntajes = dP.getPuntajes();
    DataComentario dC = null;
    for (DataComentario c : LCome) {
        if (c.getId() == C) {
            dC = c;
        }
    }
    String User = dC.getClie();
    String Txt = dC.getTexto();
    String fecha = Utilidad.formatL(dC.getFecha());
%>
<div class="well degrade">
    <div class="row-fluid">
        <div class="span1">
            <img src="Image?id=<%=User%>&tp=C" >
        </div>
        <div class="span4">
            <legend><%=User%> Dijo:</legend>
        </div>
    </div>
    <div class="row-fluid">
        <div id="<%=C%>" class="fito span6" >
            <a >Responder</a>
        </div>
        <div class="span6 pull-right">
            <div class="pull-right">
                <%=fecha%>
            </div>
        </div>
    </div>
    <div class="row-fluid">
        <span class="stars"><%=puntClie%></span> <%=Txt%>
    </div>

    <%  puntClie = 0;
        for (DataComentario D : dC.getRespuestas()) {
            Integer Num = D.getId();
            for (DataPuntaje dPunt : puntajes) {
                if ((dPunt.getCli()).equals(D.getClie())) {
                    puntClie = dPunt.getPuntos();
                }
            }
    %>
    <jsp:include page="/WEB-INF/pages/Producto/Comentario.jsp">
        <jsp:param name="Prod" value="<%=P%>"/>
        <jsp:param name="Comen" value="<%=Num%>"/>
        <jsp:param name="Punt" value="<%=puntClie%>"/>
    </jsp:include>
    <%}%>

    <div class="fito-form" id="F<%=C%>" hidden>
        <input id="T<%=C%>" type="text" name="txtComentario" placeholder="Ingrese comentario">
        <button class="btn btn-inverse btn-mini fito-btn" id="B<%=C%>">
            Responder <i class="icon-check icon-white"></i>
        </button>
    </div>
</div>