<%@page import="ajaxmvc.modelo.beans.Usuario"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<center>
<table width="100%" align="center" border-radius= "20%" border= "1" solid= "#000000">
			<tr>
<% Usuario usuario = (Usuario) session.getAttribute("usuario");
   String puntos = (String) session.getAttribute("puntos");
	if(usuario != null && puntos != null){
	%>
				<td align="center">
					<p style="color: red">Bienvenido <%=usuario.getLogin()%></p>
				</td>
				<td align="center">
					<p style="color: blue">Puntos: <%=puntos%></p>
				</td>
				
	<%
	}
 %>
 	</tr>	
		</table>
</center>
</body>
</html>