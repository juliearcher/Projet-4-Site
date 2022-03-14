<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Get Hero Incidents</title>
</head>
<body>
<h2>Liste de vos incidents</h2>
<div class="table-responsive">
<table class="table table-striped table-sm">
	<thead>
		<tr>
			<th scope="col">Date de l'incident</td>
			<th scope="col">Ville</td>
			<th scope="col">Type de l'incident</td>
		</tr>
	</thead>
	<c:forEach items="${ incidents }" var="incident">
		<tr>
		<td><fmt:formatDate value="${ incident.date }" pattern="dd/MM/yyyy"/></td>
		<td><c:out value="${ incident.city }" /></td>
		<td><c:out value="${ incident.code }" /></td>
		</tr>
		</c:forEach>
</table>
</div>
</body>
</html>