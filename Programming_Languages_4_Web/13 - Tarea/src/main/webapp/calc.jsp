<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.Calc"%>


<!DOCTYPE html>

<html>
    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width,initial-scale=1" />
        <title>Calculadora JSP</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="/assets/css/styles.css" />
    </head>
    <body>
        <main class="container main-wrapper" style="padding-bottom:2rem;">
            <section class="card" style="margin-top:1.6rem; text-align:center;">
                <jsp:useBean id="link" scope="application" class = "model.Calc" />
                <% int a= Integer.parseInt(request.getParameter("num1"));  %>                
                <% int b= Integer.parseInt(request.getParameter("num2"));  %>        
                <% String ope= new String(request.getParameter("operand"));  %>   
                <% Calc calc1 = new Calc( a, b, ope);   %>
                
                <p>   resultado es     </p>

                <%= calc1.getNum1() %>
                <%= calc1.getOperator() %>
                <%= calc1.getNum2() %>
                
                <%= calc1.getResultS() %>   

            </section>
        </main>
    </body>
</html>
