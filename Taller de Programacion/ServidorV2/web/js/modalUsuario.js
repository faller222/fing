//Para el boton Siguiente del Modal
$("#usig1").click(function() {
    $("#link2").trigger("click");
});
$("#usig2").click(function() {
    $("#link3").trigger("click");
});
$("#usig3").click(function() {
    $("#link4").trigger("click");
});
//Para el Fecha Picker
$(function() {
    $('#datetimepicker4').datetimepicker({
        pickTime: false,
        maskInput: true
    });
});

//para el tab de tipo
function display(obj, id1) {
    txt = obj.options[obj.selectedIndex].value;
    document.getElementById(id1).style.display = 'none';
    if (txt.match(id1)) {
        document.getElementById(id1).style.display = 'block';
    }
}


function checkPass() {
    var pass2 = $("#pass2").val();
    var pass1 = $("#pass1").val();

    if (pass1 !== pass2) {
        $("#errorTabU2").show();
        $("#errorTabU2").text("Las contraseñas no coinciden");
    } else {
        $("#errorTabU2").hide();
        $("#errorTabU2").text("");
    }
}

function valid(tipo) {
    valido(tipo, true);
}

var valido = function(tipo, async) {
    var valu = $("#" + tipo).val();
    if (tipo.match('email')) {
        if (checkEmail(valu)) {
            return ajaxCheck(tipo, valu, async);

        } else {
            return false;
        }
    } else {
        return ajaxCheck(tipo, valu, async);
    }
};

var checkEmail = function(val) {
    var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    $("#messageemail").removeClass();
    $("#messageemail").text("");
    if (!re.test(val)) {
        $("#messageemail").addClass("alert");
        $("#messageemail").addClass("alert-error");
        $("#messageemail").text("El email es invalido");
        return false;
    } else {
        return true;
    }
};

var ajaxCheck = function(tipo, val, async) {
    var ajaxResponse;
    $.ajax({
        async: async,
        type: "GET",
        url: "AltaUsuario",
        data: "verif=" + tipo + "&" + tipo + "=" + val,
        success: function foo(response) {
            ajaxResponse = showMessage(tipo, response);
        }
    });
    return ajaxResponse;
};

var showMessage = function(tipo, Msg) {
    $("#message" + tipo).removeClass();
    $("#message" + tipo).addClass("alert");
    if (Msg === "<true>") {
        $("#message" + tipo).addClass("alert-success");
        $("#message" + tipo).text("El " + tipo + " esta disponible.");
        return true;
    } else {
        $("#message" + tipo).addClass("alert-danger");
        $("#message" + tipo).text("El " + tipo + " esta en uso.");
        return false;
    }
};

var checkBasicos1 = function() {
    var ret = true;

    if (!valido('nick', false)) {
        ret = false;
        $("#link1").trigger("click");
    }
    if (!valido('email', false)) {
        ret = false;
        $("#link1").trigger("click");
    }
    return ret;
};

var checkBasicos2 = function() {
    var ret = true;
    var nm = $("#nombre").val();
    var ap = $("#apellido").val();
    var ps1 = $("#pass1").val();
    var ps2 = $("#pass2").val();
    var date = $("#DateSelect").val();

    $("#errorTabU2").hide();

    if (ret && nm === "") {
        $("#errorTabU2").text("Nombre Vacio").show();
        ret = false;
        $("#link2").trigger("click");
    }
    if (ret && ap === "") {
        $("#errorTabU2").text("Apellido Vacio").show();
        ret = false;
        $("#link2").trigger("click");
    }
    if (ret && ps2 !== ps1) {
        $("#errorTabU2").text("Contraseñas no coinciden").show();
        ret = false;
        $("#link2").trigger("click");
    }
    if (ret && ps1 === "") {
        $("#errorTabU2").text("Debe escribir una Contraseña").show();
        ret = false;
        $("#link2").trigger("click");
    }
    if (ret && date === "") {
        $("#errorTabU2").text("Debe ingresar su nacimiento").show();
        ret = false;
        $("#link2").trigger("click");
    }
    return ret;
};

var checkTipo = function() {
    var ret = true;
    var esProve = false;
    var tipo = $("#tipoClie").val();
    var web = $("#sitio").val();
    var comp = $("#compania").val();
    alert(tipo + " " + web + " " + comp);
    $("#errorTabU4").hide();

    if (ret && tipo === "no") {
        $("#errorTabU4").text("Seleccione el tipo de Usuario.").show();
        ret = false;
        $("#link4").trigger("click");
    }

    if (ret && tipo === "proveedor") {
        esProve = true;
    }

    if (ret && esProve) {
        if (ret && comp === "") {
            $("#errorTabU4").text("Debe indicar su compania").show();
            ret = false;
            $("#link4").trigger("click");
        }
        if (ret && web === "") {
            $("#errorTabU4").text("Debe indicar un sitio web").show();
            ret = false;
            $("#link4").trigger("click");
        }
    }
    return ret;
};

function altaUsuario() {
    var ret = checkBasicos1();
    if (ret) {
        ret = checkBasicos2();
    }
    if (ret) {
        ret = checkTipo();
    }
    if (ret) {
        $("#regUser_form").submit();
    }

}
