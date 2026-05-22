<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Login - Sistema Escolar</title>
        <link rel="stylesheet" href="assets/css/styles.css">
    </head>
    <body>
        <div class="container">
            <div class="hero">
                <div class="intro">
                    <h1>Acceso al sistema</h1>
                    <p class="lead">
                        Sistema de Registro de Materias Aprobadas por Alumnos<br>
                        <strong>Versión con Servlets Puros (Sin DAO)</strong>
                    </p>

                    <%
                    String user = request.getParameter("user");
                    String pass = request.getParameter("pass");

                    if (user != null) {
                        if (user.equals("admin") && pass.equals("123")) {
                            response.sendRedirect("AlumnoServlet");
                        } else {
                            %>
                            <div class="card" style="background: #ffe6e6; border-color: #ff4444; margin: 1rem 0;">
                                <strong> Login incorrecto</strong><br>
                                Usuario o contraseña incorrectos. Intente nuevamente.
                            </div>
                            <%
                        }
                    }
                    %>

                    <form class="form" method="post">
                        <label>Usuario</label>
                        <input class="form-control" name="user" placeholder="admin" required>

                        <label>Password</label>
                        <input class="form-control" type="password" name="pass" placeholder="123" required>

                        <button class="btn btn-primary mt-1" type="submit">
                             Entrar al Sistema
                        </button>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
