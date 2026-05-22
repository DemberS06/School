<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link href="assets/css/styles.css" rel="stylesheet">

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

<div class="container">

<div class="card">

<form action="update.jsp" method="post" class="form">

<c:forEach var="row" items="${producto.rows}">

<input type="hidden" name="id" value="${row.id}">

<label>Nombre</label>
<input class="form-control" name="nom" value="${row.nom}">

<label>Precio</label>
<input class="form-control" name="precio" value="${row.precio}">

<label>Cantidad</label>
<input class="form-control" name="cantidad" value="${row.cantidad}">

</c:forEach>

<button class="btn btn-primary">Actualizar</button>

</form>

</div>
</div>