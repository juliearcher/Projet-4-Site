<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Create new Hero</title>
</head>
<body>
<%@ include file="navigationBar.jsp" %>
<form method="post" action="createHero">
	<table>
		<tr>
			<td><label for="name">Nom de héro</label></td>
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
	<p>Types d'incidents maitrisés (1 à 3)</p>
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

</body>
</html>