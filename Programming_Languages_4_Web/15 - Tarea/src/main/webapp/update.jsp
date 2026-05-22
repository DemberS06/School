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
    String name = request.getParameter("name");
    String city = request.getParameter("city");
    String phone = request.getParameter("phone");

    Statement st = con.createStatement();
    st.executeUpdate("UPDATE student_info SET name='"+name+"', city='"+city+"', phone='"+phone+"' WHERE id="+id);

    response.sendRedirect("list.jsp");

}catch(Exception e){
    out.println("Error: "+e.getMessage());
}
%>