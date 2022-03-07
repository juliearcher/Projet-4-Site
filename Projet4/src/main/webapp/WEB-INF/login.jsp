<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
</head>
<body>
<form method="post" action="login">
	<table>
		<tr>
			<td><label for="username">Nom de héros ou e-mail</label></td>
			<td><input type="text" name="username" required/></td>
		</tr>
		<tr>
			<td><label for="password">Mot de passe</label></td>
			<td><input type="password" name="password" required/></td>
		</tr>
	</table>
	<c:if test="${ not empty errorMessage }"><p style="color:red;">${errorMessage }</p></c:if>
	<input type="submit" value="OK" />
</form>
</body>
</html>