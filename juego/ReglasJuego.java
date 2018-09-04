package pr3.juego;

import pr3.logica.Ficha;
import pr3.logica.Tablero;
import pr3.movimientos.Movimiento;
import pr3.logica.TipoJuego;

public abstract class ReglasJuego {
	public abstract Tablero iniciarTablero();
	public abstract Ficha comprobarGanador(Tablero tab, Movimiento mov);
	public abstract boolean hayTablas(Tablero tab);
	public abstract Ficha siguienteTurno(Ficha turno);
	public abstract String dibujarTablero(Tablero tab);
	public abstract Ficha juegaPrimero();
	public abstract TipoJuego juegoActual();
}
