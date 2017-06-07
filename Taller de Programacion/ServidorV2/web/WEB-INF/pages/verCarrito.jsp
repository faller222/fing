
<%@page import="java.net.InetAddress"%>
<%@page import="java.net.URL"%>
<%@page import="publisher.*"%>
<%@page import="Adapter.*"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>


<div class="span10 ">
    <div class="well color1">
        <legend><h2 class="color1"> Carrito de compras </h2></legend>
    </div>
    <div class="row-fluid">
        <legend>
            <div class="span1">
                Nº
            </div>
            <div class="span2">
                Producto
            </div>
            <div class="span2">
                Imagen
            </div>
            <div class="span2">
                Precio
            </div>
            <div class="span4">
                Descripcion
            </div>
            <div class="span1">
                Cant
            </div>
        </legend>
    </div>
    <%


        // String dire = "https://" + InetAddress.getLocalHost().getHostAddress() + ":8443";
        //String[] parts = InetAddress.getLocalHost().getHostAddress().split("\\.");
        String IP = InetAddress.getLocalHost().getHostAddress();
        int punto = IP.lastIndexOf(".");
        String nummaq = IP.substring(punto + 1);
        String dire = "https://" + "pcunix" + nummaq + ":8443";
        String returnURL = dire + "/Generar";
        String callbackURL = dire + "/DirectMarket";

        DataOrdenCompra doc = (DataOrdenCompra) request.getAttribute("carrito");
        Integer nroOrd = doc.getNumero();
        ISesion IS = (ISesion) session.getAttribute("Sesion");
        DataUsuario user = IS.verInfoPerfil();
        String Clie = user.getNombre();
        Float Total = doc.getTotal();
        List<DataLinea> listaLineas = doc.getLineas();
        Iterator it = listaLineas.iterator();
        DataProducto dp;
        Integer nRef;
        String nombre;
        Float precio;
        String descripcion;
        Integer cant;
        for (it = listaLineas.iterator(); it.hasNext();) {
            DataLinea dl = (DataLinea) it.next();
            dp = dl.getProd();
            nRef = dp.getNRef();
            nombre = dp.getNombre();
            precio = dp.getPrecio();
            descripcion = dp.getDescripcion();
            cant = dl.getCantidad();

    %>
    <div class="row-fluid">
        <div class="span1">
            <%= nRef%>
        </div>
        <div class="span2">
            <a href=VerProducto?nRef=<%=nRef%>><%= nombre%></a>
        </div>
        <div class="span2">
            <img src="Image?id=<%=nRef%>&tp=P&no=0" class="img-polaroid img-rounded ">
        </div>
        <div class="span2">
            <%="U$S " + precio%>
        </div>
        <div class="span4">
            <%= descripcion%>
        </div>
        <div class="span1">
            <%= cant%>
        </div>
    </div>
    <%}%>
    <br>
    <strong>Total: U$S <%=Total%>  </strong>
    <%if (Total > 0) {%>
    <a data-toggle="modal" class="btn btn-info btn-mini" href="#ordenModal">
        <i class="icon-shopping-cart icon-white"></i>Generar Orden
    </a>
    <%}%>
</div>

<div class="modal fade" id="ordenModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">Generar orden de compra</h4>
            </div>
            <div class="modal-body">
                <h5> <%=Clie%>, </h5>
                <p><h5>estás generando una orden de compra con los productos
                    agregados a tu carrito. ¿Deseas continuar?</h5></p>
                Precio total de la orden: U$S <%=Total%>
            </div>
            <div class="modal-footer">
                <div class="row">
                    <div class="span2 offset7"><button type="button" class="btn btn-default" data-dismiss="modal" id="canGen">Cancelar</button></div>
                    <div class="span2">
                        <script src="https://raw.github.com/paypal/JavaScriptButtons/master/dist/paypal-button.min.js"></script>
                        <script 
                            id="Generar"
                            data-env="sandbox"
                            data-return="<%=returnURL%>"
                            data-tax="0" 
                            data-shipping="0" 
                            data-currency="USD" 
                            data-amount="<%=Total%>" 
                            data-quantity="1"
                            data-name="Orden de compra" 
                            data-button="buynow" src="paypal-button.min.js?merchant=JWC83HCLZ4BDA"
                        ></script>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
