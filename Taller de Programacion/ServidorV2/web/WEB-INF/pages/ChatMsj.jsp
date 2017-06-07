<%@page import="java.util.List"%>
<%
    List msjs = (List) request.getAttribute("Lista");

    for (Object O : msjs) {
        String txt = (String) O;

%>
<%=txt%><br>

<%}%>