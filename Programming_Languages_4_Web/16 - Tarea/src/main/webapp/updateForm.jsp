<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Actualizar</title>
<link href="assets/css/styles.css" rel="stylesheet">
</head>

<body>

<div class="container">

<sql:setDataSource
    var="db"
    driver="org.mariadb.jdbc.Driver"
    url="jdbc:mariadb://localhost:3306/prueba01"
    user="root"
    password="41258789"
/>

<sql:query dataSource="${db}" var="datos">
SELECT * FROM test1 WHERE id=?
<sql:param value="${param.id}" />
</sql:query>

<c:forEach var="row" items="${datos.rows}">

<div class="card">
<form action="update.jsp" method="post" class="form">

<input type="hidden" name="id" value="${row.id}">

<label>Nombre</label>
<input class="form-control" type="text" name="nom" value="${row.nom}">

<label>Edad</label>
<input class="form-control" type="number" name="edad" value="${row.edad}">

<button class="btn btn-primary">
Actualizar
</button>

</form>
</div>

</c:forEach>

</div>

</body>
</html>