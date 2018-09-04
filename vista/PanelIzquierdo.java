package pr3.vista;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import pr3.logica.Ficha;
import pr3.logica.Observador;
import pr3.logica.TableroInmutable;
import pr3.logica.TipoJuego;

@SuppressWarnings("serial")
public class PanelIzquierdo extends JPanel implements Observador{
	private ControlVentana cv;
	private JButton bMovAleatorio;
	private PanelTablero panelTablero;
	private JTextField jugadorActual;
	
	public PanelIzquierdo(ControlVentana cv){
		this.cv = cv;
		this.cv.addObservador(this);
		iniciarPanelIzquierdo();
	}

	private void iniciarPanelIzquierdo() {
		this.setLayout(new BorderLayout());
		
		this.jugadorActual = new JTextField(20);
		this.jugadorActual.setFont(new Font("Calibri", 2, 19));
		this.jugadorActual.setForeground(Color.GREEN);
		this.jugadorActual.setBackground(Color.WHITE);
		this.jugadorActual.setHorizontalAlignment(SwingConstants.CENTER);
		this.jugadorActual.setEditable(false);
		JPanel axu = new JPanel();
		axu.setLayout(new FlowLayout());
		axu.add(this.jugadorActual);
		this.add(axu, BorderLayout.NORTH);
		
		this.bMovAleatorio = new JButton("Aleatorio");
		this.bMovAleatorio.setName("aleatorio");
		this.bMovAleatorio.setIcon(Iconos.getIcono("aleatorio"));
		this.bMovAleatorio.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				cv.poner();
			}
		});
		JPanel aux = new JPanel();
		aux.add(this.bMovAleatorio);
		this.add(aux, BorderLayout.SOUTH);
	}
	

	/*------------------- PARTIDA TERMINADA ------------------*/
	public void onPartidaTerminada(TableroInmutable tabFin, Ficha ganador) {
		this.panelTablero.dibujarTablero(tabFin);
		this.panelTablero.apaga(tabFin);
		this.bMovAleatorio.setEnabled(false);
		if(ganador == Ficha.BLANCA){
			this.jugadorActual.setText("Ganan las blancas");
		}else if(ganador == Ficha.NEGRA){
			this.jugadorActual.setText("Ganan las negras");
		}else{
			this.jugadorActual.setText("Tablas");
		}	
	}
	
	/*------------------- MOVIMIENTO START ------------------*/
	public void onMovimientoStart(Ficha turno) {
		if(turno == Ficha.BLANCA){
			this.jugadorActual.setText("Juegan blancas");		
		}else{
			this.jugadorActual.setText("Juegan negras");		
		}
	}

	/*------------------- MOVIMIENTO END ------------------*/
	public void onMovimientoEnd(TableroInmutable tab){
		this.panelTablero.dibujarTablero(tab);
	}

	/*---------------------- ON UNDO ----------------------*/
	public void onUndo(TableroInmutable tab, boolean hayMas) {
		this.panelTablero.dibujarTablero(tab);
	}

	/*------------------- RESET PARTIDA ------------------*/
	public void onResetPartida(TableroInmutable tabIni, Ficha turno) {
		this.panelTablero.dibujarTablero(tabIni);
		this.panelTablero.enciende(tabIni);
		onMovimientoStart(turno);
		if(!this.bMovAleatorio.isEnabled()){
			this.bMovAleatorio.setEnabled(true);
		}
	}

	/*------------------- UNDO NOT POSSIBLE ------------------*/
	public void onUndoNotPossible() {}

	/*------------------- CAMBIO TURNO ------------------*/
	public void onCambioTurno(Ficha turno) {
		onMovimientoStart(turno);
	}

	/*------------------- MOVIMIENTO INCORRECTO ------------------*/
	public void onMovimientoIncorrecto(String explicacion) {}

	/*------------------- CAMBIO JUEGO ------------------*/
	public void onCambioJuego(TableroInmutable tab, TipoJuego tipo, Ficha turno) {
		this.remove(this.panelTablero);
		onStart(tab, turno);
		if(!this.bMovAleatorio.isEnabled()){
			this.bMovAleatorio.setEnabled(true);
		}
	}

	/*------------------- MUESTRA AYUDA ------------------*/
	public void onMuestraAyuda() {}

	/*-------------------- ON START --------------------*/
	public void onStart(TableroInmutable tabIn, Ficha turno) {
		this.panelTablero = new PanelTablero(tabIn, this.cv);
		this.add(this.panelTablero, BorderLayout.CENTER);
		this.revalidate();
		if(turno == Ficha.BLANCA){
			this.jugadorActual.setText("Juegan blancas");
		}else{
			this.jugadorActual.setText("Juegan negras");
		}
	}
}
