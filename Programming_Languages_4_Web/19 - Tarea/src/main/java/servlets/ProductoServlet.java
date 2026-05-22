package servlets;

import dao.ProductoDAO;
import models.Producto;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/producto")
public class ProductoServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        if (action == null) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }

        switch (action) {
            case "nuevo":
                request.getRequestDispatcher("insert.jsp").forward(request, response);
                break;
            case "editar":
                int idEditar = Integer.parseInt(request.getParameter("id"));
                Producto producto = ProductoDAO.obtenerProducto(idEditar);
                request.setAttribute("producto", producto);
                request.getRequestDispatcher("update.jsp").forward(request, response);
                break;
            case "eliminar":
                int idEliminar = Integer.parseInt(request.getParameter("id"));
                ProductoDAO.eliminarProducto(idEliminar);
                response.sendRedirect(request.getContextPath() + "/");
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/");
                break;
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");

        if ("insertar".equals(action)) {
            String nombre = request.getParameter("nombre");
            double precio = Double.parseDouble(request.getParameter("precio"));
            int cantidad = Integer.parseInt(request.getParameter("cantidad"));

            Producto p = new Producto();
            p.setNombre(nombre);
            p.setPrecio(precio);
            p.setCantidad(cantidad);

            ProductoDAO.insertarProducto(p);
            
        } else if ("actualizar".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            String nombre = request.getParameter("nombre");
            double precio = Double.parseDouble(request.getParameter("precio"));
            int cantidad = Integer.parseInt(request.getParameter("cantidad"));

            Producto p = new Producto(id, nombre, precio, cantidad);
            ProductoDAO.actualizarProducto(p);
        }

        response.sendRedirect(request.getContextPath() + "/");
    }
}
