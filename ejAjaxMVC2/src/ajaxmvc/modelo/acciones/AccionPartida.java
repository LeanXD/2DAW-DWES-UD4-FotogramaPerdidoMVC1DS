package ajaxmvc.modelo.acciones;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import com.google.gson.Gson;

import ajaxmvc.controlador.Accion;
import ajaxmvc.modelo.beans.BeanError;
import ajaxmvc.modelo.beans.BeanPartida;
import ajaxmvc.modelo.beans.ModeloAjax;
import ajaxmvc.modelo.beans.Usuario;
import ajaxmvc.modelo.procesos.ProcesaBD;


public class AccionPartida implements Accion{

	// Aquí se deben declarar las propiedades de la acción
		private String vista;
		private  String vistaOK = "partida.jsp";
		private final String vistaError = "gesError.jsp";
		private BeanPartida partidaJugadas = new BeanPartida();
		private ModeloAjax modelo = new ModeloAjax();
		
		// Estas variables las necesitan todas las acciones 
		private ServletContext sc;
		private HttpSession sesion;
		private DataSource DS;	
		private BeanError error;
		private String opcionSelect;
		private boolean resultado;
		private Usuario datosUsuario;
		
		
	@Override
	public boolean ejecutar(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		resultado = true;
		this.sesion = request.getSession();
		String accion = (String) request.getParameter("accion");
		String peticion = (String) request.getParameter("peticion");
		int maxPartida = 10;
		ProcesaBD procesa = new ProcesaBD(this.DS);
		datosUsuario = (Usuario) sesion.getAttribute("usuario");
		if(peticion!=null && peticion.equals("ajax")){
			modelo.setContentType("application/json;charset=UTF-8");
			if(datosUsuario!=null){
				partidaJugadas = (BeanPartida) sesion.getAttribute("partida");
				if(partidaJugadas!=null){
						//Controlamos si el usuario ha pulsado finalizar la partida
						if(accion!=null){
							if(accion.equals("FinPartida")){
								partidaJugadas.setFinPartida(true);
							}
						}
						//Comprobamos que todas las partidas sean correctas
						if(partidaJugadas.getPartidasJugadas()>10){
							partidaJugadas.setFinPartida(true);
						}
						if(partidaJugadas.isFinPartida()){
							partidaJugadas = FinalizarPartida(procesa);
							sesion.setAttribute("partida", partidaJugadas );
						}else{
							ComprobarRespuesta(request);
							if(partidaJugadas.getPorAdivinar().size()>0){
								partidaJugadas = SiguienteImagen(procesa);
								sesion.setAttribute("partida", partidaJugadas);
							}else{
								partidaJugadas = FinalizarPartida(procesa);
								sesion.setAttribute("partida", partidaJugadas);
							}		
						}
				}else{
						partidaJugadas = CrearPartida(procesa);
						sesion.setAttribute("partida", partidaJugadas);
				}
				modelo.setRespuesta(new Gson().toJson(partidaJugadas));
			}else{
				vistaOK = "login.jsp";
			}
		}else{
			vista = vistaOK;
		}
		
		return resultado;
	}

	@Override
	public String getVista() {
		// TODO Auto-generated method stub
		return this.vista;
	}

	@Override
	public Object getModelo() {
		// TODO Auto-generated method stub
		return this.modelo;
	}

	@Override
	public void setSc(ServletContext sc) {
		// TODO Auto-generated method stub
		this.sc = sc;
	}

	@Override
	public BeanError getError() {
		// TODO Auto-generated method stub
		return this.error;
	}

	@Override
	public void setDS(DataSource ds) {
		// TODO Auto-generated method stub
		this.DS = ds;
	}

