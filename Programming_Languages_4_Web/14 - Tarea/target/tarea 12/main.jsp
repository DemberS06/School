<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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
            <!--1 Imprimir  cualquier   string   ‘prueba 1’-->
            <section class="card" style="margin-top:1.6rem; text-align:center;">
                <c:out value="${'prueba 1'}" />
                <b>This is a <c:out value="example" /></b>
            </section>
            <!--2   almacenar “casa”    en variable  v,   imprimir   v-->
            <section class="card" style="margin-top:1.6rem; text-align:center;">
                <c:set var="v" value="casa"/>
                <c:out value="${v}"/>
            </section>
            <!--3   cachar el error de una excepcion por   division sobre cero-->
            <section class="card" style="margin-top:1.6rem; text-align:center;">
                <c:catch var ="exception">
                       <% int x = 10/0;   %>
                </c:catch>
                <c:if test = "${exception != null}">
                   Occurred exception is : ${exception}
                </c:if>
            </section>
            <!--4    preguntar si un número es mayor de 100 e imprimir ese número-->
            <section class="card" style="margin-top:1.6rem; text-align:center;">
                <c:set var="num" value="123"/>
                <c:out value=" ${num}:"/>
                <c:if test = "${num>100}">
                    Number is greater than 100.
                </c:if>
                <c:if test = "${num<=100}">
                    Number is less than or equal 100.
                </c:if>
                
            </section>
            <!--5    variante del switch-->
            <section class="card" style="margin-top:1.6rem; text-align:center;">
                <c:set var="num" value="100"/>
                <c:out value=" ${num}:"/>
                <c:choose>
                    <c:when test="${num > 10}">
                        Number is greater than 10.
                    </c:when>
                    <c:otherwise>
                        Number is less than or equal to 10.
                    </c:otherwise>
                </c:choose>                             
            </section>
            <!--6   mandar a otra pagina  (redirect)-->
            <section class="card" style="margin-top:1.6rem; text-align:center;">
                <form action="/hello.jsp" method="post">
                    <button type="submit">Ir a Hello</button>
                    <%--<c:redirect /> --%>
                </form>
            </section>
            <!--7   imprimir donde está un string (JSTL) en otro string (Hello this is a JSTL function example)-->
            <section class="card" style="margin-top:1.6rem; text-align:center;">
                <c:set var="testString"  value="Hello this is a JSTL function example"/>
                Position of the given string:
		        <c:out value="${fn:indexOf(testString, 'JSTL')}" /><br/>
            </section>
            <!--8   imprimir longitud de un string-->
            <section class="card" style="margin-top:1.6rem; text-align:center;">
                <c:set var="testString" value="Hello this is a JSTL function example."/>
	            Length of the given string: 
                <c:out value="${fn:length(testString)}" />
            </section>
            <!--9 reemplazar un string por otro, dentro de un string-->
            <section class="card" style="margin-top:1.6rem; text-align:center;">
                <c:set var="testString"             value="Hello this is a JSTL function example."/>
		        Given String: <br/>  	<c:out value="${testString}" /><br/><br/>
		        String after replacing JSTL to jstl:<br/>
		        <c:out value="${fn:replace(testString, 'JSTL', 'jstl')}"/>
            </section>
            <!--10 trimm un string-->
            <section class="card" style="margin-top:1.6rem; text-align:center;">
                <c:out value="${fn:trim(testString)}" />
            </section>
        </main>
    </body>
</html>