package dao;

import models.Producto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {
    
    public static Connection getConnection(){
        Connection con=null;
        try{
            Class.forName("org.mariadb.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mariadb://localhost:3306/prueba01","root","41258789");
        }catch(Exception e){System.out.println(e);}
        return con;
    }
    
    public static List<Producto> listarProductos(){
        List<Producto> list=new ArrayList<Producto>();
        try{
            Connection con=getConnection();
            System.out.println("Conexión: " + (con != null ? "OK" : "NULL"));
            PreparedStatement ps=con.prepareStatement("SELECT * FROM Tiendita");
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                Producto p=new Producto();
                p.setId(rs.getInt("id"));
                p.setNombre(rs.getString("nom"));
                p.setPrecio(rs.getDouble("precio"));
                p.setCantidad(rs.getInt("cantidad"));
                list.add(p);
                System.out.println("Producto: " + p.getNombre());
            }
            System.out.println("Total productos: " + list.size());
        }catch(Exception e){System.out.println(e);}
        return list;
    }
    
    public static Producto obtenerProducto(int id){
        Producto p=null;
        try{
            Connection con=getConnection();
            PreparedStatement ps=con.prepareStatement("SELECT * FROM Tiendita WHERE id=?");
            ps.setInt(1,id);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                p=new Producto();
                p.setId(rs.getInt("id"));
                p.setNombre(rs.getString("nom"));
                p.setPrecio(rs.getDouble("precio"));
                p.setCantidad(rs.getInt("cantidad"));
            }
        }catch(Exception e){System.out.println(e);}
        return p;
    }
    
    public static int insertarProducto(Producto p){
        int status=0;
        try{
            Connection con=getConnection();
            PreparedStatement ps=con.prepareStatement("INSERT INTO Tiendita(nom,precio,cantidad) VALUES(?,?,?)");
            ps.setString(1,p.getNombre());
            ps.setDouble(2,p.getPrecio());
            ps.setInt(3,p.getCantidad());
            status=ps.executeUpdate();
        }catch(Exception e){System.out.println(e);}
        return status;
    }
    
    public static int actualizarProducto(Producto p){
        int status=0;
        try{
            Connection con=getConnection();
            PreparedStatement ps=con.prepareStatement("UPDATE Tiendita SET nom=?,precio=?,cantidad=? WHERE id=?");
            ps.setString(1,p.getNombre());
            ps.setDouble(2,p.getPrecio());
            ps.setInt(3,p.getCantidad());
            ps.setInt(4,p.getId());
            status=ps.executeUpdate();
        }catch(Exception e){System.out.println(e);}
        return status;
    }
    
    public static int eliminarProducto(int id){
        int status=0;
        try{
            Connection con=getConnection();
            PreparedStatement ps=con.prepareStatement("DELETE FROM Tiendita WHERE id=?");
            ps.setInt(1,id);
            status=ps.executeUpdate();
        }catch(Exception e){System.out.println(e);}
        return status;
    }
    
    public static int actualizarStock(int id, int cantidad){
        int status=0;
        try{
            Connection con=getConnection();
            PreparedStatement ps=con.prepareStatement("UPDATE Tiendita SET cantidad=cantidad-? WHERE id=?");
            ps.setInt(1,cantidad);
            ps.setInt(2,id);
            status=ps.executeUpdate();
        }catch(Exception e){System.out.println(e);}
        return status;
    }
}