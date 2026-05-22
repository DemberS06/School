<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ page import="models.Carrito" %>
<%@ page import="java.util.*" %>
<%@ page import="java.sql.*" %>

<sql:setDataSource
var="db"
driver="org.mariadb.jdbc.Driver"
url="jdbc:mariadb://localhost:3306/prueba01"
user="root"
password="41258789"
/>

<%
Carrito carrito = (Carrito)session.getAttribute("carrito");

if(carrito != null){

ArrayList<Integer> ids = carrito.getIds();
ArrayList<Integer> frq = carrito.getFrq();

Connection conn = null;
PreparedStatement pstmt = null;
ResultSet rs = null;

try {
    Class.forName("org.mariadb.jdbc.Driver");
    conn = DriverManager.getConnection(
        "jdbc:mariadb://localhost:3306/prueba01",
        "root",
        "41258789"
    );
    
    double totalCompra = 0;
    for(int i=0; i<ids.size(); i++){
        pstmt = conn.prepareStatement("SELECT precio FROM Tiendita WHERE id=?");
        pstmt.setInt(1, ids.get(i));
        rs = pstmt.executeQuery();
        
        if(rs.next()){
            double precio = rs.getDouble("precio");
            totalCompra += precio * frq.get(i);
        }
        rs.close();
        pstmt.close();
    }
    
    pstmt = conn.prepareStatement(
        "INSERT INTO facturas (fecha, total) VALUES (NOW(), ?)",
        Statement.RETURN_GENERATED_KEYS
    );
    pstmt.setDouble(1, totalCompra);
    pstmt.executeUpdate();
    
    rs = pstmt.getGeneratedKeys();
    int idFactura = 0;
    if(rs.next()){
        idFactura = rs.getInt(1);
    }
    rs.close();
    pstmt.close();
    
    for(int i=0; i<ids.size(); i++){
        int productoId = ids.get(i);
        int cantidad = frq.get(i);
        
        pstmt = conn.prepareStatement("SELECT precio FROM Tiendita WHERE id=?");
        pstmt.setInt(1, productoId);
        rs = pstmt.executeQuery();
        
        double precio = 0;
        if(rs.next()){
            precio = rs.getDouble("precio");
        }
        rs.close();
        pstmt.close();
        
        pstmt = conn.prepareStatement(
            "INSERT INTO detalle_factura (id_factura, id_producto, cantidad, precio_unitario) VALUES (?, ?, ?, ?)"
        );
        pstmt.setInt(1, idFactura);
        pstmt.setInt(2, productoId);
        pstmt.setInt(3, cantidad);
        pstmt.setDouble(4, precio);
        pstmt.executeUpdate();
        pstmt.close();
    }
    
    for(int i=0; i<ids.size(); i++){
%>

<sql:update dataSource="${db}">
UPDATE Tiendita
SET cantidad = cantidad - <%=frq.get(i)%>
WHERE id = ?
<sql:param value="<%=ids.get(i)%>"/>
</sql:update>

<%
    }
    
    carrito.clear();
    
} catch(Exception e) {
    e.printStackTrace();
} finally {
    if(rs != null) try { rs.close(); } catch(Exception e) {}
    if(pstmt != null) try { pstmt.close(); } catch(Exception e) {}
    if(conn != null) try { conn.close(); } catch(Exception e) {}
}

}
%>

<jsp:forward page="index.jsp"/>
