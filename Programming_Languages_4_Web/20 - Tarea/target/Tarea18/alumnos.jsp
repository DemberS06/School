<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="models.Alumno"%>
<%@ page import="servlets.AlumnoServlet"%>
<%@ page import="java.util.List"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Gestión de Alumnos</title>
        <link rel="stylesheet" href="assets/css/styles.css">
    </head>
    <body>
        <header class="site-header">
            <div class="container header-inner">
                <div class="logo">
                    <div class="mark">UAA</div>
                    Sistema Escolar
                </div>

                <nav class="nav">
                    <a href="index.jsp" class="btn btn-outline">🚪 Salir</a>
                </nav>
            </div>
        </header>

        <div class="container">
            <div class="card">
                <h2>📝 Registrar Nuevo Alumno</h2>
                <form class="form" action="AlumnoServlet" method="post">
                    <input type="hidden" name="accion" value="agregar">
                    
                    <div class="grid-2">
                        <div>
                            <label>ID del Alumno</label>
                            <input class="form-control" name="id" type="number" 
                                   placeholder="Ej: 1234" required>
                        </div>
                        <div>
                            <label>Nombre Completo</label>
                            <input class="form-control" name="nombre" 
                                   placeholder="Ej: Juan Pérez López" required>
                        </div>
                    </div>

                    <label>Escuela de Procedencia</label>
                    <input class="form-control" name="escuela" 
                           placeholder="Ej: Ciencias de la Computación" required>
                    
                    <button class="btn btn-primary mt-1" type="submit">
                        ✅ Agregar Alumno
                    </button>
                </form>
            </div>

            <div class="card mt-1">
                <h2>👥 Lista de Alumnos Registrados</h2>

                <%
                List<Alumno> listaAlumnos = (List<Alumno>) request.getAttribute("listaAlumnos");
                
                if (listaAlumnos == null || listaAlumnos.isEmpty()) {
                %>
                    <div style="text-align: center; padding: 2rem; color: #586069;">
                        <p>📭 No hay alumnos registrados todavía.</p>
                        <p><small>Usa el formulario de arriba para agregar el primer alumno.</small></p>
                    </div>
                <%
                } else {
                %>
                    <table class="table">
                        <thead>
                            <tr>
                                <th>🆔 ID</th>
                                <th>👤 Nombre</th>
                                <th>🏫 Escuela</th>
                                <th>📚 Materias</th>
                            </tr>
                        </thead>

                        <tbody>
                            <%
                            for (Alumno alumno : listaAlumnos) {
                                int cantidadMaterias = AlumnoServlet.contarMaterias(alumno.getId());
                            %>
                            <tr>
                                <td><strong><%= alumno.getId() %></strong></td>
                                <td><%= alumno.getNombre() %></td>
                                <td><%= alumno.getEscuela() %></td>
                                <td>
                                    <a class="btn btn-outline" 
                                       href="MateriaServlet?id=<%= alumno.getId() %>">
                                        📖 Ver Materias (<%= cantidadMaterias %>)
                                    </a>
                                </td>
                            </tr>
                            <%
                            }
                            %>
                        </tbody>
                    </table>
                <%
                }
                %>
            </div>

            <div class="card mt-1" style="background: #f0f4f9;">
                <small>
                    <strong>ℹ️ Nota Técnica:</strong> Esta aplicación utiliza 
                    <strong>Servlets puros</strong> sin capa DAO. Toda la lógica de 
                    base de datos está implementada directamente en los Servlets.
                </small>
            </div>
        </div>
    </body>
</html>
