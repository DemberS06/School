package servlets;

import dao.EmpDao;
import models.Emp;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/EditServlet")
public class EditServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html");
        PrintWriter out=response.getWriter();
        
        String sid=request.getParameter("id");
        int id=Integer.parseInt(sid);
        
        Emp e=EmpDao.getEmployeeById(id);
        
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Edit Employee</title>");
        out.println("<link href='assets/css/styles.css' rel='stylesheet'>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div class='container'>");
        
        out.println("<h1>Update Employee</h1>");
        
        out.println("<div class='card'>");
        out.print("<form action='EditServlet2' method='post'>");
        out.print("<table>");
        out.print("<tr><td></td><td><input type='hidden' name='id' value='"+e.getId()+"'/></td></tr>");
        out.print("<tr><td>Name:</td><td><input type='text' name='name' value='"+e.getName()+"' class='form-control'/></td></tr>");
        out.print("<tr><td>Password:</td><td><input type='password' name='password' value='"+e.getPassword()+"' class='form-control'/></td></tr>");
        out.print("<tr><td>Email:</td><td><input type='email' name='email' value='"+e.getEmail()+"' class='form-control'/></td></tr>");
        out.print("<tr><td>Country:</td><td>");
        out.print("<select name='country' class='form-control' style='width:150px'>");
        out.print("<option"+(e.getCountry().equals("India")?" selected":"")+">India</option>");
        out.print("<option"+(e.getCountry().equals("USA")?" selected":"")+">USA</option>");
        out.print("<option"+(e.getCountry().equals("UK")?" selected":"")+">UK</option>");
        out.print("<option"+(e.getCountry().equals("Other")?" selected":"")+">Other</option>");
        out.print("</select>");
        out.print("</td></tr>");
        out.print("<tr><td colspan='2'><button type='submit' class='btn btn-primary'>Edit & Save</button> ");
        out.print("<a href='ViewServlet' class='btn btn-outline'>Cancel</a></td></tr>");
        out.print("</table>");
        out.print("</form>");
        out.println("</div>");
        
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
        
        out.close();
    }
}
