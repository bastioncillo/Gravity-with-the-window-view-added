package pr3.control;

import java.util.Scanner;

import pr3.logica.Ficha;
import pr3.logica.Observador;
import pr3.logica.TableroInmutable;
import pr3.logica.TipoJuego;

public class VistaConsola implements Observador{
	
	private ControlConsola cc;
	private Scanner in;
	
	/*Constructora*/
	public VistaConsola(ControlConsola cc, Scanner sc){
		this.cc = cc;
		this.in = sc;
		this.cc.addObservador(this);
	}

	/*Método para notificar a los observadores que la partida ha terminado*/
	public void onPartidaTerminada(TableroInmutable tabFin, Ficha ganador) {
		System.out.println(tabFin.dibujarTablero());
		if(ganador == Ficha.BLANCA){
			System.out.println("Ganan las BLANCAS");
		}else if(ganador == Ficha.NEGRA){
			System.out.println("Ganan las NEGRAS");
		}else{
			System.out.println("Partida terminada en tablas");
		}
	}

	/*Método para notificar a los observadores que un movimieno ha comenzado*/
	public void onMovimientoStart(Ficha turno) {
		if(turno == Ficha.BLANCA){
			System.out.println("Juegan BLANCAS:");
		}else{
			System.out.println("Juegan NEGRAS:");
		}
	}

	/*Método para notificar a los observadores que un movimiento ha terminado*/
	public void onMovimientoEnd(TableroInmutable tabIn) {
		System.out.println(tabIn.dibujarTablero());
	}

	/*Notifica a los observadores que se ha deshecho un movimiento, 
	 *así como la posibilidad de que se puedan deshacer más*/
	public void onUndo(TableroInmutable tabIn, boolean hayMas) {
		System.out.println(tabIn.dibujarTablero());
		if(hayMas){
			System.out.println("Puedes deshacer otro movimiento");
		}else{
			System.out.println("No puedes deshacer más movimientos");
		}
	}

	/*Notifica a los observadores que se ha reiniciado la partida*/
	public void onResetPartida(TableroInmutable tabIni, Ficha turno) {
		System.out.println("Partida reiniciada");
		if(turno == Ficha.BLANCA){
			System.out.println("Juegan BLANCAS:");
		}else{
			System.out.println("Juegan NEGRAS:");
		}
		System.out.println(tabIni.dibujarTablero());
	}

	/*Notifica a los observadores que no se puede deshacer un movimiento*/
	/* Esta función sobra ya que "onUndo" también te indica 
	 * si no puedes deshacer más movimientos*/
	public void onUndoNotPossible() {
		System.out.println("IMPOSIBLE DESHACER");
	}

	/*Notifica a los observadores que se ha cambiado el turno*/
	public void onCambioTurno(Ficha turno) {
		if(turno == Ficha.BLANCA){
			System.out.println("Juegan BLANCAS:");
		}else{
			System.out.println("Juegan NEGARS:");
		}
	}

	/*Notifica a los observadores que el movimiento es incorrecto y por qué lo es*/
	public void onMovimientoIncorrecto(String explicacion) {
		System.out.println("El movimiento es incorrecto porque " + explicacion);	
	}

	/*Notifica a los observadores que se ha cambiado el juego*/
	public void onCambioJuego(TableroInmutable tabIn, TipoJuego tipo, Ficha turno) {
		if(turno == Ficha.BLANCA){
			System.out.println("Juegan BLANCAS:");
		}else{
			System.out.println("Juegan NEGRAS:");
		}
		System.out.println(tabIn.dibujarTablero());
		System.out.println("Se ha cambiado el juego a " + tipo);
	}
	
	public void onStart(TableroInmutable tabIn, Ficha f) {}
	
	/*Muestra el texto con las instrucciones de juego por pantalla*/
	public void onMuestraAyuda(){
		System.out.println("Los comandos disponibles son:");
		System.out.println("poner: utilizalo para poner la siguiente ficha");
		System.out.println("deshacer: deshace el último movimiento de la partida");			
		System.out.println("reiniciar: reinicia la partida");			
		System.out.println("jugar [c4|co|gr] [tamX|tamY]: cambia el tipo de juego");
		System.out.println("jugador [blancas|negras] [humano|aleatorio]: cambia el tipo de jugador");
		System.out.println("salir: termina la aplicación");
		System.out.println("ayuda: muestra esta ayuda");
		System.out.println("\n");
	}
	
