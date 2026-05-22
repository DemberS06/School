<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>CRUD - Students</title>
<link rel="stylesheet" href="assets/css/styles.css">
</head>

<body>
<div class="container">

<h1 class="center">Student Management</h1>

<div class="card">
    <a class="btn btn-primary" href="insert.jsp">+ Add Record</a>

    <table class="table mt-1">
        <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>City</th>
                <th>Phone</th>
                <th>Edit</th>
                <th>Delete</th>
            </tr>
        </thead>
        <tbody>

<%
try {
    Class.forName("org.mariadb.jdbc.Driver");
    Connection con = DriverManager.getConnection(
        "jdbc:mariadb://localhost:3306/prueba01",
        "root",
        "41258789"
    );

    Statement stmt = con.createStatement();
    ResultSet rs = stmt.executeQuery("SELECT * FROM student_info");

    while(rs.next()){
%>
<tr>
    <td><%=rs.getInt("id")%></td>
    <td><%=rs.getString("name")%></td>
    <td><%=rs.getString("city")%></td>
    <td><%=rs.getString("phone")%></td>
    <td><a class="btn btn-outline" href="edit.jsp?id=<%=rs.getInt("id")%>">Edit</a></td>
    <td><a class="btn btn-outline" href="delete.jsp?id=<%=rs.getInt("id")%>">Delete</a></td>
</tr>
<%
    }

    con.close();
} catch(Exception e){
    out.println("<p class='pill'>Error: "+e.getMessage()+"</p>");
}
%>

        </tbody>
    </table>
</div>

</div>
</body>
</html>