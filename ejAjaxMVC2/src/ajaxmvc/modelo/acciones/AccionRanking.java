package ajaxmvc.modelo.acciones;

import java.io.IOException;
import java.util.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.google.gson.Gson;

import ajaxmvc.modelo.beans.*;
import ajaxmvc.modelo.procesos.*;
import ajaxmvc.controlador.Accion;

/**
 * Acción ranking. Muestra los puntos de los usuarios
 * @author  Eduardo A. Ponce
 * @version  Ajax-MVC2
 */
public class AccionRanking implements Accion {
	/**
	 * Datasource que se empleará para acceder a la base de datos.
	 * @uml.property  name="dS"
	 */
	private DataSource DS = null;
	/**
	 * Bean de error para situaciones en los que el método ejecutar() devuelve false.
	 * @uml.property  name="error"
	 * @uml.associationEnd  
	 */
	private BeanError error = null;
	/**
	 * Objeto que encapsula el modelo que procesará la vista.
	 * @uml.property  name="modelo"
	 */
	private Object modelo = null;
	/**
	 * Página JSP que se devuelve como "vista" del procesamiento de la acción.
	 * @uml.property  name="vista"
	 */
	private String vista = "ranking.jsp";
	/**
	 * Contexto de aplicación.
	 */
	private ServletContext Sc = null;

	@Override
	/**
	 * Ejecuta la acción de ranking. Recupera el datasource y obtiene el ranking
	 * en orden decreciente, que será devuelto como modelo.
	 */
	public boolean ejecutar(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		boolean estado = true;
		String peticion = request.getParameter("peticion");
		String error =  "No hay participantes";
		if( peticion!=null && peticion.equals("ajax")){
			ProcesaBD procesaBD = new ProcesaBD(this.getDS());
			ArrayList<Ranking> ListaRanking = procesaBD.getRanking();
			ModeloAjax modelo = new ModeloAjax();
			modelo.setContentType("application/json;charset=UTF-8");
			Ranking ranking = new Ranking();
			if(ListaRanking.size()<=0){
				ranking.setError(error);
				modelo.setRespuesta(new Gson().toJson(ranking));
				System.out.println("Error");
			}else{
				modelo.setRespuesta(new Gson().toJson(ListaRanking));
			}
			this.setModelo(modelo);
			vista = null;
			this.setVista(vista);
		}
		return estado;
	}

	/**
	 * @return
	 * @uml.property  name="error"
	 */
	@Override
	/**
	 * Devuelve el error asociado a la acción, si lo hubiera.
	 */
	public Exception getError() {
		return error;
	}

	/**
	 * @return
	 * @uml.property  name="modelo"
	 */
	@Override
	/**
	 * Devuelve el objeto modelo
	 */
	public Object getModelo() {
		return modelo;
	}
	/**
	 * Método setter para la propiedad modelo.
	 * @param modelo  El modelo a establecer.
	 * @uml.property  name="modelo"
	 */
	private void setModelo(Object modelo) {
		this.modelo = modelo;
	}	

	/**
	 * @return
	 * @uml.property  name="vista"
	 */
	@Override
	/**
	 * Devuelve la vista que debe procesar el modelo. En caso de ser
	 * una petición AJAX, la vista deberá ser null.
	 */
	public String getVista() {
		// La vista devuelta por una petición AJAX es null
		return vista;
	}
	/**
	 * Método setter para la propiedad vista.
	 * @param vista  La vista a establecer.
	 * @uml.property  name="vista"
	 */
	private void setVista(String vista) {
		this.vista = vista;
	}
	/**
	 * Método getter para la propiedad DS (datasource).
	 * @return  El datasource DS.
	 * @uml.property  name="dS"
	 */
	private DataSource getDS() {
		return DS;
	}	
	/**
	 * @param ds
	 * @uml.property  name="dS"
	 */
	@Override
	/**
	 * Establece el valor del datasource
	 */
	public void setDS(DataSource ds) {
		this.DS = ds;
	}

	/**
	 * @param sc
	 * @uml.property  name="sc"
	 */
	@Override
	/**
	 * Establece el contexto de aplicación
	 */
	public void setSc(ServletContext sc) {
		this.Sc = sc;
	}

}
