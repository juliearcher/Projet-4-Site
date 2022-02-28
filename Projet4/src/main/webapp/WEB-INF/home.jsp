<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Home</title>
</head>
<body>
<%@ include file="navigationBar.jsp" %>

<p>Liste des héros</p>
<table>
	<tr>
		<td>Nom du héro</td>
		<td>Types d'incidents gérés</td>
	</tr>
	<c:forEach items="${ heroes }" var="hero">
		<tr>
		<td><c:out value="${ hero.name }" /></td>
		<td><c:out value="${ hero.email }" /></td>
		</tr>
		</c:forEach>
</table>
</body>
</html>