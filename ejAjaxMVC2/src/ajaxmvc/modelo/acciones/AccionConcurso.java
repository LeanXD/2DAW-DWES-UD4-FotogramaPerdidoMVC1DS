package ajaxmvc.modelo.acciones;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import ajaxmvc.controlador.Accion;

public class AccionConcurso implements Accion {
	public String vistaOK = "WEB-INF/concurso.jsp";
	public String vista;
	public String vistaError = "login.jsp";
	private HttpSession sesion;
	
	@Override
	public boolean ejecutar(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		boolean estado = true;
		String accion = request.getParameter("accion");
		sesion = request.getSession();
		if(sesion.getAttribute("partida")!=null){
			sesion.removeAttribute("partida");
		}
		if(sesion.getAttribute("usuario")!=null){
			if(accion!=null && accion.equals("salir")){
				sesion.invalidate();
				vista = vistaError;
			}else{
				vista = vistaOK;
			}
		}else{
			vista = vistaError;
		}
		this.setVista(vista);
		return estado;
	}

	public void setVista(String vista) {
		// TODO Auto-generated method stub
		this.vista = vista;
	}

	@Override
	public String getVista() {
	
		return this.vista;
	}

	@Override
	public Object getModelo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSc(ServletContext sc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Exception getError() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDS(DataSource ds) {
		// TODO Auto-generated method stub
		
	}

}
