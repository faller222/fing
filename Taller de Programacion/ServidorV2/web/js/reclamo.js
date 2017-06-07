
//para responder reclamo
$('.respRec').click(function() {
    var reclamo = $(this).attr("id");
    var IdProd = $("#P" + reclamo).val();
    $("#divResp").show();
    $("#respRecId").val(reclamo);
    $("#IdProd").val(IdProd);

});

$("#RespRecBtn").click(function() {

    $("#enviarRespuesta").submit();

});