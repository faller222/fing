
<%@page import="java.util.List"%>

<div class="row-fluid">
    <div class="span3">
        <div class="well">
            <%
                List ppl = (List) request.getAttribute("Lista");
                for (Object O : ppl) {
                    String txt = (String) O;
            %>

            <a href="#" id="<%=txt%>" class="chat-list"><%=txt%></a><br>
            <%}%>

        </div>
    </div>
    <DIV class="span8">
        <DIV id="chat-bloque" class="well">

        </DIV>
    </DIV>
</DIV>
<script  type="text/javascript">
    $('.chat-list').click(function() {
        var Nick = $(this).attr("id");
        $.ajax({
            type: "POST",
            url: "Chat",
            data: {
                Tipo: 'Chat',
                Otro: Nick}
        }).done(function(msg) {
            $("#chat-bloque").html(msg);
        });
    });</script>