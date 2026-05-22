<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ page import="models.Carrito" %>
<%@ page import="java.util.*" %>

<sql:setDataSource
var="db"
driver="org.mariadb.jdbc.Driver"
url="jdbc:mariadb://localhost:3306/prueba01"
user="root"
password="41258789"
/>

<%
Carrito carrito = (Carrito)session.getAttribute("carrito");

if(carrito != null){

ArrayList<Integer> ids = carrito.getIds();
ArrayList<Integer> frq = carrito.getFrq();

for(int i=0; i<ids.size(); i++){
%>

<sql:update dataSource="${db}">
UPDATE Tiendita
SET cantidad = cantidad - <%=frq.get(i)%>
WHERE id = ?
<sql:param value="<%=ids.get(i)%>"/>
</sql:update>

<%
}

carrito.clear();
}
%>

<jsp:forward page="index.jsp"/>