package ajaxmvc.modelo.beans;

import java.io.Serializable;
/**
 * Encapsula los datos (login y clave) de un usuario
 * @author  Eduardo A. Ponce
 * @version  Ajax-MVC2
 */
public class Usuario implements Serializable{
	/**
	 * login de usuario
	 * @uml.property  name="login"
	 */
	private String login = null;
	/**
	 * clave de usuario
	 * @uml.property  name="clave"
	 */
	private String clave = null;
	
	private String error = null;
	
	/**
	 * Constructor sin parámetros
	 */
	public Usuario() {}
	/**
	 * Constructor con parámetros (login y clave)
	 * @param login Login de usuario
	 * @param clave Clave de usuario
	 */
	public Usuario(String login, String clave) {
		this.setLogin(login);
		this.setClave(clave);
	}
	/**
	 * @param login  the login to set
	 * @uml.property  name="login"
	 */
	public void setLogin(String login) {
		this.login = login;
	}
	/**
	 * @return  the login
	 * @uml.property  name="login"
	 */
	public String getLogin() {
		return login;
	}
	/**
	 * @param clave  the clave to set
	 * @uml.property  name="clave"
	 */
	public void setClave(String clave) {
		this.clave = clave;
	}
	/**
	 * @return  the clave
	 * @uml.property  name="clave"
	 */
	public String getClave() {
		return clave;
	}
	
	
	public void setError(String error){
		this.error = error;
	}
	
	public String getError(){
		return this.error;
	}
}
