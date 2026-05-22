<%@ page import="models.Carrito" %>

<%
Carrito carrito = (Carrito)session.getAttribute("carrito");

if(carrito != null){

int id = Integer.parseInt(request.getParameter("id"));
carrito.remove(id);

}
%>

<jsp:forward page="index.jsp"/>