
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 3.2 Final//EN">
<html>
    <head>
        <%@include file="/WEB-INF/head.html" %>

    </head>

    <body background="/img/FondoPayPal.jpg" style="background-attachment: fixed">
        <div class="row">
            <div class="span4"></div>
            <div class="span3"></div>
            <div class="span7 offset3">
                <div class="hero-un">
                    <h1>¡Error!</h1>
                    <p>Has intentado acceder a una pagina que no existe o fue movida.</p>
                    <p>
                        <a class="btn btn-primary btn-large" href="/">
                            Inicio
                        </a>
                    </p>
                </div>
            </div>
        </div>
    </body>
</html>

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
