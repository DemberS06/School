<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Editar Producto</title>
<link href="assets/css/styles.css" rel="stylesheet">
</head>
<body>

<div class="container">
    <h1>Editar Producto</h1>

    <div class="card">
        <form action="producto" method="post">
            <input type="hidden" name="action" value="actualizar">
            <input type="hidden" name="id" value="${producto.id}">

            <div class="form-group">
                <label>Nombre:</label>
                <input type="text" name="nombre" class="form-control" value="${producto.nombre}" required>
            </div>

            <div class="form-group">
                <label>Precio:</label>
                <input type="number" step="0.01" name="precio" class="form-control" value="${producto.precio}" required>
            </div>

            <div class="form-group">
                <label>Cantidad:</label>
                <input type="number" name="cantidad" class="form-control" value="${producto.cantidad}" required>
            </div>

            <button type="submit" class="btn btn-primary">Actualizar</button>
            <a href="<%= request.getContextPath() %>/" class="btn btn-outline">Cancelar</a>
        </form>
    </div>
</div>

</body>
</html>
