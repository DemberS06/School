<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="models.Carrito" %>
<%@ page import="java.util.*" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Tiendita</title>
<link href="assets/css/styles.css" rel="stylesheet">
</head>
<body>

<div class="container">

<h1>Productos</h1>

<sql:setDataSource
var="db"
driver="org.mariadb.jdbc.Driver"
url="jdbc:mariadb://localhost:3306/prueba01"
user="root"
password="41258789"
/>

<%
String busqueda = request.getParameter("busqueda");
if(busqueda == null) busqueda = "";
%>

<div class="card">
    <form action="index.jsp" method="get">
        <input 
            type="text" 
            name="busqueda" 
            class="form-control" 
            placeholder="Buscar productos..."
            value="<%=busqueda%>"
            style="margin-bottom: 1rem;"
        >
    </form>

    <% if(busqueda.isEmpty()) { %>
        <sql:query dataSource="${db}" var="productos">
            SELECT * FROM Tiendita ORDER BY nom
        </sql:query>
    <% } else { %>
        <sql:query dataSource="${db}" var="productos">
            SELECT * FROM Tiendita WHERE nom LIKE ? ORDER BY nom
            <sql:param value="<%=busqueda + \"%\"%>"/>
        </sql:query>
    <% } %>

    <table class="table">
        <thead>
        <tr>
            <th>ID</th>
            <th>Nombre</th>
            <th>Precio</th>
            <th>Cantidad</th>
            <th></th>
            <th></th>
            <th>Total</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="row" items="${productos.rows}">
            <tr>
                <td>${row.id}</td>
                <td>${row.nom}</td>
                <td>${row.precio}</td>
                <td>${row.cantidad}</td>
                <td>
                    <a href="updateForm.jsp?id=${row.id}" class="btn btn-outline">
                        Editar
                    </a>
                </td>
                <td>
                    <a href="delete.jsp?id=${row.id}" class="btn btn-outline">
                        Eliminar
                    </a>
                </td>
                <form action="add.jsp" method="post">
                <td><input 
                    type="number"
                    name="cantidad"
                    min="1"
                    max="${row.cantidad}"
                    value="1"
                    class="form-control"
                    style="width:80px"
                ></td>
                <td>
                    <input type="hidden" name="id" value="${row.id}">
                    <input type="hidden" name="busqueda" value="<%=busqueda%>">
                    <button class="btn btn-outline">🛒</button>
                </td>
                </form>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    
    <br>

    <a href="insert.jsp" class="btn btn-primary">
        Nuevo producto
    </a>
</div>

<hr class="hr">

<h2>Carrito</h2>

<%
Carrito carrito = (Carrito)session.getAttribute("carrito");

if(carrito == null){
    carrito = new Carrito();
    session.setAttribute("carrito", carrito);
}

int total = 0;
%>
<div class="card">
    <table class="table">
        <thead>
        <tr>
            <th>ID</th>
            <th>Nombre</th>
            <th>Precio</th>
            <th>Cantidad</th>
            <th>Total</th>
            <th>Quitar</th>
        </tr>
        </thead>
        <tbody>
        <%
        ArrayList<Integer> ids = carrito.getIds();
        ArrayList<Integer> frq = carrito.getFrq();
        for(int i=0; i<frq.size(); i++){
        %>

        <sql:query dataSource="${db}" var="p">
        SELECT * FROM Tiendita WHERE id=<%=ids.get(i)%>
        </sql:query>

        <c:forEach var="r" items="${p.rows}">
            <c:set var="precioTmp" value="${r.precio}" />
            <%
            Integer precio = (Integer)pageContext.getAttribute("precioTmp");
            %>
            <tr>
            <td>${r.id}</td>
            <td>${r.nom}</td>
            <td>${r.precio}</td>
            <td><%=frq.get(i)%></td>
            <td><%=precio*frq.get(i)%></td>
            <td>
                <a href="quit.jsp?id=${r.id}" class="btn btn-outline">
                Quitar
                </a>
            </td>
            </tr>
            
            <%
            total += precio*frq.get(i);
            %>
        </c:forEach>
        <%}%>
        </tbody>
    </table>

    <h3>Total: $<%=total%></h3>

    <a href="buy.jsp" class="btn btn-primary">
        Comprar
    </a>
</div>

<hr class="hr">

<h2>Facturas</h2>

<div class="card center">
    <a href="facturas_resumidas.jsp" class="btn btn-primary" style="margin: 0.5rem;">
        Ver Facturas Resumidas
    </a>
    <a href="facturas_detalladas.jsp" class="btn btn-primary" style="margin: 0.5rem;">
        Ver Facturas Detalladas
    </a>
</div>

</div>

</body>
</html>
