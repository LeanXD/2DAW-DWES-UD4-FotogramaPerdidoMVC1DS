package ajaxmvc.modelo.acciones;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import com.google.gson.Gson;

import ajaxmvc.modelo.beans.*;
import ajaxmvc.controlador.Accion;
import ajaxmvc.modelo.procesos.*;

/**
 * Acción registrar. Registra un usuario
 * @author  Eduardo A. Ponce
 * @version  Ajax-MVC2
 */
public class AccionRegistrar implements Accion {

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
	private String vista = null;
	
	/**
	 * Si no hay errores en el procesamiento de la acción
	 */
	private String vistaOk = "login.jsp";
	
	/**
	 * Si se precisa reintentar el registro.
	 */
	private String vistaNoOk = "registro.jsp";
	/**
	 * Contexto de aplicación.
	 */
	private ServletContext Sc;
	
	@Override
	/**
	 * Ejecuta la acción de registrar. Intenta registrar a un usuario nuevo. Si
	 * se completa correctamente se vuelve a la página de login para que se conecte, en
	 * caso contrario, se invalida la sesión y se le solicita que lo intente de nuevo.
	 */
	public boolean ejecutar(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		ProcesaBD procesaBD;
		Usuario user;
		String login, clave, peticion;
		boolean estado = true;
		HttpSession sesion = request.getSession();
		login = request.getParameter("login");
		clave = request.getParameter("clave");
		peticion = request.getParameter("peticion");
		if(peticion != null && peticion.equals("ajax")){
			ModeloAjax modelo = new ModeloAjax();
			user = new Usuario(login,clave);
			modelo.setContentType("application/json;charset=UTF-8");
			/*if ((login!=null) && (!login.equals("")) && (clave!=null) && (!clave.equals("")))*/
			if ((login!=null && !login.equals("")) && (clave!=null && !clave.equals("")))
			{
				procesaBD = new ProcesaBD(this.getDS());
				if(!procesaBD.existeUsr(login, clave)){
					if (!procesaBD.registrar(user))
					{
						user.setError("Error en el registro");
					}	
				}else{
					user.setError("El nombre de usuario ya existe");
					System.out.println("aqui");
				}
		}else{
			user.setError("Por favor introduzca un usuario y una contrase&ntilde; validos");
		}
		modelo.setRespuesta(new Gson().toJson(user));
		this.setModelo(modelo);
	}else{
		this.setVista(vistaNoOk);
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
