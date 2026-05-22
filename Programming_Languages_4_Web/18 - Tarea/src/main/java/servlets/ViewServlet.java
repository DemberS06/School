package servlets;

import dao.EmpDao;
import models.Emp;
import java.util.List;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ViewServlet")
public class ViewServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html");
        PrintWriter out=response.getWriter();
        
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Employee List</title>");
        out.println("<link href='assets/css/styles.css' rel='stylesheet'>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div class='container'>");
        
        out.println("<h1>Employees List</h1>");
        out.println("<a href='index.html' class='btn btn-primary'>Add New Employee</a>");
        out.println("<br><br>");

        List<Emp> list=EmpDao.getAllEmployees();

        out.println("<div class='card'>");
        out.println("<table class='grid'>");
        out.println("<tr><th>Id</th><th>Name</th><th>Password</th><th>Email</th><th>Country</th><th>Edit</th><th>Delete</th></tr>");
        for(Emp e:list){
            out.print("<tr>");
            out.print("<td>"+e.getId()+"</td>");
            out.print("<td>"+e.getName()+"</td>");
            out.print("<td>"+e.getPassword()+"</td>");
            out.print("<td>"+e.getEmail()+"</td>");
            out.print("<td>"+e.getCountry()+"</td>");
            out.print("<td><a href='EditServlet?id="+e.getId()+"' class='btn btn-outline'>Edit</a></td>");
            out.print("<td><a href='DeleteServlet?id="+e.getId()+"' class='btn btn-outline'>Delete</a></td>");
            out.print("</tr>");
        }
        out.println("</table>");
        out.println("</div>");
        
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");

        out.close();
    }
}
