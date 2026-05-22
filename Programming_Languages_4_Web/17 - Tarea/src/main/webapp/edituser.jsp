<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="dao.UserDao"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        
       <jsp:useBean id="u" class="model.User" >  </jsp:useBean>
       <jsp:setProperty property="*" name="u"  />

<%
int i=UserDao.update(u);
response.sendRedirect("viewusers.jsp");
%> 
        
    </body>
</html>
