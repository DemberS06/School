package servlets;

import models.Carrito;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/quitarCarrito")
public class QuitarCarritoServlet extends HttpServlet {
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        int id = Integer.parseInt(request.getParameter("id"));

        HttpSession session = request.getSession();
        Carrito carrito = (Carrito) session.getAttribute("carrito");
        
        if (carrito != null) {
            carrito.remove(id);
        }

        response.sendRedirect(request.getContextPath() + "/");
    }
}
