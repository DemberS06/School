<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Facturas Resumidas</title>
<link href="assets/css/styles.css" rel="stylesheet">
</head>
<body>

<div class="container">

<h1>Facturas Resumidas</h1>

<sql:setDataSource
var="db"
driver="org.mariadb.jdbc.Driver"
url="jdbc:mariadb://localhost:3306/prueba01"
user="root"
password="41258789"
/>

<sql:query dataSource="${db}" var="facturas">
    SELECT id, fecha, total 
    FROM facturas 
    ORDER BY fecha DESC
</sql:query>

<div class="card">
    <table class="table">
        <thead>
        <tr>
            <th>ID Factura</th>
            <th>Total</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="factura" items="${facturas.rows}">
            <tr>
                <td>#${factura.id}</td>
                <td>$${factura.total}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    
    <br>
    
    <a href="index.jsp" class="btn btn-primary">Volver</a>
</div>

</div>

</body>
</html>
