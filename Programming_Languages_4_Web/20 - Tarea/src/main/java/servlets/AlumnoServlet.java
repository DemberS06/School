package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Alumno;

@WebServlet("/AlumnoServlet")
public class AlumnoServlet extends HttpServlet {

    // Configuración de conexión a BD
    private static final String DB_URL = "jdbc:mariadb://localhost:3306/prueba01";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "41258789";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String accion = request.getParameter("accion");

        if ("agregar".equals(accion)) {
            // Agregar alumno
            agregarAlumno(request, response);
        } else {
            // Listar alumnos (por defecto)
            listarAlumnos(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }

    /**
     * Agregar un nuevo alumno a la base de datos
     */
    private void agregarAlumno(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String nombre = request.getParameter("nombre");
            String escuela = request.getParameter("escuela");

            // Conectar a la base de datos
            Class.forName("org.mariadb.jdbc.Driver");
            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // Insertar alumno
            String sql = "INSERT INTO alumnos VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, nombre);
            ps.setString(3, escuela);
            ps.executeUpdate();

            // Cerrar conexión
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Redirigir a la lista de alumnos
        response.sendRedirect("AlumnoServlet");
    }

    /**
     * Listar todos los alumnos y redirigir a alumnos.jsp
     */
    private void listarAlumnos(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        List<Alumno> listaAlumnos = new ArrayList<>();

        try {
            // Conectar a la base de datos
            Class.forName("org.mariadb.jdbc.Driver");
            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // Consultar alumnos
            String sql = "SELECT * FROM alumnos";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                Alumno alumno = new Alumno();
                alumno.setId(rs.getInt("id"));
                alumno.setNombre(rs.getString("nombre"));
                alumno.setEscuela(rs.getString("escuela"));
                listaAlumnos.add(alumno);
            }

            // Cerrar conexión
            rs.close();
            st.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Pasar lista a JSP
        request.setAttribute("listaAlumnos", listaAlumnos);
        request.getRequestDispatcher("alumnos.jsp").forward(request, response);
    }

    /**
     * Método auxiliar para contar materias de un alumno
     * Este método se usa desde el JSP mediante scriptlets
     */
    public static int contarMaterias(int idAlumno) {
        int count = 0;

        try {
            Class.forName("org.mariadb.jdbc.Driver");
            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            String sql = "SELECT COUNT(*) as total FROM alumno_materia WHERE id_alumno = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idAlumno);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                count = rs.getInt("total");
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return count;
    }
}
