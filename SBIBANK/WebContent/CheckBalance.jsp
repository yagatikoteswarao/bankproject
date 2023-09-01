<%@page import="com.sun.xml.internal.ws.api.pipe.NextAction"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.*"%>
<%@page import="java.sql.DriverManager"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<style>
table
{
border-collapse: collapse;
}
.t
{
font-weight: bold;
}
td
{
border: 2px solid black;
padding:5px;
}
</style>
</head>
<body>
<center>
<form action="CheckBalance.jsp">
<input placeholder="Enter Password" name="password">
<br>
<input type="submit">
</form>
</center>
<%
String password=request.getParameter("password");
String url="jdbc:mysql://localhost:3306/teca44?user=root&password=12345";

String select="select * from bank where password=?";
String name="";
String mobilenumber="";
double amount=0.0;
try
{
	Class.forName("com.mysql.jdbc.Driver");
	Connection connection=DriverManager.getConnection(url);
	PreparedStatement ps=connection.prepareStatement(select);
	ps.setString(1,password);
	ResultSet rs=ps.executeQuery();
	
	if(rs.next())
	{
		name=rs.getString(2);
		mobilenumber=rs.getString(3);
		amount = rs.getDouble(5);
	}
	else
	{
		
		
	}
}

catch(Exception e)
{
	e.printStackTrace();
}
%>

<%if(name!=""){ %>
<center>
<table>
<tr>
<td class="t">Name </td>
<td class="t">MobileNumber</td>
<td class="t">Amount</td>
</tr>
<tr>
<td > <%=name %> </td>
<td > <%=mobilenumber %></td>
<td > <%=amount %> </td>
</tr>
</table>
</center>
<% } %>
</body>
</html>