package db;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {

    public static Connection getConexion() {
        Connection con = null;

        try {
            Class.forName("org.mariadb.jdbc.Driver");
            con = DriverManager.getConnection(
                "jdbc:mariadb://localhost:3306/prueba01",
                "root",
                "41258789"
            );

        } catch (Exception e) {
            e.printStackTrace();
        }

        return con;
    }
}