
<%
    String N = request.getParameter("Prod");
    String nRef = request.getParameter("nRef");
%>

<div class="modal fade" id="reclamoModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true" id="closRec">&times;</button>
                <h4 class="modal-title">Reclamo de <%=N%></h4>
            </div>

            <div class="" id="RespRec">
                <div class="modal-body">
                    <h5> Reclamo:</h5>
                    <input type="hidden"  name="Prod" value="<%=nRef%>" id="Produ">
                    <input type="text" class="span4" name="Reclamo" id="Reclamo" placeholder="Escribe tu reclamo..." required>
                </div>
                <div class="modal-footer">
                    <input type="button" value="Reclamar" class="btn btn-primary pull-right" onclick="reclamar();">
                </div>
            </div>

        </div>
    </div>
</div>
