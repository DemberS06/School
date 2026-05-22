<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Facturas Detalladas</title>
<link href="assets/css/styles.css" rel="stylesheet">
</head>
<body>

<div class="container">

<h1>Facturas Detalladas</h1>

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

<c:forEach var="factura" items="${facturas.rows}">
    <div class="card" style="margin-bottom: 1.5rem;">
        <h3>Factura #${factura.id}</h3>
        <p><strong>Fecha:</strong> ${factura.fecha}</p>
        <p><strong>Total:</strong> $${factura.total}</p>
        
        <hr class="hr">
        
        <h4>Productos Comprados:</h4>
        
        <sql:query dataSource="${db}" var="detalles">
            SELECT df.cantidad, df.precio_unitario, t.nom
            FROM detalle_factura df
            INNER JOIN Tiendita t ON df.id_producto = t.id
            WHERE df.id_factura = ${factura.id}
        </sql:query>
        
        <table class="table">
            <thead>
            <tr>
                <th>Producto</th>
                <th>Cantidad</th>
                <th>Precio Unitario</th>
                <th>Subtotal</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="detalle" items="${detalles.rows}">
                <tr>
                    <td>${detalle.nom}</td>
                    <td>${detalle.cantidad}</td>
                    <td>$${detalle.precio_unitario}</td>
                    <td>$${detalle.cantidad * detalle.precio_unitario}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</c:forEach>

<div class="card center">
    <a href="index.jsp" class="btn btn-primary">Volver</a>
</div>

</div>

</body>
</html>
