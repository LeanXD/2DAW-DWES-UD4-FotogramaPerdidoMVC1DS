
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Partida</title>
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<script type="text/javascript" src="javascript.js">
</script>
</head>
<body onload="CargarPartida()">
<input type="hidden" name="peticion" value="ajax" id="peticion">
<center>
	 <P id="cabecera"><jsp:include page="./WEB-INF/head.jsp" flush="true"/></P>
	 <br/>
	 <br/> 
<div id="divJugar" style="display: none;">	 
	 	<img id="imgPeli" width="500px" height="500px" >		
	 	<br/>
	 	<br/>
	 	<form method="post" id="formJugar">
	 	<table align="center">
	 		<tr>
	 			<td>
			 		<input type="button" name="b1" id="respuesta" onclick="ValidarRespuesta()"> 
			 		<br/>
			 		<br/>
		 		</td>
		 		<td>		
			 		<input type="button" name="b2" id="respuesta" onclick="ValidarRespuesta()"> 
			 		<br/>
			 		<br/>
			 	</td> 	
				<td>	
			 		<input type="button" name="b3" id="respuesta" onclick="ValidarRespuesta()"> 
			 		<br/>
			 		<br/>
			 	</td>
			</tr>
		</table>
		<table align="center">			
		 	<tr>
		 		<td>
		 		</td>
		 		<td>
	 	 			<input type="button" name="fin" value="Fin Partida" onclick="FinalizarPartida()">
	 			</td>
	 			<td>
	 			</td>
	 		</tr>
	 	</table>
	 	</form>
	 	<br/>
	 	<br/>
</div>
<div id="divFinPartida" style="display: none;" >	 		
	 	<h3>Fin de Partida</h3>
	 	<label id="jugador" style="color: purple;"></label>
	 	<br/>
	 	<br/>
	 	<label id="puntos" style="color: blue;"></label>
	 	<br/>
	 	<br/>
	 	<label id="aciertos" style="color: green;"></label>
	 	<br/>
	 	<br/>
	 	<label id="perdidos" style="color: red;"></label>
	 	<br/>
	 	<br/>
	 	<form action="controlador?accion=controlador" method="post">
	 		<input type="submit" name="volver" value="Volver">
	 	</form>
</div>
<h5 style="color: red;" id="errores"></h5>
</center>
</body>
</html>