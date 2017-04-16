package ajaxmvc.modelo.procesos;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.sql.DataSource;
import java.util.Collection;

import ajaxmvc.modelo.beans.*;

/**
 * Se encarga de proporcionar el servicio de acceso a BD
 * @author Eduardo A. Ponce
 * @version Ajax-MVC2
 */
public class ProcesaBD {
	
	/**
	 * Datasource
	 */
	DataSource ds = null;
	
	/**
	 * Constructor que recibe el datasource
	 * @param ds El datasource para el acceso a la base de datos
	 */
	public ProcesaBD(DataSource ds) {
		this.ds = ds;
	}
	/**
	 * Comprueba si existe un usuario y se valida su clave. En caso de login y clave correctos, 
	 * se devuelve true, en caso contrario, false.
	 * @param login Login del usuario a localizar
	 * @param clave Clave del usuario a localizar
	 * @return
	 */
	public boolean existeUsr(String login, String clave) {
		boolean existe = false;
		Connection conBD = null;
		Statement st = null;
		ResultSet rs = null;
		String sentenciaSQL = null;
	
		sentenciaSQL = "select login, clave from usuarios where login like '"+login+"'";
		try {
        	conBD = ds.getConnection();
        	st = conBD.createStatement();
        	rs = st.executeQuery(sentenciaSQL);

        	if (rs.next())
        	{
        		if (rs.getString("clave").equals(clave))
        			existe = true;
        	}
        }
        catch(Exception excepcion) {
        	excepcion.printStackTrace();
        }
		finally {
			if (conBD!=null)
				try {
					conBD.close();
				}
				catch(SQLException sqle)
				{
					System.out.println(sqle);
				}
		}		
		return existe;
	}
	
	/**
	 * Devuelve un ArrayList de objetos Ranking que encapsula el listado
	 * de ranking en orden descendente
	 * @return La lista de objetos Ranking en orden descendente
	 */
	public ArrayList<Ranking> getRanking() {
		ArrayList<Ranking> ranking = new ArrayList<Ranking>();
		Ranking rnkUsuario = null;
		
		Connection conBD = null;
		Statement st = null;
		ResultSet rs = null;
		String sentenciaSQL = null;
		
		sentenciaSQL = "select login, puntos from ranking ORDER BY puntos DESC LIMIT 10 ";
		try {
        	conBD = ds.getConnection();
        	st = conBD.createStatement();
        	rs = st.executeQuery(sentenciaSQL);

        	while (rs.next())
        	{
        		rnkUsuario = new Ranking();
        		rnkUsuario.setLogin(rs.getString("login"));
        		rnkUsuario.setPuntos(rs.getString("puntos"));
        		//System.out.println("login: "+rs.getString("login") );
        		//System.out.println("puntos: "+rs.getString("puntos") );
        		ranking.add(rnkUsuario);
        	}
        	//System.out.println("Tamaño del array: "+ranking.size());
        }
        catch(Exception excepcion) {
        	excepcion.printStackTrace();
        }
		finally {
			if (conBD!=null)
				try {
					conBD.close();
				}
				catch(SQLException sqle)
				{
					System.out.println(sqle);
				}
		}		
		return ranking;
	}
	/**
	 * Realiza el proceso de registro de un usuario. Si el proceso se completa correctamente
	 * se devolverá true, en caso contrario, false.
	 * @param user Objeto que encapsula login y clave del usuario a registrar.
	 * @return true si se ha podido registrar al usuario, false en caso contrario.
	 */
	public boolean registrar(Usuario user){
		boolean registrado = false;
		Connection conBD = null;
		Statement st = null;
		ResultSet rs = null;
		String sentenciaSQL = null;
	
		sentenciaSQL = "select login, clave from usuarios where login like '"+user.getLogin()+"'";
		try {
        	conBD = ds.getConnection();
        	st = conBD.createStatement();
        	rs = st.executeQuery(sentenciaSQL);

        	if (!rs.next())
        	{
        		sentenciaSQL = "insert into usuarios(login,clave) values('"+user.getLogin()+"','"+user.getClave()+"')";
        		st.executeUpdate(sentenciaSQL);
        		//Cuando creamos un usuario lo registramos en la tabla ranking con 0 puntos
        		sentenciaSQL = "INSERT INTO ranking (login, puntos) VALUES('"+user.getLogin()+"', 0)";
        		st.execute(sentenciaSQL);
        		registrado=true;
        	}
        }
        catch(Exception excepcion) {
        	excepcion.printStackTrace();
        }
		finally {
			if (conBD!=null)
				try {
					conBD.close();
				}
				catch(SQLException sqle)
				{
					System.out.println(sqle);
				}
		}	
		return registrado;
	}
	
