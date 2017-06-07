
<div id="altaUsuario" class="modal hide fade">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">X</button>
        <h3>Registrate</h3>
    </div>
    <div class="modal-body">
        <div class="span4">
            <form method="POST" action="AltaUsuario" id="regUser_form" name="regUser_form" accept-charset="UTF-8" enctype="multipart/form-data">
                <div class="tabbable">
                    <ul class="nav nav-tabs">
                        <li class="active"><a id="link1" href="#tab1" data-toggle="tab">Paso 1</a></li>
                        <li class=""><a id="link2" href="#tab2" data-toggle="tab">Paso 2</a></li>
                        <li class=""><a id="link3" href="#tab3" data-toggle="tab">Paso 3</a></li>
                        <li class=""><a id="link4" href="#tab4" data-toggle="tab">Paso 4</a></li>
                    </ul>
                    <div class="tab-content">
                        <div class="tab-pane fade in active" id="tab1">
                            <legend> Datos basicos: </legend>
                            <input type="text" id="nick" class="span4" name="nick" placeholder="Nickname" autocomplete="off" onkeyup="valid('nick');" required>
                            <div id="messagenick"></div>
                            <input type="email" id="email" class="span4" name="email" placeholder="Email" autocomplete="off" onkeyup="valid('email');" required>
                            <div id="messageemail"></div>
                            <div class="tycFoot">
                                <p> Todos los campos son obligatorios </p>
                            </div>
                            <input type="reset" value="Cancelar" class="btn">
                            <a class="btn pull-right" id="usig1">Siguiente</a>

                        </div>
                        <div class="tab-pane fade" id="tab2">
                            <legend> Datos basicos: </legend>
                            <input type="text" id="nombre" class="span2" name="nombre" placeholder="Nombre" required>

                            <input type="text" id="apellido" class="span2" name="apellido" placeholder="Apellido" required>

                            <input type="password" id="pass1" class="span2" name="pass1" placeholder="Password" onkeyup="checkPass();" required>
                            <input type="password" id="pass2" class="span2" name="pass2" placeholder="Confirme password" onkeyup="checkPass();" required>
                            <label>Fecha de nacimiento</label>
                            <div id="datetimepicker4" class="input-append">
                                <input id="DateSelect" name="DateSelect" data-format="dd-MM-yyyy" type="text"></input>
                                <span class="add-on">
                                    <i data-time-icon="icon-time" data-date-icon="icon-calendar">
                                    </i>
                                </span>
                            </div>
                            <br>
                            <div id="errorTabU2" class="well well-small alert-danger" hidden></div>
                            <div class="tycFoot">
                                <p> Todos los campos son obligatorios </p>
                            </div>
                            <input type="reset" value="Cancelar" class="btn">
                            <a class="btn pull-right" id="usig2">Siguiente</a>
                        </div>
                        <div class="tab-pane fade" id="tab3">
                            <legend> Imagen: </legend>
                            <input type="file" id="image"  name="image" placeholder="..." autocomplete="off">
                            <a class="btn pull-right" id="usig3">Siguiente</a>
                            <div class="tycFoot">
                                <p> Opcional </p>
                            </div>
                        </div>

                        <div class="tab-pane fade" id="tab4">
                            <legend> Es Cliente o Proveedor? </legend>
                            <div class="controls controls-row">
                                <select id="tipoClie" class="span4" name="tipo" onChange="display(this, 'proveedor');">
                                    <option value="no">Seleccione:</option>
                                    <option value="cliente">Cliente</option>
                                    <option value="proveedor">Proveedor</option>
                                </select>
                            </div>
                            <div id="proveedor" style="display: none;">
                                <input type="text" class="span4" name="compania" id="compania" placeholder="Compania">
                                <input type="text" class="span4" name="sitio" id="sitio" placeholder="Sitio Web">
                            </div>
                            <div id="errorTabU4" class="well well-small alert-danger" hidden></div>
                            <div class="tycFoot">
                                <p> Todos los campos son obligatorios </p>
                            </div>
                            <input onclick="altaUsuario();" value="Registrarme" class="btn btn-primary pull-right">
                            <input type="reset" value="Cancelar" class="btn">
                            <div class="tycFoot">
                                <p> Al registrarme, declaro que soy mayor de edad y acepto los Terminos y Condiciones y las  Politicas de Privacidad de DirectMarket.</p>
                            </div>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<!--script src="/js/registro.js"></script-->
<script src="/js/bootstrap-datetimepicker.min.js"></script>
<script src="/js/modalUsuario.js"></script>
