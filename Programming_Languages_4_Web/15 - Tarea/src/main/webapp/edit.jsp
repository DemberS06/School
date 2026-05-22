<%@page contentType="text/html; charset=UTF-8"%>
<%@ page import="java.sql.*"%>

<%
int id = Integer.parseInt(request.getParameter("id"));
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="assets/css/styles.css">
<title>Edit</title>
</head>

<body>
<div class="container">
<div class="card">

<%
try{
    Class.forName("org.mariadb.jdbc.Driver");
    Connection con = DriverManager.getConnection(
        "jdbc:mariadb://localhost:3306/prueba01",
        "root",
        "41258789"
    );

    Statement st = con.createStatement();
    ResultSet rs = st.executeQuery("SELECT * FROM student_info WHERE id="+id);

    if(rs.next()){
%>

<form class="form" method="POST" action="update.jsp">

<input type="hidden" name="id" value="<%=id%>">

<label>Name</label>
<input class="form-control" type="text" name="name" value="<%=rs.getString("name")%>">

<label>City</label>
<input class="form-control" type="text" name="city" value="<%=rs.getString("city")%>">

<label>Phone</label>
<input class="form-control" type="text" name="phone" value="<%=rs.getString("phone")%>">

<div class="mt-1">
<button class="btn btn-primary">Update</button>
<a class="btn btn-outline" href="list.jsp">Back</a>
</div>

</form>

<%
    }
}catch(Exception e){
    out.println("Error: "+e.getMessage());
}
%>

</div>
</div>
</body>
</html>