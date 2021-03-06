<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
<c:if test="${not empty code }"><p style="color:red;">Votre code pour assigner le héros ultérieurement est : ${ code }</p></c:if>

<div class="table-responsive">
	<table class="table table-striped table-sm">
	<thead>
		<tr>
			<th scope="col"><input type="text" name="incidentId" value="${ incidentId }" hidden/></th>
			<th scope="col">Nom du Héros</th>
			<th scope="col">Numéro de téléphone</th>
			<th scope="col">Distance de l'incident</th>
		</tr>
		</thead>
		<c:forEach items="${ heroes }" var="hero">
		<c:set var="distance"><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${ hero.distance }" /></c:set>
		<c:set var="distance2"><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${ hero.distance/1000 }" /></c:set>
		<tr>
		<td><input type="radio" name="heroId" value="${hero.id }" /></td>
		<td><c:out value="${ hero.name }" /></td>
		<td><c:out value="${ hero.phoneNumber }" /></td>
		<td><c:out value="${ hero.distance > 1000 ? distance2 : distance }" /><c:out value="${ hero.distance > 1000 ? 'km' : 'm' }" /></td>
		</tr>
		</c:forEach>
	</table>
</div>
	<c:if test="${ not empty errorMessage }"><p style="color:red;">${errorMessage }</p></c:if>
	<input type="submit" value="OK" />
</form>
</body>
</html>