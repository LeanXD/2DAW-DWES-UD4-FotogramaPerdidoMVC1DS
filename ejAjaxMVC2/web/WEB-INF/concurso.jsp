<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Bienvenido al juego</title>
</head>
<body>
<center>
	 <P><jsp:include page="head.jsp" flush="true"/></P>
	 <br/>
	 <br/> 
	 <table align="center">
		 <tr>
		 	<td>
				<form action="controlador?accion=ranking" method="post">
				<input name="ranking" value="Ver ranking" type="submit" style="width: 84px; height: 33px">
				</form>
			</td>
			<td>				
				<form action="controlador?accion=jugar" method="post">
					<input name="jugar" value="Jugar" type="submit" style="width: 84px; height: 33px">
				</form>
			</td>
			<td>				
				<form action="controlador?accion=salir" method="post">
					<input name="salir" value="Cerrar Sesi&oacute;n" type="submit" style="width: 100px; height: 33px">
				</form>
			</td>
		</tr>
	</table>
</center>
</body>
</html>