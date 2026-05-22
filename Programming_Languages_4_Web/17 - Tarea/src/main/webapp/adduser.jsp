<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="dao.UserDao"%>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="assets/css/styles.css" rel="stylesheet">
    </head>
    <body>

        <jsp:useBean id="u" class="model.User"> </jsp:useBean>
        <jsp:setProperty property="*" name="u"/>
        
        
     <p>Record successfully saved!</p>
    <div class="card">
    <jsp:include page="userform.html" /> 
    
      <%
    int i=UserDao.save(u);
    if(i>0){
    response.sendRedirect("viewusers.jsp");
    }else{
    response.sendRedirect("adduser.jsp");
    }
      %>  
    </div>
    </body>
</html>
