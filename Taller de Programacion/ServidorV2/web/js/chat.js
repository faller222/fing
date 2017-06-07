
function isConnected() {
    var intervalID = setInterval(function() {
        var nick = $("#chat-nick").val();
        $.ajax({
            type: "POST",
            url: "isOnline",
            data: {
                prove: nick}
        }).done(function(msg) {
            if (msg === "<true>") {
                $("#chat-btn").show();
                $('#estaOnline').show();
                $('#estaOffline').hide();
            } else {
                $("#chat-btn").hide();
                $('#estaOnline').hide();
                $('#estaOffline').show();
            }
        });
    }, 500);
}


function hayMsj() {
    var intervalID = setInterval(function() {
        var Nick = $("#chat-nick").val();
        $.ajax({
            type: "POST",
            url: "Chat",
            data: {
                Tipo: 'Contar',
                Otro: Nick}
        }).done(function(msg) {
            if (msg === "<true>") {
                dameMsj(Nick);
            }
        });
    }, 500);
}


function dameMsj() {
    var Nick = $("#chat-nick").val();
    $.ajax({
        type: "POST",
        url: "Chat",
        data: {
            Tipo: 'Leer',
            Otro: Nick}
    }).done(function(msg) {
        $("#Mensajes").html(msg);
    });
}


$("#chat-btn").click(function() {
    var Nick = $("#chat-nick").val();
    var txt = $("#chat-txt").val();
    $.ajax({
        type: "POST",
        url: "Chat",
        data: {
            Tipo: 'Mandar',
            Mensaje: txt,
            Otro: Nick}
    }).done(function(msg) {
        $("#chat-txt").val("");
        $("#Mensajes").append("Enviando: " + txt + "<br>");
    });
});


$(document).ready(function() {
    isConnected();
    hayMsj();
    dameMsj();
});
