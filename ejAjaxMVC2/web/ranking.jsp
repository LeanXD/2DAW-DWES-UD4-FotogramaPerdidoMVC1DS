<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Consulta de ranking</title>
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<script language="javascript" src="javascript.js">
</script>
</head>
<body onload="CargarRanking()">
<center>
	 <P><jsp:include page="WEB-INF/head.jsp" flush="true"/></P>
	 <br/>
	 <br/> 
<div id="tabRan">
<input type="hidden" name="peticion" value="ajax" id="peticion"/>
</div>
<br/>
<br/>
<form action="controlador?accion=controlador" method="post">
	<input type="submit" value="Volver">
</form>
<h5 style="color: red;" id="errores"></h5>
</center>
</body>
</html>