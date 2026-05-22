<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>CRUD JSTL</title>

<link href="assets/css/styles.css" rel="stylesheet">

</head>
<body>

<div class="container">

<h1>CRUD con JSTL</h1>

<sql:setDataSource
    var="db"
    driver="org.mariadb.jdbc.Driver"
    url="jdbc:mariadb://localhost:3306/prueba01"
    user="root"
    password="41258789"
/>

<sql:query dataSource="${db}" var="datos">
    SELECT * FROM test1
</sql:query>

<div class="card">
<form action="insert.jsp" method="post" class="form">

<label>Nombre</label>
<input class="form-control" type="text" name="nom" required>

<label>Edad</label>
<input class="form-control" type="number" name="edad" required>

<button class="btn btn-primary">Insertar</button>

</form>
</div>

<br>

<table class="table">
<thead>
<tr>
<th>ID</th>
<th>Nombre</th>
<th>Edad</th>
<th>Editar</th>
<th>Eliminar</th>
</tr>
</thead>

<tbody>

<c:forEach var="row" items="${datos.rows}">
<tr>

<td>${row.id}</td>
<td>${row.nom}</td>
<td>${row.edad}</td>

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

</tr>
</c:forEach>

</tbody>
</table>

</div>

</body>
</html>