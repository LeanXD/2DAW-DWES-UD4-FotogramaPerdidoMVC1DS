package ajaxmvc.controlador;

import java.io.*;
import java.util.ArrayList;

import javax.naming.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.sql.*;

import ajaxmvc.modelo.beans.*;

/**
 * Implementación del servlet Controlador
 * @author  Eduardo A. Ponce
 * @version  Ajax-MVC2
 */
public class Controlador extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private static final String CONTENT_TYPE = "text/html; charset=UTF-8";
	/* Es recomendable establecer como propiedades del servlet aquellos objetos
	 * que encapsulan fuentes de datos.
	*/
	/**
	 * Cualquier fuente de datos que requiera acceso desde la aplicación deberá contemplarse como propiedad del servlet. En este caso, la fuente de datos es la base de datos bdfotogramas.
	 * @uml.property  name="dsBdfotogramas"
	 */
  private DataSource dsBdfotogramas;
	/**
     * Este objeto es precisamente el objeto que encapsula toda la información
     * a nivel de aplicación. Se corresponde con el objeto application generado
     * por el contenedor web al generar los servlets de las páginas JSP.
     */
  
  	private String archivoPropiedades;
  	
	private ServletContext sc; 

	/**
     * Inicializa el servlet, y le proporciona un objeto, ServletConfig con
     * informaciï¿½n de nivel de aplicaciï¿½n sobre el contexto de datos que rodea
     * al servlet en el contenedor web.
     * @see Servlet#init(ServletConfig)
     */

	public void init(ServletConfig config) throws ServletException {
	// Imprescindible llamar a super.init(config) para tener acceso a la configuraciÃ³n
	// del servlet a nivel de contenedor web.
	    super.init(config);

	    try {
	    	sc = config.getServletContext();
	    	Context contexto = new InitialContext();
	    	setDsBdfotogramas((DataSource) contexto.lookup(sc.getInitParameter("ds")));
	    	if (getDsBdfotogramas()==null)
	    		System.out.println("dsBdfotogramas es null.");
	    	// El datasource se almacena a nivel de aplicaciï¿½n.
	    	sc.setAttribute("dsBdfotogramas", getDsBdfotogramas());
	    	
	    	archivoPropiedades = config.getInitParameter("archivoPropiedades");
	    } 
	    catch(NamingException ne)
	    {
	        System.out.println("Error: Método init(). tipo NamingException.");
	    } 
	}

	/**
     * Lo último que se debe hacer antes de que se elimine la instancia del
     * servlet.
     * @see Servlet#destroy()
     */

	public void destroy() {
      // Elimina el datasource del ï¿½mbito de aplicaciï¿½n, liberando todos los
      // recursos que tuviera asignados.
      sc.removeAttribute("dsBdfotogramas");
      // Elimina el ï¿½mbito de aplicaciï¿½n.
      sc = null;
	}

	/**
     * Procesa las peticiones que vienen por la vía GET.
     * @param request La petición.
     * @param response La respuesta.
     * @throws javax.servlet.ServletException Error al ejecutar doPost()
     * @throws java.io.IOException Error de E/S proviniente de doPost()
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Se reenvï¿½a hacia el mï¿½todo doPost(), ya que tanto las peticiones GET como
	    // las POST se procesarï¿½n igual, y de esta manera, se evita cï¿½digo redundante.
	    doPost(request,response);		
	}

	/**
     * Procesa las peticiones que vienen por la vï¿½a POST.
     * @param request La peticiï¿½n.
     * @param response La respuesta.
     * @throws javax.servlet.ServletException Puede ser lanzada por forward().
     * @throws java.io.IOException Puede ser lanzada por forward().
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Se obtiene el objeto de ï¿½mbito sesiï¿½n
		HttpSession sesion = request.getSession();
		
		// Obtener referencia archivo propiedades acciones-clases asociadas
		String archivoProp = sc.getRealPath("/WEB-INF/"+archivoPropiedades); 
	    // Obtener un objeto de ayuda para la solicitud
	    AyudaSolicitud ayudaSol = new AyudaSolicitud(request);
	    // Crear un objeto de acciï¿½n partiendo de los parï¿½metros asociados a la solicitud
	    Accion accion = ayudaSol.getAccion(archivoProp);
	    // Se proporciona el contexto del servlet (ï¿½mbito aplicaciï¿½n) a la acciï¿½n
	    accion.setSc(sc);
	    // Se proporciona el DataSource asociado al servlet a la acciï¿½n
	    accion.setDS (dsBdfotogramas);
	    // Se procesa la solicitud (lï¿½gica de empresa)
	    if (accion.ejecutar(request,response))
	    {
	    // Si es correcto, obtener el componente relativo a la vista
	      String vista = accion.getVista();
	      Object modelo = accion.getModelo();
	      
	      if (vista!=null) {
	    	  // Aï¿½adir en la peticiï¿½n el modelo a visualizar
	    	  request.setAttribute("modelo",accion.getModelo());
	    	  // Enviar la respuesta a la solicitud
	    	  RequestDispatcher rd = request.getRequestDispatcher(vista);
	    	  rd.forward(request,response);
	      }
	      else {
	    	  ejecutarAjax (request, response, (ModeloAjax) modelo);
	      }
	    }
	    else
	    {
	    // Si la ejecuciï¿½n ha generado un error, procesarlo mediante el gestor centralizado de errores
	      gesError(accion.getVista(),accion.getError(),request,response);
	    }
	    
	}
	
	/**
     * Reenvï¿½a el proceso hacia una vista de gestiï¿½n de errores.
     * @param vistaError Pï¿½gina que gestionarï¿½ el error.
     * @param excepcion Objeto encapsulador de la excepciï¿½n.
     * @param request La peticiï¿½n.
     * @param response La respuesta.
     * @throws javax.servlet.ServletException Puede ser generada por forward().
     * @throws java.io.IOException Puede ser generada por forward().
     */
  private void gesError(String vistaError, Exception excepcion, HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    RequestDispatcher rd = request.getRequestDispatcher(vistaError);
    request.setAttribute("error",excepcion);
    rd.forward(request,response);
  }
  
  private void ejecutarAjax (HttpServletRequest request, HttpServletResponse response, ModeloAjax modelo) {
	  PrintWriter out = null;
	  try {
			out = response.getWriter();
			response.setContentType(modelo.getContentType());
			out.println(modelo.getRespuesta());
		} catch (IOException e) {
			System.out.println("Error al obtener el flujo de salida.");
	  }
	  finally {
		  out.close();
	  }
  }

  /**
 * Establece la fuente de datos para la aplicaciï¿½n.
 * @param dsBdfotogramas  Acceso a la base de datos bdfotogramas.
 * @uml.property  name="dsBdfotogramas"
 */
	public void setDsBdfotogramas(DataSource dsBdfotogramas) {
		this.dsBdfotogramas = dsBdfotogramas;
	}

    /**
	 * Obtiene la referencia a la fuente de datos de la aplicaciï¿½n.
	 * @return  La fuente de datos asociada a la base de datos bdfotogramas.
	 * @uml.property  name="dsBdfotogramas"
	 */
	public DataSource getDsBdfotogramas() {
		return dsBdfotogramas;
	}

}
