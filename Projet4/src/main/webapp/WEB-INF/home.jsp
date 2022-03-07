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
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js" integrity="sha384-7+zCNj/IqJ95wo16oMtfsKbZ9ccEh31eOz1HGyDuCQ6wgnyJNSYdrPa03rtR1zdB" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.min.js" integrity="sha384-QJHtvGhmr9XOIpI6YVutG+2QOK9T+ZnN4kzFN1RtK3zEFEIsxhlmWl5/YESvpZ13" crossorigin="anonymous"></script>
</body>
</html>