	public BeanPartida CrearPartida(ProcesaBD procesa){
		BeanPartida partida = new BeanPartida();
		BeanPartida datosPartida = new BeanPartida();
		boolean borrado = false;
		int aleatorio = 0;
		int n = 0;
		String opcion; 
		
			//Añadimos una partida jugada
			partida.setPartidasJugadas();
			
			//Obtenenos los peliculas para jugar 
			datosPartida = procesa.getPeliculas(this.datosUsuario.getLogin());
			partida.setOpciones(datosPartida.getOpciones());
			partida.setPorAdivinar(datosPartida.getPorAdivinar());
			
			//Obtenemos el número aleatorio de la posición de la película para obtener
			aleatorio = (int) (Math.random()*partida.getPorAdivinar().size()+0);
			partida.setidPeli(partida.getPorAdivinar().get(aleatorio));
			//Borramo la película seleccionada.
			partida.delePorAdivinar(aleatorio);
			
			//Obtenemos los datos de la pelicula para adivinar
			datosPartida = procesa.getPelicula(Integer.toString(partida.getidPeli()));
			partida.setNombrePeli(datosPartida.getNombrePeli());
			partida.setRutaImagen(datosPartida.getRutaImagen());
			
			//Obtenemos las dos opciones que acompañara a la opción correcta
			while(n<2){
				//Para que se ha diferente las opciones obtenemos un número aleatorio
				aleatorio = (int) (Math.random()*partida.getOpciones().size()+0);
				//Controlamos que no se repita las opciones ni la opción a elegir
				opcion = partida.getOpciones().get(aleatorio); 
				if(!partida.getNombrePeli().equals(opcion)){
					if(partida.getNombresOpciones().size()>0){
						if(!partida.getNombresOpciones().get(0).equals(opcion)){
							n++;
							partida.addNombreOpciones(opcion);
						}
					}else{
						partida.addNombreOpciones(opcion);
						n++;
					}
				}
			}
			
		return partida;
	}
	
	public void ComprobarRespuesta(HttpServletRequest request){
		String respuesta = (String) request.getParameter("respuesta");
		System.out.println("Respuesta " + respuesta);
		System.out.println("Por adivinar " + this.partidaJugadas.getNombrePeli());
		if(respuesta.equals(this.partidaJugadas.getNombrePeli())){
			this.partidaJugadas.setAciertos();
			this.partidaJugadas.setPuntos();
			//Obtenemos la pelicula acertada para que no aparezca de nuevo
			this.partidaJugadas.addPelisAcertadas(this.partidaJugadas.getidPeli());
		}else{
			this.partidaJugadas.setFalladas();
		}
	}
	
	public BeanPartida SiguienteImagen(ProcesaBD procesa){
		int aleatorio = 0;
		int i = 0;
		int n = 0;
		ResultSet rs = null;
		BeanPartida datosPartida = new BeanPartida();
		String opcion; 
		
		//Añadimos una partida jugada
		this.partidaJugadas.setPartidasJugadas();
		//Limpiamos las opciones anteriores que podia seleccionar el usuario
		this.partidaJugadas.deteNombreOpciones();
		//Obtenemos el número aleatorio de la posición de la película para obtener
		aleatorio = (int) (Math.random()*this.partidaJugadas.getPorAdivinar().size()+0);
		this.partidaJugadas.setidPeli(this.partidaJugadas.getPorAdivinar().get(aleatorio));
		//Borramos la película seleccionada.
		this.partidaJugadas.delePorAdivinar(aleatorio);
		
		//Obtenemos los datos de la pelicula para adivinar
		datosPartida = procesa.getPelicula(Integer.toString(partidaJugadas.getidPeli()));
		partidaJugadas.setNombrePeli(datosPartida.getNombrePeli());
		partidaJugadas.setRutaImagen(datosPartida.getRutaImagen());

			//Obtenemos las dos opciones que acompañara a la opción correcta
			while(n<2){
				//Para que se ha diferente las opciones obtenemos un número aleatorio
				aleatorio = (int) (Math.random()*this.partidaJugadas.getOpciones().size()+0);
				opcion = this.partidaJugadas.getOpciones().get(aleatorio); 
				if(!this.partidaJugadas.getNombrePeli().equals(opcion)){
					//Controlamos que no se repita las opciones
					if(this.partidaJugadas.getNombresOpciones().size()>0){
						if(!this.partidaJugadas.getNombresOpciones().get(0).equals(opcion)){
							n++;
							this.partidaJugadas.addNombreOpciones(opcion);
						}
					}else{
						this.partidaJugadas.addNombreOpciones(opcion);
						n++;
					}
				}
			}
						
		return this.partidaJugadas;
	}
	
	public BeanPartida FinalizarPartida(ProcesaBD proceso){
		Usuario datosUsario = (Usuario) sesion.getAttribute("usuario");
		String puntosUsuario = (String) sesion.getAttribute("puntos");
		String puntuacion = "";
		if(this.partidaJugadas.getPuntos()>0){
			//Actualizamos los puntos del usuario
			puntuacion = Integer.toString(Integer.parseInt(puntosUsuario)+this.partidaJugadas.getPuntos());
			proceso.ActualizarDatos(puntuacion, datosUsario.getLogin(), this.partidaJugadas.getPelisAcertadas());
			this.sesion.setAttribute("puntos", puntuacion);
		}
		this.partidaJugadas.setFinPartida(true);
		
		return this.partidaJugadas;
	}
	

}
