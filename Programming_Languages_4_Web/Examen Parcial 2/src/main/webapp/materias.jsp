<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="dao.*"%>
<%@page import="model.*"%>
<%@page import="java.util.List"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Materias</title>

        <link rel="stylesheet" href="assets/css/styles.css">
    </head>
    <body>
        <header class="site-header">
            <div class="container header-inner">
                <!--div class="logo">
                    <div class="mark">U</div>
                    Sistema Escolar
                </div-->

                <nav class="nav">
                <a href="alumnos.jsp">Regresar</a>
                </nav>
            </div>
        </header>

        <div class="container">
            <%
            int id = Integer.parseInt(request.getParameter("id"));

            AlumnoMateriaDAO dao = new AlumnoMateriaDAO();
            MateriaDAO materiaDAO = new MateriaDAO();

            String accion = request.getParameter("accion");

            if("agregar".equals(accion)){

                int idMateria = Integer.parseInt(request.getParameter("materia"));
                int cal = Integer.parseInt(request.getParameter("calificacion"));

                dao.agregar(id,idMateria,cal);
            }

            List<AlumnoMateria> lista = dao.listar(id);
            List<Materia> materias = materiaDAO.listar();
            %>

            <div class="card">
                <h2>Agregar Materia</h2>

                <form class="form">
                    <input type="hidden" name="id" value="<%=id%>">
                    <input type="hidden" name="accion" value="agregar">

                    <label>Materia</label>

                    <select class="form-control" name="materia">

                        <%
                        for(Materia m : materias){
                            %>
                            <option value="<%=m.getId()%>">
                            <%=m.getNombre()%>
                            </option>

                            <%
                        }
                        %>
                    </select>

                    <label>Calificación</label>
                    <input class="form-control" name="calificacion">

                    <button class="btn btn-primary mt-1">
                        Agregar
                    </button>
                </form>
            </div>

            <div class="card mt-1">
                <h2>Materias del alumno</h2>

                <table class="table">
                    <thead>
                        <tr>
                            <th>Materia</th>
                            <th>Calificación</th>
                        </tr>
                    </thead>

                    <tbody>

                        <%
                        for(AlumnoMateria am : lista){
                            %>
                            <tr>
                                <td><%=am.getNombreMateria()%></td>
                                <td><%=am.getCalificacion()%></td>
                            </tr>
                            <%
                        }
                        %>
                    </tbody>
                </table>
            </div>
        </div>
    </body>
</html>