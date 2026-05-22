<%
String c1 = request.getParameter("campo1");
String c2 = request.getParameter("campo2");
%>

<html>
<head>
  <meta charset="utf-8" />
  <meta name="viewport" content="width=device-width,initial-scale=1" />
  <title>Show (JSP)</title>
  <link rel="stylesheet" href="/assets/css/styles.css" />
</head>

<body>
  <main class="container main-wrapper" style="padding-bottom:2rem;">
  
    <section class="card" style="margin-top:1.6rem; text-align:center;">
      <h2 style="margin-bottom:0.6rem;">Datos recibidos</h2>
      <p><strong>Nombre:</strong> <%= c1 %></p>
      <p><strong>Apellido:</strong> <%= c2 %></p>
    </section>
  </main>
</body>
</html>