<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<html>
<head>
<link rel="stylesheet" href="style.css">
</head>

<body>
<center>

<div class="nav">
    <div>Attendance System</div>
    <div>Add Attendance</div>
</div>

<div class="container">

<div class="card">

<h2>Enter Attendance</h2>

<form action="MainServlet" method="post">

<input type="number" name="id" placeholder="Student ID" required>
<input type="text" name="name" placeholder="Student Name" required>
<input type="number" name="attended" placeholder="Classes Attended" required>
<input type="number" name="total" placeholder="Total Classes" required>

<button type="submit">Submit</button>

</form>

</div>
</div>

</center>
</body>
</html>