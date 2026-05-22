<%@page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add Record</title>
<link rel="stylesheet" href="assets/css/styles.css">
</head>

<body>
<div class="container">

<div class="card">
<h2>Add Student</h2>

<form class="form" method="POST" action="save.jsp">

<label>Name</label>
<input class="form-control" type="text" name="name">

<label>City</label>
<input class="form-control" type="text" name="city">

<label>Phone</label>
<input class="form-control" type="text" name="telephone">

<div class="mt-1">
    <button class="btn btn-primary" type="submit">Save</button>
    <a class="btn btn-outline" href="list.jsp">Back</a>
</div>

</form>
</div>

</div>
</body>
</html>