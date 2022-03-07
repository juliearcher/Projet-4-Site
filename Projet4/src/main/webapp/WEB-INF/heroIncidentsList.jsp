<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Get Hero Incidents</title>
</head>
<body>
<p>Liste de vos incidents</p>
<table>
	<tr>
		<td>Date de l'incident</td>
		<td>Ville</td>
		<td>Type de l'incident</td>
	</tr>
	<c:forEach items="${ incidents }" var="incident">
		<tr>
		<td><fmt:formatDate value="${ incident.date }" pattern="dd/MM/yyyy"/></td>
		<td><c:out value="${ incident.city }" /></td>
		<td><c:out value="${ incident.code }" /></td>
		</tr>
		</c:forEach>
</table>
</body>
</html>