	// Mediante este método obtenemos los puntos del usuario pasado por parámetro
	public String getPuntosUser(String login){
		String puntos = "";
		Connection conBD = null;
		Statement st = null;
		ResultSet rs = null;
		String sentenciaSQL = null;
		sentenciaSQL = "select puntos from ranking where login = '"+login+"'";
    	try {
    		conBD = ds.getConnection();
        	st = conBD.createStatement();
			rs = st.executeQuery(sentenciaSQL);
			//Controlamos que no sea nulo los puntos del usuario
			try{
				if(rs.next()){
					puntos = Integer.toString(rs.getInt("puntos"));
				}else{
					puntos = "0";	
				}
			}catch (NullPointerException e) {
				puntos = "0";	
				System.out.println("los puntos son nulos");
			}
				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if (conBD!=null)
				try {
					conBD.close();
				}
				catch(SQLException sqle)
				{
					System.out.println(sqle);
				}
		}		
		
		return puntos;
	}
	
	// Mediante este método actualizamos los datos de la partida
		public String ActualizarDatos(String puntuacion, String login, ArrayList<Integer> acertadas){
			String puntos = "";
			Connection conBD = null;
			Statement st = null;
			ResultSet rs = null;
			String sentenciaSQL = null;
			sentenciaSQL = "UPDATE ranking SET puntos = "+ puntuacion +" WHERE login = '"+ login +"'";
	    	try {
	    		conBD = ds.getConnection();
	        	st = conBD.createStatement();
	        	st.execute(sentenciaSQL);
				if(acertadas.size()>0){
					for(int i = 0; i<acertadas.size(); i++){
						sentenciaSQL = "INSERT INTO acertadas (idFotograma, login) VALUES ("+ Integer.toString(acertadas.get(i)) +", '"+ login +"')";
						st.execute(sentenciaSQL);
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				if (conBD!=null)
					try {
						conBD.close();
					}
					catch(SQLException sqle)
					{
						System.out.println(sqle);
					}
			}		
			
			return puntos;
		}
	
	public BeanPartida getPeliculas(String login){
		Connection conBD = null;
		Statement st = null;
		ResultSet rs = null;
		String fotoAcertadas = "";
		String sentenciaSQL = "SELECT idFotograma FROM acertadas WHERE login ='"+ login +"'";
		ArrayList<Integer> idAcertadas = new ArrayList<Integer>();
		BeanPartida partida = new BeanPartida();
		try {
    		conBD = ds.getConnection();
        	st = conBD.createStatement();
        	//Obtenemos las imagenes acertadas por el usuario
        	rs = st.executeQuery(sentenciaSQL);
        	while(rs.next()){
        		idAcertadas.add(rs.getInt("idFotograma"));
        	}
        	//Si no acertado ninguna el usuario obtendremos todos los fotogramas
        	if(idAcertadas.size()>0){
        		//Casteamos todos los id's para poder realizar la sentencia para poder obtener
        		if(idAcertadas.size()<2){
        			fotoAcertadas = Integer.toString(idAcertadas.get(0));
        		}else{
	        		for(int i = 0; i<idAcertadas.size(); i++){
	        			fotoAcertadas = fotoAcertadas + Integer.toString(idAcertadas.get(i));
	        			//Controlamos que no se ha el final del array
		        		if(i!=idAcertadas.size()-1){
		        			fotoAcertadas = fotoAcertadas + ", ";
		        		}
	        		}
	        		
        		}
        		sentenciaSQL = "SELECT idFotograma, titPelicula FROM fotogramas WHERE NOT idFotograma IN("+fotoAcertadas+")";
        	}else{
        		sentenciaSQL = "SELECT idFotograma, titPelicula FROM fotogramas";
        	}
			System.out.println(sentenciaSQL);
        	rs = st.executeQuery(sentenciaSQL);
			//Obtenemos todas las peliculas seleccionadas.
			while(rs.next()){
				partida.addOpciones(rs.getString("titPelicula"));
				partida.addPorAdivinar(rs.getInt("idFotograma"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if (conBD!=null)
				try {
					conBD.close();
				}
				catch(SQLException sqle)
				{
					System.out.println(sqle);
				}
		}		
		
		return partida;
	
	}
	
	public BeanPartida getPelicula(String id){
		Connection conBD = null;
		Statement st = null;
		ResultSet rs = null;
		String sentenciaSQL = "SELECT titPelicula, archivo FROM fotogramas WHERE idFotograma = "+id;
		BeanPartida partida = new BeanPartida();
		try {
    		conBD = ds.getConnection();
        	st = conBD.createStatement();
			rs = st.executeQuery(sentenciaSQL);
			rs.next();
			partida.setNombrePeli(rs.getString("titPelicula"));
			partida.setRutaImagen(rs.getString("archivo"));
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if (conBD!=null)
				try {
					conBD.close();
				}
				catch(SQLException sqle)
				{
					System.out.println(sqle);
				}
		}		
		
		return partida;
	
	}
}
