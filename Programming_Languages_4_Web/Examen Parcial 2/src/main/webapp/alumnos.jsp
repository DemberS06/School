<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="dao.*"%>
<%@page import="model.*"%>
<%@page import="java.util.List"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Alumnos</title>

        <link rel="stylesheet" href="assets/css/styles.css">
    </head>
    <body>
        <header class="site-header">
        <div class="container header-inner">
            <!--div class="logo">
            <div class="mark">UAA</div>
            Sistema Escolar
            </div-->

            <nav class="nav">
            <a href="index.jsp">Salir</a>
            </nav>
        </div>
        </header>

        <div class="container">
            <%
            AlumnoDAO Adao = new AlumnoDAO();

            String accion = request.getParameter("accion");

            if("agregar".equals(accion)){

                Alumno a = new Alumno();

                a.setId(Integer.parseInt(request.getParameter("id")));
                a.setNombre(request.getParameter("nombre"));
                a.setEscuela(request.getParameter("escuela"));

                Adao.agregar(a);
            }

            List<Alumno> lista = Adao.listar();
            %>

            <div class="card">
                <h2>Registrar Alumno</h2>
                <form class="form">
                    <input type="hidden" name="accion" value="agregar">
                    <div class="grid-2">
                        <div>
                            <label>ID</label>
                            <input class="form-control" name="id">
                        </div>
                        <div>
                            <label>Nombre</label>
                            <input class="form-control" name="nombre">
                        </div>
                    </div>

                    <label>Escuela</label>
                    <input class="form-control" name="escuela">
                    <button class="btn btn-primary mt-1">Agregar</button>
                </form>
            </div>

            <div class="card mt-1">
                <h2>Lista de Alumnos</h2>

                <table class="table">
                    <thead>
                        <tr>
                        <th>ID</th>
                        <th>Nombre</th>
                        <th>Escuela</th>
                        <th>Materias</th>
                        </tr>
                    </thead>

                    <tbody>
                        <%
                        for(Alumno a : lista){
                        %>
                        <tr>
                            <td><%=a.getId()%></td>
                            <td><%=a.getNombre()%></td>
                            <td><%=a.getEscuela()%></td>

                            <td>
                                <%
                                    
                                    AlumnoMateriaDAO materiaDAO = new AlumnoMateriaDAO();

                                    List<AlumnoMateria> materias = materiaDAO.listar(a.getId());
                                    int cnt=0;
                                    for(AlumnoMateria xd:materias){
                                        if(a.getId()==xd.getIdAlumno())cnt++;
                                    }
                                %>
                                <a class="btn btn-outline" href="materias.jsp?id=<%=a.getId()%>">
                                    Materias: <%=cnt%>
                                </a>
                            </td>
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