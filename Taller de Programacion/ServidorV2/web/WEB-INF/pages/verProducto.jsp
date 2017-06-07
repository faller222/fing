
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="publisher.*"%>
<%@page import="Adapter.*"%>
<%@page import="Extras.TipoLogIn"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<%
    ISesion Ses = (ISesion) session.getAttribute("Sesion");
    TipoLogIn tLog = (TipoLogIn) session.getAttribute("TipoLog");
    DataProducto dP = (DataProducto) request.getAttribute("Producto");
    List cates = (List) request.getAttribute("Cates");
    Integer nRef = dP.getNRef();
    Float pre = dP.getPrecio();
    String N = dP.getNombre();
    String D = dP.getDescripcion();
    String E = dP.getEspecificacion().replaceAll("\n", "<BR />");//<---Gozadoooo
    String P = dP.getProveedor();
    List aPuntuar = (List) request.getAttribute("aPuntuar");
%>

<%
    Integer stars1 = dP.getPunt1();
    Integer stars2 = dP.getPunt2();
    Integer stars3 = dP.getPunt3();
    Integer stars4 = dP.getPunt4();
    Integer stars5 = dP.getPunt5();
    Integer ptosTotal = stars1 + stars2 + stars3 + stars4 + stars5;
    Float promedio = dP.getPromedio();

    List<DataPuntaje> puntajes = dP.getPuntajes();
%>

