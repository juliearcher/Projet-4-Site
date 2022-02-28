<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form method="post" action="getIncident">
	<table>
		<tr>
			<td><label for="date">Date de l'incident</label></td>
			<td><input type="date" name="date" placeholder="yyyy-mm-dd" pattern="^\d{4}\-(0[1-9]|1[012])\-(0[1-9]|[12][0-9]|3[01])$"  title="Date au format YYYY-MM-DD" required/></td>
		</tr>
		<tr>
			<td><label for="city">Ville de l'incident</label></td>
			<td><input type="text" name="city" required/></td>
		</tr>
		<tr>
			<td><label for="code">Code de l'incident</label></td>
			<td><input type="text" name="code" required/></td>
		</tr>
	</table>
	<c:if test="${ not empty errorMessage }"><p style="color:red;">${errorMessage }</p></c:if>
	<input type="submit" value="OK" />
</form>
</body>
</html>