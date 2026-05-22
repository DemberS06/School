<%@page contentType="text/html; charset=UTF-8"%>
<%@ page import="java.sql.*"%>

<%
try{
    Class.forName("org.mariadb.jdbc.Driver");
    Connection con = DriverManager.getConnection(
        "jdbc:mariadb://localhost:3306/prueba01",
        "root",
        "41258789"
    );

    String name = request.getParameter("name");
    String city = request.getParameter("city");
    String phone = request.getParameter("telephone");

    Statement st = con.createStatement();
    st.executeUpdate("INSERT INTO student_info (name,city,phone) VALUES ('"+name+"','"+city+"','"+phone+"')");

    response.sendRedirect("list.jsp");

}catch(Exception e){
    out.println("Error: "+e.getMessage());
}
%>