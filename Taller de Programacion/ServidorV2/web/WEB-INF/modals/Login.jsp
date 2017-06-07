<script src="/js/modalLogin.js"></script>

<div id="Logueo" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content ">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">X</button>
                <h4 class="modal-title">Login:</h4>
            </div>
            <div class="modal-body">
                <form method="post"  name="Ini" action="LogIn" class="navbar-form">
                    <input id="usu" type=text class="span5" placeholder="Nick/Mail" name=usua onkeydown="enter(event);" >
                    <input id="pas" type=password class="span5" placeholder="Password" name=pass onkeydown="enter(event);">
                    <a class="btn btn-mini btn-primary" onclick="send();" onkeydown="send();" >
                        <i class="icon-circle-arrow-right icon-white"></i>
                    </a>
                </form>
                <br>
                <div class="span5 ">
                    <div id="respuesta">
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>