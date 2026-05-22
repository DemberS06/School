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
            <section class="card" style="margin-top:1.6rem; text-align:center;">
                <h2>1 Imprimir  cualquier   string   ‘prueba 1’</h2>
                <c:out value="${'prueba 1'}" />
                <b>This is a <c:out value="example" /></b>
            </section>
            <section class="card" style="margin-top:1.6rem; text-align:center;">
                <h2>2   almacenar “casa”    en variable  v,   imprimir   v</h2>
                <c:set var="v" value="casa"/>
                <c:out value="${v}"/>
            </section>
            <section class="card" style="margin-top:1.6rem; text-align:center;">
                <h2>3   cachar el error de una excepcion por   division sobre cero</h2>
                <c:catch var ="exception">
                       <% int x = 10/0;   %>
                </c:catch>
                <c:if test = "${exception != null}">
                   Occurred exception is : ${exception}
                </c:if>
            </section>
            <section class="card" style="margin-top:1.6rem; text-align:center;">
                <h2>4    preguntar si un número es mayor de 100 e imprimir ese número</h2>
                <c:set var="num" value="098"/>
                <c:out value=" ${num}:"/>
                <c:if test = "${num>100}">
                    Number is greater than 100.
                </c:if>
                <c:if test = "${num<=100}">
                    Number is less than or equal 100.
                </c:if>
            </section>
            <section class="card" style="margin-top:1.6rem; text-align:center;">
                <h2>5    variante del switch</h2>
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
            <section class="card" style="margin-top:1.6rem; text-align:center;">
                <h2>6   mandar a otra pagina  (redirect)</h2>
                <form action="/hello.jsp" method="post">
                    <button type="submit">Ir a Hello</button>
                    <%--<c:redirect /> --%>
                </form>
            </section>
            <section class="card" style="margin-top:1.6rem; text-align:center;">
                <h2>7   imprimir donde está un string (JSTL) en otro string (Hello this is a JSTL function example)</h2>
                <c:set var="testString"  value="Hello this is a JSTL function example"/>
                Position of the given string:
		        <c:out value="${fn:indexOf(testString, 'JSTL')}" /><br/>
            </section>
            <section class="card" style="margin-top:1.6rem; text-align:center;">
                <h2>8   imprimir longitud de un string</h2>
                <c:set var="testString" value="Hello this is a JSTL function example."/>
	            Length of the given string: 
                <c:out value="${fn:length(testString)}" />
            </section>
            <section class="card" style="margin-top:1.6rem; text-align:center;">
                <h2>9 reemplazar un string por otro, dentro de un string</h2>
                <c:set var="testString"             value="Hello this is a JSTL function example."/>
		        Given String: <br/>  	<c:out value="${testString}" /><br/><br/>
		        String after replacing JSTL to jstl:<br/>
		        <c:out value="${fn:replace(testString, 'JSTL', 'jstl')}"/>
            </section>
            <section class="card" style="margin-top:1.6rem; text-align:center;">
                <h2>10 trimming un string</h2>
                <c:out value="${fn:trim(testString)}" />
            </section>
        </main>
    </body>
</html>