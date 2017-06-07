
var sMax; // Isthe maximum number of stars
var holder; // Is the holding pattern for clicked state
var preSet; // Is the PreSet value onces a selection has been made
var rated;
var stars;
var nref;
// Rollover for image Stars //
function rating(num) {
    sMax = 0; // Isthe maximum number of stars
    for (n = 0; n < num.parentNode.childNodes.length; n++) {
        if (num.parentNode.childNodes[n].nodeName === "A") {
            sMax++;
        }
    }

    if (!rated) {
        s = num.id.replace("_", ''); // Get the selected star
        stars = s;
        a = 0;
        for (i = 1; i <= sMax; i++) {
            if (i <= s) {
                document.getElementById("_" + i).className = "on";
                document.getElementById("rateStatus").innerHTML = num.title;
                holder = a + 1;
                a++;
            } else {
                document.getElementById("_" + i).className = "";
            }
        }
    }
}

// For when you roll out of the the whole thing //
function off(me) {
    if (!rated) {
        if (!preSet) {
            for (i = 1; i <= sMax; i++) {
                document.getElementById("_" + i).className = "";
                document.getElementById("rateStatus").innerHTML = me.parentNode.title;
            }
        } else {
            rating(preSet);
            document.getElementById("rateStatus").innerHTML = document.getElementById("ratingSaved").innerHTML;
        }
    }
}

// When you actually rate something //
function rateIt(me) {
    if (!rated) {
        document.getElementById("rateStatus").innerHTML = document.getElementById("ratingSaved").innerHTML;
        preSet = me;
        rated = 1;
        sendRate();
        rating(me);
    }
}
function sendRate() {
    nref = document.getElementById("rateMe").getAttribute("value");
    $.ajax({
        type: "POST",
        url: "PuntuarProducto",
        data: {numr: nref,
            prom: $("#Pro").val(),
            cant: $("#pT").val(),
            punt: stars}
    }).done(function(msg) {
        if (!(msg === "<false>")) {
            $("#bien").show();
            $("#Promedio").html(msg);
            refresh(stars);
            $("#puntuar").hide();
        }
    });
}

$(function() {
    $('span.stars').stars();
});
$.fn.stars = function() {
    return $(this).each(function() {
        $(this).html($('<span />').width(Math.max(0, (Math.min(5, parseFloat($(this).html())))) * 16));
    });
};

function refresh(cual) {
    var x = parseInt(cual);
    switch (x)
    {
        case 1:
            var x = parseInt($("#star1").val()) + 1;
            $("#star1").val(x);
            break;
        case 2:
            var x = parseInt($("#star2").val()) + 1;
            $("#star2").val(x);
            break;
        case 3:
            var x = parseInt($("#star3").val()) + 1;
            $("#star3").val(x);
            break;
        case 4:
            var x = parseInt($("#star4").val()) + 1;
            $("#star4").val(x);
            break;
        case 5:
            var x = parseInt($("#star5").val()) + 1;
            $("#star5").val(x);
            break;
        default:

    }
    drawBar();

    $(function() {
        $('span.stars').stars();
    });
    $.fn.stars = function() {
        return $(this).each(function() {
            $(this).html($('<span />').width(Math.max(0, (Math.min(5, parseFloat($(this).html())))) * 16));
        });
    };
}

//Para comentar el prod
$("#Comentar").click(function() {
    $('.fito-form').fadeOut();
    $("#ComForm").show();
});
//para responder
$('.fito').click(function() {
    var comen = $(this).attr("id");
    $("#ComForm").fadeOut();
    $('.fito-form').fadeOut();
    $("#F" + comen).fadeIn();
    $("#T" + comen).focus();
});

//para reclamo
function reclamar() {
    var pro = $("#Produ").val();
    var rec = $("#Reclamo").val();
    $.post("AltaReclamo", {Prod: pro, Reclamo: rec}).done(function(data) {
        if (data === "<true>") {
            $("#RespRec").html("");
            $("#RespRec").addClass("well").addClass("well-small").addClass("alert-success");
            $("#RespRec").text("Reclamo Enviado");
            setTimeout(
                    function() {
                        $("#closRec").trigger("click");
                    },
                    1500
                    );
        }
    });
}

function caty(A) {
    $.ajax({
        type: "POST",
        url: "Categoria",
        data: {
            Cate: A}
    }).done(function(msg) {
        $("#main").html(msg);
    });
}


//para Comentario
$('.fito-btn').click(function() {
    var btn = $(this).attr("id");
    var nCom = btn.replace("B", "");
    var Comentario = $("#T" + nCom).val();
    $("#T" + nCom).val("Espere...");
    var Prod = $("#numProd").val();
    $.ajax({
        type: "POST",
        url: "Comentar",
        data: {
            responde: nCom,
            idProducto: Prod,
            txtComentario: Comentario
        }
    }).done(function(msg) {
        if (msg === "<true>") {
            $("#T" + nCom).val("Listo...");
            document.location.reload(true);
        }
        else {
            $("#T" + nCom).val("Error...");
            setTimeout(
                    function() {
                        $("#T" + nCom).val(Comentario);
                    },
                    500
                    );
        }
    });
});

//para Chat

function isOnline(nick) {
    var intervalID = setInterval(function() {
        $.ajax({
            type: "POST",
            url: "isOnline",
            data: {
                prove: nick}
        }).done(function(msg) {
            if (msg === "<true>") {
                $("#isOnlineBtn").show();
            } else {
                $("#isOnlineBtn").hide();
            }

        });
    }, 2000);
}

function quieroChatear(Nick) {
    $.ajax({
        type: "POST",
        url: "Chat",
        data: {
            Tipo: 'Chat',
            Otro: Nick}
    }).done(function(msg) {
        $("#main").html(msg);
    });
}