	/*------------------------------------ RUN ------------------------------------*/
	/*Método Run que ejecuta el programa*/
	public void run(){
		// Variables locales																
		boolean finDelJuego = false;
		String aux;
		this.cc.reiniciar();
		preguntarUsuario();
		// Se solicita al usuario que introduzca la acción a realizar.
		String orden = this.in.nextLine();
		String[] arrayS = orden.split(" +");
		
		while(!finDelJuego){
			// Si el usuario elige "jugar" (y ha introducido más información)...
			if (arrayS[0].equalsIgnoreCase("jugar") && arrayS.length > 1) {
				aux = arrayS[1];
				/* Comprobamos que la primera orden este dentro de los nombre de
				 *  los juegos disponibles...*/
				if(aux.equalsIgnoreCase("c4") || aux.equalsIgnoreCase("co")
						|| aux.equalsIgnoreCase("gr") || aux.equalsIgnoreCase("re")){
					/* Si lo está, comprobamos la longitud del array...*/
					int largo = arrayS.length, ancho = 0, alto = 0;
					if(largo == 4){
						/* Si mide 4, parseamos el string de las medidas del
						 * ancho y el alto */
						try{
							ancho = Integer.parseInt(arrayS[2]);
							alto = Integer.parseInt(arrayS[3]);
							// Y llamamos a crear juego
							this.cc.creaJuego(aux, ancho, alto);
							preguntarUsuario();
						}catch(NumberFormatException nfe){
							System.out.println("Debes introucir un valor numérico");
							preguntarUsuario();
						}
					}else if(largo == 2){
						/* Si mide 2, llamamos a creaJuego directamente, ya que 
						 * no necesitamos las medidas del ancho y del alto*/
						this.cc.creaJuego(aux, ancho, alto);
						preguntarUsuario();
					}else{
						/* Si las medidas del array son cualquier otra 
						 * la instrucción no es válida*/
						System.out.println("Datos insuficientes para crear el juego");
						preguntarUsuario();
					}
				}else{
					/* Si el nombre de juego introducido no es válido se lo decimos al jugador*/
					System.out.println("La orden introducida no es válida");
					preguntarUsuario();
				}
			}
			// Si el usuario elige "poner"...
			else if (arrayS[0].equalsIgnoreCase("poner")) {
				this.cc.poner();
				// Si no ha habido ninguna excepción al ejecutar el movimiento, se comprueba si alguno de los jugadores ha ganado.
				if(this.cc.finalizada()){
					finDelJuego = true;
				}else{
					preguntarUsuario();
				}
			}
			// Si el usuario elige "jugador <B o N> <H o A>"...
			else if (arrayS[0].equalsIgnoreCase("jugador")) {
				boolean ok = true;
				Ficha colorAux = null;
				// Primero comprobamos que el color de la ficha es correcto
				if(arrayS[1].equalsIgnoreCase("negras")){
					colorAux = Ficha.NEGRA;
				}else if(arrayS[1].equalsIgnoreCase("blancas")){
					colorAux = Ficha.BLANCA;
				}else{
					ok = false;
				}
				// Si el color era correcto, comprobamos si lo es también el jugador
				if(ok){
					if(arrayS[2].equalsIgnoreCase("aleatorio") || 
							arrayS[2].equalsIgnoreCase("humano")){
						this.cc.cambiarJugador(colorAux, arrayS[2]);
						System.out.println("El jugador con fichas "+arrayS[1]+" es ahora "+arrayS[2]);
						preguntarUsuario();
					}else{
						System.out.println("Tipo de jugador incorrecto");
						preguntarUsuario();
					}
				}else{
					System.out.println("Tipo de ficha incorrecta");
					preguntarUsuario();
				}
			}
			// Si el usuario elige "deshacer"...
			else if (arrayS[0].equalsIgnoreCase("deshacer")) {
				// ...se deshace el último movimiento y se cambia al turno del jugador anterior.
				this.cc.undo();
				preguntarUsuario();
			}
			// Si el usuario elige "reiniciar"...
			else if (arrayS[0].equalsIgnoreCase("reiniciar")) {
				// ...se resetea por completo el tablero.
				this.cc.reiniciar();
				preguntarUsuario();
			}
			//Si el usuario elige "ayuda"...
			else if (arrayS[0].equalsIgnoreCase("ayuda")) {
				//...se le muestran los comandos disponibles
				this.cc.mostrarAyuda();
				preguntarUsuario();
			}
			//Si el usuario elige salir, finDelJuego se pone a true para salir al final del bucle
			else if(arrayS[0].equalsIgnoreCase("salir")){
				this.cc.finalizar();
				finDelJuego = true;
			}
			// Si se introduce cualquier otra instrucción, se lanza un mensaje informando del error.
			else {
				System.out.println("La orden introducida no existe");
				preguntarUsuario();
			}
			// Comprobamos si se ha acabado la partida...
			if(!finDelJuego){
				/* Si no se ha terminado, volvemos a pedirle al usuario una instrucción */
				orden = this.in.nextLine();
				arrayS = orden.split(" +");
			}
		}
	}
	
	private void preguntarUsuario(){
		System.out.println("Opciones: \nsalir // jugar c4|co|gr tamX|tamY // poner // "
			+ "jugador negras|blancas humano|aleatorio // deshacer // reiniciar // ayuda");
		System.out.println("Que quieres hacer?: ");
	}
}
