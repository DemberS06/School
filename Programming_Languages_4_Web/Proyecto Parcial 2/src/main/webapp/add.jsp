<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="models.Carrito" %>

<sql:setDataSource
var="db"
driver="org.mariadb.jdbc.Driver"
url="jdbc:mariadb://localhost:3306/prueba01"
user="root"
password="41258789"
/>

<sql:query dataSource="${db}" var="producto">
SELECT * FROM Tiendita WHERE id=?
<sql:param value="${param.id}"/>
</sql:query>

<%
Carrito carrito = (Carrito)session.getAttribute("carrito");

if(carrito == null){
    carrito = new Carrito();
    session.setAttribute("carrito", carrito);
}

int id = Integer.parseInt(request.getParameter("id"));
int cantidadPedida = Integer.parseInt(request.getParameter("cantidad"));
%>

<c:forEach var="row" items="${producto.rows}">

<c:set var="cantidadBD" value="${row.cantidad}" />

<%
Integer stock = (Integer)pageContext.getAttribute("cantidadBD");

for(int i=0;i<cantidadPedida;i++){
    if(carrito.count(id) < stock){
        carrito.add(id);
    }
}
%>

</c:forEach>

<jsp:forward page="index.jsp"/>