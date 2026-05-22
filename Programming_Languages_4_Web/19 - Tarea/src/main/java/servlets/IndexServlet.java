package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.ProductoDAO;
import models.Carrito;
import models.Producto;

@WebServlet("/index")
public class IndexServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Carrito carrito = (Carrito) session.getAttribute("carrito");
        if (carrito == null) {
            carrito = new Carrito();
            session.setAttribute("carrito", carrito);
        }

        List<Producto> productos = ProductoDAO.listarProductos();
        request.setAttribute("productos", productos);

        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}