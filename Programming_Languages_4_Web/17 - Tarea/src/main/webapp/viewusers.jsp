<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="assets/css/styles.css" rel="stylesheet">
    </head>
    <body>

<%@page import="dao.UserDao, model.User, java.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="card">
    <h1>Users List</h1>   
            
    <%
        List<User> list=UserDao.getAllRecords();
    request.setAttribute("list",list);
    %>

    <table class = "grid">
        <tr> 
        <th> Id </th>
        <th> Name </th>
        <th> Password </th>
        <th> Email </th>
        <th> Sex </th>
        <th> Country </th>
        <th> Edit </th>
        <th> Delete </th></tr>

        <c:forEach items="${list}" var="u">
            <tr>
                <td> ${u.getId()} </td>
                <td> ${u.getName()} </td>
                <td> ${u.getPassword()} </td>
                <td> ${u.getEmail()} </td>
                <td> ${u.getSex()} </td>
                <td> ${u.getCountry()} </td>
                <td><a href="editform.jsp?id=${u.getId()}" class="btn btn-outline">Edit   </a></td>
                <td><a href="deleteuser.jsp?id=${u.getId()}" class="btn btn-outline">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</div>   
<br/><a href="adduserform.jsp" class="btn btn-outline">Add New User</a>   
        
</body>
</html>
