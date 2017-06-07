
$(document).ready(function() {
    $('.op').click(function() {
        var x = $(this).attr("id");
        document.getElementById("Tp").value = x;
        document.getElementById("selOp").innerHTML = document.getElementById(x).innerHTML + "<span class=\"caret\"></span>";
    });
});

function enterSearch(e) {
    if (e.keyCode === 13) {
        busProds(0);
        //Serch.submit();
    }
}

function busProds(page) {
    $.ajax({
        type: "POST",
        url: "Buscar",
        data: {
            Clave: document.getElementById("bPro").value,
            Pag: page,
            Tp: document.getElementById("Tp").value}
    }).done(function(msg) {
        $("#main").html(msg);
    });
}