<input type="hidden" value="<%=stars1%>" id="star1">
<input type="hidden" value="<%=stars2%>" id="star2">
<input type="hidden" value="<%=stars3%>" id="star3">
<input type="hidden" value="<%=stars4%>" id="star4">
<input type="hidden" value="<%=stars5%>" id="star5">
<div class="span11">
    <div class="well color1">
        <div class="row-fluid ">
            <div class="span7 "> <legend><h1 class="color1"><%=N%></h1></legend></div>
            <div id="Carro" class="pull-right" >
                <%if (tLog == TipoLogIn.CLIENTE) {%>
                <div class="col-lg-6">
                    <div class="input-group">
                        <span class="input-group-addon">Agregar al Carrito</span>
                        <input type="hidden" name="idProducto" value="<%=nRef%>" class="form-control" id="ProdCarr">
                        <input size="3" type="number" name="Cant" value="1" class="form-control span3" id="CantCarr">
                        <span class="input-group-btn">
                            <button id="addCarr" class="btn btn-info" type="button">
                                <i class="icon-plus icon-white"></i>
                            </button>
                        </span>
                    </div>
                </div>
                <%}%>
            </div>
        </div>
    </div>

    <script>
        $("#addCarr").click(function() {
            $.ajax({
                type: "POST",
                url: "ACarrito",
                data: {idProducto: $("#ProdCarr").val(),
                    Cant: $("#CantCarr").val()
                }
            }).done(function(msg) {
                $("#main").html(msg);
            });
        });
    </script>

    <div class="row-fluid">
        <div class="span4">
            <a href="Image?id=<%=nRef%>&tp=P&no=0"><img class="img-rounded" src="Image?id=<%=nRef%>&tp=P&no=0"/></a>
        </div>
        <div class="span4">
            <a href="Image?id=<%=nRef%>&tp=P&no=1"><img class="img-rounded" src="Image?id=<%=nRef%>&tp=P&no=1"/></a>
        </div>
        <div class="span4">
            <a href="Image?id=<%=nRef%>&tp=P&no=2"><img class="img-rounded" src="Image?id=<%=nRef%>&tp=P&no=2"/></a>
        </div>
    </div>
    <br>
    <div class="well color3" id="puntuacion">
        <legend class="color4"><h3> Calificación<img src='star_on.gif'></h3></legend>
        <div class="row fluid">
            <div class="span2 well-small color5" id="Promedio">
                <input type="hidden" value="<%=promedio%>" id="Pro">
                <input type="hidden" value="<%=ptosTotal%>" id="pT">
                <jsp:include page="/WEB-INF/pages/Producto/Puntaje.jsp" flush="True">
                    <jsp:param name="Prom" value="<%=promedio%>"/>
                    <jsp:param name="Total" value="<%=ptosTotal%>"/>
                </jsp:include>
            </div>
            <div class="span5 offset1" id="barChart">
                <%-- grafiquita --%>
            </div>
        </div>
    </div>
    <br>
    <% if (tLog == TipoLogIn.CLIENTE && (aPuntuar.contains(nRef))) {%>
    <div id="puntuar" class="well color2">
        <h5>¡Califica este producto!</h5>
        <h6>¿Cuál es tu calificación general de <%=N%>?</h6>
        <h6>Recuerda que solo podrás puntuar este producto una vez.</h6>
        <div class="span10">
            <span id="rateStatus">¿Puntuación?</span>
            <span id="ratingSaved">!Puntuado!</span>
            <div id="rateMe" value="<%=nRef%>" title="¿Puntuación?">
                <a onclick="rateIt(this);" id="_1" title="Malo" onmouseover="rating(this);" onmouseout="off(this);"></a>
                <a onclick="rateIt(this);" id="_2" title="Regular" onmouseover="rating(this);" onmouseout="off(this);" ></a>
                <a onclick="rateIt(this);" id="_3" title="Bueno" onmouseover="rating(this);" onmouseout="off(this);" ></a>
                <a onclick="rateIt(this);" id="_4" title="Muy bueno" onmouseover="rating(this);" onmouseout="off(this);" ></a>
                <a onclick="rateIt(this);" id="_5" title="Excelente" onmouseover="rating(this);" onmouseout="off(this);" ></a>
            </div>
        </div>
        <br>
        <br>
    </div>
    <%}%>
    <div hidden id="bien" class="alert alert-success">
        <button type="button" class="close" data-dismiss="alert">&times;</button>
        <strong>¡Muy bien!</strong>
        Tu calificación se ha realizado exitosamente. ¡Gracias!
        <br>
    </div>
    <br>
    <div class="well alert-info">
        <legend class='alert-info'><h3> Info</h3></legend>
        Proveedor:
        <strong> <%= P%></strong>
        <% if (tLog == TipoLogIn.CLIENTE) {%>
        <button id="isOnlineBtn" class="btn btn-mini btn-success" type="button" onclick="quieroChatear('<%=P%>');">
            <i class="icon-fire icon-white"></i> OnFire
        </button>
        <%}%>  
        <br><br>
        Categorias:
        <div class="row-fluid">
            <%  for (Object Ca : cates) {
                    String C = ((DataCategoria) Ca).getNombre();
            %>
            <a href="#" onclick="caty('<%=C%>');"><%=C%></a><%=", "%>
            <%}%>
        </div>
        <br>
        <table>
            <tr>
            <td style="vertical-align:top">
                Precio:
            </td>
            <td >
                U$S <%=pre%>
            </td  >
            </tr>
            <tr>
            <td style="vertical-align:top">
                Descripcion:
            </td>
            <td >
                <%=D%>
            </td  >
            </tr>
            <tr>
            <td style="vertical-align:top">
                Especificacion:
            </td>
            <td >
                <%=E%>
            </td >
            </tr>
        </table>
    </div>
    <br>
    <div id="ComProd" >

        <%
            boolean Comenta = (Ses != null) && (Ses.puedeComentar(nRef));
            if (Comenta) {%>
        <a data-toggle="modal" class="btn btn-info " href="#reclamoModal">
            <i class="icon-bullhorn icon-white"></i>Reclamar
        </a>
        <div Class="alert-success well"><legend >Mis reclamos</legend></div>
        <div class="well degrade">
            <table class="table table-striped">

                <%List<DataReclamo> recs = dP.getReclamos();
                    for (DataReclamo rec : recs) {
                        if (rec.getClient().equals(Ses.verInfoPerfil().getNickname())) {
                %>
                <tr>
                <td>
                    Reclamo:
                    <%=rec.getTexto()%>
                    <% if (rec.getRespuesta() != null) {%>
                    <br>
                    <br>
                    Respuesta:
                    <%=rec.getRespuesta()%>
                    <%}%>
                </td>
                </tr>
                <%

                        }
                    }
                %>
            </table>
        </div>

        <a id="Comentar" class="btn btn-inverse ">
            Comentar <i class="icon-check icon-white"></i>
        </a>

        <div id="ComForm" hidden>

            <input type="hidden" name="idProducto" value="<%=nRef%>" id="numProd">
            <input id="T-1" type="text" name="txtComentario" placeholder="Ingrese comentario">
            <button class="btn btn-inverse btn-mini fito-btn" id="B-1"><!--es -1 si Comenta al producto-->
                <i class="icon-check icon-white"></i>
            </button>


        </div>
        <%}%>
    </div>
    <div Class="alert-success well"><legend >Comentarios</legend></div>
    <% for (DataComentario dCom : dP.getComents()) {
            Integer Num = dCom.getId();
            Integer puntClie = 0;
            for (DataPuntaje dPunt : puntajes) {
                if ((dPunt.getCli()).equals(dCom.getClie())) {
                    puntClie = dPunt.getPuntos();
                }
            }
    %>

    <jsp:include page="/WEB-INF/pages/Producto/Comentario.jsp">
        <jsp:param name="Prod" value="<%=nRef%>"/>
        <jsp:param name="Comen" value="<%=Num%>"/>
        <jsp:param name="Punt" value="<%=puntClie%>"/>
    </jsp:include>

    <%}%>

    <jsp:include page="/WEB-INF/modals/AltaReclamo.jsp">
        <jsp:param name="Prod" value="<%=N%>"/>
        <jsp:param name="nRef" value="<%=nRef%>"/>
    </jsp:include>
    <Script>
        $(document).ready(function() {
            isOnline('<%= P%>');
        });

        <%if (!Comenta) {%>
        $('.fito').hide();
        <%}%>
    </script>
