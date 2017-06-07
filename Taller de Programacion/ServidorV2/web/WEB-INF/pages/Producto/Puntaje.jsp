<%
    Float promedio = Float.parseFloat(request.getParameter("Prom"));
    Integer ptosTotal = Integer.parseInt(request.getParameter("Total"));
    String prom = String.format("%.2g%n", promedio);
%>
<div >
    <h4>Promedio <%=prom%></h4>
    <span class="stars"><%=promedio%></span>
    <h5><%= ptosTotal%> calificaciones</h5>
</div>