<%@page import="java.util.Date"%>
<%@page import="java.util.List" %>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="publisher.*"%>
<%@page import="Adapter.*"%>
<%@page import="Extras.*"%>

<%
    //DataUsuario du = (DataUsuario) session.getAttribute("Usuario");
    DataUsuario du = (DataUsuario) request.getAttribute("Usuario");
    String n = du.getNombre();
    String a = du.getApellido();
    String nick = du.getNickname();
    String mail = du.getMail();
    String fNac = Utilidad.formatS(du.getFechaNac());
    TipoLogIn tLog = (TipoLogIn) request.getAttribute("TipoUsuario");
    String tipo = "";
    String sitioWeb = "";
    String compania = "";
    String uFotoxx = "";
    if (tLog == TipoLogIn.CLIENTE) {
        uFotoxx = "Image?id=" + nick + "&tp=C";
        tipo = "Cliente";
    } else {
        if (tLog == TipoLogIn.PROVEEDOR) {
            uFotoxx = "Image?id=" + nick + "&tp=P";
            tipo = "Proveedor";
            DataProveedor dp = (DataProveedor) du;
            sitioWeb = dp.getSitioWeb();
            compania = dp.getCompania();
        }
    }
%>

<div class="span10">
    <div class=" well color1">
        <div class="row-fluid ">
            <div class="span7 "> <legend><h1 class="color1"><%=tipo + " " + n + " " + a%></h1></legend></div>
        </div>
    </div>
    <div class="row-fluid">
        <div class="span2">
            <img class="img-polaroid" src="<%=uFotoxx%>">
        </div>
        <div class="span10">
            Nick: <strong> <%=nick%></strong><br>
            Mail: <strong>  <%=mail%></strong><br>
            Fecha de nacimiento: <strong> <%=fNac%> </strong><br>
            <% if (tLog == TipoLogIn.PROVEEDOR) {%>
            Sitio Web: <strong><a href="<%=sitioWeb%>"><%=sitioWeb%></a></strong> <br>
            Compañia: <strong><%= compania%></strong><br>

            <%}%>
        </div>
    </div>
    <% if (tLog == TipoLogIn.CLIENTE) {%>
    <br>
    <a class="btn btn-info btn-mini" href="#" onclick="verOrdenes();">
        <i class="icon-align-left icon-white"></i> Ver compras
    </a>
    <% DataCliente dc = (DataCliente) du;
        String nOrd = "Activar";
        String nPrv = "Activar";
        String nPrd = "Activar";
        String nRec = "Activar";
        if (dc.isNotiOrden()) {
            nOrd = "Desactivar";
        }
        if (dc.isNotiProd()) {
            nPrd = "Desactivar";
        }
        if (dc.isNotiProve()) {
            nPrv = "Desactivar";
        }
        if (dc.isNotiRec()) {
            nRec = "Desactivar";
        }

    %>
    <br>
    <br>
    Activar/Desactivar Notificaciones:
    <br>
    Orden de compra:
    <div class="btn btn-mini noti" id="Ord"><%=nOrd%></div>
    <br>
    Nuevo Comentario:
    <div class="btn btn-mini noti" id="Prod"><%=nPrd%></div>
    <br>
    Nuevo Producto:
    <div class="btn btn-mini noti" id="Prove"><%=nPrv%></div>
    <br>
    Respuesta a Reclamo:
    <div class="btn btn-mini noti" id="Rec"><%=nRec%></div>
    <%}%>
    <script type="text/javascript">
        $('.noti').click(function() {
            var accion = $(this).text();
            var tipo = $(this).attr('id');
            $.ajax({
                type: "POST",
                url: "Notis",
                data: {
                    tnoti: tipo,
                    fun: accion}
            }).done(function(msg) {
                if (msg !== "<false>") {
                    $("#" + tipo).text(msg);
                }
            });
        });
    </script>


    <% if (tLog == TipoLogIn.PROVEEDOR) {%>
    <br>
    <a class="btn btn-info btn-mini" onclick="prodProv();">
        <i class="icon-align-left icon-white"></i> Ver Productos
    </a>
    <br>
    <%
        DataProveedor pro = (DataProveedor) du;
        List<Integer> lista = pro.getProductos();
        IVerProducto IVP = new IVerProducto();
        List<DataProducto> productos = IVP.getProductos(lista);%>

    <script type="text/javascript">
        google.load("visualization", "1", {packages: ["corechart"]});
        google.setOnLoadCallback(drawLines);
        function drawLines() {
            var data = google.visualization.arrayToDataTable(<%=request.getAttribute("Lineas")%>);
            var options = {
                curveType: 'function',
                title: 'Estadisticas'
            };
            var chart = new google.visualization.LineChart(document.getElementById('lineChart'));
            chart.draw(data, options);
        }

        google.setOnLoadCallback(drawPie);
        function drawPie() {
            var data = new google.visualization.DataTable();
            data.addColumn('string', 'Topping');
            data.addColumn('number', 'Slices');
            data.addRows(<%=request.getAttribute("Torta")%>);
            var options = {
                title: 'Ganancias',
                pieHole: 0.4
            };
            var chart = new google.visualization.PieChart(document.getElementById('piechart'));
            chart.draw(data, options);
        }
    </script>
    <div id="piechart" style="width: 900px; height: 500px;"></div>

    <div id="lineChart" style="width: 900px; height: 500px;"></div>
</div>
<%}%>
