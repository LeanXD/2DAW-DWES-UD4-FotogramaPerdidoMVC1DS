var datosUsuarios;

function Login(){
	$.ajax({
        async:true,
        type: "POST",
        dataType: "json",
        contentType: "application/x-www-form-urlencoded",
        url:"controlador?accion=validar",
        data:$("#formLogin").serialize(),
        beforeSend: inicioEnvio,
        success: UsuarioLogeado,
        timeout: 4000,
        error: problemas
    });
}

function inicioEnvio() {
    //no se hace nada
}

function problemas(datos) {
    $("#errores").text('Problemas en el servidor.'+datos.error);
}

function UsuarioLogeado(datos){
	var user = datos;
	if (user.error == null){
		window.document.location="controlador?accion=controlador";
	}else{
		$("#errores").text(user.error);
		$("#registro").css('display', 'block');
	}
}

function ObtenerUser(ranking){
	var user;
	$.ajax({
        async:true,
        type: "POST",
        dataType: "json",
        contentType: "application/x-www-form-urlencoded",
        url:"controlador?accion=solicitud",
        data:null,
        beforeSend: inicioEnvio,
        success: function(datos){
        	MostrarRanking(ranking, datos);
        },
        timeout: 4000,
        error: problemas
    });
	
	return user;
}

function getDatosUser(datos){
	if(datos.error!=null){
		alert(datos.error);
	}else{
		this.datosUsuarios = datos;
	}
}

function Registrar(){
	$.ajax({
        async:true,
        type: "POST",
        dataType: "json",
        contentType: "application/x-www-form-urlencoded",
        url:"controlador?accion=registrar",
        data:$("#formRegis").serialize(),
        beforeSend: inicioEnvio,
        success: ComprobarRegistro,
        timeout: 4000,
        error: problemas
    });
	
}
  
function ComprobarRegistro(datos){
	if(datos.error!=null){
		$("#errores").text(datos.error);
	}else{
		alert("¡¡Ya eres usuario!!");
		window.document.location="controlador?accion=validar";
	}
}

function CargarRanking(){
	$.ajax({
        async:true,
        type: "POST",
        dataType: "json",
        contentType: "application/x-www-form-urlencoded",
        url:"controlador?accion=ranking",
        data:$("#peticion").serialize(),
        beforeSend: inicioEnvio,
        success: function(datos){
        	ObtenerUser(datos);
        },
        timeout: 4000,
        error: problemas
    });
}

function MostrarRanking(ranking, datosUser){
	var i;
	var tab;
	if(ranking.error==null){
		document.getElementById("tabRan").innerHTML = "";
		//Montamos la tabla

		tab = "<table border="+"\""+"5px"+"\""+ " align="+"\""+"center"+"\""+" bordercolor="+"\""+"gray"+"\""+" width="+"\""+"50%"+"\""+ " height="+"\""+"50%"+"\""+"><tr><td align="+"\""+"center"+"\""+"><b>Puesto</td>"+
		"<td align="+"\""+"center"+"\""+"><b>Jugador</td>"+
		"<td align="+"\""+"center"+"\""+"><b>Puntos</td>"
	+"</tr>";
		var fila;
		for(i=0; i<ranking.length; i++){
			if(ranking[i].login == datosUser.login){
				fila = "<tr><td align="+"\""+"center"+"\""+" style="+"\""+"color: red;"+"\""+">"+(i+1).toString()+"</td>"
					+"<td align="+"\""+"center"+"\""+" style="+"\""+"color: blue;"+"\""+">"+ranking[i].login+"</td>"+
					"<td align="+"\""+"center"+"\""+" style="+"\""+"color: green;"+"\""+">"+ranking[i].puntos+"</td></tr>";
			}else{
				fila="<tr><td align="+"\""+"center"+"\""+">"+(i+1).toString()+"</td>"+
				"<td align="+"\""+"center"+"\""+">"+ranking[i].login+"</td>"+
				"<td align="+"\""+"center"+"\""+">"+ranking[i].puntos+"</td></tr>";
			}
			tab = tab+fila;
			//Limpiamos la variable fila
			fila = "";
		}
		tab = tab + "</table>";
		//$("#tabRan").text(tab);
		document.getElementById("tabRan").innerHTML = tab;
	}else{
		$("#error").text(datos.error);
	}
}

