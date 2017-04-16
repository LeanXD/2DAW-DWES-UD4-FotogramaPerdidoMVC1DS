<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Cache-Control" content="no-cache, mustrevalidate">
<title>Registro de usuario</title>
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<script type="text/javascript" src="javascript.js">
</script>
</head>
<body>
<center>
	<h3>Se uno de los nuestro</h3>
	<form action="controlador?accion=registrar" method="post" id="formRegis">
		<label>Ingresa tu nick: </label>
		<input type="text" maxlength="12" name="login" requiered>
		<br/>
		<br/>
		<label>Ingresa tu clave: </label>
		<input type="password" maxlength="12" name="clave" requiered>
		<br/>
		<br/>
		<input type="button" value="Registrar" name="botEnviar" onclick="Registrar()"/>
		<input type="hidden" name="peticion" value="ajax"/>
	</form>
	<h5 style="color: red;" id="errores"></h5>
</center>
</body> 
</html>