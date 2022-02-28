<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form method="post" action="assignHeroToIncident">
	<table>
		<tr>
		<td><input type="text" name="incidentId" value="${ incidentId }" hidden/></td>
		<td><p>Nom du Héro</p></td>
		<td><p>Numéro de téléphone du héro</p></td>
		<td><p>Distance de l'incident</p></td>
		</tr>
		<c:forEach items="${ heroes }" var="hero">
		<tr>
		<td><input type="radio" name="heroId" value="${hero.id }" /></td>
		<td><c:out value="${ hero.name }" /></td>
		<td><c:out value="${ hero.phoneNumber }" /></td>
		<td><c:out value="${ hero.distance > 1000 ? hero.distance/1000 : hero.distance }" /><c:out value="${ hero.distance > 1000 ? 'km' : 'm' }" /></td>
		</tr>
		</c:forEach>
	</table>
	<c:if test="${ not empty errorMessage }"><p style="color:red;">${errorMessage }</p></c:if>
	<input type="submit" value="OK" />
</form>
</body>
</html>