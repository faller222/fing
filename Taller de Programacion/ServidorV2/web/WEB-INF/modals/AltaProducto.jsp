
<%@page import="Adapter.IAgregarProducto"%>
<%@page import="java.util.Map"%>
<%@page import="publisher.*"%>

<div id="altaProducto" class="modal hide fade">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">X</button>
        <h3>Nuevo producto</h3>
    </div>
    <div class="modal-body">
        <div class="span4">
            <form method="POST" action="AltaProducto" name="regProd_form" accept-charset="UTF-8" enctype="multipart/form-data">
                <div class="tabbable">
                    <ul class="nav nav-tabs">
                        <li class="active"><a id="lik1"  href="#tb1" data-toggle="tab">Paso 1</a></li>
                        <li class=""><a id="lik2" href="#tb2" data-toggle="tab">Paso 2</a></li>
                        <li class=""><a id="lik3" href="#tb3" data-toggle="tab">Paso 3</a></li>
                    </ul>
                    <div class="tab-content">
                        <div class="tab-pane fade in active" id="tb1">
                            <legend> Datos basicos: </legend>
                            <input type="text" id="pname" class="span4" name="pname" placeholder="Nombre" required>
                            <div id="messagepname"></div>
                            <input type="number" id="nref" class="span4" name="nref" placeholder="Numero de referencia" autocomplete="off" onkeyup="validar('nref');" required>
                            <div id="messagenref"></div>
                            <input type="text" id="desc" class="span4" name="desc" placeholder="Descripción" autocomplete="off" required>
                            <div class="input-prepend input-append">
                                <span class="add-on">U$S</span>
                                <input type="number" id="precio" class="input-small" name="precio" placeholder="Precio" onkeyup="validarPrecio();" required>
                            </div>
                            <div id="messageprecio"></div>
                            <legend> Especificación: </legend>
                            <textarea rows="4" id="espe" class="span4" name="espe" autocomplete="off"></textarea>
                            <div class="tycFoot">
                                <p> Todos los campos son obligatorios </p>
                            </div>
                            <input type="reset" value="Cancelar" class="btn">
                            <a class="btn pull-right" id="psig1">Siguiente</a>
                        </div>
                        <div class="tab-pane fade" id="tb2">
                            <legend> Categorías del producto: </legend>
                            <%  IAgregarProducto aspkodm = new IAgregarProducto();
                                DataCategoria daC = aspkodm.listarCategorias();
                            %>
                            <ul>
                                <%   for (DataCategoria Dc : daC.getCateHijas()) {
                                        String Nombre = Dc.getNombre();
                                        if (Dc.isEsSimple()) {%>
                                <li><label class="checkbox">
                                    <input type="checkbox" value="<%=Nombre%>" id="categoria" name="categoria">
                                    <%=Nombre%>
                                </label>
                                </li>
                                <%} else {%>
                                <li><a href="#" class="parent"><%=Nombre%></a>
                                    <jsp:include page="/WEB-INF/modals/cateTemp.jsp">
                                        <jsp:param name="Cate" value="<%=Nombre%>"/>
                                    </jsp:include>
                                </li>
                                <%    }
                                    }%>
                            </ul>
                            <br>

                            <input type="reset" value="Cancelar" class="btn">
                            <a class="btn pull-right" id="psig2">Siguiente</a>
                        </div>
                        <div class="tab-pane fade" id="tb3">
                            <legend> Imagenes: </legend>
                            <input type="file" id="image0"  name="image0" placeholder="..." autocomplete="off"> (Opcional)
                            <input type="file" id="image1"  name="image1" placeholder="..." autocomplete="off"> (Opcional)
                            <input type="file" id="image2"  name="image2" placeholder="..." autocomplete="off"> (Opcional)
                            <br>
                            <br>
                            <input type="reset" value="Cancelar" class="btn">
                            <input type="submit" value="Confirmar" class="btn btn-primary pull-right">
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<script src="/js/modalProducto.js"></script>