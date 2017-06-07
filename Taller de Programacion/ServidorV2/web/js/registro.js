
function AJAXInteraction(url, callback, tipo) {

    var req = init();
    req.onreadystatechange = processRequest;
    //esto es para el tipo de navegador que estoy usando Mozilla es distinto de IExplo
    function init() {
        if (window.XMLHttpRequest) {
            return new XMLHttpRequest();
        } else if (window.ActiveXObject) {
            return new ActiveXObject("Microsoft.XMLHTTP");
        }
    }

    function processRequest() {
        // readyState of 4 signifies request is complete
        if (req.readyState == 4) {
            if (req.status == 200) {
                // status of 200 signifies sucessful HTTP call
                if (callback)
                    callback(req.responseXML, tipo);
            }
        }
    }

    this.doGet = function() {
        // make a HTTP GET request to the URL asynchronously
        req.open("GET", url, true);
        req.send(null);
    }
}

function validateCallback(responseXML, tipo) {

    var msg = responseXML.
            getElementsByTagName("valid")[0].firstChild.nodeValue;
    var idDiv = "message" + tipo;
    var mdiv = document.getElementById(idDiv);
    mdiv.style.diplay = 'none';
    if (tipo.match('nref')) {
        tipo = "numero de referencia";
    }
    // var submitBtn = document.getElementById("submit_btn");
    if (msg == "false") {
// set the style on the div to invalid
        mdiv.style.diplay = 'block';
        mdiv.className = "alert alert-error";
        mdiv.innerHTML = "El " + tipo + " esta en uso";
        document.getElementById("btn_tab1").disabled = true;
    } else {
// set the style on the div to valid
        mdiv.style.diplay = 'block';
        mdiv.className = "alert alert-success";
        mdiv.innerHTML = "El " + tipo + " esta disponible";
        //submitBtn.disabled = false;
    }

}

function validar(tipo) {
    var target = document.getElementById(tipo);
    var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    if (tipo.match('email')) {
        var mdiv = document.getElementById("messageemail");
        //mdiv.style.display = 'none';
        if (!re.test(target.value)) {
//mdiv.style.display = 'block';
            mdiv.className = "alert alert-error";
            mdiv.innerHTML = "El email es invalido";
            return;
        }
    }
    var url;
    if (tipo.match('nref')) {
        url = "pregistro?nref=" + encodeURIComponent(target.value);
    } else {
        url = "registro?verif=" + tipo + "&" + tipo + "=" + encodeURIComponent(target.value);
    }

    var ajax = new AJAXInteraction(url, validateCallback, tipo);
    ajax.doGet();
}


function validarProd() {
    var xmlhttp;
    xmlhttp = new XMLHttpRequest();

    xmlhttp.onreadystatechange = function()
    {
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200)
        {
            document.getElementById("messagenref").innerHTML = xmlhttp.responseText;
        }
    }
    var x = document.getElementById("nref").value;
    xmlhttp.open("GET", +x, true);
    xmlhttp.send();
}