function CargarPartida(){
	$('#divFinPartida').fadeOut(2000);
	$.ajax({
        async:true,
        type: "POST",
        dataType: "json",
        contentType: "application/x-www-form-urlencoded",
        url:"controlador?accion=jugar",
        data:$('#peticion').serialize(),
        beforeSend: inicioEnvio,
        success: MostrarPartida,
        timeout: 4000,
        error: problemas
    });
}

function MostrarPartida(datosPartida){
	var ordenBoton = Math.floor(Math.random()*3+1);
	var opcion1;
	var opcion2;
	var opcion3;
	var ruta;
	
	if(datosPartida.error!=null){
		$('#errores').text(datosPartida.error);
	}else{
		if(!datosPartida.FinPartida){
			$('#divJugar').fadeIn(2000);
			ruta = "images/"+datosPartida.rutaImagen;
			$('#imgPeli').attr("src", ruta);
			//Para poner la partida más interesante reorganizamos las repuesta
			//según el número que salga en cada partida.
			if(ordenBoton%2 == 0){
				opcion1 = datosPartida.Opciones[0];
				opcion2 = datosPartida.Opciones[1];
				opcion3 = datosPartida.NombrePeli;
			}else if(ordenBoton == 3){
				opcion1 = datosPartida.NombrePeli; 
				opcion2 = datosPartida.Opciones[1];
				opcion3 = datosPartida.Opciones[0];
			}else{
				opcion1 = datosPartida.Opciones[1]; 
				opcion2 = datosPartida.NombrePeli; 
				opcion3 = datosPartida.Opciones[0];
			}
			//Mediante estos selectores introducimos el valor de cada opcion que puede 
			//elegir el usuario;
			$("[name='b1']").attr("value", opcion1);
			$("[name='b2']").attr("value", opcion2);
			$("[name='b3']").attr("value", opcion3);
		}else{
			MostrarFinPartida(datosPartida);
		}
		
	}
}

function FinalizarPartida(){
	$('#divJugar').fadeOut(2000);
	$.ajax({
        async:true,
        type: "POST",
        dataType: "json",
        contentType: "application/x-www-form-urlencoded",
        url:"controlador?accion=FinPartida",
        data:$('#peticion').serialize(),
        beforeSend: inicioEnvio,
        success: MostrarFinPartida,
        timeout: 4000,
        error: problemas
    });
}

function MostrarFinPartida(datosPartida){
	$('#divFinPartida').fadeIn(2000);
	$.ajax({
        async:true,
        type: "POST",
        dataType: "json",
        contentType: "application/x-www-form-urlencoded",
        url:"controlador?accion=solicitud",
        data:null,
        beforeSend: inicioEnvio,
        success: function(datosUser){
        	$('#jugador').text('Jugador: '+datosUser.login);
        	$('#puntos').text('Puntos: '+(datosPartida.puntos).toString());
        	$('#aciertos').text('Acertadas: '+(datosPartida.aciertos).toString());
        	$('#perdidos').text('Falladas: '+(datosPartida.falladas).toString());
        	document.getElementById("cabecera").innerHTML("<jsp:include page="+"\""+"./WEB-INF/head.jsp"+"\""+"flush="+"\""+"true"+"\""+"/>");
        },
        timeout: 4000,
        error: problemas
    });
}

function ValidarRespuesta(){
	$('#divJugar').fadeOut(500);
	var respuesta = $('#respuesta').val();
	$.ajax({
        async:true,
        type: "POST",
        dataType: "json",
        contentType: "application/x-www-form-urlencoded",
        url:"controlador?accion=jugar",
        data:{"respuesta": respuesta, "peticion": $('#peticion').val()},
        beforeSend: inicioEnvio,
        success: MostrarPartida,
        timeout: 4000,
        error: problemas
    });
}

