<body>

<c:if test="${ not empty sessionScope.user && sessionScope.user.id > 0 }">
	<c:out value=" Bonjour ${ sessionScope.user.name }" />
</c:if>

<h2>Liste des héros</h2>
<div class="table-responsive">
<table class="table table-striped table-sm">
	<thead>
	   <tr>
	     <th scope="col">Nom du héros</th>
	     <th scope="col">Types d'incidents gérés</th>
	   </tr>
	</thead>
	<tbody>
		<c:forEach items="${ heroes }" var="hero">
			<tr>
			<td><c:out value="${ hero.name }" /></td>
			<td><c:out value="${ hero.email }" /></td>
			</tr>
		</c:forEach>
	</tbody>
</table>
</div>
</body>
</html>