package pr3.control;

import pr3.control.factorias.*;
import pr3.logica.*;
import pr3.movimientos.Movimiento;
import pr3.jugadores.*;
import java.util.Scanner;

public class ControlConsola {
	
	private Partida partida;
	private Scanner in;
	private FactoriaJuego factoria;
	private Jugador jugador1, jugador2;
	private Movimiento mov;
	
	/*----------------------- CONSTRUCTORA -------------------*/
	public ControlConsola(Partida partida, Scanner sc, FactoriaJuego fj){
		this.partida = partida;
		this.in = sc;
		this.factoria = fj;
		this.jugador1 = this.factoria.creaJugadorHumano(this.in);
		this.jugador2 = this.factoria.creaJugadorHumano(this.in);
	}
	
	/*------------------- RESET -------------------------*/
	private void cambioJuego(FactoriaJuego f){
		this.partida.cambiarJuego(f.creaReglas());
	}
	
	/*-------------------- UNDO -------------------------*/
	public void undo(){
		this.partida.deshacer();
	}
	
	/*------------------- CAMBIAR JUGADOR ----------------*/
	public void cambiarJugador(Ficha color, String tipoJugador){
		if(color == Ficha.BLANCA){
			if(tipoJugador.equalsIgnoreCase("aleatorio")){
				this.jugador1 = this.factoria.creaJugadorAleatorio();
			}else if(tipoJugador.equalsIgnoreCase("humano")){
				this.jugador1 = this.factoria.creaJugadorHumano(this.in);
			}
		}else if(color == Ficha.NEGRA){
			if(tipoJugador.equalsIgnoreCase("aleatorio")){
				this.jugador2 = this.factoria.creaJugadorAleatorio();
			}else if(tipoJugador.equalsIgnoreCase("humano")){
				this.jugador2 = this.factoria.creaJugadorHumano(this.in);
			}
		}
	}
	
	/*----------------------- PONER ----------------------*/
	public void poner(){
		if(this.partida.getTurno() == Ficha.BLANCA){
			this.mov = this.partida.getMovimiento(this.factoria, this.jugador1);
		}else{
			this.mov = this.partida.getMovimiento(this.factoria, this.jugador2);
		}
		if(this.mov != null){
			this.partida.ejecutaMovimiento(this.mov);
		}
	} 
	
	/*--------------------- REINICIAR ---------------*/
	public void reiniciar(){
		this.partida.reset();
	}
	
	/*--------------------- FINALIZAR -----------------*/
	public void finalizar(){}
	
	/*-------------------- FINALIZADA -----------------*/
	public boolean finalizada(){
		boolean finDelJuego = false;
		if(this.partida.getGanador() != null){
			finDelJuego = true;
		}else if(this.partida.getHayTablas()){
			finDelJuego = true;
		}
		return finDelJuego;
	}
	
	/*--------------------- CREA JUEGO -----------------*/
	public void creaJuego(String jg, int ancho, int alto){
		if(jg.equalsIgnoreCase("c4")){
			this.factoria = new FactoriaJuegoConecta4();
		}else if(jg.equalsIgnoreCase("co")){
			this.factoria = new FactoriaJuegoComplica();
		}else if(jg.equalsIgnoreCase("gr")){
			this.factoria = new FactoriaJuegoGravity(alto, ancho);
		}
		this.jugador1 = this.factoria.creaJugadorHumano(in);
		this.jugador2 = this.factoria.creaJugadorHumano(in);
		cambioJuego(this.factoria);
	}
	
	/*---------------------- MOSTRAR AYUDA --------------------*/
	public void mostrarAyuda(){
		this.partida.muestraAyuda();
	}
	
	/*-------------------- ADD OBSERVER ---------------*/
	public void addObservador(Observador o){
		this.partida.addObservador(o);
	}
	
	public void removeObservador(Observador o){}
}
