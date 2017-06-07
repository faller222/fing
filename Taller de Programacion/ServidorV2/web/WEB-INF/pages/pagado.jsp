
<!--body background="../img/FondoPayPal.jpg" style="background-attachment: fixed"-->
<div class="row">
    <div class="span4"></div>
    <div class="span3"></div>
    <div class="span7">
        <div class="hero-un">
            <h1>¡Genial!</h1>
            <p>La transacción se ha realizado exitosamente. Recuerda confirmar la orden de compra
                cuando hayas obtenido tus nuevos productos. ¡Gracias por confiar en DirectMarket!</p>
            <p>
                <a class="btn btn-primary btn-large" id="Generar">
                    Continuar
                </a>
            </p>
        </div>
    </div>
</div>


<script>
    $("#Generar").click(function() {
        $.post("VerOrden", {}).done(function(data) {
            $("#main").html(data);
        });
    });
</script>

<style type="text/css">
    .hero-un {
        padding: 60px;
        margin-bottom: 30px;
        font-size: 18px;
        font-weight: 200;
        line-height: 30px;
        color: inherit;
        background-color: #B2C5DD;
        -webkit-border-radius: 6px;
        -moz-border-radius: 6px;
        border-radius: 6px;
    }


    .hero-un h1 {
        margin-bottom: 0;
        font-size: 60px;
        line-height: 1;
        letter-spacing: -1px;
        color: inherit;
    }

    .hero-un li {
        line-height: 30px;
    }
</style>
