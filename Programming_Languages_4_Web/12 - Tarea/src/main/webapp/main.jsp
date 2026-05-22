<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="pruebas.prueba1"%>

<!DOCTYPE html>

<html>
    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width,initial-scale=1" />
        <title>JSP + Java</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="/assets/css/styles.css" />
    </head>
    <body>
        <main class="container main-wrapper" style="padding-bottom:2rem;">
            <section class="card" style="margin-top:1.6rem; text-align:center;">
                <jsp:useBean id="link" scope="application" class = "pruebas.prueba1" /> 
                
                <h1>Hello World!</h1>
                <% 
                prueba1 my = new prueba1();
                %>
            
                <%=my.Method1()  %>
            </section>
        </main>
    </body>
</html>
