<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<sql:setDataSource
var="db"
driver="org.mariadb.jdbc.Driver"
url="jdbc:mariadb://localhost:3306/prueba01"
user="root"
password="41258789"
/>

<c:if test="${not empty param.nom}">

<sql:update dataSource="${db}">
INSERT INTO Tiendita(nom,precio,cantidad)
VALUES (?,?,?)
<sql:param value="${param.nom}"/>
<sql:param value="${param.precio}"/>
<sql:param value="${param.cantidad}"/>
</sql:update>

<jsp:forward page="index.jsp"/>

</c:if>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insertar</title>
<link href="assets/css/styles.css" rel="stylesheet">
</head>
<body>

<div class="container">

<h1>Insertar producto</h1>

<div class="card">

<form action="insert.jsp" method="post" class="form">

<label>Nombre</label>
<input class="form-control" name="nom" required>

<label>Precio</label>
<input class="form-control" type="number" name="precio" required>

<label>Cantidad</label>
<input class="form-control" type="number" name="cantidad" required>

<button class="btn btn-primary">Insertar</button>

<a href="index.jsp" class="btn btn-outline">
Cancelar
</a>

</form>

</div>

</div>

</body>
</html>