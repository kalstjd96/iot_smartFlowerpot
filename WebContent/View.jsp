<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,serial.*"%>

<jsp:useBean id="se" class="serial.SerialDB" />

<jsp:useBean id="addrbook" class="serial.SerialBean" />
<jsp:setProperty name="addrbook" property="*"/>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="stylesheet" href="css/bootstrap.min.css">
<link rel="stylesheet" href="css/bootstrap-theme.min.css">
<script src="js/jquery-3.3.1.min.js"></script>
<script src="js/bootstrap.min.js"></script>
</head>
<body>
<div class="jumbotron">
<p style="text-align: center; font-weight: bold; font-size: 40px;"><code>온도/습도 목록</code></p>
</div>

<div class="container">
<div class="table-responsive">
<table class="table table-hover table-striped">
<tr>
	<th>시간</th>
	<th>온도</th>
	<th>습도</th>
</tr>
<%
ArrayList<SerialBean> datas = se.getDBList();
	for(SerialBean serial : (ArrayList<SerialBean>)datas){
%>
<tr>
	<td><%=serial.getTime() %></td>
	<td><%=serial.getTemperature() %></td>
	<td><%=serial.getHumidity() %></td>
</tr>
<%} %>
</table>
</div>
</div>
</body>
</html>