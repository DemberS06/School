<%@ page import="java.sql.*" %>
<%
try {
    Class.forName("org.mariadb.jdbc.Driver");

    Connection con = DriverManager.getConnection(
        "jdbc:mariadb://localhost:3306/prueba01",
        "root",
        "41258789"
    );

    out.println("Conexión exitosa");

    con.close();
} catch(Exception e) {
    out.println("Error: " + e.getMessage());
}
%>