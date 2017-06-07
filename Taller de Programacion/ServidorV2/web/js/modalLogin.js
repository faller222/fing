
function enter(e) {
    if (e.keyCode === 13) {
        send();
    }
}

function send() {
    var Usua = $("#usu").val();
    var Pass = $("#pas").val();
    $("#respuesta").removeClass();
    if (Usua === "" || Pass === "") {
        $("#respuesta").addClass("well").addClass("well-small");
        $("#respuesta").text("Complete los campos...").addClass("alert-info");
    } else {
        $("#respuesta").removeClass();
        $("#respuesta").addClass("well").addClass("well-small");
        $.post("LogIn", {usua: Usua, pass: Pass}).done(function(data) {
            $("#respuesta").removeClass("alert-info");
            if (data === "<true>") {
                $("#respuesta").text("Correcto").addClass("alert-success");
                document.location.reload(true);
            } else {
                $("#respuesta").text("Usuario o contraseña incorrecto").addClass("alert-danger");
            }
        });
    }
}


