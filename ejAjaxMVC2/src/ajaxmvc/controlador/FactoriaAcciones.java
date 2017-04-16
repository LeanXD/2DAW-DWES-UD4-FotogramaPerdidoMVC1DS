/**
 * Instancia objetos de tipo Acci�n.
 * Es una clase abstracta que impide que se puedan instanciar objetos de ella,
 * pero permite que se obtengan clases derivadas.
 * Se encarga de obtener los objetos Acci�n espec�ficos para una determinada acci�n.
 */
package ajaxmvc.controlador;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import ajaxmvc.modelo.acciones.*;

/**
 * Factor�a de acciones. Debe ser abstracta para que no se creen instancias de ellas, dado
 * que s�lo dispone de un m�todo est�tico, creaAccion(), encargado de devolver un objeto 
 * de tipo Accion.
 * @author Eduardo A. Ponce
 * @version Ajax-MVC2
 */
public abstract class FactoriaAcciones {
	/**
	 * Devuelve objetos de tipo Accion en funci�n del par�metro de acci�n proporcionado.
	 * @param accion Cadena que representa la acci�n que se desea llevar a cabo
	 * @return Objeto de tipo Accion, que encapsula el proceso a llevar a cabo. Devolver� null
	 * si la acci�n solicitada no est� contemplada en la aplicaci�n.
	 */

	@SuppressWarnings("unchecked")
	public static Accion creaAccion(String accion, String archivoProp) {
		Properties propAcciones = new Properties();
    	Class<Accion> claseAccion = null;
    	Accion objetoAccion = null;
    	String accionPedida = null;
    	
    	try {
			propAcciones.load(new FileInputStream(archivoProp));
			accionPedida = propAcciones.getProperty(accion);
			if (accionPedida!=null)
				try {
					claseAccion = (Class<Accion>) Class.forName(accionPedida);
					objetoAccion = claseAccion.newInstance();
				} catch (ClassNotFoundException excepcion) {
					excepcion.printStackTrace();
				} catch (InstantiationException excepcion) {
					excepcion.printStackTrace();
				} catch (IllegalAccessException excepcion) {
					excepcion.printStackTrace();
				}				
		} catch (FileNotFoundException excepcion) {
			excepcion.printStackTrace();
		} catch (IOException excepcion) {
			excepcion.printStackTrace();
		}
		return objetoAccion;
	}

}
