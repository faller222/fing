
<%@page import="Extras.Utilidad"%>
<%@page import="publisher.*"%>
<%@page import="Adapter.*"%>
<%@page import="java.util.List" %>
<%@page import="java.util.Iterator" %>
<%@page import="java.text.SimpleDateFormat" %>


<div class="span10">
    <%
        //ISesion IS = (ISesion) session.getAttribute("Sesion");
        //DataUsuario user = IS.verInfoPerfil();
        //String name = user.getNombre();
        //String lname = user.getApellido();
        List Ordenes = (List) request.getAttribute("Ordenes");
    %>
    <div class="well color1">
        <legend><h2 class="color1"> Ordenes de compras </h2></legend>
    </div>
    <table class="table table-striped">
        <tr>
            <th>#</th>
            <th>Modificada</th>
            <th>Estado</th>
            <th>Total</th>
        </tr>
        <%
            for (Object OdC : Ordenes) {
                DataOrdenCompra DOC = (DataOrdenCompra) OdC;
                List<DataEstado> estados = DOC.getEstados();
                DataEstado est = estados.get(estados.size() - 1);
                String fecha = Utilidad.formatL(DOC.getEstados().get(DOC.getEstados().size() - 1).getFecha());
        %>
        <tr>
            <td><%= DOC.getNumero()%></td>
            <td><%=fecha%></td>
            <td><%=est.getEst().toString()%></td>
            <td><%= DOC.getTotal()%></td>
        </tr>
        <% }%>
    </table>
    <br>
    <br>
    Por favor seleccione una orden para ver su información:


    <select name="orden" id="orden">
        <%
            for (Object OdC : Ordenes) {
                DataOrdenCompra DOC2 = (DataOrdenCompra) OdC;
        %>
        <option value="<%= DOC2.getNumero()%>">Orden <%= DOC2.getNumero()%></option>
        <% }%>
    </select>
    <br>
    <button class="btn btn-info btn-small" reset="false" id="verinfo">
        <i class="icon-info-sign icon-white"></i> Ver informacion
    </button>
    <br>
    <br>
    <div id="Ordn">
    </div>

</div>
</div>
<script type="text/javascript">
    $("#verinfo").click(function() {
        var x = document.getElementById("orden").value;
        $.ajax({
            type: "POST",
            url: "UnaOrden",
            data: {
                onClie: 'true',
                Orden: x}
        }).done(function(msg) {
            document.getElementById("Ordn").innerHTML = msg;
        });
    });
</script>