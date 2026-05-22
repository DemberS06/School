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

import models.AlumnoMateria;
import models.Materia;

@WebServlet("/MateriaServlet")
public class MateriaServlet extends HttpServlet {

    // Configuración de conexión a BD
    private static final String DB_URL = "jdbc:mariadb://localhost:3306/prueba01";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "41258789";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String accion = request.getParameter("accion");
        String idAlumnoParam = request.getParameter("id");

        if (idAlumnoParam == null) {
            response.sendRedirect("AlumnoServlet");
            return;
        }

        int idAlumno = Integer.parseInt(idAlumnoParam);

        if ("agregar".equals(accion)) {
            // Agregar materia al alumno
            agregarMateria(request, response, idAlumno);
        } else {
            // Listar materias del alumno (por defecto)
            listarMaterias(request, response, idAlumno);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }

    /**
     * Agregar una materia a un alumno
     */
    private void agregarMateria(HttpServletRequest request, HttpServletResponse response, int idAlumno) 
            throws ServletException, IOException {
        
        try {
            int idMateria = Integer.parseInt(request.getParameter("materia"));
            int calificacion = Integer.parseInt(request.getParameter("calificacion"));

            // Conectar a la base de datos
            Class.forName("org.mariadb.jdbc.Driver");
            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // Insertar en alumno_materia
            String sql = "INSERT INTO alumno_materia (id_alumno, id_materia, calificacion) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idAlumno);
            ps.setInt(2, idMateria);
            ps.setInt(3, calificacion);
            ps.executeUpdate();

            // Cerrar conexión
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Redirigir a la lista de materias del alumno
        response.sendRedirect("MateriaServlet?id=" + idAlumno);
    }

    /**
     * Listar materias de un alumno y todas las materias disponibles
     */
    private void listarMaterias(HttpServletRequest request, HttpServletResponse response, int idAlumno) 
            throws ServletException, IOException {
        
        List<AlumnoMateria> listaMaterias = new ArrayList<>();
        List<Materia> todasLasMaterias = new ArrayList<>();

        try {
            // Conectar a la base de datos
            Class.forName("org.mariadb.jdbc.Driver");
            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // 1. Obtener materias del alumno con JOIN
            String sql1 = "SELECT am.id_alumno, am.id_materia, am.calificacion, m.nombre " +
                         "FROM alumno_materia am " +
                         "INNER JOIN materias m ON am.id_materia = m.id " +
                         "WHERE am.id_alumno = ?";
            
            PreparedStatement ps1 = con.prepareStatement(sql1);
            ps1.setInt(1, idAlumno);
            ResultSet rs1 = ps1.executeQuery();

            while (rs1.next()) {
                AlumnoMateria am = new AlumnoMateria();
                am.setIdAlumno(rs1.getInt("id_alumno"));
                am.setIdMateria(rs1.getInt("id_materia"));
                am.setCalificacion(rs1.getInt("calificacion"));
                am.setNombreMateria(rs1.getString("nombre"));
                listaMaterias.add(am);
            }

            rs1.close();
            ps1.close();

            // 2. Obtener todas las materias disponibles para el select
            String sql2 = "SELECT * FROM materias";
            Statement st = con.createStatement();
            ResultSet rs2 = st.executeQuery(sql2);

            while (rs2.next()) {
                Materia materia = new Materia();
                materia.setId(rs2.getInt("id"));
                materia.setNombre(rs2.getString("nombre"));
                todasLasMaterias.add(materia);
            }

            rs2.close();
            st.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Pasar datos a JSP
        request.setAttribute("listaMaterias", listaMaterias);
        request.setAttribute("todasLasMaterias", todasLasMaterias);
        request.setAttribute("idAlumno", idAlumno);
        request.getRequestDispatcher("materias.jsp").forward(request, response);
    }
}
