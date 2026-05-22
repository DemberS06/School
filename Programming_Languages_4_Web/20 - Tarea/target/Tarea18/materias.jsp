<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="models.AlumnoMateria"%>
<%@ page import="models.Materia"%>
<%@ page import="java.util.List"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Materias del Alumno</title>
        <link rel="stylesheet" href="assets/css/styles.css">
    </head>
    <body>
        <header class="site-header">
            <div class="container header-inner">
                <div class="logo">
                    <div class="mark">UAA</div>
                    Gestión de Materias
                </div>

                <nav class="nav">
                    <a href="AlumnoServlet" class="btn btn-outline">← Regresar a Alumnos</a>
                </nav>
            </div>
        </header>

        <div class="container">
            <%
            Integer idAlumno = (Integer) request.getAttribute("idAlumno");
            List<AlumnoMateria> listaMaterias = (List<AlumnoMateria>) request.getAttribute("listaMaterias");
            List<Materia> todasLasMaterias = (List<Materia>) request.getAttribute("todasLasMaterias");

            if (idAlumno == null) {
                response.sendRedirect("AlumnoServlet");
                return;
            }
            %>

            <div class="card">
                <h2>➕ Agregar Materia al Alumno ID: <%= idAlumno %></h2>

                <form class="form" action="MateriaServlet" method="post">
                    <input type="hidden" name="id" value="<%= idAlumno %>">
                    <input type="hidden" name="accion" value="agregar">

                    <label>Seleccionar Materia</label>
                    <select class="form-control" name="materia" required>
                        <option value="">-- Seleccione una materia --</option>
                        <%
                        if (todasLasMaterias != null) {
                            for (Materia materia : todasLasMaterias) {
                        %>
                            <option value="<%= materia.getId() %>">
                                <%= materia.getNombre() %>
                            </option>
                        <%
                            }
                        }
                        %>
                    </select>

                    <label>Calificación (0-100)</label>
                    <input class="form-control" name="calificacion" type="number" 
                           min="0" max="100" placeholder="Ej: 85" required>

                    <button class="btn btn-primary mt-1" type="submit">
                        ✅ Registrar Materia
                    </button>
                </form>
            </div>

            <div class="card mt-1">
                <h2>📚 Materias Cursadas por el Alumno</h2>

                <%
                if (listaMaterias == null || listaMaterias.isEmpty()) {
                %>
                    <div style="text-align: center; padding: 2rem; color: #586069;">
                        <p>📭 Este alumno no tiene materias registradas todavía.</p>
                        <p><small>Usa el formulario de arriba para agregar la primera materia.</small></p>
                    </div>
                <%
                } else {
                %>
                    <table class="table">
                        <thead>
                            <tr>
                                <th>📖 Materia</th>
                                <th>📊 Calificación</th>
                                <th>✅ Estado</th>
                            </tr>
                        </thead>

                        <tbody>
                            <%
                            for (AlumnoMateria am : listaMaterias) {
                                String estado = "";
                                String estilo = "";
                                
                                if (am.getCalificacion() >= 70) {
                                    estado = "✅ Aprobado";
                                    estilo = "color: #1fa36c; font-weight: bold;";
                                } else {
                                    estado = "❌ Reprobado";
                                    estilo = "color: #d64545; font-weight: bold;";
                                }
                            %>
                            <tr>
                                <td><strong><%= am.getNombreMateria() %></strong></td>
                                <td>
                                    <span class="pill" style="<%= estilo %>">
                                        <%= am.getCalificacion() %> pts
                                    </span>
                                </td>
                                <td style="<%= estilo %>">
                                    <%= estado %>
                                </td>
                            </tr>
                            <%
                            }
                            %>
                        </tbody>
                    </table>

                    <%
                    // Calcular estadísticas
                    int totalMaterias = listaMaterias.size();
                    int aprobadas = 0;
                    int sumCalificaciones = 0;

                    for (AlumnoMateria am : listaMaterias) {
                        if (am.getCalificacion() >= 70) {
                            aprobadas++;
                        }
                        sumCalificaciones += am.getCalificacion();
                    }

                    double promedio = (double) sumCalificaciones / totalMaterias;
                    int reprobadas = totalMaterias - aprobadas;
                    %>

                    <div class="card mt-1" style="background: #f0f4f9;">
                        <h3>📈 Estadísticas del Alumno</h3>
                        <div class="grid-2">
                            <div>
                                <strong>Total de materias:</strong> <%= totalMaterias %>
                            </div>
                            <div>
                                <strong>Promedio general:</strong> 
                                <span style="color: <%= (promedio >= 70) ? "#1fa36c" : "#d64545" %>; font-weight: bold;">
                                    <%= String.format("%.2f", promedio) %>
                                </span>
                            </div>
                            <div>
                                <strong>Materias aprobadas:</strong> 
                                <span style="color: #1fa36c; font-weight: bold;"><%= aprobadas %></span>
                            </div>
                            <div>
                                <strong>Materias reprobadas:</strong> 
                                <span style="color: #d64545; font-weight: bold;"><%= reprobadas %></span>
                            </div>
                        </div>
                    </div>
                <%
                }
                %>
            </div>
        </div>
    </body>
</html>
