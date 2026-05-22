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
int id = Integer.parseInt(request.getParameter("id"));

Carrito carrito = (Carrito)session.getAttribute("carrito");

if(carrito != null){

ArrayList<Integer> nueva = new ArrayList<Integer>();

for(Integer x : carrito.getIds()){
    if(x != id){
        nueva.add(x);
    }
}

carrito.getIds().clear();
carrito.getIds().addAll(nueva);

}
%>

<sql:update dataSource="${db}">
DELETE FROM Tiendita WHERE id = ?
<sql:param value="<%=id%>"/>
</sql:update>

<jsp:forward page="index.jsp"/>