<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
<link rel="stylesheet" href="style.css">
</head>

<body>
<center>

<div class="nav">
    <div>Attendance System</div>
    <div>Welcome ${sessionScope.user}</div>
</div>

<div class="container">

<div class="card">
<h1>Dashboard</h1>

<br>

<a href="addStudent.jsp"> Add Attendance</a>

<br><br>

<a href="MainServlet?action=analysis"> View Analysis</a>

<br><br>

<a href="LogoutServlet"> Logout</a>

</div>

</div>
</center>

</body>
</html>