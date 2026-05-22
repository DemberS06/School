package servlets;

import dao.ProductoDAO;
import models.Carrito;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/comprar")
public class ComprarServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Carrito carrito = (Carrito) session.getAttribute("carrito");

        if (carrito != null) {
            ArrayList<Integer> ids = carrito.getIds();
            ArrayList<Integer> frq = carrito.getFrq();

            for (int i = 0; i < ids.size(); i++) {
                ProductoDAO.actualizarStock(ids.get(i), frq.get(i));
            }

            carrito.clear();
        }

        response.sendRedirect(request.getContextPath() + "/");
    }
}
