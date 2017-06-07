<%
    String o = (String) request.getAttribute("Receptor");
%>
<h1>Chat con <%=o%></h1>
<input type="hidden" value="<%=o%>" id="chat-nick">
<div id="Mensajes">

</div>
<input type="text" id="chat-txt" placeholder="Escriba aqui...">
<button class="btn btn-inverse"  id="chat-btn">
    Enviar
</button>
<div id="estaOnline" class="well alert-success" hidden>
    <%=o%> Esta Conectado
</div>
<div id="estaOffline" class="well alert-danger" hidden>
    <%=o%> no se encuentra disponible en este momento.
</div>

<script src="/js/chat.js" type="text/javascript"></script>

