<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*" %>

<html>
<head>
<link rel="stylesheet" href="style.css">
</head>

<body>
<center>

<div class="nav">
    <div>Attendance System</div>
    <div>Analysis</div>
</div>

<div class="container">

<div class="card">
<h2>Attendance Analysis</h2>

<%
ArrayList<Integer> ids = (ArrayList<Integer>)request.getAttribute("ids");
ArrayList<String> names = (ArrayList<String>)request.getAttribute("names");
ArrayList<Integer> perc = (ArrayList<Integer>)request.getAttribute("percentages");
ArrayList<Integer> bunks = (ArrayList<Integer>)request.getAttribute("bunks");
%>

<table>
<tr>
<th>ID</th>
<th>Name</th>
<th>Attendance %</th>
<th>Bunk Allowed</th>
</tr>

<%
for(int i=0;i<ids.size();i++){
%>
<tr>
<td><%= ids.get(i) %></td>
<td><%= names.get(i) %></td>
<td><%= perc.get(i) %>%</td>
<td><%= bunks.get(i) %></td>
</tr>
<%
}
%>

</table>

</div>

</div>
</center>

</body>
</html>