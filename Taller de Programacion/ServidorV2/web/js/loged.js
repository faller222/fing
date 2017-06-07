
function Cerrar() {
    $.post("LogOut", {}).done(function(data) {
        document.location.reload(true);
    });
}

function verPaPuntuar() {
    $.post("Puntuar", {}).done(function(data) {
        $("#main").html(data);
    });
}

function verReclamos() {
    $.post("VerReclamos", {}).done(function(data) {
        $("#main").html(data);
    });
}

function prodProv() {
    $.ajax({
        type: "POST",
        url: "Proveedor"
    }).done(function(msg) {
        $("#main").html(msg);
    });
}

function verOrdenes() {
    $.post("VerOrden", {}).done(function(data) {
        $("#main").html(data);
    });
}

function verCarrito() {
    $.post("Carrito", {}).done(function(data) {
        $("#main").html(data);
    });
}

function tengoMensaje() {
    var intervalID = setInterval(function() {
        $.ajax({
            type: "POST",
            url: "Chat",
            data: {
                Tipo: 'Nuevos'}
        }).done(function(msg) {
            if (msg === "<true>") {
                $("#chat-btn-barra").show();
            } else {
                $("#chat-btn-barra").hide();
            }
        });
    }, 500);
}

$(document).ready(function() {
    tengoMensaje();
});