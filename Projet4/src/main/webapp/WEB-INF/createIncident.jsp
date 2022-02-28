<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%@ include file="navigationBar.jsp" %>
<form method="post" action="createIncident" ${ empty incidentId || incidentId == 0 ? '' : 'style="display:none;"' }>
	<table>
		<tr>
			<td><label for="type">Type d'incident</label></td>
			<td><select name="type">
			 	<c:forEach items="${ incidentTypes}" var="type">
       				 <option value="${ type.id }" ${ type == selectedType ? 'selected' : ''}>${ type.name }</option>
    			</c:forEach>
			</select></td>
		</tr>
		<tr>
			<td><label for="address">Adresse</label></td>
			<td><input type="text" name="address" value="${ address }" required /></td>
		</tr>
		<tr>
			<td><label for="zipcode">Code postal</label></td>
			<td><input type="text" name="zipcode"  value="${ zipcode }"required/></td>
		</tr>
		<tr>
			<td><label for="city">Ville</label></td>
			<td><input type="text" name="city"  value="${ city }"required/></td>
		</tr>
	</table>
	<c:if test="${ not empty errorMessage }"><p style="color:red;">${errorMessage }</p></c:if>
	<input type="submit" value="OK" />
</form>
</body>
</html>