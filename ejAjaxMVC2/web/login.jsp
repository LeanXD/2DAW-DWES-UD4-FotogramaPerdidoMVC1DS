<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>El juego del cineasta</title>
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<script language="javascript" src="javascript.js">
</script>
</head>
<body>
<center>
<br>
<h3>¡Bienvenido! Por favor logeate</h3>
<table align="center">
	<tr>
		<td>
			<form action="controlador" method="post" id="formLogin">
			Login: <input maxlength="12" type="text" name="login" value="">
			<br/>
		</td>
	</tr>
	<tr>
		<td>
			Password: <input maxlength="12" type="password" name="clave" value"">
			<br/>
			<br/>
		</td>
	</tr>
</table>
<table aling="center">
	<tr>
		<td>
			<input type="button" value="Entrar" onclick="Login();" name="botEntrar"/>
			<input type="hidden" value="ajax" name="peticion"/>
			</form>
		</td>
	</tr>
</table>
<br/>
<br/>
<h5 style="color: red;" id="errores"></h5>
<div id="registro" style="display:none">
	<br/><br/>
	<h5 style="color: green;" id="errores">Es tu momento de unirte a nosotros</h5>
	<form action="controlador" id="formRegis">
		<input type="submit" value="Reg&iacute;strate"/>
		<input type="hidden" value="registrar" name="accion">
		<input type="hidden" value="no" name="peticion">  
	</form>
</div>
</center>
</body>
</html>