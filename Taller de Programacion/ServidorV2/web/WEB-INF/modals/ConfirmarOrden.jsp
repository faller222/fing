<%
    String nom = request.getParameter("nombre");
    String num = request.getParameter("numero");
%>

<!--Modal-->
<div class="modal fade" id="confordenModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">Confirmar orden de compra</h4>
            </div>
            <div class="modal-body">
                <h5> <%=nom%>, </h5>
                <p><h5> estas confirmando la orden de compra seleccionada. ¿Desea continuar?</h5></p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                <a type="button" class="btn btn-primary" href="Confirmar?num=<%=num%>" >Continuar</a>
            </div>
        </div>
    </div>
</div>
