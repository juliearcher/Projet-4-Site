<body>
<h4 class="mb-3">Créer un compte</h4>

<form method="post" action="createHero">
	<table>
		<tr>
			<td><label for="name">Nom de héros</label></td>
			<td><input type="text" name="name" value="${ name }" required/></td>
		</tr>
		<tr>
			<td><label for="email">Adresse e-mail</label></td>
			<td><input type="email" name="email" value="${ email }"required/></td>
		</tr>
		<tr>
			<td><label for="phone">Numéro de téléphone</label></td>
			<td><input type="text" name="phone" value="${ phone }"required/></td>
		</tr>
		<tr>
			<td><label for="address">Adresse</label></td>
			<td><input type="text" name="address" value="${ address }" required/></td>
		</tr>
		<tr>
			<td><label for="zipcode">Code postal</label></td>
			<td><input type="text" name="zipcode"  value="${ zipcode }"required/></td>
		</tr>
		<tr>
			<td><label for="city">Ville</label></td>
			<td><input type="text" name="city"  value="${ city }"required/></td>
		</tr>
		<tr>
			<td><label for="password" >Mot de passe</label></td>
			<td><input type="password" name="password" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}" title="Au moins 8 caractères dont un chiffre, une lettre miniscule et une lettre majuscule" required/></td>
		</tr>
	</table>
	<br/>
	<h5 class="mb-3">Types d'incidents maitrisés (1 à 3)</h4>
	<table>
	<c:forEach items="${ incidentTypes }" var="type" varStatus="status">
		<c:if test="${ status.count % 2 != 0 }"><tr></c:if>
		<td><label for="checkBox${ type.id  }">${type.name }</label></td>
		<c:set var="contains" value="false" />
		<c:forEach var="typeId" items="${incidentTypeCheckboxes}">
		  <c:if test="${typeId == type.id}">
		    <c:set var="contains" value="true" />
		  </c:if>
		</c:forEach>
		<c:if test="${ contains == true	}"><td><input type="checkbox" name="incident_types" value="${ type.id  }" id="checkBox${ type.id  }" checked></td></c:if>
		<c:if test="${ contains == false }"><td><input type="checkbox" name="incident_types" value="${ type.id  }" id="checkBox${ type.id  }"></td></c:if>
		<c:if test="${ status.count % 2 == 0 || status.count == incidentTypes.size() }"></tr></c:if>
	</c:forEach>
	</table>
	<c:if test="${ not empty errorMessage }"><p style="color:red;">${errorMessage }</p></c:if>
	<input type="submit" value="OK" />
</form>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script>
$("input:checkbox").click(function() {
    let bol = $("input:checkbox:checked").length >=3;     
    $("input:checkbox").not(":checked").attr("disabled",bol);
    });
</script>
</body>
</html>
