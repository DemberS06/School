<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<sql:setDataSource
    var="db"
    driver="org.mariadb.jdbc.Driver"
    url="jdbc:mariadb://localhost:3306/prueba01"
    user="root"
    password="41258789"
/>

<sql:update dataSource="${db}">
INSERT INTO test1(nom,edad)
VALUES (?,?)
<sql:param value="${param.nom}" />
<sql:param value="${param.edad}" />
</sql:update>

<c:redirect url="index.jsp"/>