package servlets;

import dao.ProductoDAO;
import models.Carrito;
import models.Producto;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/agregarCarrito")
public class AgregarCarritoServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        int id = Integer.parseInt(request.getParameter("id"));
        int cantidadPedida = Integer.parseInt(request.getParameter("cantidad"));

        HttpSession session = request.getSession();
        Carrito carrito = (Carrito) session.getAttribute("carrito");
        if (carrito == null) {
            carrito = new Carrito();
            session.setAttribute("carrito", carrito);
        }

        Producto producto = ProductoDAO.obtenerProducto(id);
        if (producto != null) {
            for (int i = 0; i < cantidadPedida; i++) {
                if (carrito.count(id) < producto.getCantidad()) {
                    carrito.add(id);
                }
            }
        }

        response.sendRedirect(request.getContextPath() + "/");
    }
}
