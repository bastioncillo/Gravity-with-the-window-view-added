package pr3.vista;

import pr3.jugadores.Jugador;
import pr3.logica.*;
import pr3.movimientos.Movimiento;
import pr3.control.factorias.*;

public class ControlVentana {
	private Partida partida;
	private FactoriaJuego factoria;
	private Jugador jugadorAleatorio;
	private Movimiento mov;
	
	/*--------------------- CONSTRUCTORA ------------------*/
	public ControlVentana(Partida partida, FactoriaJuego fj){
		this.partida = partida;
		this.factoria = fj;
		this.jugadorAleatorio = this.factoria.creaJugadorAleatorio();
	}
	
	/*-------------------- PONER ALEATORIO ----------------*/
	public void poner(){
		this.mov = this.partida.getMovimiento(this.factoria, this.jugadorAleatorio);
		this.partida.ejecutaMovimiento(this.mov);
	}

	/*----------------------- PONER ----------------------*/
	public void poner(int col, int fila){
		this.mov = this.factoria.creaMovimiento(fila, col, this.partida.getTurno());
		this.partida.ejecutaMovimiento(this.mov);
	}
	
	/*------------------------ RESET --------------------*/
	public void cambiarJuego(TipoJuego tj, int ancho, int alto){
		if(tj == TipoJuego.C4){
			this.factoria = new FactoriaJuegoConecta4();
		}else if(tj == TipoJuego.CO){
			this.factoria = new FactoriaJuegoComplica();
		}else if(tj == TipoJuego.GR){
			this.factoria = new FactoriaJuegoGravity(alto, ancho);
		}
		this.jugadorAleatorio = this.factoria.creaJugadorAleatorio();
		this.partida.cambiarJuego(this.factoria.creaReglas());
	}
	
	/*---------------------- UNDO ----------------------*/
	public void undo(){
		this.partida.deshacer();
	}
	
	/*-------------------- REINICIAR ------------------*/
	public void reiniciar(){
		this.partida.reset();
	}
	
	/*-------------------- FINALIZAR -------------------*/
	public void finalizar(){
		
	}
	
	/*------------------- FINALIZADA -------------------*/
	public boolean finalizada(){
		boolean finDelJuego = false;
		if(this.partida.getGanador() != null){
			finDelJuego = true;
		}else if(this.partida.getHayTablas()){
			finDelJuego = true;
		}
		return finDelJuego;
	}
	
	/*---------------------- START ---------------------*/
	public void Start(){
		this.partida.onStart();
	}
	
	/*-------------------- ADD OBSERVER ---------------*/
	public void addObservador(Observador o){
		this.partida.addObservador(o);
	}
	
	/*------------------- REMOVE OBSERVER -------------*/
	public void removeObserver(Observador o){}
}
