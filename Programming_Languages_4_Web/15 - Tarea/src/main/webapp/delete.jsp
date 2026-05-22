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

    int id = Integer.parseInt(request.getParameter("id"));

    Statement st = con.createStatement();
    st.executeUpdate("DELETE FROM student_info WHERE id="+id);

    response.sendRedirect("list.jsp");

}catch(Exception e){
    out.println("Error: "+e.getMessage());
}
%>