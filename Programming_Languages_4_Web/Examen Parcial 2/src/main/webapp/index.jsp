<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Login</title>

        <link rel="stylesheet" href="assets/css/styles.css">

    </head>
    <body>
        <!--header class="site-header">
        <div class="container header-inner">
        <div class="logo">
        <div class="mark">UAA</div>
        Sistema Escolar
        </div>
        </div>
        </header-->

        <div class="container">
            <div class="hero">
                <div class="intro">
                    <h1>Acceso al sistema</h1>
                    <p class="lead">
                    Registro de materias aprobadas por alumnos
                    </p>

                    <%
                    String user = request.getParameter("user");
                    String pass = request.getParameter("pass");

                    if(user!=null){
                        if(user.equals("admin") && pass.equals("123")){
                            response.sendRedirect("alumnos.jsp");
                        }else{
                            %>
                            <div class="card">
                            Login incorrecto
                            </div>
                            <%
                        }
                    }
                    %>

                    <form class="form">
                        <label>Usuario</label>
                        <input class="form-control" name="user">

                        <label>Password</label>
                        <input class="form-control" type="password" name="pass">

                        <button class="btn btn-primary mt-1">
                            Entrar
                        </button>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>