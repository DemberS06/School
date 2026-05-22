<%
int c1 = Integer.parseInt(request.getParameter("campo1"));
int c2 = Integer.parseInt(request.getParameter("campo2"));
%>

<html>
<head>
  <meta charset="utf-8" />
  <meta name="viewport" content="width=device-width,initial-scale=1" />
  <title>Add</title>
  <link rel="stylesheet" href="/assets/css/styles.css" />
</head>

<body>
  <main class="container main-wrapper" style="padding-bottom:2rem;">
  
    <section class="card" style="margin-top:1.6rem; text-align:center;">
      <h2 style="margin-bottom:0.6rem;">Suma</h2>
      <p><strong>A + B = </strong> <%= c1 + c2 %></p>
      
    </section>
  </main>
</body>
</html>