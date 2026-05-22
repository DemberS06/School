<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>

<sql:setDataSource
var="db"
driver="org.mariadb.jdbc.Driver"
url="jdbc:mariadb://localhost:3306/prueba01"
user="root"
password="41258789"
/>

<sql:update dataSource="${db}">
UPDATE Tiendita
SET nom=?,
precio=?,
cantidad=?
WHERE id=?

<sql:param value="${param.nom}"/>
<sql:param value="${param.precio}"/>
<sql:param value="${param.cantidad}"/>
<sql:param value="${param.id}"/>

</sql:update>

<jsp:forward page="index.jsp"/>