</div>

<style type="text/css">
    #rateStatus{float:left; clear:both; width:100%; height:20px;}
    #rateMe{float:left; clear:both; width:100%; height:auto; padding:0px; margin:0px;}
    #rateMe li{float:left;list-style:none;}
    #rateMe li a:hover,
    #rateMe .on{background:url(img/Punt/star_on.gif) no-repeat;}
    #rateMe a{float:left;background:url(img/Punt/star_off.gif) no-repeat;width:20px; height:20px;}
    #ratingSaved{display:none;}
    .saved{color:red; }
    span.stars, span.stars span {
        display: block;
        background: url(img/Punt/stars.png) 0 -16px repeat-x;
        width: 80px;
        height: 16px;
    }

    span.stars span {
        background-position: 0 0;
    }
</style>


<script src="/js/producto.js" type="text/javascript"></script>
<script type="text/javascript">
        google.load('visualization', '1.0', {'packages': ['corechart']});
        google.setOnLoadCallback(drawBar);
        function drawBar() {

            var data = google.visualization.arrayToDataTable([
                ['Puntaje', '5 stars', '4 stars', '3 stars', '2 stars', '1 stars'],
                ['Stars', parseInt($("#star5").val()), parseInt($("#star4").val()),
                    parseInt($("#star3").val()), parseInt($("#star2").val()), parseInt($("#star1").val())]
            ]);
            var options = {series: {0: {targetAxisIndex: 0}},
                hAxes: {0: {textStyle: {color: '1693A5'}}},
                hAxis: {gridlines: {color: 'FFFFFF', count: '5'},
                    baseline: '0',
                    baselineColor: '1693A5',
                    viewWindowMode: 'pretty'},
                width: 350, height: 200,
                chartArea: {left: 30,
                    top: 0
                },
                colors: ['44AA55', 'D8F500', 'FFF400', 'FFA300', 'F91210']
            };
            var chart = new google.visualization.BarChart(document.getElementById('barChart'));
            chart.draw(data, options);
        }
</script>
