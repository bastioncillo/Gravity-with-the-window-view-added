package pr3.logica;
///////////////////////////////////////////////
public enum TipoJuego {
	C4, CO, GR;
	
	public String toString() {
		String simbolo = " ";
		if (this == C4) {
			simbolo = "Conecta 4";
		}
		else if (this == CO) {
			simbolo = "Complica";
		}
		else if(this == GR){
			simbolo = "Gravity";
		}
		return simbolo;
	}
///////////////////////////////////////////////
}
