package ajaxmvc.modelo.beans;

import java.util.ArrayList;

public class BeanPartida {
	public int aciertos;
	public int falladas;
	public int PartidasJugadas;
	public ArrayList<Integer> PorAdivinar;
	public ArrayList<Integer> PelisAcertadas;
	public ArrayList<String> Opciones;
	public boolean FinPartida;
	public String NombrePeli;
	public String rutaImagen;
	public String error;
	public ArrayList<String> NombresOpciones;
	public int puntos;
	public int idPeli;
	
	public BeanPartida(){
		aciertos = 0;
		falladas = 0;
		PartidasJugadas = 0;
		puntos = 0;
		PorAdivinar = new ArrayList<Integer>();
		Opciones = new ArrayList<String>();
		FinPartida = false;
		NombresOpciones = new ArrayList<String>();
		PelisAcertadas =  new ArrayList<Integer>();
		idPeli = 0;
	}

	public int getAciertos() {
		return aciertos;
	}

	public void setAciertos(int aciertos) {
		this.aciertos = aciertos;
	}
	
	public void setAciertos(){
		this.aciertos++;
	}

	public int getFalladas() {
		return falladas;
	}

	public void setFalladas(int falladas) {
		this.falladas = falladas;
	}
	
	public void setFalladas(){
		this.falladas++;
	}
	
	public int getPartidasJugadas(){
		return this.PartidasJugadas;
	}
	
	public void setPartidasJugadas(int i){
		this.PartidasJugadas = i;
	}
	
	public void setPartidasJugadas(){
		this.PartidasJugadas++;
	}
	
	public ArrayList<Integer> getPorAdivinar() {
		return PorAdivinar;
	}

	public void setPorAdivinar(ArrayList<Integer> porAdivinar) {
		PorAdivinar = porAdivinar;
	}

	public ArrayList<String> getOpciones() {
		return Opciones;
	}

	public void setOpciones(ArrayList<String> opciones) {
		Opciones = opciones;
	}

	public boolean isFinPartida() {
		return FinPartida;
	}

	public void setFinPartida(boolean finPartida) {
		FinPartida = finPartida;
	}

	public String getNombrePeli() {
		return NombrePeli;
	}

	public void setNombrePeli(String nombrePeli) {
		NombrePeli = nombrePeli;
	}

	public String getRutaImagen() {
		return rutaImagen;
	}

	public void setRutaImagen(String rutaImagen) {
		this.rutaImagen = rutaImagen;
	}

	public ArrayList<String> getNombresOpciones() {
		return NombresOpciones;
	}

	public void setNombresOpciones(ArrayList<String> nombresOpciones) {
		NombresOpciones = nombresOpciones;
	}

	public int getPuntos() {
		return puntos;
	}

	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}
	
	public void setPuntos(){
		this.puntos++;
	}
	
	public void addNombreOpciones(String nombre){
		this.NombresOpciones.add(nombre);
	}
	
	public void addPorAdivinar(int id){
		this.PorAdivinar.add(id);
	}
	
	public void addOpciones(String titulo){
		this.Opciones.add(titulo);
	}
		
	public int getidPeli(){
		return this.idPeli;
	}
	
	public void setidPeli(int id){
		this.idPeli = id;
	}
	
	public void delePorAdivinar(int id){
		this.PorAdivinar.remove(id);
		
	}
	
	public void  deteNombreOpciones(){
		this.NombresOpciones.clear();
	}
	
	public void setError(String error){
		this.error = error;
	}
	
	public String getError(){
		return this.error;
	}
	
	public void setPelisAcertadas(ArrayList<Integer> pelisAcertadas){
		this.PelisAcertadas = pelisAcertadas;
	}
	
	public ArrayList<Integer> getPelisAcertadas(){
		return this.PelisAcertadas;
	}
	
	public void addPelisAcertadas(int id){
		this.PelisAcertadas.add(id);
	}
}
