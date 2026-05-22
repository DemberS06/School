<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="models.Carrito" %>
<%@ page import="dao.ProductoDAO" %>
<%@ page import="models.Producto" %>
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

<div class="card">
    <table class="grid">
        <tr>
            <th>ID</th>
            <th>Nombre</th>
            <th>Precio</th>
            <th>Cantidad</th>
            <th> </th>
            <th> </th>
            <th>Total</th>
        </tr>
        <c:forEach var="producto" items="${productos}">
            <tr>
                <td>${producto.id}</td>
                <td>${producto.nombre}</td>
                <td>${producto.precio}</td>
                <td>${producto.cantidad}</td>
                <td>
                    <a href="producto?action=editar&id=${producto.id}" class="btn btn-outline">
                        Editar
                    </a>
                </td>
                <td>
                    <a href="producto?action=eliminar&id=${producto.id}" class="btn btn-outline">
                        Eliminar
                    </a>
                </td>
                <form action="agregarCarrito" method="post">
                <td><input 
                    type="number"
                    name="cantidad"
                    min="1"
                    max="${producto.cantidad}"
                    value="1"
                    class="form-control"
                    style="width:80px"
                ></td>
                <td>
                    <input type="hidden" name="id" value="${producto.id}">
                    <button class="btn btn-outline">🛒</button>
                </td>

                </form>
            </tr>
        </c:forEach>
    </table>
    
    <br>

    <a href="producto?action=nuevo" class="btn btn-primary">
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
    <table class="grid">

        <tr>
            <th>ID</th>
            <th>Nombre</th>
            <th>Precio</th>
            <th>Cantidad</th>
            <th>Total</th>
            <th>Quitar</th>
        </tr>

        <%
        java.util.ArrayList<Integer> ids = carrito.getIds();
        java.util.ArrayList<Integer> frq = carrito.getFrq();
        for(int i=0; i<frq.size(); i++){
            Producto p = ProductoDAO.obtenerProducto(ids.get(i));
            if(p != null){
                int subtotal = (int)(p.getPrecio() * frq.get(i));
                total += subtotal;
        %>
            <tr>
            <td><%=p.getId()%></td>
            <td><%=p.getNombre()%></td>
            <td><%=p.getPrecio()%></td>
            <td><%=frq.get(i)%></td>
            <td><%=subtotal%></td>
            <td>
                <a href="quitarCarrito?id=<%=p.getId()%>" class="btn btn-outline">
                Quitar
                </a>
            </td>
            </tr>
        <%
            }
        }
        %>
    </table>

    <h3>Total: $<%=total%></h3>

    <a href="comprar" class="btn btn-primary">
        Comprar
    </a>
</div>

</div>

</body>
</html>